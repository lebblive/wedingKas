package c.kevin.mariage;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 123;
    private TextView tvNameMr;
    private TextView tvNameMme;
    private TextView tvDateFrench;
    private TextView tvDay;
    private TextView tvHours;
    private TextView tvMinute;
    private Button btnChange;
    private TextView tvResume;
    private ImageView ivPhoto;
    private ImageView ivMusic;
    private ImageView ivPlace;
    private ImageView ivContact;
    private ImageView ivOther;
    private TextView tvSetProfil;

    Calendar c=Calendar.getInstance();

    private int currentYear=c.get(Calendar.YEAR);
    private int currentMonth=c.get(Calendar.MONTH)+1;
    private int currentDay=c.get(Calendar.DAY_OF_MONTH);
    private int currentHour=c.get(Calendar.HOUR_OF_DAY);
    private int currentMinute=c.get(Calendar.MINUTE);
    private int man=0;
    private int woman=0;
    private int old =0;
    private int adult=0;
    private int young =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        login();
        layout();
        tvNameMr.setOnClickListener(v -> {
            ProfilFragment profilFragment = new ProfilFragment();
            profilFragment.show(getSupportFragmentManager(), "ProfilFragment");
        });
        tvNameMme.setOnClickListener(v -> {
            ProfilFragment profilFragment = new ProfilFragment();
            profilFragment.show(getSupportFragmentManager(), "ProfilFragment");
        });
        tvDateFrench.setOnClickListener(v -> {
                    ProfilFragment profilFragment = new ProfilFragment();
                    profilFragment.show(getSupportFragmentManager(), "ProfilFragment");
                });
        btnChange.setOnClickListener(v -> {
            ProfilFragment profilFragment =new ProfilFragment();
            profilFragment.show(getSupportFragmentManager(),"ProfilFragment");
        });
        ivPhoto.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),FotoActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this,ivPhoto,"");
            startActivity(intent,transition.toBundle());
        });
        ivMusic.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),MusicActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this,ivMusic,"");
            startActivity(intent,transition.toBundle());
        });
        ivPlace.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),PlaceActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this,ivPlace,"");
            startActivity(intent,transition.toBundle());
        });
        ivContact.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),ContactListActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this,ivContact,"");
            startActivity(intent,transition.toBundle());
        });
        ivOther.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),OtherActivity.class);
            ActivityOptions transition=ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this,ivOther,"");
            startActivity(intent,transition.toBundle());
        });
        fetch();
        getNumberPerson();
    }

    private void getNumberPerson() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference dbRangerContact = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("contact");

            dbRangerContact.addListenerForSingleValueEvent(new ValueEventListener() {
                // church in db all contact and give me ther children
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> arrayList= new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String pushKey = snapshot.getKey();
                        arrayList.add(pushKey);
                    }
                    getIdContact(arrayList);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
                //end recuperation

                //get name one once id contact
                private void getIdContact(ArrayList<String> arrayList) {
                    String idC;
                    for (int i = 0; i < arrayList.size(); i++) {
                        idC = arrayList.get(i);

                        //rentre dans la liste des contact et recuper toute les info
                        DatabaseReference dbNumber = FirebaseDatabase.getInstance().getReference()
                                .child("users").child(uid).child("contact").child(idC);
                        dbNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String dsSexe = (String) dataSnapshot.child("sexe").getValue();
                                String dsAge = (String) dataSnapshot.child("age").getValue();

                                //je comence ici
                                assert dsSexe != null;
                                if (dsSexe.equals(getString(R.string.man))) {
                                    man++;
                                }
                                if (dsSexe.equals(getString(R.string.woman))){
                                    woman++;
                                }
                                assert dsAge != null;
                                if (dsAge.equals(getString(R.string.old_person))){
                                    old++;
                                }
                                if (dsAge.equals(getString(R.string.adult_person))){
                                    adult++;
                                }
                                if (dsAge.equals(getString(R.string.young_person))){
                                    young++;
                                }

                                String resume=getString(R.string.Persons_number)+" " + arrayList.size()+ " : ";

                                if (man!=0){
                                    resume=resume+ "\n"+ man +" " +getString(R.string.mans);
                                }
                                if (woman!=0){
                                    resume=resume+"\n"+
                                            woman +" " +getString(R.string.womans);
                                }
                                if (old!=0){
                                    resume=resume+"\n"+
                                            old +" " +getString(R.string.old_persons);
                                }
                                if (adult!=0){
                                    resume=resume+"\n"+
                                            adult +" " +getString(R.string.adult_persons);
                                }
                                if (young!=0){
                                    resume=resume+"\n"+
                                            young +" " +getString(R.string.young_persons);
                                }
                                tvResume.setText(resume);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });
                    }
                }
            });

        }
    }



    //get profile from data base
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
                        tvSetProfil.setText(getString(R.string.set_your_profil));
                    }
                    if (dataSnapshot.child("nameMme").getValue() != null) {
                        String nameMme = Objects.requireNonNull(dataSnapshot.child("nameMme").getValue()).toString();
                        tvNameMme.setText(nameMme);
                        YoYo.with(Techniques.DropOut).playOn(tvNameMme);
                    }else {
                        tvNameMme.setText("");
                        tvSetProfil.setText(getString(R.string.set_your_profil));

                    }
                    if (dataSnapshot.child("date").getValue() != null) {
                        String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                        tvDateFrench.setText(date);
                        YoYo.with(Techniques.Landing).playOn(tvDateFrench);
                    }else {
                        tvDateFrench.setText("");
                        tvSetProfil.setText(getString(R.string.set_your_profil));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //get dateselected
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

    private void layout() {
        tvNameMr=findViewById(R.id.tvNameMr);
        tvNameMme=findViewById(R.id.tvNameMme);
        tvDateFrench=findViewById(R.id.tvDateFrench);
        tvDay=findViewById(R.id.tvDay);
        tvHours=findViewById(R.id.tvHours);
        tvMinute=findViewById(R.id.tvMinute);
        btnChange=findViewById(R.id.btnChange);
        tvResume=findViewById(R.id.tvResume);
        ivPhoto=findViewById(R.id.ivPhoto);
        ivMusic=findViewById(R.id.ivMusic);
        ivPlace=findViewById(R.id.ivPlace);
        ivContact=findViewById(R.id.ivContact);
        ivOther=findViewById(R.id.ivOther);
        tvSetProfil=findViewById(R.id.tvSetProfil);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN && resultCode==RESULT_OK){

            /*
            je cree un tableau du nom de users
            dans lequel je met le id et dans le id
            je met son nom son mail et son id
            */

            /*
            tout se quil y a dedans
             */

            String dname= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
            String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("users").child(uid);

            ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        databaseReference.setValue(new User(dname, email, uid));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }


     }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.photo) {
            Intent intent = new Intent(getApplicationContext(),FotoActivity.class);
            startActivity(intent);
        } else if (id == R.id.salle) {
            Intent intent = new Intent(getApplicationContext(),PlaceActivity.class);
            startActivity(intent);

        } else if (id == R.id.music) {
            Intent intent = new Intent(getApplicationContext(),MusicActivity.class);
            startActivity(intent);
        } else if (id == R.id.other) {
            Intent intent = new Intent(getApplicationContext(),OtherActivity.class);
            startActivity(intent);
        } else if (id == R.id.list) {
            Intent intent=new Intent(getApplicationContext(),ContactListActivity.class);
            startActivity(intent);
        } else if (id == R.id.disconect) {
            goOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void login() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            startActivityForResult(
                    AuthUI.getInstance().
                            createSignInIntentBuilder().
                            setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build()))
                            .setLogo(R.drawable.flower)
                            .build(),
                    RC_SIGN_IN);
        }
    }
    private void goOut(){
        AuthUI.getInstance().signOut(this).addOnSuccessListener(
                aVoid -> login()
        );
    }

}
