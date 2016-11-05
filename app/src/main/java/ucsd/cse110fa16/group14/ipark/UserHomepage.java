package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * Created by Mag on 10/19/2016.
 */

public class UserHomepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListenter;
    protected static HashMap<String,String> infoMap= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);



        ImageButton logoutButt = (ImageButton) findViewById(R.id.logoutButton);
        ImageButton reserveButt = (ImageButton) findViewById(R.id.reserveButton);
        ImageButton checkStatusButt = (ImageButton) findViewById(R.id.checkStatus);
        ImageButton viewMapButt = (ImageButton) findViewById(R.id.viewMap);
        ImageButton emergencyButt = (ImageButton) findViewById(R.id.emergency);
        ImageButton personalInfoButt = (ImageButton) findViewById(R.id.personalInfo);
        ImageButton reviewHistoryButt = (ImageButton) findViewById(R.id.reviewHistory);
        ImageButton helpButt = (ImageButton) findViewById(R.id.imageButton);

        mAuth = FirebaseAuth.getInstance();

        logoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder Quest = new AlertDialog.Builder(UserHomepage.this);
                Quest.setTitle("Log out");
                Quest.setMessage(
                        "Are you sure to log out?");
                Quest.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                Quest.setNegativeButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UserHomepage.this, LoginPage.class);
                        FirebaseAuth.getInstance().signOut();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Toast.makeText(UserHomepage.this, "Logout Successful", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    }
                });
                AlertDialog alertDialog = Quest.create();
                alertDialog.show();


            }
        });

        reserveButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, Clockin.class);
                startActivity(intent);
            }
        });

        checkStatusButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, CountDownCheckOut.class);

                final Bundle bundle = getIntent().getExtras();
                Intent thisIntent = getIntent();
                if(thisIntent.hasExtra("arriveHour") && thisIntent.hasExtra("departHour")) {
                    intent.putExtra("arriveHour", bundle.getInt("arriveHour"));
                    intent.putExtra("arriveMin", bundle.getInt("arriveMin"));
                    intent.putExtra("departHour", bundle.getInt("departHour"));
                    intent.putExtra("departMin", bundle.getInt("departMin"));
                }
                else {
                    intent.putExtra("arriveHour", 0);
                    intent.putExtra("arriveMin", 0);
                    intent.putExtra("departHour", 0);
                    intent.putExtra("departMin", 0);
                }
                startActivity(intent);
            }
        });

        viewMapButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, MapDirectional.class);
                startActivity(intent);
            }
        });

        emergencyButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, Emergency.class);
                startActivity(intent);
            }
        });

        personalInfoButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String currUser = mAuth.getCurrentUser().getDisplayName();
                infoMap = iLink.getPersonalInfoFromFirebase("Users",currUser);
                Intent intent = new Intent(UserHomepage.this, PersonalInfo.class);
                startActivity(intent);
            }
        });

        reviewHistoryButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, UserReviewHistory.class);
                startActivity(intent);
            }
        });

        helpButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, HELP_USER.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
    }
}
