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

public class OtherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnAddO;
    private RecyclerView rvOther;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private Button btnBack;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("other");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        btnAddO =findViewById(R.id.btnAddO);
        rvOther=findViewById(R.id.rvOther);
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout other_root;
        public TextView tvNameO;
        public TextView tvPhoneO;
        public TextView tvAdressO;
        public TextView tvMailO;
        public TextView tvNoteO;
        public TextView tvPriceO;
        public Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
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

        public void setTvNameO(String tvNameOs){
            tvNameO.setText(tvNameOs);
        }
        public void setTvPhoneO(String tvPhoneOs){
            tvPhoneO.setText(tvPhoneOs);
        }
        public void setTvAdressO(String tvAdressOs){
            tvAdressO.setText(tvAdressOs);
        }
        public void setTvMailO(String tvMailOs) {
            tvMailO.setText(tvMailOs);
        }
        public void setTvNoteO(String tvNoteOs){
            tvNoteO.setText(tvNoteOs);
        }
        public void setTvPriceO(String tvPriceOs){
            if (tvPriceOs.length()!=0) {
                tvPriceO.setText(tvPriceOs + " ₪");
            }
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Other> options=
                new FirebaseRecyclerOptions.Builder<Other>().setQuery(query, snapshot -> new Other(
                        snapshot.child("id").getKey().toString(),
                        snapshot.child("name").getValue().toString(),
                        snapshot.child("phone").getValue().toString(),
                        snapshot.child("adress").getValue().toString(),
                        snapshot.child("email").getValue().toString(),
                        snapshot.child("note").getValue().toString(),
                        snapshot.child("price").getValue().toString())).build();

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
                    String oid=other.getNameO().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("other").child(oid);
                    databaseReference.removeValue();
                });
                //si je click dessu
                viewHolder.other_root.setOnClickListener(v -> {

                    String oid=other.getNameO().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("other").child(oid);

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
        linearLayoutManager=new LinearLayoutManager(this);
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
