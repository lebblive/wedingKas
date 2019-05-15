package c.kevin.mariage;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class ContactListActivity extends AppCompatActivity {

    private RecyclerView rvContact;
    private Button btnBack;
    private FirebaseRecyclerAdapter adapter;


    String uid= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("contact");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Button btnAddC = findViewById(R.id.btnAddC);
        rvContact=findViewById(R.id.rvContact);
        btnBack=findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    ContactListActivity.this,btnBack,"");
            startActivity(intent,transition.toBundle());
        });

        btnAddC.setOnClickListener(v -> {
            AddContactFragment addContactFragment=new AddContactFragment();
            String add="add";
            Bundle bundle=new Bundle();
            bundle.putString("add",add);
            addContactFragment.setArguments(bundle);
            addContactFragment.show(getSupportFragmentManager(),"AddContactFragment");
        });
        viewRecyclerViewContact();
        fetch();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout contact_root;
        TextView tvFirstName;
        TextView tvFamilyName;
        TextView tvPhoneContact;
        TextView tvSexe;
        TextView tvAge;
        TextView tvNoteContact;
        public Button btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_root=itemView.findViewById(R.id.contact_root);
            tvFirstName=itemView.findViewById(R.id.tvFirstName);
            tvFamilyName=itemView.findViewById(R.id.tvFamilyName);
            tvPhoneContact=itemView.findViewById(R.id.tvPhoneContact);
            tvSexe=itemView.findViewById(R.id.tvSexe);
            tvAge=itemView.findViewById(R.id.tvAge);
            tvNoteContact=itemView.findViewById(R.id.tvNoteContact);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }

        void setTvFirstName(String tvFirstNames){
            tvFirstName.setText(tvFirstNames);
        }
        void setTvFamilyName(String tvFamilyNames){
            tvFamilyName.setText(tvFamilyNames);
        }
        void setTvPhoneContact(String tvPhoneContacts){
            tvPhoneContact.setText(tvPhoneContacts);
        }
        void setTvSexe(String tvSexes) {
            tvSexe.setText(tvSexes);
        }
        void setTvAge(String tvAges){
            tvAge.setText(tvAges);
        }
        void setTvNoteContact(String tvNoteContacts){
                tvNoteContact.setText(tvNoteContacts);
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Contact> options=
                new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query, snapshot -> new Contact(
                        snapshot.child("id").getKey(),
                        Objects.requireNonNull(snapshot.child("firstName").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("familyName").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("phone").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("sexe").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("age").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("noteContact").getValue()).toString())).build();

        adapter = new FirebaseRecyclerAdapter<Contact, ContactListActivity.ViewHolder>(options) {

            @NonNull
            @Override
            public ContactListActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.contact_item,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ContactListActivity.ViewHolder viewHolder, int i, @NonNull Contact contact) {

                viewHolder.setTvFirstName(contact.getFirstName());
                viewHolder.setTvFamilyName(contact.getFamilyName());
                viewHolder.setTvPhoneContact(contact.getPhoneNumberContact());
                viewHolder.setTvSexe(contact.getSex());
                viewHolder.setTvAge(contact.getAge());
                viewHolder.setTvNoteContact(contact.getNoteContact());



                viewHolder.btnDelete.setOnClickListener(v -> {
                    String cidFirst= contact.getFirstName();
                    String cidFamily= contact.getFamilyName();
                    String cid=cidFirst+cidFamily;
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("contact").child(cid);
                    databaseReference.removeValue();
                });

                //si je click dessu
                viewHolder.contact_root.setOnClickListener(v -> {
                    String cidFirst= contact.getFirstName();
                    String cidFamily= contact.getFamilyName();
                    String cid=cidFirst+cidFamily;

                    AddContactFragment addContactFragment=new AddContactFragment();

                    /*
                    set the id on selected for
                    if i touch this i can update the info
                    so i set the id on the fragment for get her
                     */
                    Bundle bundle=new Bundle();
                    bundle.putString("cid",cid);
                    addContactFragment.setArguments(bundle);

                    addContactFragment.show(getSupportFragmentManager(),"AddContactFragment");
                });
            }
        };
        rvContact.setAdapter(adapter);
    }


    private void viewRecyclerViewContact() {
        LandingAnimator animator=new LandingAnimator(new OvershootInterpolator(1f));
        rvContact.setItemAnimator(animator);
        Objects.requireNonNull(rvContact.getItemAnimator()).setAddDuration(1000);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvContact.setLayoutManager(linearLayoutManager);
        rvContact.setHasFixedSize(true);
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
