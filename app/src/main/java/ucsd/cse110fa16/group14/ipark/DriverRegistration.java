package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class DriverRegistration extends AppCompatActivity {
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

        Firebase.setAndroidContext(this);

        Button submit = (Button) findViewById(R.id.submit_registration);
        Button reset = (Button)findViewById(R.id.resetButton);

        final EditText firstName = (EditText)findViewById(R.id.firstName);
        final EditText lastName = (EditText)findViewById(R.id.lastName);
        final EditText email = (EditText)findViewById(R.id.emailAddress);
        final EditText username = (EditText)findViewById(R.id.userName);
        final EditText password = (EditText)findViewById(R.id.userPassword);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName.setText("");
                lastName.setText("");
                email.setText("");
                username.setText("");
                password.setText("");

            }
        });

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Driver driver = new Driver();
                driver.setName(firstName,lastName);
                driver.setEmail(email);
                driver.setUsername(username);
                driver.setPassword(password);

                Firebase myFirebaseRef = new Firebase("https://ipark-e243b.firebaseio.com");
                myFirebaseRef.child(driver.getUsername()).setValue(driver);

                Intent output = new Intent();
                setResult(RESULT_OK, output);
                finish();
            }
        });


    }
}
