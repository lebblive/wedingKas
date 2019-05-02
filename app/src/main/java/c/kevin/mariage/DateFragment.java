package c.kevin.mariage;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends AppCompatDialogFragment {

    EditText etNameMr;
    EditText etNameMme;
    TextView tvDate;
    CalendarView cvDate;
    Button btnSaveD;

    String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static final String EXTRA_NAMEMR="nameMr";
    public static final String EXTRA_NAMEMME="nameMmme";
    public static final String EXTRA_DATE="date";



    public DateFragment() {
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
        setProfilDate();


        btnSaveD.setOnClickListener(v -> {
            saveD();
        });

    }

    // get UI date
    private void setProfilDate() {



        cvDate.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            int yearSelected = year;
            int monthSelected = month+1; // because 0=january
            int dayOfMonthSelected = dayOfMonth;
            String date=year+"/"+monthSelected+"/"+dayOfMonth;


            DatabaseReference databaseReference=
                    FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("dateSelected");
            Map<String,Object> mapDate=new HashMap<>();
            mapDate.put("month",monthSelected);
            mapDate.put("dayOfMonth",dayOfMonthSelected);
            mapDate.put("date",date);
            databaseReference.setValue(mapDate);
            System.out.println("HashMap  : "+mapDate);




//            String date=dayOfMonth+"/"+month+"/"+year;

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





        //set name of couple and date
//        Intent intent=new Intent(getContext(),MainActivity.class);
//        intent.putExtra(EXTRA_NAMEMR,nameMr);
//        intent.putExtra(EXTRA_NAMEMME,nameMme);
//        intent.putExtra(EXTRA_DATE,date);
//
//        startActivity(intent);

    }

    private void layout(View view) {
        etNameMr=view.findViewById(R.id.etNameMr);
        etNameMme=view.findViewById(R.id.etNameMme);
        tvDate=view.findViewById(R.id.tvDate);
        btnSaveD=view.findViewById(R.id.btnSaveD);
        cvDate=view.findViewById(R.id.cvDate);
    }
}
