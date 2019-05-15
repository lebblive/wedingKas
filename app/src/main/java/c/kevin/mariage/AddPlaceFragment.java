package c.kevin.mariage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPlaceFragment extends AppCompatDialogFragment {

    private EditText etNameP;
    private EditText etPhoneP;
    private EditText etAdressP;
    private EditText etMailP;
    private EditText etNoteP;
    private EditText etPriceP;
    private Button btnSaveP;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public AddPlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_place, container, false);
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
        if (this.getArguments().getString("autre")==this.getArguments().getString("add")) {
            getInfo();
        }
        btnSaveP.setOnClickListener(v -> {
            setInfoPlace();
        });
    }
    private void getInfo() {
        String idP=this.getArguments().getString("pid");

        DatabaseReference dbFoto = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("place").child(idP);
        dbFoto.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object  dsName=dataSnapshot.child("name").getValue();
                Object  dsPhone=dataSnapshot.child("phone").getValue();
                Object  dsAdress=dataSnapshot.child("adress").getValue();
                Object  dsEmail=dataSnapshot.child("email").getValue();
                Object  dsPrice=dataSnapshot.child("price").getValue();
                Object  dsNote=dataSnapshot.child("note").getValue();

                if (dsName!=null){
                    String nameF=dsName.toString();
                    etNameP.setText(nameF);
                }
                if (dsPhone!=null){
                    String phoneF=dsPhone.toString();
                    etPhoneP.setText(phoneF);
                }
                if (dsAdress!=null){
                    String adressF=dsAdress.toString();
                    etAdressP.setText(adressF);
                }
                if (dsEmail!=null){
                    String emailF=dsEmail.toString();
                    etMailP.setText(emailF);
                }
                if (dsPrice!=null){
                    String priceF=dsPrice.toString();
                    etPriceP.setText(priceF);
                }
                if (dsNote!=null){
                    String noteF=dsNote.toString();
                    etNoteP.setText(noteF);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setInfoPlace() {

        String idP=etNameP.getText().toString();

        if (idP.length()!=0){

            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("place").child(idP);
            Map<String,Object> mapPlace=new HashMap<>();

            mapPlace.put("id",databaseReference.getKey());
            mapPlace.put("name",etNameP.getText().toString());
            mapPlace.put("phone",etPhoneP.getText().toString());
            mapPlace.put("adress",etAdressP.getText().toString());
            mapPlace.put("email",etMailP.getText().toString());
            mapPlace.put("price",etPriceP.getText().toString());
            mapPlace.put("note",etNoteP.getText().toString());

            databaseReference.setValue(mapPlace);
        }else{
            etNameP.setError("you need write name");
        }
        dismiss();


    }

    private void layout(View view) {
        etNameP=view.findViewById(R.id.etNameP);
        etPhoneP=view.findViewById(R.id.etPhoneP);
        etAdressP=view.findViewById(R.id.etAdressP);
        etMailP=view.findViewById(R.id.etMailP);
        etNoteP=view.findViewById(R.id.etNoteP);
        etPriceP=view.findViewById(R.id.etPriceP);
        btnSaveP=view.findViewById(R.id.btnSaveP);
    }

}
