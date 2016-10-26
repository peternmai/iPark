package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Iterator;

public class DriverRegistration extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static HashSet<String> users = new HashSet<>();


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

        getData();

        submit.setEnabled(false);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] editTexts1 = {firstName, lastName, email, username, password, license};
                clear(editTexts1);
                invalidUser.setText("");
                invalidEmail.setText("");
                notRobot.setChecked(false);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Firebase myFirebaseRef = new Firebase("https://ipark-e243b.firebaseio.com");

                User driver = new User();
                driver.setName(firstName, lastName);
                driver.setEmail(email);
                driver.setUsername(username);
                driver.setPassword(password);
                driver.setLicense(license);

                if (users.contains(driver.getUsername())) {
                    invalidEmail.setText("");
                    invalidUser.setText(msg1);
                } else {
                    myFirebaseRef.child(driver.getUsername()).setValue(driver);
                    EditText[] editTexts1 = {firstName, lastName, email, username, password, license};
                    notRobot.setChecked(false);
                    submit.setEnabled(false);
                    String finalMsg = "Congratulations!!! Your account has been created.";
                    invalidUser.setText(finalMsg);
                }
                Intent output = new Intent();
                setResult(RESULT_OK, output);
            }
        });

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


    protected void getData() {

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> usernames = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = usernames.iterator();
                while (iterator.hasNext()) {
                    String uname = iterator.next().getKey();
                    users.add(uname);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());

            }
        });

    }

    protected boolean isEmpty(EditText[] editTexts) {

        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().equals("")) {
                return true;
            }
        }
        return false;
    }

    protected void clear(EditText[] editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setText("");
        }
    }
}
