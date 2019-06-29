package c.kevin.mariage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.LandingAnimator;



public class ChairActivity extends AppCompatActivity {
    private RecyclerView rvChair;
    EditText etTableName;
    EditText etNumberSit;
    TextView tvEmptySit;
    Button btnGenerate;
    ImageView btnSaveChair;
    Button btnNew;

    ImageView ivQuestAdd;
    ImageView ivQuestContact;
    ImageView btnAddContact;

    private FirebaseRecyclerAdapter<Chair, ViewHolder> adapter;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chair);
        layout();
        getInfoChair();

        //enregistre la table et envoie les donner a table activity
        btnSaveChair.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),TableActivity.class);
            setInfoChair();
            addBundleAdd();
            startActivity(intent);
        });

        // genere des table
        btnGenerate.setOnClickListener(v -> {
            String numberSit=etNumberSit.getText().toString();
            if (numberSit.isEmpty()){
                etNumberSit.setError("you need write a number");
            }else {
                seeAllChair();
            }
        });

        btnAddContact.setOnClickListener(v -> {
            String tableName = etTableName.getText().toString();
            if (!tableName.isEmpty()){
                AddContactExist_Fragment addContactExist_fragment = new AddContactExist_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("tableName", tableName);
                addContactExist_fragment.setArguments(bundle);
                addContactExist_fragment.show(getSupportFragmentManager(),"AddContactExist_Fragment");

            }else {
                etTableName.setError("you need write a name on table first");
            }
        });

        ivQuestAdd.setOnClickListener(v -> {
            String addNewSit = getString(R.string.addNewSit);
            Toast.makeText(this, addNewSit, Toast.LENGTH_SHORT).show();
        });
        ivQuestContact.setOnClickListener(v -> {
            String addForContactList = getString(R.string.addForContactList);
            Toast.makeText(this, addForContactList, Toast.LENGTH_SHORT).show();
        });

        viewRecyclerViewChair();
        fetch();
    }

    // envoie des donner a AddChairFragment
    private void addBundleAdd() {
        AddChairFragment addChairFragment = new AddChairFragment();
        String add = "add";
        Bundle bundle = new Bundle();
        bundle.putString("add", add);
        addChairFragment.setArguments(bundle);
        addChairFragment.show(getSupportFragmentManager(), "addChairFragment");
    }

    // enregistre les doner de la table
    private void setInfoChair() {
        String idTable = etTableName.getText().toString();
        if (idTable.length() != 0) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("table").child(idTable);
            Map<String, Object> mapChair = new HashMap<>();

            mapChair.put("id", Objects.requireNonNull(databaseReference.getKey()));
            mapChair.put("name", etTableName.getText().toString());
            mapChair.put("place", etNumberSit.getText().toString());
            databaseReference.setValue(mapChair);
        } else {
            etTableName.setError(getString(R.string.needWriteName));
        }
    }

    // recupere les donne de la table
    private void getInfoChair() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tid = bundle.getString("tid");

            assert tid != null;
            DatabaseReference dbTable = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid)
                    .child("table").child(tid);

            dbTable.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Object dsName = dataSnapshot.child("name").getValue();
                    Object dsPlace = dataSnapshot.child("place").getValue();

                    if (dsName != null) {
                        String nameT = dsName.toString();
                        etTableName.setText(nameT);
                    }
                    if (dsPlace != null) {
                        String placeT = dsPlace.toString();
                        etNumberSit.setText(placeT);
                    }

                    //recupere la base de donne de chair avec ses infos
//                    dbChair.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                String familyName = Objects.requireNonNull(snapshot.child("familyName").getValue()).toString();
//                                String firstName = Objects.requireNonNull(snapshot.child("firstName").getValue()).toString();
//                                String tableName = Objects.requireNonNull(snapshot.child("tableName").getValue()).toString();
//
////                                afiche que ceux qui son de la table sinon afiche les vide
//                                assert dsName != null;
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {}
//                    });

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
    }

    // anime les la view des chaise
    private void viewRecyclerViewChair() {
        LandingAnimator animator=new LandingAnimator(new OvershootInterpolator(1f));
        rvChair.setItemAnimator(animator);
        Objects.requireNonNull(rvChair.getItemAnimator()).setAddDuration(1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvChair.setLayoutManager(linearLayoutManager);
        rvChair.setHasFixedSize(true);
    }


    // ce que je voi dans le rv -> les chaises
    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout chair_root;

        TextView tvFamilyNameChair;
        TextView tvFirstNameChair;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            chair_root=itemView.findViewById(R.id.chair_root);
            tvFamilyNameChair=itemView.findViewById(R.id.tvFamilyNameChair);
            tvFirstNameChair=itemView.findViewById(R.id.tvFirstNameChair);
        }
        void setTvFirstNameChair(String tvFirstNameChairs){
            tvFirstNameChair.setText(tvFirstNameChairs);
        }
        void setTvFamilyNameChair(String tvFamilyNameChairs){
            tvFamilyNameChair.setText(tvFamilyNameChairs);
        }
    }

    //je recupere mes donnes
    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference().child("users").child(uid).child("chair");
        FirebaseRecyclerOptions<Chair> options =
                new FirebaseRecyclerOptions.Builder<Chair>().setQuery(query, snapshot -> new Chair(
                        snapshot.child("id").getKey(),
                        (Objects.requireNonNull(snapshot.child("firstName").getValue())).toString(),
                        (Objects.requireNonNull(snapshot.child("familyName").getValue())).toString(),
                        (Objects.requireNonNull(snapshot.child("tableName").getValue())).toString()
                )).build();
        //montre moi les chaise
        adapter = new FirebaseRecyclerAdapter<Chair, ChairActivity.ViewHolder>(options) {

            @NonNull
            @Override
            // cree moi la facon de les voir
            public ChairActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chair_item, parent, false);
                return new ChairActivity.ViewHolder(view);
            }

            @Override

            // ecrit sur chacun d'entre eux
            protected void onBindViewHolder(@NonNull ChairActivity.ViewHolder viewHolder, int i, @NonNull Chair chair) {

                // afiche moi que ceux qui font partie de cete table.
                String idTable = etTableName.getText().toString();
                String chairInTable = chair.getTableName();

                if (idTable.equals(chairInTable)) {
                    viewHolder.setTvFirstNameChair(chair.getFirstNameChair());
                    viewHolder.setTvFamilyNameChair(chair.getFamilyNameChair());
                } else {
                    viewHolder.chair_root.setVisibility(View.GONE);
                    viewHolder.itemView.setVisibility(View.GONE);
                }

//                if (viewHolder.chair_root.getVisibility() != View.GONE) {
////                        System.out.println("test 81 ");
//                    // la condition est bonne
                /*
                je dois fair une serie de test:
                je dois rajouter depuis la liste contact
                ou virer les bouton inutile
                 */
//
//                }

                btnNew.setOnClickListener(v -> {
                    AddChairFragment addChairFragment = new AddChairFragment();
                    AddContactExist_Fragment addContactExist_fragment = new AddContactExist_Fragment();

                    Bundle bundle = new Bundle();
                    String tableid = etTableName.getText().toString();
                    String chairid = viewHolder.tvFirstNameChair.getText().toString();
                    bundle.putString("chairid", chairid);
                    bundle.putString("tableid", tableid);
                    addContactExist_fragment.setArguments(bundle);
                    addChairFragment.setArguments(bundle);
                    addChairFragment.show(getSupportFragmentManager(), "addChairFragment");
                });


                //si je click sur l'un d'entre eux
                viewHolder.chair_root.setOnClickListener(v -> {
                    //ouvre addChair et envoi lui le id
                    AddChairFragment addChairFragment = new AddChairFragment();
                    AddContactExist_Fragment addContactExist_fragment = new AddContactExist_Fragment();

                    Bundle bundle = new Bundle();
                    String tableid = etTableName.getText().toString();
                    String chairid = viewHolder.tvFirstNameChair.getText().toString();
                    bundle.putString("chairid", chairid);
                    bundle.putString("tableid", tableid);
                    addChairFragment.setArguments(bundle);
                    addContactExist_fragment.setArguments(bundle);
                    addChairFragment.show(getSupportFragmentManager(), "addChairFragment");
                });
            }

        };
        // apres que tu es fait tout sa afiche les moi.
        rvChair.setAdapter(adapter);
    }

    // afiche les chaises uniquement pour la premiere fois
    private void seeAllChair() {
        String numberSit=etNumberSit.getText().toString();

        RecyclerView.Adapter<ViewHolder> firstAdapter = new RecyclerView.Adapter<ViewHolder>() {

            @NonNull
            @Override
            //cree la vue des chaise
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chair_item, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

                // si je click dessu
                holder.chair_root.setOnClickListener(v -> {

                    // envoie les donner au fragment et ouvre le
                    AddChairFragment addChairFragment =new AddChairFragment();
                    Bundle bundle = new Bundle();
                    String tableid=etTableName.getText().toString();
                    String chairid = holder.tvFirstNameChair.getText().toString();
                    bundle.putString("chairid",chairid);
                    bundle.putString("tableid",tableid);

                    addChairFragment.setArguments(bundle);
                    addChairFragment.show(getSupportFragmentManager(),"addChairFragment");
                });

                //si je click lontem
                holder.chair_root.setOnLongClickListener(v -> {
                    holder.chair_root.setBackgroundColor(Color.YELLOW);
                    return true;
                });
            }
            @Override
            // jafiche le nombre depuis le nombre de chaise
            public int getItemCount() {
                return Integer.parseInt(numberSit);
            }
        };
        //apres tout sa afiche le
        rvChair.setAdapter(firstAdapter);
    }

    private void layout() {
        rvChair=findViewById(R.id.rvChair);
        etTableName=findViewById(R.id.etTableName);
        etNumberSit=findViewById(R.id.etNumberSit);
        tvEmptySit=findViewById(R.id.tvEmptySit);
        btnGenerate=findViewById(R.id.btnGenerate);
        btnSaveChair=findViewById(R.id.btnSaveChair);
        btnNew=findViewById(R.id.btnNew);
        btnAddContact=findViewById(R.id.btnAddContact);
        ivQuestAdd=findViewById(R.id.ivQuestAdd);
        ivQuestContact=findViewById(R.id.ivQuestContact);
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
