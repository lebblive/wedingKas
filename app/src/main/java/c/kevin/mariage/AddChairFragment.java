package c.kevin.mariage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AddChairFragment extends AppCompatDialogFragment {

    private TextView tvTableName;
    private EditText etFamilyName;
    private EditText etFirstName;
    private ImageView btnSaveGuest;

    String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    public AddChairFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_chair, container, false);
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
        //get info on chairActivity

        assert this.getArguments() != null;
        if (Objects.equals(this.getArguments().getString("autre"), this.getArguments().getString("add"))) {
            getInfo();
        }
        btnSaveGuest.setOnClickListener(v -> setInfoChair());
    }

    private void setInfoChair() {

        String idChair=etFirstName.getText().toString();

        if (idChair.length()!=0){

            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                            .child("chair")
                            .child(idChair);
            Map<String,Object> mapChair=new HashMap<>();

            mapChair.put("id", Objects.requireNonNull(databaseReference.getKey()));
            mapChair.put("firstName",etFirstName.getText().toString());
            mapChair.put("familyName",etFamilyName.getText().toString());
            mapChair.put("tableName",tvTableName.getText().toString());

            databaseReference.setValue(mapChair);
        }else{
            etFirstName.setError("you need write name");
        }
        dismiss();
    }

    private void getInfo() {

        assert this.getArguments() != null;
        String idTable = this.getArguments().getString("tableid");
        String idChair = this.getArguments().getString("chairid");
            tvTableName.setText(idTable);
            assert idTable != null;
            assert idChair != null;
            DatabaseReference dbChair = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid)
                    .child("chair")
                    .child(idChair);
            dbChair.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Object dsFirstName = dataSnapshot.child("firstName").getValue();
                    Object dsFamilyName = dataSnapshot.child("familyName").getValue();

                    if (dsFirstName != null) {
                        String dsFirstNames = dsFirstName.toString();
                        etFirstName.setText(dsFirstNames);
                    }
                    if (dsFamilyName != null) {
                        String dsFamilyNames = dsFamilyName.toString();
                        etFamilyName.setText(dsFamilyNames);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }

    private void layout(View view) {
        tvTableName=view.findViewById(R.id.tvTableName);
        etFirstName=view.findViewById(R.id.etFirstName);
        etFamilyName=view.findViewById(R.id.etFamilyName);
        btnSaveGuest=view.findViewById(R.id.btnSaveGuest);
    }
}
