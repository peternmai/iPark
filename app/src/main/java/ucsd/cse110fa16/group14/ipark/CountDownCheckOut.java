package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CountDownCheckOut extends AppCompatActivity {
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
        setContentView(R.layout.activity_count_down_check_out);

        Button checkoutButt = (Button) findViewById(R.id.button6);
        Button reportButt = (Button) findViewById(R.id.button2);
        Button emergencyButt = (Button) findViewById(R.id.emer);
        // we need to figure out how to report emergency, add that activity
        Button mapButt = (Button) findViewById(R.id.button8);
        Button emergButton = (Button) findViewById(R.id.emer);

        checkoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, activity_review.class);
                startActivity(intent);
            }
        });

        reportButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, Clockin.class);
                startActivity(intent);
            }
        });

        mapButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, MapDirectional.class);
                startActivity(intent);
            }
        });

        emergButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, BossEmergency.class);
                startActivity(intent);
            }
        });

    }
}
