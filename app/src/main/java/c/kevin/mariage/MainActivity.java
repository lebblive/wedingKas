package c.kevin.mariage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 123;
    TextView tvNameMr;
    TextView tvNameMme;
    TextView tvDateFrench;
    TextView tvDay;
    TextView tvHours;
    TextView tvMinute;
    Button btnChange;

    Calendar c=Calendar.getInstance();

    int currentYear=c.get(Calendar.YEAR);
    int currentMonth=c.get(Calendar.MONTH)+1;
    int currentDay=c.get(Calendar.DAY_OF_MONTH);
    int currentHour=c.get(Calendar.HOUR_OF_DAY);
    int currentMinute=c.get(Calendar.MINUTE);
     int name=0;
     int man=0;
    int woman=0;
    int old =0;
    int adult=0;
    int young =0;


//    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        login();
        layout();
        btnChange.setOnClickListener(v -> {
            ProfilFragment profilFragment =new ProfilFragment();
            profilFragment.show(getSupportFragmentManager(),"ProfilFragment");
        });

        fetch();
        getNumberPerson();

    }

    private void getNumberPerson() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        if (FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("contact") != null) {


            DatabaseReference dbRangerContact = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("contact");


            dbRangerContact.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList arrayList=new ArrayList();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String pushKey = snapshot.getKey();
                        arrayList.add(pushKey);

                    }
//                    System.out.println(arrayList);
//                    System.out.println("size "+arrayList.size());
//                    System.out.println("1 "+arrayList.get(1).toString());
                    for (int i=0;i<arrayList.size();i++){
                        String idC= (String) arrayList.get(i);
//                        System.out.println(idC);
                        DatabaseReference dbNumber=FirebaseDatabase.getInstance().getReference()
                                .child("users").child(uid).child("contact").child(idC);
                        dbNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                String dsFirstName = (String) dataSnapshot.child("firstName").getValue();
                                String dsFamilyName = (String) dataSnapshot.child("familyName").getValue();
                                String dsPhone = (String) dataSnapshot.child("phone").getValue();

                                String dsSexe = (String) dataSnapshot.child("sexe").getValue();
                                String dsAge = (String) dataSnapshot.child("age").getValue();

                                String dsNoteContact = (String) dataSnapshot.child("noteContact").getValue();

                                if (dsFirstName.length()!=0 || dsFamilyName.length()!=0){
                                    name++;

                                }else {
                                    System.out.println("777");
                                }
                                if (dsSexe.equals("Man")){
                                    man ++;
                                }else if (dsSexe.equals("woman")){
                                    woman ++;
                                }

                                /*
                                TODO : continuer le conteur 
                                 */
//
//                                System.out.println("3 "+dsFirstName);
//                                System.out.println("4 "+dsFamilyName);
//                                System.out.println("5 "+dsPhone);
//                                System.out.println("6 "+dsSexe);
//                                System.out.println("7 "+dsAge);
//                                System.out.println("8 "+dsNoteContact);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            System.out.println("name "+ name);
            System.out.println("man " +man);
            System.out.println("woman " +woman);
        }else {
            System.out.println("coucouc");
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
                    if (dataSnapshot != null) {
                        if (dataSnapshot.child("nameMr").getValue() != null) {
                            String nameMr = dataSnapshot.child("nameMr").getValue().toString();
                            tvNameMr.setText(nameMr);
                        }
                        if (dataSnapshot.child("nameMme").getValue() != null) {
                            String nameMme = dataSnapshot.child("nameMme").getValue().toString();
                            tvNameMme.setText(nameMme);
                        }
                        if (dataSnapshot.child("date").getValue() != null) {
                            String date = dataSnapshot.child("date").getValue().toString();
                            tvDateFrench.setText(date);
                        }
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

//                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    try {
                        Date dDateToday = simpleDateFormat.parse(dateToday);
                        Date dDateSelected = simpleDateFormat.parse(dateSelected);

                        long differenceDay = dDateSelected.getTime() - dDateToday.getTime();
                        String restOfDay = String.valueOf(Math.abs((differenceDay / (1000 * 60 * 60 * 24))));

                        String restOfHour = String.valueOf(Math.abs(dDateSelected.getHours() - dDateToday.getHours()));
                        String restOfMinute = String.valueOf(Math.abs(dDateSelected.getMinutes() - dDateToday.getMinutes()));

                        tvDay.setText(restOfDay + " DAY");
                        tvHours.setText(restOfHour + " HOUR");
                        tvMinute.setText(restOfMinute + " MINUTE");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            login();
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
            Todo : quand jouvre un user je veut pas que sa seprime
            tout se quil y a dedans
             */

            String dname=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("users").child(uid);

            ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    User user = dataSnapshot.getValue(User.class);
                    if (dataSnapshot.exists()){
//                        String usid=user.getUid();
//                        System.out.println(usid);
                    }else {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.AnonymousBuilder().build()))
                            .setLogo(R.drawable.flower)
                            .build(),
                    RC_SIGN_IN);
        }
    }
    private void goOut(){
        AuthUI.getInstance().signOut(this).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        login();
                    }
                }
        );
    }

}
