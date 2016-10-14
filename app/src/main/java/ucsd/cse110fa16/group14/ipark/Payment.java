package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button payButt = (Button) findViewById(R.id.payButton);
        Button editButt = (Button) findViewById(R.id.editButton);

        payButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, CountDownCheckOut.class);
                startActivity(intent);
            }
        });

        editButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, Clockin.class);
                startActivity(intent);
            }
        });


    }
}
