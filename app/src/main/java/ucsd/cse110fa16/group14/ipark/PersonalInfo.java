package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Mag on 10/19/2016.
 */

public class PersonalInfo extends AppCompatActivity {

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

        // home button and update button
        Button homeButt  = (Button) findViewById(R.id.button19);
        Button updateButt  = (Button) findViewById(R.id.button21);

        // editText field, first and last name, email, license, username and password
        EditText firstName = (EditText)findViewById(R.id.editText11);
        EditText lastName = (EditText)findViewById(R.id.editText25);
        EditText email = (EditText)findViewById(R.id.editText30);
        EditText license = (EditText)findViewById(R.id.editText28);
        EditText userName = (EditText)findViewById(R.id.editText9);
        EditText password = (EditText)findViewById(R.id.editText29);

        /* set the cursor at the end of text */
        int textLength = firstName.getText().length();
        firstName.setSelection(textLength, textLength);

        textLength = lastName.getText().length();
        lastName.setSelection(textLength, textLength);

        textLength = email.getText().length();
        email.setSelection(textLength, textLength);

        textLength = license.getText().length();
        license.setSelection(textLength, textLength);

        textLength = userName.getText().length();
        userName.setSelection(textLength, textLength);

        textLength = password.getText().length();
        password.setSelection(textLength, textLength);


        /* return to home page */
        homeButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfo.this, UserHomepage.class);
                startActivity(intent);

            }
        });


        /* update user info */
        updateButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                update();

                /* Will take care of this later
                if (forgotPassword.isChecked()) {
                    Intent intent = new Intent(LoginPage.this, ForgotPassword_1.class);
                    startActivity(intent);
                }
                */

            }
        });
    }

    /* the private update function */
    private void update(){}
}
