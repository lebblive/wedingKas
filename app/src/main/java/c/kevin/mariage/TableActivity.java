package c.kevin.mariage;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class TableActivity extends AppCompatActivity {

    private RecyclerView rvTable;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Button btnAddT = findViewById(R.id.btnNew);
        rvTable=findViewById(R.id.rvTable);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    TableActivity.this,btnBack,"");
            startActivity(intent,transition.toBundle());
        });
        btnAddT.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),ChairActivity.class);
            startActivity(intent);
        });
        viewRecyclerViewTable();
        fetch();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout table_root;
        TextView tvNameT;
        TextView tvPlaceT;
        TextView tvNumberPlace;
        public Button btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            table_root=itemView.findViewById(R.id.table_root);
            tvNameT=itemView.findViewById(R.id.tvNameT);
            tvPlaceT=itemView.findViewById(R.id.tvPlaceT);
            tvNumberPlace=itemView.findViewById(R.id.tvNumberPlace);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }

        void setTvNameT(String tvNameTs){
            tvNameT.setText(tvNameTs);
        }
        @SuppressLint("SetTextI18n")
        void setTvPlaceT(String tvPlaceTs){
            tvPlaceT.setText(tvPlaceTs+ getString(R.string.sit));
        }
    }

    //je recupere mes donnes
    private void fetch() {
        Query query= FirebaseDatabase.getInstance()
                .getReference().child("users").child(uid).child("table");
        FirebaseRecyclerOptions<Table> options=
                new FirebaseRecyclerOptions.Builder<Table>().setQuery(query, snapshot -> new Table(
                        snapshot.child("id").getKey(),
                        (Objects.requireNonNull(snapshot.child("name").getValue())).toString(),
                        (Objects.requireNonNull(snapshot.child("place").getValue())).toString()
                )).build();

        adapter = new FirebaseRecyclerAdapter<Table, TableActivity.ViewHolder>(options) {

            @NonNull
            @Override
            public TableActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.table_item,parent,false);
                return new TableActivity.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TableActivity.ViewHolder viewHolder, int i, @NonNull Table table) {

                viewHolder.setTvNameT(table.getNameT());
                viewHolder.setTvPlaceT(table.getPlaceT());

                //sur la poubelle
                viewHolder.btnDelete.setOnClickListener(v -> {
                    String tid= table.getNameT();

                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("table").child(tid);

                    DatabaseReference dbChair = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid)
                    .child("chair");

                    // je veut recupere le id des inviter pour pouvoir rentrer dedans

            dbChair.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        String idChair = Objects.requireNonNull(snapshot.child("tableName").getValue()).toString();
                        String fname = Objects.requireNonNull(snapshot.child("firstName").getValue()).toString();

                        /*
                        si japuie sur la poubelle sa suprime la table et
                        tout les inviter qui son dedans ->
                        ceux dont les inbiter on pour tablename le meme nom que le nom de la table en question.
                         */
                        if (idChair.equals(tid)){
                            DatabaseReference dbChair2 = FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(uid)
                                    .child("chair").child(fname);
                            dbChair2.removeValue();
                            databaseReference.removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
                });

                //si je click
                viewHolder.table_root.setOnClickListener(v -> {
                    String tid= table.getNameT();

                    Intent intent = new Intent(getApplicationContext(),ChairActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("tid",tid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
            }
        };
        rvTable.setAdapter(adapter);
    }


    private void viewRecyclerViewTable() {
        LandingAnimator animator=new LandingAnimator(new OvershootInterpolator(1f));
        rvTable.setItemAnimator(animator);
        Objects.requireNonNull(rvTable.getItemAnimator()).setAddDuration(1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTable.setLayoutManager(linearLayoutManager);
        rvTable.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
