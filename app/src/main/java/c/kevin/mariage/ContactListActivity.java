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

public class ContactListActivity extends AppCompatActivity {

    private Button btnAddC;
    private RecyclerView rvContact;
    private Button btnBack;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;


    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    Query query= FirebaseDatabase.getInstance()
            .getReference().child("users").child(uid).child("contact");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        btnAddC=findViewById(R.id.btnAddC);
        rvContact=findViewById(R.id.rvContact);
        btnBack=findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
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

        public ConstraintLayout contact_root;
        public TextView tvFirstName;
        public TextView tvFamilyName;
        public TextView tvPhoneContact;
        public TextView tvSexe;
        public TextView tvAge;
        public TextView tvNoteContact;
        public Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
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

        public void setTvFirstName(String tvFirstNames){
            tvFirstName.setText(tvFirstNames);
        }
        public void setTvFamilyName(String tvFamilyNames){
            tvFamilyName.setText(tvFamilyNames);
        }
        public void setTvPhoneContact(String tvPhoneContacts){
            tvPhoneContact.setText(tvPhoneContacts);
        }
        public void setTvSexe(String tvSexes) {
            tvSexe.setText(tvSexes);
        }
        public void setTvAge(String tvAges){
            tvAge.setText(tvAges);
        }
        public void setTvNoteContact(String tvNoteContacts){
                tvNoteContact.setText(tvNoteContacts);
        }
    }

    private void fetch() {

        FirebaseRecyclerOptions<Contact> options=
                new FirebaseRecyclerOptions.Builder<Contact>().setQuery(query, snapshot -> new Contact(
                        snapshot.child("id").getKey().toString(),
                        snapshot.child("firstName").getValue().toString(),
                        snapshot.child("familyName").getValue().toString(),
                        snapshot.child("phone").getValue().toString(),
                        snapshot.child("sexe").getValue().toString(),
                        snapshot.child("age").getValue().toString(),
                        snapshot.child("noteContact").getValue().toString())).build();

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
                    String cidFirst=contact.getFirstName().toString();
                    String cidFamily=contact.getFamilyName().toString();
                    String cid=cidFirst+cidFamily;
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("contact").child(cid);
                    databaseReference.removeValue();
                });

                //si je click dessu
                viewHolder.contact_root.setOnClickListener(v -> {
                    String cidFirst=contact.getFirstName().toString();
                    String cidFamily=contact.getFamilyName().toString();
                    String cid=cidFirst+cidFamily;

                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference()
                            .child("users").child(uid).child("contact").child(cid);

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

                    //stam pour le kif dune methode en plus a suprimer
//                    Random r=new Random();
//                    int c= Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256));
//
//                    viewHolder.foto_root.setBackgroundColor(c);

//                    Toast.makeText(FotoActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                });
            }
        };
        rvContact.setAdapter(adapter);
    }


    private void viewRecyclerViewContact() {
        linearLayoutManager=new LinearLayoutManager(this);
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
