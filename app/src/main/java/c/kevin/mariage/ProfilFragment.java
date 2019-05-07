package c.kevin.mariage;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends AppCompatDialogFragment {

    EditText etNameMr;
    EditText etNameMme;
    TextView tvDate;
    CalendarView cvDate;
    Button btnSaveD;

    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static final String EXTRA_NAMEMR="nameMr";
    public static final String EXTRA_NAMEMME="nameMmme";
    public static final String EXTRA_DATE="date";



    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_date, container, false);
        //ma boite  nest pas a la bonne taille alor je rajoute un peu de designe
        int width= (int) (getResources().getDisplayMetrics().widthPixels*0.9);
        int height= (int) (getResources().getDisplayMetrics().heightPixels*0.9);

        // layout params= x et y je prend de linear layout;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width,height);
        v.setLayoutParams(lp);
        return v;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout(view);
        getProfil();
        setProfilDate();


        btnSaveD.setOnClickListener(v -> {
            saveD();
        });
    }

    // if profil exist get value
    private void getProfil() {
        DatabaseReference dbProfil=FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("profil");
        dbProfil.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object dsNameMr=dataSnapshot.child("nameMr").getValue();
                Object dsNameMme=dataSnapshot.child("nameMme").getValue();
                Object dsDate=dataSnapshot.child("date").getValue();
                if (dsNameMr!=null){
                    String nameMr=dsNameMr.toString();
                    etNameMr.setText(nameMr);
                }
                if (dsNameMme!=null){
                    String nameMme=dsNameMme.toString();
                    etNameMme.setText(nameMme);
                }
                if (dsDate!=null){
                    String date=dsDate.toString();
                    tvDate.setText(date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // get UI date
    private void setProfilDate() {

        cvDate.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            int yearSelected = year;
            int monthSelected = month+1; // because 0=january
            int dayOfMonthSelected = dayOfMonth;
//            String date=year+"/"+monthSelected+"/"+dayOfMonth;
            String date=dayOfMonth+"/"+monthSelected+"/"+year;

//            DatabaseReference databaseReference=
//                    FirebaseDatabase.getInstance().getReference()
//                    .child("users").child(uid).child("dateSelected");
//            Map<String,Object> mapDate=new HashMap<>();
//            mapDate.put("month",monthSelected);
//            mapDate.put("dayOfMonth",dayOfMonthSelected);
//            mapDate.put("date",date);
//            databaseReference.setValue(mapDate);
//            System.out.println("HashMap  : "+mapDate);
            tvDate.setText(date);
        });
    }
    // Todo: changer les nom des classe pour profile et non date ...
    // Todo : essayer de changer la date en hebreux soit par calcule soit sous ArrayList
    // Todo : fair le compte a rebour.

    private void saveD() {
        String nameMr=etNameMr.getText().toString();
        String nameMme=etNameMme.getText().toString();
        String date=tvDate.getText().toString();

        DatabaseReference databaseReference=
                FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("profil");
        Map<String,Object> mapProfil=new HashMap<>();
        mapProfil.put("nameMr",nameMr);
        mapProfil.put("nameMme",nameMme);
        mapProfil.put("date",date);

        databaseReference.setValue(mapProfil);
        Intent intent=new Intent(getContext(),MainActivity.class);
        startActivity(intent);

    }

    private void layout(View view) {
        etNameMr=view.findViewById(R.id.etNameMr);
        etNameMme=view.findViewById(R.id.etNameMme);
        tvDate=view.findViewById(R.id.tvDate);
        btnSaveD=view.findViewById(R.id.btnSaveD);
        cvDate=view.findViewById(R.id.cvDate);
    }
}
