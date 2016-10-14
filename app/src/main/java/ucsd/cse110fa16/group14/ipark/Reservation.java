package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.view.View;



public class Reservation extends AppCompatActivity {
    private static Button cancel;

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
