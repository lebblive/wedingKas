package c.kevin.mariage;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddOtherFragment extends AppCompatDialogFragment {

    EditText etNameO;
    EditText etPhoneO;
    EditText etAdressO;
    EditText etMailO;
    EditText etNoteO;
    EditText etPriceO;
    Button btnSaveO;

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
