package ucsd.cse110fa16.group14.ipark;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class UserHomepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    protected static HashMap<String, String> infoMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        final Bundle bundle = getIntent().getExtras();

        ImageButton logoutButt = (ImageButton) findViewById(R.id.logoutButton);
        ImageButton reserveButt = (ImageButton) findViewById(R.id.reserveButton);
        ImageButton checkStatusButt = (ImageButton) findViewById(R.id.checkStatus);
        ImageButton viewMapButt = (ImageButton) findViewById(R.id.viewMap);
        ImageButton emergencyButt = (ImageButton) findViewById(R.id.emergency);
        ImageButton personalInfoButt = (ImageButton) findViewById(R.id.personalInfo);
        ImageButton reviewHistoryButt = (ImageButton) findViewById(R.id.reviewHistory);
        ImageButton helpButt = (ImageButton) findViewById(R.id.imageButton);
        ImageButton mail = (ImageButton) findViewById(R.id.imageButton5);

        // Update the last time a user was logged in and active. In charge of resetting database
        iLink.updateUserActivity();

        mAuth = FirebaseAuth.getInstance();

        String userName = "";

        // Get user reservation status
        if (mAuth.getCurrentUser() != null) {
            userName = mAuth.getCurrentUser().getDisplayName();
        }
        else
        {
            Intent intent = new Intent(UserHomepage.this, LoginPage.class);
            startActivity(intent);
        }

/*
        Firebase userReservationDB = new Firebase("https://ipark-e243b.firebaseio.com/Users/" + userName + "/ReservationStatus");
        userReservationDB.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                iLink.getUserReservationStatus();

                if( iLink.newMessages == true ) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(UserHomepage.this);
                    alert.setTitle("Alert!!");
                    alert.setMessage(
                            "You have new important unread message(s)!");
                    alert.setNegativeButton("Read Messages", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(UserHomepage.this, Messages.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
        */

        logoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder Quest = new AlertDialog.Builder(UserHomepage.this);
                Quest.setTitle("Log out");
                Quest.setMessage(
                        "Are you sure to log out?");
                Quest.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                // Update user reservation status
                iLink.getUserReservationStatus();
                int arriveHour = (int) iLink.userReservationStartTime / 60 / 60;
                int arriveMin = (int) (iLink.userReservationStartTime / 60) % 60;
                int departHour = (int) iLink.userReservationEndTime / 60 / 60;
                int departMin = (int) (iLink.userReservationEndTime / 60) % 60;
                String assignedSpot = iLink.userReservationSpot;
                double spotRate = iLink.userReservationSpotRate;

                Intent intent = new Intent(UserHomepage.this, Clockin.class);
                intent.putExtra("arriveHour", arriveHour);
                intent.putExtra("arriveMin", arriveMin);
                intent.putExtra("departHour", departHour);
                intent.putExtra("departMin", departMin);
                intent.putExtra("spotAssign", assignedSpot);
                startActivity(intent);
            }
        });

        checkStatusButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Update user reservation status
                iLink.getUserReservationStatus();
                System.out.println("Checking");
                int arriveHour = (int) iLink.userReservationStartTime / 60 / 60;
                int arriveMin = (int) (iLink.userReservationStartTime / 60) % 60;
                int departHour = (int) iLink.userReservationEndTime / 60 / 60;
                int departMin = (int) (iLink.userReservationEndTime / 60) % 60;
                String assignedSpot = iLink.userReservationSpot;
                double spotRate = iLink.userReservationSpotRate;

                Intent intent = new Intent(UserHomepage.this, CountDownCheckOut.class);
                intent.putExtra("arriveHour", arriveHour);
                intent.putExtra("arriveMin", arriveMin);
                intent.putExtra("departHour", departHour);
                intent.putExtra("departMin", departMin);
                intent.putExtra("spotAssign", assignedSpot);
                startActivity(intent);
            }
        });

        viewMapButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, MapDirectional.class);
                ProgressDialog progress = new ProgressDialog(UserHomepage.this);
                progress.show();
                progress.setMessage("Loading....");
                startActivity(intent);
                progress.dismiss();
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if mail button pressed, send user to Message page
                Intent intent = new Intent(UserHomepage.this, Messages.class);
                startActivity(intent);
            }
        });

        emergencyButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if emergencyButt pressed, send user to Emergency page
                Intent intent = new Intent(UserHomepage.this, Emergency.class);
                startActivity(intent);
            }
        });

        personalInfoButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String currUser = mAuth.getCurrentUser().getDisplayName(); // get the current user

                //Get the hashMap with the details of a user
                infoMap = iLink.getChildInfo("Users", currUser);
                Intent intent = new Intent(UserHomepage.this, PersonalInfo.class);
                startActivity(intent);
            }
        });

        reviewHistoryButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if reviewHistory button pressed, send user to UserReviewHistory page
                Intent intent = new Intent(UserHomepage.this, UserReviewHistory.class);
                startActivity(intent);
            }
        });

        helpButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if helpButt pressed, send user to HELP_USER page
                Intent intent = new Intent(UserHomepage.this, HELP_USER.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
