package ucsd.cse110fa16.group14.ipark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class DriverRegistration extends AppCompatActivity {

    private static HashSet<String> users = new HashSet<>();
    private static HashSet<String> emails = new HashSet<>();
    protected static HashMap<String, String> uMapEmail = new HashMap<>();
    private FirebaseAuth auth;
    private User newUser;
    private ProgressDialog progress;

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
        setContentView(R.layout.activity_driver_registration);

        auth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);

        final Button submit = (Button) findViewById(R.id.submit_registration);
        Button reset = (Button) findViewById(R.id.resetButton);
        final TextView invalidUser = (TextView) findViewById(R.id.invalidUsernameTV);
        final TextView invalidEmail = (TextView) findViewById(R.id.invalidEmailTV);
        final CheckBox notRobot = (CheckBox) findViewById(R.id.notARobot);

        final String msg1 = invalidUser.getText().toString();
        final String msg2 = invalidEmail.getText().toString();
        invalidUser.setText("");
        invalidEmail.setText("");

        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText email = (EditText) findViewById(R.id.emailAddress);
        final EditText username = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.userPassword);
        final EditText license = (EditText) findViewById(R.id.license);


        //Get data from Firebase
        getData();

        //Turn off the submit button at first
        submit.setEnabled(false);

        //Resets the text fields, buttons, and the checkbox.
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] editTexts1 = {firstName, lastName, email, username, password, license};
                clear(editTexts1);
                invalidUser.setText("");
                invalidEmail.setText("");
                notRobot.setChecked(false);
                submit.setEnabled(false);
            }
        });


        //Process the input and create a new user.
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                invalidEmail.setText("");
                invalidUser.setText("");

                newUser = new User();
                newUser.setName(firstName, lastName);
                newUser.setEmail(email);
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setLicense(license);
                boolean userExists = users.contains(newUser.getUsername());
                boolean emailExists = emails.contains(newUser.getEmail());

                if (userExists && emailExists) {
                    String msg = "Both username and email are taken. Please try again.";
                    invalidEmail.setText(msg);
                    invalidUser.setText("");
                    notRobot.setChecked(false);
                    submit.setEnabled(false);
                } else if (emailExists) {
                    invalidEmail.setText(msg2);
                    invalidUser.setText("");
                    notRobot.setChecked(false);
                    submit.setEnabled(false);
                } else if(userExists){
                    invalidEmail.setText("");
                    invalidUser.setText(msg1);
                    notRobot.setChecked(false);
                    submit.setEnabled(false);
                } else { //Valid info is presented
                    notRobot.setChecked(false);
                    submit.setEnabled(false);
                    createAccount(newUser.getEmail(), newUser.getPassword());
                }
            }
        });

        //Check if all text fields are full.
        notRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setEnabled(false);
                EditText[] editTexts1 = {firstName, lastName, email, username, password, license};
                if ((!isEmpty(editTexts1)) && notRobot.isChecked()) {
                    submit.setEnabled(true);
                }
            }
        });

    }


    /**
     * Get the user names from firebase database and store it locally.
     */
    protected void getData() {

        //Getting all the usernames
        Firebase userReference = new Firebase("https://ipark-e243b.firebaseio.com/Users");

        userReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> usernames = dataSnapshot.getChildren();
                Iterator<com.firebase.client.DataSnapshot> iterator = usernames.iterator();

                //Getting usernames
                while (iterator.hasNext()) {
                    com.firebase.client.DataSnapshot node = iterator.next();
                    String uname = node.getKey();
                    users.add(uname);

                    Iterable<com.firebase.client.DataSnapshot> userInfo = node.getChildren();
                    Iterator<com.firebase.client.DataSnapshot> iterator1 = userInfo.iterator();

                    //Getting emails
                    while (iterator1.hasNext()) {
                        com.firebase.client.DataSnapshot innerNode = iterator1.next();
                        String innerKey = innerNode.getKey();
                        if (innerKey.equals("email")) {
                            String mail = innerNode.getValue(String.class);
                            uMapEmail.put(uname, mail);
                            emails.add(mail);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(DriverRegistration.this, "Could not connect to the database", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Helper method to see if all text fields are empty
     * @param editTexts array of text fields
     * @return true if all text fields in the array are empty
     */
    private boolean isEmpty(EditText[] editTexts) {

        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to clear all the textfields
     * @param editTexts array that needs to be cleared
     */
    private void clear(EditText[] editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setText("");
        }
    }

    /**
     * Creates an account in firebase database as well as gives access to login.
     * @param mail email to use
     * @param pass password to use
     */
    private void createAccount(String mail, String pass) {
        final Firebase myFirebaseRef = new Firebase("https://ipark-e243b.firebaseio.com/Users");

        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)) {
            Toast.makeText(DriverRegistration.this, "Please enter username and password.", Toast.LENGTH_LONG).show();
        } else {
            progress.show();
            progress.setMessage("Signing in....");
            auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progress.dismiss();
                        Toast.makeText(DriverRegistration.this, "Error signing up", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DriverRegistration.this, "Sign up successful", Toast.LENGTH_LONG).show();
                        myFirebaseRef.child(newUser.getUsername()).setValue(newUser);
                        progress.dismiss();
                        Intent intent = new Intent(DriverRegistration.this,LoginPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }
}

