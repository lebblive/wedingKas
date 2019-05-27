package c.kevin.mariage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuestNumberFragment extends AppCompatDialogFragment {


    private TextView tvResume;
    private int man=0;
    private int woman=0;
    private int old =0;
    private int adult=0;
    private int young =0;

    public GuestNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout(view);
        getNumberPerson();
    }

    private void layout(View view) {
        tvResume=view.findViewById(R.id.tvResume);
    }

    private void getNumberPerson() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference dbRangerContact = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("contact");

            dbRangerContact.addListenerForSingleValueEvent(new ValueEventListener() {
                // church in db all contact and give me ther children
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> arrayList= new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String pushKey = snapshot.getKey();
                        arrayList.add(pushKey);
                    }
                    getIdContact(arrayList);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
                //end recuperation

                //get name one once id contact
                private void getIdContact(ArrayList<String> arrayList) {
                    String idC;
                    for (int i = 0; i < arrayList.size(); i++) {
                        idC = arrayList.get(i);

                        //rentre dans la liste des contact et recuper toute les info
                        DatabaseReference dbNumber = FirebaseDatabase.getInstance().getReference()
                                .child("users").child(uid).child("contact").child(idC);
                        dbNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String dsSexe = (String) dataSnapshot.child("sexe").getValue();
                                String dsAge = (String) dataSnapshot.child("age").getValue();

                                //je comence ici
                                assert dsSexe != null;
                                if (dsSexe.equals(getString(R.string.man))) {
                                    man++;
                                }
                                if (dsSexe.equals(getString(R.string.woman))){
                                    woman++;
                                }
                                assert dsAge != null;
                                if (dsAge.equals(getString(R.string.old_person))){
                                    old++;
                                }
                                if (dsAge.equals(getString(R.string.adult_person))){
                                    adult++;
                                }
                                if (dsAge.equals(getString(R.string.young_person))){
                                    young++;
                                }

                                String resume=getString(R.string.Persons_number)+" " + arrayList.size()+ " : ";

                                if (man!=0){
                                    resume=resume+ "\n"+ man +" " +getString(R.string.mans);
                                }
                                if (woman!=0){
                                    resume=resume+"\n"+
                                            woman +" " +getString(R.string.womans);
                                }
                                if (old!=0){
                                    resume=resume+"\n"+
                                            old +" " +getString(R.string.old_persons);
                                }
                                if (adult!=0){
                                    resume=resume+"\n"+
                                            adult +" " +getString(R.string.adult_persons);
                                }
                                if (young!=0){
                                    resume=resume+"\n"+
                                            young +" " +getString(R.string.young_persons);
                                }
                                tvResume.setText(resume);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });
                    }
                }
            });

        }
    }
}
