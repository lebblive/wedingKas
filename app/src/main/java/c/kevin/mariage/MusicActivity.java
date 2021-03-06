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

public class MusicActivity extends AppCompatActivity  {

    private RecyclerView rvMusic;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("music");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Button btnAddM = findViewById(R.id.btnAddM);
        rvMusic=findViewById(R.id.rvMusic);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    MusicActivity.this,btnBack,"");
            startActivity(intent,transition.toBundle());

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

        ConstraintLayout music_root;
        TextView tvNameM;
        TextView tvPhoneM;
        TextView tvAdressM;
        TextView tvMailM;
        TextView tvNoteM;
        TextView tvPriceM;
        public Button btnDelete;

        ViewHolder(@NonNull View itemView) {
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

        void setTvNameM(String tvNameMs){
            tvNameM.setText(tvNameMs);
        }
        void setTvPhoneM(String tvPhoneMs){
            tvPhoneM.setText(tvPhoneMs);
        }
        void setTvAdressM(String tvAdressMs){
            tvAdressM.setText(tvAdressMs);
        }
        void setTvMailM(String tvMailMs) {
            tvMailM.setText(tvMailMs);
        }
        void setTvNoteM(String tvNoteMs){
            tvNoteM.setText(tvNoteMs);
        }
        @SuppressLint("SetTextI18n")
        void setTvPriceM(String tvPriceMs){
            if (tvPriceMs.length()!=0) {
                tvPriceM.setText(tvPriceMs + getString(R.string.money));
            }
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Music> options=
                new FirebaseRecyclerOptions.Builder<Music>().setQuery(query, snapshot -> new Music(
                        snapshot.child("id").getKey(),
                        Objects.requireNonNull(snapshot.child("name").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("phone").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("adress").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("email").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("note").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("price").getValue()).toString())).build();

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
                    String mid= music.getNameM();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("music").child(mid);
                    databaseReference.removeValue();
                });
                //si je click dessu
                viewHolder.music_root.setOnClickListener(v -> {

                    String mid= music.getNameM();

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
        LandingAnimator animator=new LandingAnimator(new OvershootInterpolator(1f));
        rvMusic.setItemAnimator(animator);
        Objects.requireNonNull(rvMusic.getItemAnimator()).setAddDuration(1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
