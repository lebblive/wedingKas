package c.kevin.mariage;

/*
https://medium.com/android-grid/how-to-use-firebaserecycleradpater-with-latest-firebase-dependencies-in-android-aff7a33adb8b
 */


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;


public class AddFotoFragment extends AppCompatDialogFragment {

    EditText etNameF;
    EditText etPhoneF;
    EditText etAdressF;
    EditText etMailF;
    EditText etNoteF;
    EditText etPriceF;
    Button btnSaveF;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public AddFotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        btnSaveF.setOnClickListener(v -> {
            setInfoFoto();
        });
    }

    /*
    Pour afficher les données dans la liste,
     nous avons besoin de certaines données dans la base de données.
      Nous allons donc ajouter des données via cette méthode.
     */
    public void setInfoFoto() {

        String idF=etNameF.getText().toString();

        if (idF.length()!=0){

        DatabaseReference databaseReference=
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("foto").child(idF);
        Map<String,Object> mapFoto=new HashMap<>();

        mapFoto.put("id",databaseReference.getKey());
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

        //TODO interdir que le champ contienne es point.


               /*
                Pattern : permet d’avoir une représentation compilée de l’expression régulière
                Matcher : permet d’interpréter l’expression régulière et de trouver des correspondances dans
                une chaîne de caractères
                */
        //etName.equalsIgnoreCase(".")
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
