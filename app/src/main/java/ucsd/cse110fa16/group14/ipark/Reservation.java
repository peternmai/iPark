package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.view.View;



public class Reservation extends AppCompatActivity {
    private static Button cancel;
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
