package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class Reservation extends AppCompatActivity {

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
        setContentView(R.layout.activity_reservation);

        Button reserveButton = (Button) findViewById(R.id.resetButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        reserveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation.this, Clockin.class);
                startActivity(intent);
            }
        });


    }

}
