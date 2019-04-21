package c.kevin.mariage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class SalleActivity extends AppCompatActivity {


    //properties

    private EditText editText, etd;
    private Button button;


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);

        layout();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoSalle();
            }
        });

        viewRecycleView();
        fetch();
    }

    private void viewRecycleView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void setInfoSalle() {

        /*
        dans users dans post
        -> post qui est dans users

        dans users dans uid dans post
        ->post qui est dans uid dans user



         */
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("posts").push();
        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("posts").push();
        Map<String, Object> map = new HashMap<>();
        map.put("id", databaseReference.getKey());
        map.put("title", editText.getText().toString());
        map.put("desc", etd.getText().toString());
        databaseReference.setValue(map);
    }

    private void layout() {
        editText = findViewById(R.id.et);
        etd = findViewById(R.id.etd);
        button = findViewById(R.id.btn);
        recyclerView = findViewById(R.id.list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtTitle;
        public TextView txtDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtTitle = itemView.findViewById(R.id.list_title);
            txtDesc = itemView.findViewById(R.id.list_desc);
        }

        public void setTxtTitle(String string) {
            txtTitle.setText(string);
        }

        public void setTxtDesc(String string) {
            txtDesc.setText(string);
        }
    }

    //fetch == va chercher
    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference().child("users").child(uid).child("posts");

        FirebaseRecyclerOptions<Salle> options =
                new FirebaseRecyclerOptions.Builder<Salle>()
                        .setQuery(query, new SnapshotParser<Salle>() {
                            @NonNull
                            @Override
                            public Salle parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Salle(snapshot.child("id").getValue().toString(),
                                        snapshot.child("title").getValue().toString(),
                                        snapshot.child("desc").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Salle, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Salle salle) {
                //ecrit
                holder.setTxtTitle(salle.getTitle());
                holder.setTxtDesc(salle.getDesc());

                // si je click dessu

                holder.root.setOnClickListener(v -> {

                });
            }

        };
        recyclerView.setAdapter(adapter);
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
