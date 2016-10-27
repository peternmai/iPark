package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mag on 10/19/2016.
 */

public class UserHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        auth = FirebaseAuth.getInstance();
        ImageButton logoutButt = (ImageButton) findViewById(R.id.logoutButton);
        ImageButton reserveButt = (ImageButton) findViewById(R.id.reserveButton);
        ImageButton checkStatusButt = (ImageButton) findViewById(R.id.checkStatus);
        ImageButton viewMapButt = (ImageButton) findViewById(R.id.viewMap);
        ImageButton emergencyButt = (ImageButton) findViewById(R.id.emergency);
        ImageButton personalInfoButt = (ImageButton) findViewById(R.id.personalInfo);
        ImageButton reviewHistoryButt = (ImageButton) findViewById(R.id.reviewHistory);

        logoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomepage.this, LoginPage.class);
                FirebaseAuth.getInstance().signOut();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(UserHomepage.this, "Logout Successful", Toast.LENGTH_LONG).show();
                startActivity(intent);
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
                Intent intent = new Intent(UserHomepage.this, BossEmergency.class);
                startActivity(intent);
            }
        });

        personalInfoButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
    }
}
