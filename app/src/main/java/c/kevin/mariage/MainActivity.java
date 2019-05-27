package c.kevin.mariage;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.Arrays;
import java.util.Objects;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 123;
    private Button btnChange;
    private ImageView ivPhoto;
    private ImageView ivMusic;
    private ImageView ivPlace;
    private ImageView ivContact;
    private ImageView ivOther;
    private TextView tvSetProfil;
    private View fGuestNumber;
    private Boolean viewGuest=false;

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
        setProfil();
        fGuestNumber.setVisibility(View.VISIBLE);

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
    }

    // if a profil nul write set profil
    private void setProfil() {
        if (FirebaseAuth.getInstance().getCurrentUser() !=null){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference dbProfil=FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("profil");
            dbProfil.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                String nameMr = Objects.requireNonNull(dataSnapshot.child("nameMr").getValue()).toString();
                                String nameMme = Objects.requireNonNull(dataSnapshot.child("nameMme").getValue()).toString();
                                String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                                if (nameMr.isEmpty()) {
                                    tvSetProfil.setText(getString(R.string.set_name_groom));
                                    YoYo.with(Techniques.Pulse).duration(3000).repeat(10).playOn(tvSetProfil);
                                }
                                if (nameMme.isEmpty()) {
                                    tvSetProfil.setText(getString(R.string.set_name_bride));
                                    YoYo.with(Techniques.Pulse).duration(3000).repeat(10).playOn(tvSetProfil);
                                }
                                if (date.equals(getString(R.string.date))) {
                                    tvSetProfil.setText(getString(R.string.set_date));
                                    YoYo.with(Techniques.Pulse).duration(3000).repeat(10).playOn(tvSetProfil);
                                }
                            }else {
                                tvSetProfil.setText(getString(R.string.set_your_profil));
                                YoYo.with(Techniques.Pulse).duration(3000).repeat(10).playOn(tvSetProfil);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }
    }

    private void layout() {
        btnChange=findViewById(R.id.btnChange);
        ivPhoto=findViewById(R.id.ivPhoto);
        ivMusic=findViewById(R.id.ivMusic);
        ivPlace=findViewById(R.id.ivPlace);
        ivContact=findViewById(R.id.ivContact);
        ivOther=findViewById(R.id.ivOther);
        tvSetProfil=findViewById(R.id.tvSetProfil);
        fGuestNumber=findViewById(R.id.fGuestNumber);
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
                public void onCancelled(@NonNull DatabaseError databaseError) {}
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
            //dismiss or show the guest list
            viewGuest = !viewGuest;
            if (viewGuest) {
                fGuestNumber.setVisibility(View.INVISIBLE);
            }else fGuestNumber.setVisibility(View.VISIBLE);
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
