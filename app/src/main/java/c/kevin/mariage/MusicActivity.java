package c.kevin.mariage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MusicActivity extends AppCompatActivity  {

    private Button btnAddM;
    private RecyclerView rvMusic;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("music");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        btnAddM =findViewById(R.id.btnAddM);
        rvMusic=findViewById(R.id.rvMusic);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        btnAddM.setOnClickListener(v -> {
            AddMusicFragment addMusicFragment=new AddMusicFragment();
            String add="add";
            Bundle bundle=new Bundle();
            bundle.putString("add",add);
            addMusicFragment.setArguments(bundle);
            addMusicFragment.show(getSupportFragmentManager(),"AddMusicFragment");
        });
        viewRecyclerViewMusic();
        fetch();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout music_root;
        public TextView tvNameM;
        public TextView tvPhoneM;
        public TextView tvAdressM;
        public TextView tvMailM;
        public TextView tvNoteM;
        public TextView tvPriceM;
        public Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            music_root=itemView.findViewById(R.id.music_root);
            tvNameM=itemView.findViewById(R.id.tvNameM);
            tvPhoneM=itemView.findViewById(R.id.etPhoneM);
            tvAdressM=itemView.findViewById(R.id.etAdressM);
            tvMailM=itemView.findViewById(R.id.etMailM);
            tvNoteM=itemView.findViewById(R.id.etNoteM);
            tvPriceM=itemView.findViewById(R.id.etPriceM);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }

        public void setTvNameM(String tvNameMs){
            tvNameM.setText(tvNameMs);
        }
        public void setTvPhoneM(String tvPhoneMs){
            tvPhoneM.setText(tvPhoneMs);
        }
        public void setTvAdressM(String tvAdressMs){
            tvAdressM.setText(tvAdressMs);
        }
        public void setTvMailM(String tvMailMs) {
            tvMailM.setText(tvMailMs);
        }
        public void setTvNoteM(String tvNoteMs){
            tvNoteM.setText(tvNoteMs);
        }
        public void setTvPriceM(String tvPriceMs){
            if (tvPriceMs.length()!=0) {
                tvPriceM.setText(tvPriceMs + " ₪");
            }
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Music> options=
                new FirebaseRecyclerOptions.Builder<Music>().setQuery(query, snapshot -> new Music(
                        snapshot.child("id").getKey().toString(),
                        snapshot.child("name").getValue().toString(),
                        snapshot.child("phone").getValue().toString(),
                        snapshot.child("adress").getValue().toString(),
                        snapshot.child("email").getValue().toString(),
                        snapshot.child("note").getValue().toString(),
                        snapshot.child("price").getValue().toString())).build();

        adapter = new FirebaseRecyclerAdapter<Music, MusicActivity.ViewHolder>(options) {

            @NonNull
            @Override
            public MusicActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.music_item,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MusicActivity.ViewHolder viewHolder, int i, @NonNull Music music) {

                viewHolder.setTvNameM(music.getNameM());
                viewHolder.setTvPhoneM(music.getPhoneM());
                viewHolder.setTvAdressM(music.getAdressM());
                viewHolder.setTvMailM(music.getEmailM());
                viewHolder.setTvNoteM(music.getNoteM());
                viewHolder.setTvPriceM(music.getPriceM());


                viewHolder.btnDelete.setOnClickListener(v -> {
                    String mid=music.getNameM().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("music").child(mid);
                    databaseReference.removeValue();
                });
                //si je click dessu
                viewHolder.music_root.setOnClickListener(v -> {

                    String mid=music.getNameM().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("music").child(mid);

                    AddMusicFragment addMusicFragment=new AddMusicFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("mid",mid);
                    addMusicFragment.setArguments(bundle);
                    addMusicFragment.show(getSupportFragmentManager(),"AddMusicFragment");
                });
            }
        };
        rvMusic.setAdapter(adapter);
    }

    private void viewRecyclerViewMusic() {
        linearLayoutManager=new LinearLayoutManager(this);
        rvMusic.setLayoutManager(linearLayoutManager);
        rvMusic.setHasFixedSize(true);
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
