package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mag on 10/19/2016.
 */

public class PersonalInfo extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView license;
    private TextView email;
    private TextView uName;
    private Button homeButt;
    private Button changePasswordButt;
    private Button helpButt;
    private FirebaseAuth auth;

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
        firstName = (TextView) findViewById(R.id.first);
        lastName = (TextView) findViewById(R.id.last);
        email = (TextView) findViewById(R.id.email);
        license = (TextView) findViewById(R.id.license);
        uName = (TextView) findViewById(R.id.username);

        // home button and update button
        homeButt  = (Button) findViewById(R.id.button19);
        changePasswordButt  = (Button) findViewById(R.id.button21);
        helpButt = (Button) findViewById(R.id.help);

        // editText password
        //password = (EditText)findViewById(R.id.password);

        /* set the cursor at the end of text */
        //int textLength = password.getText().length();
        //password.setSelection(textLength, textLength);


        //Still got work to do
        /*User user = iLink.getCurrentUser();
        firstName.setText("Need to figure out");
        lastName.setText("Need to figure out");
        email.setText(user.getEmail());
        license.setText(user.getLicense());
        license.setText(user.getUsername());
        */

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
