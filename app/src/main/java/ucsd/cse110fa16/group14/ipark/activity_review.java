package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class activity_review extends AppCompatActivity {


    private Firebase root;
    private static FirebaseAuth auth;


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        auth = FirebaseAuth.getInstance();
        final String userName = auth.getCurrentUser().getDisplayName();
        root = new Firebase("https://ipark-e243b.firebaseio.com/Users/"+userName+"/History");

        Button commentButt = (Button) findViewById(R.id.button);
        Button nopButt = (Button) findViewById(R.id.button3);

        // Get values passed on from previous activity
        final Bundle bundle = getIntent().getExtras();

        final TextView startTimeText = (TextView) findViewById(R.id.textView8);
        final TextView endTimeText = (TextView) findViewById(R.id.textView10);
        final TextView priceText = (TextView) findViewById(R.id.textView2);


        String clockInTime = generateTimeText(bundle.getInt("arriveHour"), bundle.getInt("arriveMin"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        root.child(date + " " + clockInTime).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                startTimeText.setText(dataSnapshot.child("Clockin").getValue(String.class));
                endTimeText.setText(dataSnapshot.child("Clockout").getValue(String.class));
                priceText.setText(dataSnapshot.child("Rate").getValue(String.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        commentButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_review.this, comment.class);
                startActivity(intent);
            }
        });

        nopButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_review.this, UserHomepage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    private String generateTimeText(int hour, int min) {
        String timeText;
        String am_pm_Text = (hour < 12) ? "AM" : "PM";

        // Format hour
        if (hour <= 12) {
            if (hour == 0)
                hour += 12;
            timeText = String.format("%02d", hour);
        } else {
            timeText = String.format("%02d", (hour - 12));
        }

        // Add colon, min, and AM/PM sign
        timeText = (timeText + ":" + String.format("%02d", min) + " " + am_pm_Text);


        return timeText;
    }
}

