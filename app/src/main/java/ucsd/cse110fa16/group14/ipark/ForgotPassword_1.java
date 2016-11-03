package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword_1 extends AppCompatActivity {
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

        Button sendButton = (Button) findViewById(R.id.send);
        Button backToLogin = (Button) findViewById(R.id.back);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                // should pop up a window to enter verification code, then direct to next page
                // TO DO


                Intent intent = new Intent(ForgotPassword_1.this, NewPasswordRequest.class);
                startActivity(intent);
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
}
