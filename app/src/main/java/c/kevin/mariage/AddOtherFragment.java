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
public class AddOtherFragment extends AppCompatDialogFragment {

    private EditText etNameO;
    private EditText etPhoneO;
    private EditText etAdressO;
    private EditText etMailO;
    private EditText etNoteO;
    private EditText etPriceO;
    private Button btnSaveO;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public AddOtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_other, container, false);
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
        btnSaveO.setOnClickListener(v -> {
            setInfoOther();
        });
    }

    public void setInfoOther() {

        String idO=etNameO.getText().toString();

        if (idO.length()!=0){

            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("other").child(idO);
            Map<String,Object> mapOther=new HashMap<>();

            mapOther.put("id",databaseReference.getKey());
            mapOther.put("name",etNameO.getText().toString());
            mapOther.put("phone",etPhoneO.getText().toString());
            mapOther.put("adress",etAdressO.getText().toString());
            mapOther.put("email",etMailO.getText().toString());
            mapOther.put("price",etPriceO.getText().toString());
            mapOther.put("note",etNoteO.getText().toString());

            databaseReference.setValue(mapOther);
        }else{
            etNameO.setError("you need write name");
        }
        dismiss();
    }

    private void getInfo() {
        String idO=this.getArguments().getString("oid");

        DatabaseReference dbOther = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("other").child(idO);
        dbOther.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    etNameO.setText(nameF);
                }
                if (dsPhone!=null){
                    String phoneF=dsPhone.toString();
                    etPhoneO.setText(phoneF);
                }
                if (dsAdress!=null){
                    String adressF=dsAdress.toString();
                    etAdressO.setText(adressF);
                }
                if (dsEmail!=null){
                    String emailF=dsEmail.toString();
                    etMailO.setText(emailF);
                }
                if (dsPrice!=null){
                    String priceF=dsPrice.toString();
                    etPriceO.setText(priceF);
                }
                if (dsNote!=null){
                    String noteF=dsNote.toString();
                    etNoteO.setText(noteF);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void layout(View view) {
        etNameO=view.findViewById(R.id.etNameO);
        etPhoneO=view.findViewById(R.id.etPhoneO);
        etAdressO=view.findViewById(R.id.etAdressO);
        etMailO=view.findViewById(R.id.etMailO);
        etNoteO=view.findViewById(R.id.etNoteO);
        etPriceO=view.findViewById(R.id.etPriceO);
        btnSaveO=view.findViewById(R.id.btnSaveO);
    }
}
