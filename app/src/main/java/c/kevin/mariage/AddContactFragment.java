package c.kevin.mariage;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;



public class AddContactFragment extends AppCompatDialogFragment {

    private EditText etFirstName;
    private EditText etFamilyName;
    private EditText etPhoneNumber;
    private TextView tvMan;
    private TextView tvWoman;
    private TextView tvOldPerson;
    private TextView tvAdultPerson;
    private TextView tvYoungPerson;
    private EditText etNoteContact;
    private Button btnSaveContact;

    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_contact, container, false);
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
        //get info on contactActivity

        assert this.getArguments() != null;
        if (Objects.equals(this.getArguments().getString("autre"), this.getArguments().getString("add"))) {
            getInfo();
        }

        btnSaveContact.setOnClickListener(v -> setInfoFoto());
        tvMan.setOnClickListener(v -> selectedSexeMan());
        tvWoman.setOnClickListener(v -> selectedSexeWoman());
        tvOldPerson.setOnClickListener(v -> selectedAgeOldPerson());
        tvAdultPerson.setOnClickListener(v -> selectedAgeAdultPerson());
        tvYoungPerson.setOnClickListener(v -> selectedAgeYoungPerson());
    }



    private void selectedAgeYoungPerson() {
        tvYoungPerson.setTextColor(Color.WHITE);
        tvYoungPerson.setBackgroundColor(0xFF03A9F4);
        tvAdultPerson.setTextColor(Color.BLACK);
        tvAdultPerson.setBackgroundColor(Color.WHITE);
        tvOldPerson.setTextColor(Color.BLACK);
        tvOldPerson.setBackgroundColor(Color.WHITE);
    }

    private void selectedAgeAdultPerson() {
        tvAdultPerson.setTextColor(Color.WHITE);
        tvAdultPerson.setBackgroundColor(0xFF03A9F4);
        tvOldPerson.setTextColor(Color.BLACK);
        tvOldPerson.setBackgroundColor(Color.WHITE);
        tvYoungPerson.setTextColor(Color.BLACK);
        tvYoungPerson.setBackgroundColor(Color.WHITE);

    }

    private void selectedAgeOldPerson() {
        tvOldPerson.setTextColor(Color.WHITE);
        tvOldPerson.setBackgroundColor(0xFF03A9F4);
        tvAdultPerson.setTextColor(Color.BLACK);
        tvAdultPerson.setBackgroundColor(Color.WHITE);
        tvYoungPerson.setTextColor(Color.BLACK);
        tvYoungPerson.setBackgroundColor(Color.WHITE);
    }

    private void selectedSexeMan() {
        tvMan.setTextColor(Color.WHITE);
        tvMan.setBackgroundColor(0xFF03A9F4);
        tvWoman.setTextColor(Color.BLACK);
        tvWoman.setBackgroundColor(Color.WHITE);

    }
    private void selectedSexeWoman() {
        tvWoman.setTextColor(Color.WHITE);
        tvWoman.setBackgroundColor(0xFF03A9F4);
        tvMan.setTextColor(Color.BLACK);
        tvMan.setBackgroundColor(Color.WHITE);

    }

    private void setInfoFoto() {
        String cidFirst=etFirstName.getText().toString();
        String cidFamily=etFamilyName.getText().toString();
        String cid=cidFirst+cidFamily;


        if (cid.length()!=0){

            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("contact").child(cid);
            Map<String,Object> mapContact=new HashMap<>();

            mapContact.put("id", Objects.requireNonNull(databaseReference.getKey()));
            mapContact.put("firstName",etFirstName.getText().toString());
            mapContact.put("familyName",etFamilyName.getText().toString());
            mapContact.put("phone",etPhoneNumber.getText().toString());

            //select sex
            if (tvMan.getCurrentTextColor()==Color.WHITE){
                mapContact.put("sexe",tvMan.getText().toString());

            }if (tvWoman.getCurrentTextColor()==Color.WHITE){
                mapContact.put("sexe",tvWoman.getText().toString());
            }if (tvMan.getCurrentTextColor()==Color.BLACK && tvWoman.getCurrentTextColor()==Color.BLACK ){
                mapContact.put("sexe","");
            }
            // select Age
            if (tvOldPerson.getCurrentTextColor()==Color.WHITE){
                mapContact.put("age",tvOldPerson.getText().toString());
            }
            if (tvAdultPerson.getCurrentTextColor()==Color.WHITE){
                mapContact.put("age",tvAdultPerson.getText().toString());
            }
            if (tvYoungPerson.getCurrentTextColor()==Color.WHITE){
                mapContact.put("age",tvYoungPerson.getText().toString());
            } if(tvOldPerson.getCurrentTextColor()==Color.BLACK &&
                    tvAdultPerson.getCurrentTextColor()==Color.BLACK &&
                    tvYoungPerson.getCurrentTextColor()==Color.BLACK) {
                mapContact.put("age","");
            }

            mapContact.put("noteContact",etNoteContact.getText().toString());

            databaseReference.setValue(mapContact);
        }else{
            etFirstName.setError("you need write name");
        }
        dismiss();
    }

    private void getInfo() {

        assert this.getArguments() != null;
        String idC = this.getArguments().getString("cid");

        assert idC != null;
        DatabaseReference dbContact = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("contact").child(idC);
        dbContact.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object dsFirstName = dataSnapshot.child("firstName").getValue();
                Object dsFamilyName = dataSnapshot.child("familyName").getValue();
                Object dsPhone = dataSnapshot.child("phone").getValue();

                Object dsSexe = dataSnapshot.child("sexe").getValue();
                Object dsAge = dataSnapshot.child("age").getValue();

                Object dsNoteContact = dataSnapshot.child("noteContact").getValue();

                if (dsFirstName != null) {
                    String firstName = dsFirstName.toString();
                    etFirstName.setText(firstName);
                }
                if (dsFamilyName != null) {
                    String familyName = dsFamilyName.toString();
                    etFamilyName.setText(familyName);
                }
                if (dsPhone != null) {
                    String phone = dsPhone.toString();
                    etPhoneNumber.setText(phone);
                }
                if (dsSexe != null) {
                    String sexe = dsSexe.toString();
                    if (sexe.equals("Man")){
                        selectedSexeMan();
                    }
                    if (sexe.equals("woman")){
                        selectedSexeWoman();
                    }
                }

                if (dsAge != null) {
                    String age = dsAge.toString();
                    if (age.equals("old Person")){
                        selectedAgeOldPerson();
                    }
                    if (age.equals("adult Person")){
                        selectedAgeAdultPerson();
                    }
                    if (age.equals("young Person")){
                        selectedAgeYoungPerson();
                    }
                }
                if (dsNoteContact != null) {
                    String noteContact = dsNoteContact.toString();
                    etNoteContact.setText(noteContact);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void layout(View view) {
        etFirstName=view.findViewById(R.id.etFirstName);
        etFamilyName=view.findViewById(R.id.etFamilyName);
        etPhoneNumber=view.findViewById(R.id.etPhoneNumber);
        tvMan=view.findViewById(R.id.tvMan);
        tvWoman=view.findViewById(R.id.tvWoman);
        tvOldPerson=view.findViewById(R.id.tvOldPerson);
        tvAdultPerson=view.findViewById(R.id.tvAdultPerson);
        tvYoungPerson=view.findViewById(R.id.tvYoungPerson);
        etNoteContact=view.findViewById(R.id.etNoteContact);
        btnSaveContact=view.findViewById(R.id.btnSaveContact);

    }
}
