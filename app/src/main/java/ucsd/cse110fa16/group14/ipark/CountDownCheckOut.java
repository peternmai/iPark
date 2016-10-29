package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.ActionBar;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CountDownCheckOut extends AppCompatActivity {

    private int mProgressStatus;

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        final Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent(CountDownCheckOut.this, UserHomepage.class);
        intent.putExtra("arriveHour", bundle.getInt("arriveHour"));
        intent.putExtra("arriveMin", bundle.getInt("arriveMin"));
        intent.putExtra("departHour", bundle.getInt("departHour"));
        intent.putExtra("departMin", bundle.getInt("departMin"));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_check_out);

        Button checkoutButt = (Button) findViewById(R.id.button6);
        Button reportButt = (Button) findViewById(R.id.button2);
        Button emergencyButt = (Button) findViewById(R.id.emer);
        Button mapButt = (Button) findViewById(R.id.button8);
        Button help = (Button) findViewById(R.id.button16);

        // Get values passed on from previous activity
        final Bundle bundle = getIntent().getExtras();
        final Handler mHandler = new Handler();

        TextView startTimeText = (TextView) findViewById(R.id.StartTime);
        TextView endTimeText = (TextView) findViewById(R.id.EndTime);
        final TextView title = (TextView) findViewById(R.id.TimeRemainingTitle);
        final TextView timerText = (TextView) findViewById(R.id.Timer);

        startTimeText.setText( generateTimeText( bundle.getInt("arriveHour"), bundle.getInt("arriveMin") ));
        endTimeText.setText( generateTimeText( bundle.getInt("departHour"), bundle.getInt("departMin") ));


        // Express current, start, and end time in seconds
        final long curTimeInSec = getCurrentTimeInSec();
        final long startTimeInSec = ( (bundle.getInt("arriveHour") * 60) + bundle.getInt("arriveMin")) * 60;
        final long endTimeInSec = ( (bundle.getInt("departHour") * 60) + bundle.getInt("departMin")) * 60;

        // Set initial condition of progress bar
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.ProgressBar);
        mProgress.setMax( (int) (startTimeInSec-endTimeInSec) );



        // Updates the timer every 1 second from current time
        new CountDownTimer( (endTimeInSec - curTimeInSec) * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                // Display a countdown until start
                boolean beforeStartTime = false;
                if( (millisUntilFinished/1000) > (endTimeInSec - startTimeInSec) ) {
                    millisUntilFinished -= (endTimeInSec - startTimeInSec) * 1000;
                    title.setText("Time Until Start");
                    timerText.setTextColor(Color.RED);
                }
                else {
                    title.setText("Time Remaining");
                    timerText.setTextColor(Color.BLUE);
                    mProgress.setProgress(0);
                }

                // Display a countdown until time's up (after time start)

                long t_sec, t_min, t_hour;
                long timeLeft = millisUntilFinished/1000;       // Gives Seconds

                t_sec = timeLeft%60;
                timeLeft /= 60;                                 // Gives minutes

                t_min = timeLeft%60;
                t_hour = timeLeft/60;                           // Gives hours


                timerText.setText(String.format("%02d:%02d:%02d", t_hour, t_min, t_sec) );
            }
            public void onFinish() {
                timerText.setText("00:00:00");
                timerText.setTextColor(Color.RED);
            }
        }.start();

        /* CODE NOT WORKING
        // Update status bar once start time has begun
        new Thread(new Runnable() {
            public void run() {
                while (   mProgressStatus < 100) {

                    // If start timer hasn't start, progress bar at 100%. Else calculate percentage
                    if( getCurrentTimeInSec() < startTimeInSec )
                        mProgressStatus = 0;
                    else
                        mProgressStatus = (int) ( getCurrentTimeInSec() - startTimeInSec );


                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start(); */



        /* information page */

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(CountDownCheckOut.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("Timer will start when at the time you put for Clock In.\n" +
                        "Click 'CHECKOUT' at any time to sign out and end your reservation.\n"+
                        "Click 'REPORT' to report illegal parking if there is a car in your spot. " +
                        "You will recieve a new parking space\n"
                        + " 'MAP' will give you a map of where your space is.\n" +
                        " 'EMERGENCY' is to call the police in case of an emergency.");
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });

                hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });


        /* check out and go to review */
        checkoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, activity_review.class);
                startActivity(intent);

            }
        });


        /* report your own spot */
        reportButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, ReportIllegal.class);
                startActivity(intent);

            }
        });


        /* go to map */
        mapButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, MapDirectional.class);
                startActivity(intent);

            }
        });


        /* emergency call */
        emergencyButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, BossEmergency.class);
                startActivity(intent);

            }
        });



    }

    private String generateTimeText(int hour, int min) {
        String timeText;
        String am_pm_Text = (hour < 12)?"AM":"PM";

        // Format hour
        if( hour <= 12 ) {
            if( hour == 0 )
                hour += 12;
            timeText = String.format("%02d", hour);
        }
        else  {
            timeText = String.format("%02d", (hour-12) );
        }

        // Add colon, min, and AM/PM sign
        timeText = ( timeText + ":" + String.format("%02d", min) + " " + am_pm_Text);


        return timeText;
    }

    private long getCurrentTimeInSec() {
        Date date = new Date();                               // given date
        Calendar calendar = GregorianCalendar.getInstance();  // creates a new calendar instance
        calendar.setTime(date);                               // assigns calendar to given date
        long curHour = calendar.get(Calendar.HOUR_OF_DAY);    // gets hour in 24h format
        long curMin  = calendar.get(Calendar.MINUTE);         // get cur minute
        long curSec  = calendar.get(Calendar.SECOND);         // get cur second

        return ((curHour * 60) + curMin) * 60 + curSec;
    }
}
