package ucsd.cse110fa16.group14.ipark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword_1 extends AppCompatActivity {
    private Button sendButton;
    private Button backToLogin;
    private EditText emailField;
    private TextView caption;
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
        setContentView(R.layout.activity_forgot_password_1);

        sendButton = (Button) findViewById(R.id.send);
        backToLogin = (Button) findViewById(R.id.back);
        emailField = (EditText) findViewById(R.id.emailField);
        caption = (TextView) findViewById(R.id.invalidEmailFP);

        auth = FirebaseAuth.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailField.getText().toString();
                String msg;
                if(DriverRegistration.uMapEmail.containsValue(email)){
                    auth.sendPasswordResetEmail(email);
                    msg = "An email has been sent to you. \n"
                            +"Please follow the steps in your email to reset your password";
                    caption.setText(msg);
                    hideKeyboard(v);
                    emailField.setEnabled(false);
                    sendButton.setEnabled(false);
                    Toast.makeText(ForgotPassword_1.this, "Email sent", Toast.LENGTH_LONG).show();
                }
                else{
                    msg = "Invalid email. Please try again.";
                    caption.setText(msg);
                    //Hide Keyboard
                    hideKeyboard(v);
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ForgotPassword_1.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    //Hide Keyboard
    private void hideKeyboard(View v){
        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
