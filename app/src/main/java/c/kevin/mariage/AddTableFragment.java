package c.kevin.mariage;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.LandingAnimator;


public class AddTableFragment extends AppCompatDialogFragment {

    private EditText etTableName;
    private EditText etNumberPlace;
    private EditText etGuestFirstNameInTable;
    private EditText etGuestNameInTable;
    private Button btnAddAT;
    private RecyclerView rvGuestNameInThisTable;
    private Button btnSaveAT;
    private FirebaseRecyclerAdapter adapter;

    private String uid = Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

    public AddTableFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_table,container,false);
        int width = (int) (getResources().getDisplayMetrics().widthPixels*0.9);
        int height = (int) (getResources().getDisplayMetrics().heightPixels*0.8);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
        v.setLayoutParams(lp);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout(view);
        assert this.getArguments() !=null;
        if (Objects.equals(this.getArguments().getString("autre"),this.getArguments().getString("add"))){
            getInfo();
        }

        btnAddAT.setOnClickListener(v -> {
            // ramener des contact de GuestActivity
//            Toast.makeText(getContext(), "MAZAL TOV", Toast.LENGTH_SHORT).show();
            setInfoGuestNameTable();
        });
        viewRecyclerViewTable();
        fetch();
        btnSaveAT.setOnClickListener(v -> setInfoTable());
    }

    private void setInfoGuestNameTable() {
        String gfntId=etGuestFirstNameInTable.getText().toString();
//        String gfntId=etTableName.getText().toString();
        if (gfntId.length()!=0){
            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                    .child("guest_name_table")
                    .child(gfntId);
            Map<String,Object> mapGuestNameTable=new HashMap<>();
            mapGuestNameTable.put("id",etTableName.getText().toString());
//            mapGuestNameTable.put("id",etGuestFirstNameInTable.getText().toString());
            mapGuestNameTable.put("guestFirstName",etGuestFirstNameInTable.getText().toString());
            mapGuestNameTable.put("guestFamilyName",etGuestNameInTable.getText().toString());

            databaseReference.setValue(mapGuestNameTable);
        }else {
            etGuestFirstNameInTable.setError("you need write name");
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cvGuestName;
        ConstraintLayout guestName_root;
        TextView tvGuestFirstName;
        TextView tvGuestFamilyName;
        Button btnDelete;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvGuestName=itemView.findViewById(R.id.cvGuestName);
            guestName_root=itemView.findViewById(R.id.guestName_root);
            tvGuestFirstName=itemView.findViewById(R.id.tvGuestFirstName);
            tvGuestFamilyName=itemView.findViewById(R.id.tvGuestFamilyName);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
        void setTvGuestFirstName(String tvGuestFirstNames){tvGuestFirstName.setText(tvGuestFirstNames);}
        void setTvGuestFamilyName(String tvGuestFamilyNames){tvGuestFamilyName.setText(tvGuestFamilyNames);}
    }

    //je recupere mes donne

    private void fetch(){
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .child("guest_name_table");
        FirebaseRecyclerOptions<GuestNameTable>options=
                new FirebaseRecyclerOptions.Builder<GuestNameTable>().setQuery(query,snapshot -> new GuestNameTable(
                        Objects.requireNonNull(snapshot.child("id").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("guestFirstName").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("guestFamilyName").getValue()).toString()
                        )).build();

        adapter = new FirebaseRecyclerAdapter<GuestNameTable, AddTableFragment.ViewHolder>(options) {
//jusque la sa marcheeee 12h47 ----------
            @NonNull
            @Override
            public AddTableFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.guest_name_item,parent,false);
                return new AddTableFragment.ViewHolder(view);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull GuestNameTable guestNameTable) {
                //20h59 marche
                /*
                1- recuper le id de gnt
                2- recuper le id de table
                3- si cest egal afiche
                 */
                ArrayList<String> snapshotList = new ArrayList<>();

                String snapshotListId = getSnapshots().getSnapshot(i).child("id").getValue().toString();
                String snapshotListGuestFirstName= getSnapshots().getSnapshot(i).child("guestFirstName").getValue().toString();
                String snapshotListGuestFamilyName= getSnapshots().getSnapshot(i).child("guestFamilyName").getValue().toString();
                snapshotList.add(snapshotListId);
                snapshotList.add(snapshotListGuestFirstName);
                snapshotList.add(snapshotListGuestFamilyName);


                System.out.println("i "+i);
                String IdTable= etTableName.getText().toString();

                for (int j=0; j<=i; j++) {



                    if (IdTable.equals(snapshotListId)) {


//                        viewHolder.setTvGuestFirstName(guestNameTable.getFirstNameGuest());
//                        viewHolder.setTvGuestFamilyName(guestNameTable.getNameGuest());
                        viewHolder.tvGuestFirstName.setText(snapshotListGuestFirstName);
                        viewHolder.tvGuestFamilyName.setText(snapshotListGuestFamilyName);
                        System.out.println("i oui "+i);

                    }else {
                        /*
                        i = tout les noms
                        j= le compte o fur a meusur
                         */
//                        snapshotList.remove(snapshotListGuestFamilyName);
//                        snapshotList.remove(snapshotListId);
//                        snapshotList.remove(snapshotListGuestFirstName);
                        viewHolder.cvGuestName.setVisibility(View.GONE);
                        viewHolder.guestName_root.setVisibility(View.GONE);
                        System.out.println("i non "+i);
                    }
//                    System.out.println("num: "+snapshotList.size());

//                    System.out.println("i fin "+i);

                }

//                System.out.println("j = "+j);
//                System.out.println("i = "+i);
//                System.out.println("------");
//                System.out.println("position "+ getSnapshots().getSnapshot(i).getValue());
//                System.out.println("snapshot list id: "+snapshotListId);
//                System.out.println("snapshot list fam: "+snapshotListGuestFamilyName);
//                System.out.println("snapshot list first: "+snapshotListGuestFirstName);
//                System.out.println("snapshot list : "+snapshotList);


                /*
                si je glisse a guauche alor suprime le
                 */
//                viewHolder.cvGuestName.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//
//                });


                //sur la poubel cour
                viewHolder.btnDelete.setOnClickListener(v ->
                        Toast.makeText(getContext(), "click long for delete", Toast.LENGTH_SHORT).show());

                //sur la poubelle lontemp
                viewHolder.btnDelete.setOnLongClickListener(v -> {
                    String gntId=guestNameTable.getFirstNameGuest();
//                    String gntId=etTableName.getText().toString();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid)
                            .child("guest_name_table")
                            .child(gntId);
                    databaseReference.removeValue();
                    return true;
                });
                // si je click dessu
                viewHolder.guestName_root.setOnClickListener(v ->
                        Toast.makeText(getContext(), "Je Click Dessu"+i, Toast.LENGTH_SHORT).show());
            }
        };
        rvGuestNameInThisTable.setAdapter(adapter);
    }

    private void viewRecyclerViewTable() {
        LandingAnimator animator =new LandingAnimator( new OvershootInterpolator(1f));
        rvGuestNameInThisTable.setItemAnimator(animator);
        Objects.requireNonNull(rvGuestNameInThisTable.getItemAnimator()).setAddDuration(1500);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvGuestNameInThisTable.setLayoutManager(linearLayoutManager);
        rvGuestNameInThisTable.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void getInfo() {
        assert this.getArguments() != null;
        String idT = this.getArguments().getString("tid");

        assert idT != null;
        DatabaseReference dbTable = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("table").child(idT);
        dbTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object dsName = dataSnapshot.child("name").getValue();
                Object dsPlace = dataSnapshot.child("place").getValue();
                Object dsNumberPlace = dataSnapshot.child("numberPlace").getValue();

                if (dsName != null) {
                    String nameT = dsName.toString();
                    etTableName.setText(nameT);
                }
                if (dsPlace != null) {
                    String placeT = dsPlace.toString();
                    etNumberPlace.setText(placeT);
                }
                if (dsNumberPlace != null) {
                    String numberPlace = dsNumberPlace.toString();
                    etNumberPlace.setText(numberPlace);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}});
    }
    private void setInfoTable(){
        String idT=etTableName.getText().toString();

        if (idT.length()!=0){
            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                    .child("table").child(idT);
            Map<String,Object> mapTable=new HashMap<>();

            mapTable.put("id",Objects.requireNonNull(databaseReference.getKey()));
            mapTable.put("name",etTableName.getText().toString());
            mapTable.put("place",etNumberPlace.getText().toString());
            databaseReference.setValue(mapTable);
        }else {
            etTableName.setError("you need write name");
        }
        dismiss();
    }
    private void layout(View view){
        etTableName=view.findViewById(R.id.etTableName);
        etNumberPlace=view.findViewById(R.id.etNumberPlace);
        etGuestFirstNameInTable=view.findViewById(R.id.etGuestFirstNameInTable);
        etGuestNameInTable=view.findViewById(R.id.etGuestNameInTable);
        btnAddAT=view.findViewById(R.id.btnAddaT);
        rvGuestNameInThisTable=view.findViewById(R.id.rvGuestNameInThisTable);
        btnSaveAT=view.findViewById(R.id.btnSaveAT);
    }
}
