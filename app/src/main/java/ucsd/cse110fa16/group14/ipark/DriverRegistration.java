package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DriverRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

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
    }
}
