package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dennis on 10/31/2016.
 */

public class Emergency extends AppCompatActivity {

    private Firebase root;
    Firebase hasChild;
    RadioGroup radioGroup;
    RadioButton selected;
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
        setContentView(R.layout.activity_emergency);

        root = new Firebase("https://ipark-e243b.firebaseio.com/Emergency");
        final Bundle bundle = getIntent().getExtras();

        Button sendButt = (Button) findViewById(R.id.button25);
        Button cancelButt = (Button) findViewById(R.id.button23);
        Button helpButt = (Button) findViewById(R.id.button28);

        radioGroup = (RadioGroup) findViewById(R.id.emergencyRadioGroup);
        final EditText parkingNum = (EditText) findViewById(R.id.parkingNumber);
        auth = FirebaseAuth.getInstance();
        final String userName = auth.getCurrentUser().getDisplayName();


        /* emergency sent */
        sendButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(Emergency.this);
                hlp.setTitle("Emergency Reported");

                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

                //////////////////////////////////////////////////////
                int selectedButton = radioGroup.getCheckedRadioButtonId();
                selected = (RadioButton) findViewById(selectedButton);
                String selectedbButton = selected.getText().toString();
                String parkingNumber = parkingNum.getText().toString();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.format(date);

                hasChild = root.child(date + " ");
                Firebase emergencyType = hasChild.child("EmergencyType");
                Firebase dateChild = hasChild.child("Date");
                Firebase parkingNumChild = hasChild.child("ParkingNumber");
                Firebase userChild = hasChild.child("User");

                emergencyType.setValue(selectedbButton);
                dateChild.setValue(sdf.format(date));
                if (parkingNumber.isEmpty()) {
                    parkingNumChild.setValue("N/A");
                } else {
                    parkingNumChild.setValue(parkingNumber);
                }
                userChild.setValue(userName);
                //////////////////////////////////////////////////////////

            }
        });

        /* cancel and go to home page */
        cancelButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emergency.this, UserHomepage.class);
                startActivity(intent);

            }
        });


        /* information in help button */
        helpButt.setOnClickListener(new View.OnClickListener() {

            // should set the corresponding parking spot to red
            // TO DO


            // then add this log to history
            // TO DO


            // and then pop out a window indicating success report
            @Override
            public void onClick(View v) {
                AlertDialog.Builder respond = new AlertDialog.Builder(Emergency.this);
                respond.setTitle("Instruction");
                respond.setMessage("\t\t\t\tIn case of fire, please leave the parking lot\n" +
                        "\t\t\t\tIn case of injury, our medical team is on the way\n" +
                        "\t\t\t\tIn case of heart attack or stroke, please dial 911\n" +
                        "\t\t\t\tIn case of property loss,  please wait patiently " +
                        "until the police arrive" +
                        "\t\t\t\tIn case of vehicle power failure, " +
                        "the security guard will provide you with a charge");
                respond.setPositiveButton("I don't like it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                respond.setNegativeButton("It helps!", null);

                AlertDialog alertDialog = respond.create();
                alertDialog.show();

            }


        });


    }
}
