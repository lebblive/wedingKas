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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class OtherActivity extends AppCompatActivity {

    private RecyclerView rvOther;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("other");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Button btnAddO = findViewById(R.id.btnAddO);
        rvOther=findViewById(R.id.rvOther);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    OtherActivity.this,btnBack,"");
            startActivity(intent,transition.toBundle());
        });
        btnAddO.setOnClickListener(v -> {
            AddOtherFragment addOtherFragment = new AddOtherFragment();
            String add="add";
            Bundle bundle=new Bundle();
            bundle.putString("add",add);
            addOtherFragment.setArguments(bundle);
            addOtherFragment.show(getSupportFragmentManager(),"AddOtherFragment");
        });
        viewRecyclerViewOther();
        fetch();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout other_root;
        TextView tvNameO;
        TextView tvPhoneO;
        TextView tvAdressO;
        TextView tvMailO;
        TextView tvNoteO;
        TextView tvPriceO;
        public Button btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            other_root=itemView.findViewById(R.id.other_root);
            tvNameO=itemView.findViewById(R.id.tvNameO);
            tvPhoneO=itemView.findViewById(R.id.etPhoneO);
            tvAdressO=itemView.findViewById(R.id.etAdressO);
            tvMailO=itemView.findViewById(R.id.etMailO);
            tvNoteO=itemView.findViewById(R.id.etNoteO);
            tvPriceO=itemView.findViewById(R.id.etPriceO);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }

        void setTvNameO(String tvNameOs){
            tvNameO.setText(tvNameOs);
        }
        void setTvPhoneO(String tvPhoneOs){
            tvPhoneO.setText(tvPhoneOs);
        }
        void setTvAdressO(String tvAdressOs){
            tvAdressO.setText(tvAdressOs);
        }
        void setTvMailO(String tvMailOs) {
            tvMailO.setText(tvMailOs);
        }
        void setTvNoteO(String tvNoteOs){
            tvNoteO.setText(tvNoteOs);
        }
        @SuppressLint("SetTextI18n")
        void setTvPriceO(String tvPriceOs){
            if (tvPriceOs.length()!=0) {
                tvPriceO.setText(tvPriceOs + getString(R.string.money));
            }
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Other> options=
                new FirebaseRecyclerOptions.Builder<Other>().setQuery(query, snapshot -> new Other(
                        snapshot.child("id").getKey(),
                        Objects.requireNonNull(snapshot.child("name").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("phone").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("adress").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("email").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("note").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("price").getValue()).toString())).build();

        adapter = new FirebaseRecyclerAdapter<Other, OtherActivity.ViewHolder>(options) {

            @NonNull
            @Override
            public OtherActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.other_item,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OtherActivity.ViewHolder viewHolder, int i, @NonNull Other other) {

                viewHolder.setTvNameO(other.getNameO());
                viewHolder.setTvPhoneO(other.getPhoneO());
                viewHolder.setTvAdressO(other.getAdressO());
                viewHolder.setTvMailO(other.getEmailO());
                viewHolder.setTvNoteO(other.getNoteO());
                viewHolder.setTvPriceO(other.getPriceO());


                viewHolder.btnDelete.setOnClickListener(v -> {
                    String oid= other.getNameO();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("other").child(oid);
                    databaseReference.removeValue();
                });
                //si je click dessu
                viewHolder.other_root.setOnClickListener(v -> {

                    String oid= other.getNameO();

                    AddOtherFragment addOtherFragment=new AddOtherFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("oid",oid);
                    addOtherFragment.setArguments(bundle);
                    addOtherFragment.show(getSupportFragmentManager(),"AddOtherFragment");

                });
            }
        };
        rvOther.setAdapter(adapter);
    }

    private void viewRecyclerViewOther() {
        LandingAnimator animator=new LandingAnimator(new OvershootInterpolator(1f));
        rvOther.setItemAnimator(animator);
        Objects.requireNonNull(rvOther.getItemAnimator()).setAddDuration(1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvOther.setLayoutManager(linearLayoutManager);
        rvOther.setHasFixedSize(true);
    }


    /*
    Si vous exécutez l'application, aucune donnée
    ne sera affichée car nous devons ajouter les écouteurs ci-dessous.
     */

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
