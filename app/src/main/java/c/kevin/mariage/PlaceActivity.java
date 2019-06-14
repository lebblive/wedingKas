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

public class PlaceActivity extends AppCompatActivity  {

    private RecyclerView rvPlace;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("place");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);


        Button btnAddP = findViewById(R.id.btnAddaT);
        rvPlace=findViewById(R.id.rvPlace);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    PlaceActivity.this,btnBack,"");
            startActivity(intent,transition.toBundle());
        });
        btnAddP.setOnClickListener(v -> {
            AddPlaceFragment addPlaceFragment=new AddPlaceFragment();
            String add="add";
            Bundle bundle=new Bundle();
            bundle.putString("add",add);
            addPlaceFragment.setArguments(bundle);
            addPlaceFragment.show(getSupportFragmentManager(),"AddPlaceFragment");
        });
        viewRecyclerViewPlace();
        fetch();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout place_root;
        TextView tvNameP;
        TextView tvPhoneP;
        TextView tvAdressP;
        TextView tvMailP;
        TextView tvNoteP;
        TextView tvPriceP;
        public Button btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            place_root=itemView.findViewById(R.id.place_root);
            tvNameP=itemView.findViewById(R.id.tvNameP);
            tvPhoneP=itemView.findViewById(R.id.etPhoneP);
            tvAdressP=itemView.findViewById(R.id.etAdressP);
            tvMailP=itemView.findViewById(R.id.etMailP);
            tvNoteP=itemView.findViewById(R.id.etNoteP);
            tvPriceP=itemView.findViewById(R.id.etPriceP);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }

        void setTvNameP(String tvNamePs){
            tvNameP.setText(tvNamePs);
        }
        void setTvPhoneP(String tvPhonePs){
            tvPhoneP.setText(tvPhonePs);
        }
        void setTvAdressP(String tvAdressPs){
            tvAdressP.setText(tvAdressPs);
        }
        void setTvMailP(String tvMailPs) {
            tvMailP.setText(tvMailPs);
        }
        void setTvNoteP(String tvNotePs){
            tvNoteP.setText(tvNotePs);
        }
        @SuppressLint("SetTextI18n")
        void setTvPriceP(String tvPricePs){
            if (tvPricePs.length()!=0) {
                tvPriceP.setText(tvPricePs + getString(R.string.money));
            }
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Place> options=
                new FirebaseRecyclerOptions.Builder<Place>().setQuery(query, snapshot -> new Place(
                        snapshot.child("id").getKey(),
                        Objects.requireNonNull(snapshot.child("name").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("phone").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("adress").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("email").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("note").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("price").getValue()).toString())).build();

        adapter = new FirebaseRecyclerAdapter<Place, PlaceActivity.ViewHolder>(options) {

            @NonNull
            @Override
            public PlaceActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.place_item,parent,false);
                return new PlaceActivity.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PlaceActivity.ViewHolder viewHolder, int i, @NonNull Place place) {

                viewHolder.setTvNameP(place.getNameP());
                viewHolder.setTvPhoneP(place.getPhoneP());
                viewHolder.setTvAdressP(place.getAdressP());
                viewHolder.setTvMailP(place.getEmailP());
                viewHolder.setTvNoteP(place.getNoteP());
                viewHolder.setTvPriceP(place.getPriceP());


                viewHolder.btnDelete.setOnClickListener(v -> {
                    String pid= place.getNameP();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("place").child(pid);
                    databaseReference.removeValue();
                });

                viewHolder.place_root.setOnClickListener(v -> {
                    String pid= place.getNameP();
                    AddPlaceFragment addPlaceFragment=new AddPlaceFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("pid",pid);
                    addPlaceFragment.setArguments(bundle);
                    addPlaceFragment.show(getSupportFragmentManager(),"AddPlaceFragment");
                });

            }
        };
        rvPlace.setAdapter(adapter);
    }
    private void viewRecyclerViewPlace() {
        LandingAnimator animator=new LandingAnimator(new OvershootInterpolator(1f));
        rvPlace.setItemAnimator(animator);
        Objects.requireNonNull(rvPlace.getItemAnimator()).setAddDuration(1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPlace.setLayoutManager(linearLayoutManager);
        rvPlace.setHasFixedSize(true);
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
