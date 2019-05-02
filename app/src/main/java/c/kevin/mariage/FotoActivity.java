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

public class FotoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnAddF;
    private RecyclerView rvFoto;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("foto");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_foto);

        btnAddF =findViewById(R.id.btnAddF);
        rvFoto=findViewById(R.id.rvFoto);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
        btnAddF.setOnClickListener(v -> {
            AddFotoFragment addFotoFragment=new AddFotoFragment();
            addFotoFragment.show(getSupportFragmentManager(),"AddFotoFragment");
        });
        viewRecyclerViewFoto();
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
            Intent intent = new Intent(getApplicationContext(),MusicActivity.class);
            startActivity(intent);
        } else if (id == R.id.other) {
            Intent intent = new Intent(getApplicationContext(),OtherActivity.class);
            startActivity(intent);
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

    /*
    Nous avons maintenant besoin d’un ViewHolder pour gérer les vues dans chaque position.
     */

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout foto_root;
        public TextView tvNameF;
        public TextView tvPhoneF;
        public TextView tvAdressF;
        public TextView tvMailF;
        public TextView tvNoteF;
        public TextView tvPriceF;
        public Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto_root=itemView.findViewById(R.id.foto_root);
            tvNameF=itemView.findViewById(R.id.tvNameF);
            tvPhoneF=itemView.findViewById(R.id.etPhoneF);
            tvAdressF=itemView.findViewById(R.id.etAdressF);
            tvMailF=itemView.findViewById(R.id.etMailF);
            tvNoteF=itemView.findViewById(R.id.etNoteF);
            tvPriceF=itemView.findViewById(R.id.etPriceF);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }

        public void setTvNameF(String tvNameFs){
                tvNameF.setText(tvNameFs);
            }
        public void setTvPhoneF(String tvPhoneFs){
                tvPhoneF.setText(tvPhoneFs);
        }
        public void setTvAdressF(String tvAdressFs){
            tvAdressF.setText(tvAdressFs);
        }
        public void setTvMailF(String tvMailFs) {
            tvMailF.setText(tvMailFs);
        }
        public void setTvNoteF(String tvNoteFs){
            tvNoteF.setText(tvNoteFs);
        }
        public void setTvPriceF(String tvPriceFs){
            if (tvPriceFs.length()!=0) {
                tvPriceF.setText(tvPriceFs + " ₪");
            }
        }
    }

    /*
    Nous allons maintenant récupérer les données de la base de données.
     */
    private void fetch() {

        FirebaseRecyclerOptions<Foto> options=
                new FirebaseRecyclerOptions.Builder<Foto>().setQuery(query, snapshot -> new Foto(
                snapshot.child("id").getKey().toString(),
                snapshot.child("name").getValue().toString(),
                snapshot.child("phone").getValue().toString(),
                snapshot.child("adress").getValue().toString(),
                snapshot.child("email").getValue().toString(),
                snapshot.child("note").getValue().toString(),
                snapshot.child("price").getValue().toString())).build();

        adapter = new FirebaseRecyclerAdapter<Foto, FotoActivity.ViewHolder>(options) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.foto_item,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Foto foto) {

                viewHolder.setTvNameF(foto.getNameF());
                viewHolder.setTvPhoneF(foto.getPhoneF());
                viewHolder.setTvAdressF(foto.getAdressF());
                viewHolder.setTvMailF(foto.getEmailF());
                viewHolder.setTvNoteF(foto.getNoteF());
                viewHolder.setTvPriceF(foto.getPriceF());


                viewHolder.btnDelete.setOnClickListener(v -> {
                    String fid=foto.getNameF().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("foto").child(fid);
                    databaseReference.removeValue();
                });

                //si je click dessu
                viewHolder.foto_root.setOnClickListener(v -> {

                    String fid=foto.getNameF().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("foto").child(fid);

                    AddFotoFragment addFotoFragment=new AddFotoFragment();
                    addFotoFragment.show(getSupportFragmentManager(),"AddFotoFragment");

                    //stam pour le kif dune methode en plus a suprimer
//                    Random r=new Random();
//                    int c= Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256));
//
//                    viewHolder.foto_root.setBackgroundColor(c);

//                    Toast.makeText(FotoActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                });
            }
        };
        rvFoto.setAdapter(adapter);
        }
    private void viewRecyclerViewFoto() {
        linearLayoutManager=new LinearLayoutManager(this);
        rvFoto.setLayoutManager(linearLayoutManager);
        rvFoto.setHasFixedSize(true);
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
