package c.kevin.mariage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PlaceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnAddP;
    private RecyclerView rvPlace;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("place");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        btnAddP =findViewById(R.id.btnAddP);
        rvPlace=findViewById(R.id.rvPlace);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        btnAddP.setOnClickListener(v -> {
            AddPlaceFragment addPlaceFragment=new AddPlaceFragment();
            addPlaceFragment.show(getSupportFragmentManager(),"AddPlaceFragment");
        });
        viewRecyclerViewPlace();
        fetch();
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.photo) {
            Intent intent = new Intent(getApplicationContext(),FotoActivity.class);
            startActivity(intent);
        } else if (id == R.id.salle) {
            Intent intent = new Intent(getApplicationContext(),PlaceActivity.class);
            startActivity(intent);

        } else if (id == R.id.music) {

        } else if (id == R.id.deco) {

        } else if (id == R.id.invite) {

        } else if (id == R.id.table) {

        } else if (id == R.id.list) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout place_root;
        public TextView tvNameP;
        public TextView tvPhoneP;
        public TextView tvAdressP;
        public TextView tvMailP;
        public TextView tvNoteP;
        public TextView tvPriceP;
        public Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
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

        public void setTvNameP(String tvNamePs){
            tvNameP.setText(tvNamePs);
        }
        public void setTvPhoneP(String tvPhonePs){
            tvPhoneP.setText(tvPhonePs);
        }
        public void setTvAdressP(String tvAdressPs){
            tvAdressP.setText(tvAdressPs);
        }
        public void setTvMailP(String tvMailPs) {
            tvMailP.setText(tvMailPs);
        }
        public void setTvNoteP(String tvNotePs){
            tvNoteP.setText(tvNotePs);
        }
        public void setTvPriceP(String tvPricePs){
            if (tvPricePs.length()!=0) {
                tvPriceP.setText(tvPricePs + " ₪");
            }
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Place> options=
                new FirebaseRecyclerOptions.Builder<Place>().setQuery(query, snapshot -> new Place(
                        snapshot.child("id").getKey().toString(),
                        snapshot.child("name").getValue().toString(),
                        snapshot.child("phone").getValue().toString(),
                        snapshot.child("adress").getValue().toString(),
                        snapshot.child("email").getValue().toString(),
                        snapshot.child("note").getValue().toString(),
                        snapshot.child("price").getValue().toString())).build();

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
                    String pid=place.getNameP().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("place").child(pid);
                    databaseReference.removeValue();
                });

            }
        };
        rvPlace.setAdapter(adapter);
    }
    private void viewRecyclerViewPlace() {
        linearLayoutManager=new LinearLayoutManager(this);
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
