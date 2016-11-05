package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Mag on 10/19/2016.
 * Edited by Abhi on 11/4/2016.
 */

public class PersonalInfo extends AppCompatActivity {

    private TextView name;
    private TextView license;
    private TextView email;
    private TextView uName;
    private Button homeButt;
    private Button changePasswordButt;
    private Button helpButt;
    private FirebaseAuth auth;
    ArrayList<String> finalVals = new ArrayList<>();
    HashMap<String,String> infoMap = UserHomepage.infoMap;


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
        setContentView(R.layout.activity_personal_info);

        auth = FirebaseAuth.getInstance();
        Firebase fRef = new Firebase("https://ipark-e243b.firebaseio.com/Users/");
        name = (TextView) findViewById(R.id.nameTV);
        email = (TextView) findViewById(R.id.email);
        license = (TextView) findViewById(R.id.license);
        uName = (TextView) findViewById(R.id.username);
        // home button and update button
        homeButt  = (Button) findViewById(R.id.button19);
        changePasswordButt  = (Button) findViewById(R.id.button21);
        helpButt = (Button) findViewById(R.id.help);

        String name1 = infoMap.get("name");
        String username1 = infoMap.get("username");
        String email1 = infoMap.get("email");
        String license2 = infoMap.get("license");
        name.setText(name1);
        email.setText(email1);
        license.setText(license2);
        uName.setText(username1);

        /* return to home page */
        homeButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfo.this, UserHomepage.class);
                startActivity(intent);
            }
        });


        /* Change password of the user */
        changePasswordButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfo.this, ForgotPassword_1.class);
                startActivity(intent);
            }
        });

        /* information page */
        helpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(PersonalInfo.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("This is your personal info page.\n" +
                        "Please click change password if you would like to change your password.\n"
                        + "Press the home button to go back to your homepage."
                        );
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });
    }


    /* the private update function */
    private void update(){}


}
