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
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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
        Button helpButt = (Button) findViewById(R.id.help);

        // editText password
        EditText password = (EditText)findViewById(R.id.password);

        /* set the cursor at the end of text */
        int textLength = password.getText().length();
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

        /* information page */
        helpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(PersonalInfo.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("Only the password is editable.\n" +
                        "Please click update after entering new password.\n"
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

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /* the private update function */
    private void update(){}


}
