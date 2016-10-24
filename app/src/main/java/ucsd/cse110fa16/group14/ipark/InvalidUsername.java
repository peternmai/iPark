package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class InvalidUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid_username);

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
                User driver = new User();
                driver.setName(firstName,lastName);
                driver.setEmail(email);
                driver.setUsername(username);
                driver.setPassword(password);
                /*
                if(!driver.checkUsername()){
                    Intent invalid = new Intent(InvalidUsername.this, InvalidUsername.class);
                    startActivity(invalid);
                    finish();
                    return;
                }

                if(!driver.checkEmail()){
                    Intent invalid = new Intent(InvalidUsername.this, InvalidEmail.class);
                    startActivity(invalid);
                    finish();
                    return;
                }
                */
                Firebase myFirebaseRef = new Firebase("https://ipark-e243b.firebaseio.com");
                myFirebaseRef.child(driver.getUsername()).setValue(driver);

                //driver.store();

                Intent output = new Intent();
                setResult(RESULT_OK, output);
                finish();
            }
        });

    }
}
