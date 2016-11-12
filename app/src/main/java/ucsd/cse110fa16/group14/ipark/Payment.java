package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment extends AppCompatActivity {

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
        setContentView(R.layout.activity_payment);

        // Get values passed on from previous activity
        final Bundle bundle = getIntent().getExtras();

        //**************************************************************
        auth = FirebaseAuth.getInstance();
        final String userName = auth.getCurrentUser().getDisplayName();
        root = new Firebase("https://ipark-e243b.firebaseio.com/Users/"+userName);
        //***************************************************************

        Button payButt = (Button) findViewById(R.id.button);
        Button cancelButt = (Button) findViewById(R.id.button3);
        int totHours = 0;
        int totMins = 0;
        double rate = 2.50;
        double totPay = 0.00;

        // Calculate total time parked and total to pay
        totHours = bundle.getInt("departHour") - bundle.getInt("arriveHour");
        totMins = bundle.getInt("departMin") - bundle.getInt("arriveMin");
        totPay = ((double) (totHours + ((double) ((double) totMins / 60.0)))) * rate;

        TextView startTimeText = (TextView) findViewById(R.id.startTimeText);
        TextView endTimeText = (TextView) findViewById(R.id.endTimeText);
        TextView totalPayText = (TextView) findViewById(R.id.totalToPay);

        final TextView total = (TextView) findViewById(R.id.totalToPay);

        startTimeText.setText(generateTimeText(bundle.getInt("arriveHour"), bundle.getInt("arriveMin")));
        endTimeText.setText(generateTimeText(bundle.getInt("departHour"), bundle.getInt("departMin")));
        totalPayText.setText(String.format("$%.2f", totPay));


        payButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String rate = total.getText().toString();
                String clockInTime = generateTimeText(bundle.getInt("arriveHour"), bundle.getInt("arriveMin"));
                String clockOutTime = generateTimeText(bundle.getInt("departHour"), bundle.getInt("departMin"));
                //Date date = new Date();

                int clockInTimeInt = bundle.getInt("arriveHour") * 60 + bundle.getInt("arriveMin");
                int clockOutTimeInt = bundle.getInt("departHour") * 60 + bundle.getInt("departMin");

                String spotAssign = iLink.getSpot(clockInTimeInt, clockOutTimeInt);

                if( spotAssign == null ) {
                    AlertDialog.Builder respond = new AlertDialog.Builder(Payment.this);
                    respond.setTitle("PARKING LOT FULL");
                    respond.setMessage("Sorry, all spots are currently full. Please try again later.");
                    respond.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = respond.create();
                    alertDialog.show();
                }
                else {

                    //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    //sdf.format(date);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    try {
                        date = sdf.parse(sdf.format(new Date()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Firebase histChild = root.child("History");

                    Firebase hasChild = histChild.child(date + " " + clockInTime);

                    Firebase rateChild = hasChild.child("Rate");
                    Firebase clockInChild = hasChild.child("Clockin");
                    Firebase clockOutChild = hasChild.child("Clockout");
                    Firebase dateChild = hasChild.child("Date");
                    Firebase userChild = hasChild.child("User");

                    rateChild.setValue(rate);
                    clockInChild.setValue(clockInTime);
                    clockOutChild.setValue(clockOutTime);
                    dateChild.setValue(sdf.format(date));
                    userChild.setValue(userName);

                    // TODO: Needs to change this. It makes a new object on firebase
                    // iLink.changeStartTime( spotAssign, clockInTimeInt );
                    // iLink.changeEndTime( spotAssign, clockOutTimeInt );

                    Intent intent = new Intent(Payment.this, CountDownCheckOut.class);
                    intent.putExtra("arriveHour", bundle.getInt("arriveHour"));
                    intent.putExtra("arriveMin", bundle.getInt("arriveMin"));
                    intent.putExtra("departHour", bundle.getInt("departHour"));
                    intent.putExtra("departMin", bundle.getInt("departMin"));
                    intent.putExtra("spotAssign", spotAssign);
                    intent.putExtra("rate", rate);

                    startActivity(intent);
                }
            }
        });

        cancelButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, UserHomepage.class);
                startActivity(intent);
            }
        });


    }

    // Take in hour in 24 hour format and minute
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

    @Override
    public void onBackPressed() {
    }
}
