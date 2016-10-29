package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Payment extends AppCompatActivity {

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

        Button payButt = (Button) findViewById(R.id.button);
        Button cancelButt = (Button) findViewById(R.id.button3);

        // Get values passed on from previous activity
        final Bundle bundle = getIntent().getExtras();

        TextView startTimeText = (TextView) findViewById(R.id.startTimeText);
        TextView endTimeText = (TextView) findViewById(R.id.endTimeText);

        startTimeText.setText( generateTimeText( bundle.getInt("arriveHour"), bundle.getInt("arriveMin") ));
        endTimeText.setText( generateTimeText( bundle.getInt("departHour"), bundle.getInt("departMin") ));


        payButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Payment.this, CountDownCheckOut.class);
                intent.putExtra("arriveHour", bundle.getInt("arriveHour"));
                intent.putExtra("arriveMin", bundle.getInt("arriveMin"));
                intent.putExtra("departHour", bundle.getInt("departHour"));
                intent.putExtra("departMin", bundle.getInt("departMin"));
                startActivity(intent);
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

    @Override
    public void onBackPressed() {
    }
}
