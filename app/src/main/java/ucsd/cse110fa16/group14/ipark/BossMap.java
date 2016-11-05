package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

/**
 * Created by Mag on 10/14/2016.
 */

public class BossMap extends AppCompatActivity {

    Firebase parklot;


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
        setContentView(R.layout.activity_boss_map);

        // help button and status of the parking lot
        Button helpButt = (Button) findViewById(R.id.help);
        Button statusButt = (Button) findViewById(R.id.status);




        /* information page */
        helpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(BossMap.this);
                hlp.setTitle("Instruction");
                hlp.setMessage("\t\t\t\tClick to change parking spot status.\n" +
                        "\t\t\t\tLong press to show detail information of parking spot.\n" +
                        "\t\t\t\tFree parking in white, available reserved parking in green, " +
                        "occupied reserved parking in yellow, illegal parking in red.\n");
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });


        /* status of parking lot */
        statusButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(BossMap.this);
                hlp.setTitle("Parking Status");

                int freeParking = 0;
                int availableReserve = 1;
                int occupied = 2;
                int illegalParking = 3;

                CharSequence a = String.valueOf(freeParking);
                CharSequence b = String.valueOf(availableReserve);
                CharSequence c = String.valueOf(occupied);
                CharSequence d = String.valueOf(illegalParking);

                hlp.setMessage("Free parking: " + a +
                        "\nAvailable reserve: " + b +
                        "\nOccupied reserve: " + c +
                        "\nIllegal parking: " + d + "\n");

                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });
    }
}
