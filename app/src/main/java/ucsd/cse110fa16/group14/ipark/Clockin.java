package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Clockin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clockin);

        Button confirmButt = (Button) findViewById(R.id.confirmButton);
        Button logoutButt = (Button) findViewById(R.id.logoutButton);

        confirmButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clockin.this, Payment.class);
                startActivity(intent);
            }
        });

        logoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clockin.this, LoginPage.class);
                startActivity(intent);
            }
        });


    }
}
