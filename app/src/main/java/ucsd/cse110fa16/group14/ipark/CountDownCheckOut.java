package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
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

        //android.app.ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        /*public boolean onOptionsItemSelected(MenuItem menu) {
            Intent myIntent = new Intent(getApplicationContext(), UserHomepage.class);
            startActivityForResult(myIntent, 0);
            return true;

        }*/

        Button checkoutButt = (Button) findViewById(R.id.button6);
        Button reportButt = (Button) findViewById(R.id.button2);
        Button emergencyButt = (Button) findViewById(R.id.emer);
        Button mapButt = (Button) findViewById(R.id.button8);
        Button help = (Button) findViewById(R.id.button16);





        /* information page */
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(CountDownCheckOut.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("\t\t\t\tTimer starts at the arrival time entered before.\n" +
                        "\t\t\t\tClick 'CHECKOUT' to sign out and end your reservation.\n"+
                        "\t\t\t\tClick 'REPORT' if there is a car in your spot, " +
                        "and you will receive a new parking space.\n"
                        + "\t\t\t\tClick 'MAP' to view the map of parking lot.\n" +
                        "\t\t\t\tClick 'EMERGENCY' in case of any emergency.");
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });

                //hlp.setNegativeButton("No", null);
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
                AlertDialog.Builder respond = new AlertDialog.Builder(CountDownCheckOut.this);
                respond.setTitle("Successful Report");
                respond.setMessage("\t\t\t\tYour report has been successfully recorded.\n" +
                        "\t\t\t\tA new parking spot has been assigned to you. " +
                        "We apologize for the inconvenience.\n" +
                        "\t\t\t\tA reward will soon be delivered to your account.\n" +
                        "\t\t\t\tYou can view this activity in account history now.\n" );
                respond.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // need to assign the user another parking number from the system
                        // TO DO


                        dialog.cancel();
                    }

                });


                AlertDialog alertDialog = respond.create();
                alertDialog.show();
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
}
