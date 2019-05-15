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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class AddFotoFragment extends AppCompatDialogFragment {

    private EditText etNameF;
    private EditText etPhoneF;
    private EditText etAdressF;
    private EditText etMailF;
    private EditText etNoteF;
    private EditText etPriceF;
    private Button btnSaveF;

    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


    public AddFotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_foto, container, false);
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
        //get info on FotoActivity

        assert this.getArguments() != null;
        if (Objects.equals(this.getArguments().getString("autre"), this.getArguments().getString("add"))) {
            getInfo();
        }
        btnSaveF.setOnClickListener(v -> setInfoFoto());
    }

    /*
    Pour afficher les données dans la liste,
     nous avons besoin de certaines données dans la base de données.
      Nous allons donc ajouter des données via cette méthode.
     */
    private void setInfoFoto() {

        String idF=etNameF.getText().toString();

        if (idF.length()!=0){

        DatabaseReference databaseReference=
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("foto").child(idF);
        Map<String,Object> mapFoto=new HashMap<>();

        mapFoto.put("id", Objects.requireNonNull(databaseReference.getKey()));
        mapFoto.put("name",etNameF.getText().toString());
        mapFoto.put("phone",etPhoneF.getText().toString());
        mapFoto.put("adress",etAdressF.getText().toString());
        mapFoto.put("email",etMailF.getText().toString());
        mapFoto.put("price",etPriceF.getText().toString());
        mapFoto.put("note",etNoteF.getText().toString());

            databaseReference.setValue(mapFoto);
        }else{
            etNameF.setError("you need write name");
        }
        dismiss();


               /*
                Pattern : permet d’avoir une représentation compilée de l’expression régulière
                Matcher : permet d’interpréter l’expression régulière et de trouver des correspondances dans
                une chaîne de caractères
                */
        //etName.equalsIgnoreCase(".")
    }

    private void getInfo() {

        assert this.getArguments() != null;
        String idF = this.getArguments().getString("fid");

        assert idF != null;
        DatabaseReference dbFoto = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("foto").child(idF);
            dbFoto.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Object dsName = dataSnapshot.child("name").getValue();
                    Object dsPhone = dataSnapshot.child("phone").getValue();
                    Object dsAdress = dataSnapshot.child("adress").getValue();
                    Object dsEmail = dataSnapshot.child("email").getValue();
                    Object dsPrice = dataSnapshot.child("price").getValue();
                    Object dsNote = dataSnapshot.child("note").getValue();

                    if (dsName != null) {
                        String nameF = dsName.toString();
                        etNameF.setText(nameF);
                    }
                    if (dsPhone != null) {
                        String phoneF = dsPhone.toString();
                        etPhoneF.setText(phoneF);
                    }
                    if (dsAdress != null) {
                        String adressF = dsAdress.toString();
                        etAdressF.setText(adressF);
                    }
                    if (dsEmail != null) {
                        String emailF = dsEmail.toString();
                        etMailF.setText(emailF);
                    }
                    if (dsPrice != null) {
                        String priceF = dsPrice.toString();
                        etPriceF.setText(priceF);
                    }
                    if (dsNote != null) {
                        String noteF = dsNote.toString();
                        etNoteF.setText(noteF);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }


    private void layout(View view) {
        etNameF=view.findViewById(R.id.etNameF);
        etPhoneF=view.findViewById(R.id.etPhoneF);
        etAdressF=view.findViewById(R.id.etAdressF);
        etMailF=view.findViewById(R.id.etMailF);
        etNoteF=view.findViewById(R.id.etNoteF);
        etPriceF=view.findViewById(R.id.etPriceF);
        btnSaveF=view.findViewById(R.id.btnSaveF);
    }
}
