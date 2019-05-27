package c.kevin.mariage;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class UserMainFragment extends AppCompatDialogFragment {

    private TextView tvNameMr;
    private TextView tvNameMme;
    private TextView tvDateFrench;
    private TextView tvDay;
    private TextView tvHours;
    private TextView tvMinute;

    private Calendar c=Calendar.getInstance();
    private int currentYear=c.get(Calendar.YEAR);
    private int currentMonth=c.get(Calendar.MONTH)+1;
    private int currentDay=c.get(Calendar.DAY_OF_MONTH);
    private int currentHour=c.get(Calendar.HOUR_OF_DAY);
    private int currentMinute=c.get(Calendar.MINUTE);

    public UserMainFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout(view);
        fetch();
    }

    //get profile from data base yoyo = animation
    private void fetch() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference dbProfil = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("profil");

            dbProfil.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("nameMr").getValue() != null) {
                        String nameMr = Objects.requireNonNull(dataSnapshot.child("nameMr").getValue()).toString();
                        tvNameMr.setText(nameMr);
                        YoYo.with(Techniques.DropOut).playOn(tvNameMr);

                    }else {
                        tvNameMr.setText("");
                    }
                    if (dataSnapshot.child("nameMme").getValue() != null) {
                        String nameMme = Objects.requireNonNull(dataSnapshot.child("nameMme").getValue()).toString();
                        tvNameMme.setText(nameMme);
                        YoYo.with(Techniques.DropOut).playOn(tvNameMme);
                    }else {
                        tvNameMme.setText("");
                    }
                    if (dataSnapshot.child("date").getValue() != null) {
                        String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                        tvDateFrench.setText(date);
                        YoYo.with(Techniques.Landing).playOn(tvDateFrench);
                    }else {
                        tvDateFrench.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //get date selected
            DatabaseReference dbDateSelected = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("profil");
            dbDateSelected.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    // get curent date
                    String dateToday = currentDay + "/"
                            + currentMonth + "/"
                            + currentYear + " "
                            + currentHour + ":"
                            + currentMinute;
                    //get date selected
                    String dateSelectedOnTv = tvDateFrench.getText().toString();
                    String dateSelected = dateSelectedOnTv + " " + "20:30";

                    // set compte a rebour

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    try {
                        Date dDateToday = simpleDateFormat.parse(dateToday);
                        Date dDateSelected = simpleDateFormat.parse(dateSelected);

                        long differenceDay = dDateSelected.getTime() - dDateToday.getTime();
                        String restOfDay = String.valueOf(Math.abs((differenceDay / (1000 * 60 * 60 * 24))));

                        String restOfHour = String.valueOf(Math.abs(dDateSelected.getHours() - dDateToday.getHours()));
                        String restOfMinute = String.valueOf(Math.abs(dDateSelected.getMinutes() - dDateToday.getMinutes()));

                        tvDay.setText(restOfDay);
                        tvHours.setText(restOfHour);
                        tvMinute.setText(restOfMinute);

                        //animation
                        YoYo.with(Techniques.RollIn).playOn(tvDay);
                        YoYo.with(Techniques.RollIn).playOn(tvHours);
                        YoYo.with(Techniques.RollIn).playOn(tvMinute);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
    }

    private void layout(View view) {
        tvNameMr=view.findViewById(R.id.tvNameMr);
        tvNameMme=view.findViewById(R.id.tvNameMme);
        tvDateFrench=view.findViewById(R.id.tvDateFrench);
        tvDay=view.findViewById(R.id.tvDay);
        tvHours=view.findViewById(R.id.tvHours);
        tvMinute=view.findViewById(R.id.tvMinute);
    }
}
