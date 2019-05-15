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
public class AddMusicFragment extends AppCompatDialogFragment {

    private EditText etNameM;
    private EditText etPhoneM;
    private EditText etAdressM;
    private EditText etMailM;
    private EditText etNoteM;
    private EditText etPriceM;
    private Button btnSaveM;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    public AddMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_music, container, false);
        //ma boite  nest pas a la bonne taille alor je rajoute un peu de designe
        int width= (int) (getResources().getDisplayMetrics().widthPixels*0.9);
        int height= (int) (getResources().getDisplayMetrics().heightPixels*0.7);

        // layout params= x et y je prend de linear layout;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width,height);
        v.setLayoutParams(lp);
        return v;
    }

    private void getInfo() {
        String idM=this.getArguments().getString("mid");

        DatabaseReference dbMusic = FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("music").child(idM);
        dbMusic.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    etNameM.setText(nameF);
                }
                if (dsPhone!=null){
                    String phoneF=dsPhone.toString();
                    etPhoneM.setText(phoneF);
                }
                if (dsAdress!=null){
                    String adressF=dsAdress.toString();
                    etAdressM.setText(adressF);
                }
                if (dsEmail!=null){
                    String emailF=dsEmail.toString();
                    etMailM.setText(emailF);
                }
                if (dsPrice!=null){
                    String priceF=dsPrice.toString();
                    etPriceM.setText(priceF);
                }
                if (dsNote!=null){
                    String noteF=dsNote.toString();
                    etNoteM.setText(noteF);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout(view);
        if (this.getArguments().getString("autre")==this.getArguments().getString("add")) {
            getInfo();
        }
        btnSaveM.setOnClickListener(v -> {
            setInfoMusic();
        });
    }

    public void setInfoMusic() {

        String idM=etNameM.getText().toString();

        if (idM.length()!=0){

            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("music").child(idM);
            Map<String,Object> mapMusic=new HashMap<>();

            mapMusic.put("id",databaseReference.getKey());
            mapMusic.put("name",etNameM.getText().toString());
            mapMusic.put("phone",etPhoneM.getText().toString());
            mapMusic.put("adress",etAdressM.getText().toString());
            mapMusic.put("email",etMailM.getText().toString());
            mapMusic.put("price",etPriceM.getText().toString());
            mapMusic.put("note",etNoteM.getText().toString());

            databaseReference.setValue(mapMusic);
        }else{
            etNameM.setError("you need write name");
        }
        dismiss();
    }

    private void layout(View view) {
        etNameM=view.findViewById(R.id.etNameM);
        etPhoneM=view.findViewById(R.id.etPhoneM);
        etAdressM=view.findViewById(R.id.etAdressM);
        etMailM=view.findViewById(R.id.etMailM);
        etNoteM=view.findViewById(R.id.etNoteM);
        etPriceM=view.findViewById(R.id.etPriceM);
        btnSaveM=view.findViewById(R.id.btnSaveM);
    }

}
