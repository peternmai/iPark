package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button ownerButton = (Button) findViewById(R.id.ownerButton);
        Button userButton = (Button) findViewById(R.id.userButton);

        ownerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreenActivity.this, NavigationMenu.class);
                startActivity(intent);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreenActivity.this, LoginPage.class);
                startActivity(intent);
            }
        });



    }
}
