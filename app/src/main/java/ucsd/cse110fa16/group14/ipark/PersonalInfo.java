package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Mag on 10/19/2016.
 * Edited by Abhi on 11/4/2016.
 */
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class PersonalInfo extends AppCompatActivity {

    private HashMap<String, String> infoMap = UserHomepage.infoMap;

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
        final Bundle bundle = getIntent().getExtras();

        TextView name = (TextView) findViewById(R.id.nameTV);
        TextView email = (TextView) findViewById(R.id.email);
        TextView license = (TextView) findViewById(R.id.license);
        TextView uName = (TextView) findViewById(R.id.username);

        ImageButton homeButt = (ImageButton) findViewById(R.id.button19);
        Button changePasswordButt = (Button) findViewById(R.id.button21);
        Button helpButt = (Button) findViewById(R.id.help);

        /** Setting the text view to correspond the correct info **/
        name.setText(infoMap.get("name"));
        email.setText(infoMap.get("email"));
        license.setText(infoMap.get("license"));
        uName.setText(infoMap.get("username"));


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
                Intent intent = new Intent(PersonalInfo.this, ForgotPassword.class);
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
