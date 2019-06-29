package c.kevin.mariage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
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

public  class AddContactExist_Fragment extends AppCompatDialogFragment {

    private RecyclerView rvExistContact;
    private ImageView btnSaveContactExist;
    private ImageView ivQuestion;
    private TextView tvQuestion;

    private Boolean viewGuest=false;


    private FirebaseRecyclerAdapter<Contact, ViewHolder> adapter;
    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    public AddContactExist_Fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_contact_exist_, container, false);
        //ma boite  nest pas a la bonne taille alor je rajoute un peu de designe
        int width= (int) (getResources().getDisplayMetrics().widthPixels*0.9);
        int height= (int) (getResources().getDisplayMetrics().heightPixels*0.7);

        // layout params= x et y je prend de linear layout;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width,height);
        v.setLayoutParams(lp);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout(view);
        viewRecyclerViewChair();
        fetch();

        ivQuestion.setOnClickListener(v -> {


            viewGuest =! viewGuest;
            if (viewGuest){
                tvQuestion.setText(R.string.info_click);
                tvQuestion.setVisibility(View.VISIBLE);
            }else {
                tvQuestion.setVisibility(View.INVISIBLE);
            }

        });
    }

    private void viewRecyclerViewChair() {
        LandingAnimator animator = new LandingAnimator(new OvershootInterpolator(1f));
        rvExistContact.setItemAnimator(animator);
        Objects.requireNonNull(rvExistContact.getItemAnimator()).setAddDuration(1000);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvExistContact.setLayoutManager(linearLayoutManager);
        rvExistContact.setHasFixedSize(true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout contactView_root;
        private TextView tvFNameCV;
        private TextView tvNameCV;
        private CheckBox check;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactView_root=itemView.findViewById(R.id.contactView_root);
            tvFNameCV=itemView.findViewById(R.id.tvFNameCV);
            tvNameCV=itemView.findViewById(R.id.tvNameCV);
            check=itemView.findViewById(R.id.check);
        }

        void setTvFNameCV(String tvFNameCVs){
            tvFNameCV.setText(tvFNameCVs);
        }
        void setTvNameCV(String tvNameCVs){
            tvNameCV.setText(tvNameCVs);
        }
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("contact");
        // il me faut recuperer le nom de la table de chaque view

        FirebaseRecyclerOptions<Contact>options =
                new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query,snapshot -> new Contact(
                        snapshot.child("id").getKey(),
                        Objects.requireNonNull(snapshot.child("firstName").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("familyName").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("phone").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("sexe").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("age").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("noteContact").getValue()).toString()
                )).build();

        adapter = new FirebaseRecyclerAdapter<Contact, AddContactExist_Fragment.ViewHolder>(options) {

            @NonNull
            @Override
            public AddContactExist_Fragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.contact_exist_item,parent,false);
                return new AddContactExist_Fragment.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Contact contact) {
                viewHolder.setTvFNameCV(contact.getFirstName());
                viewHolder.setTvNameCV(contact.getFamilyName());

                // j'enregistre mes donner
                // si je click sur une des view

                viewHolder.contactView_root.setOnClickListener(v -> {

                    setInfo(viewHolder, contact);
                });

                btnSaveContactExist.setOnClickListener(v -> {
                    dismiss();
                });
            }
        };
        rvExistContact.setAdapter(adapter);

    }

    private void setInfo(@NonNull ViewHolder viewHolder, @NonNull Contact contact) {
        String contatcName = contact.getFirstName();
        //recuper le nom de la table depuis chairActivity
        Bundle bundle = getArguments();
        if (bundle!=null) {
            String tableName = bundle.getString("tableName");

            //si le chexbox cest plein
            if (viewHolder.check.isChecked()){
                DatabaseReference databaseReference =
                        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                                .child("chair")
                                .child(contatcName);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // enregistre le nom de tout ceux qi sont checke
                        Map<String, Object> mapChair = new HashMap<>();
                        mapChair.put("firstName", contatcName);
                        mapChair.put("familyName", viewHolder.tvNameCV.getText().toString());
                        mapChair.put("id", contatcName);
                        mapChair.put("tableName", Objects.requireNonNull(tableName));

                        databaseReference.setValue(mapChair);
                        Toast.makeText(getContext(), "add", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }
    }

    private void layout(View view) {
        rvExistContact=view.findViewById(R.id.rvExistContact);
        btnSaveContactExist=view.findViewById(R.id.btnSaveContactExist);
        ivQuestion=view.findViewById(R.id.ivQuestion);
        tvQuestion=view.findViewById(R.id.tvQuestion);
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
}
