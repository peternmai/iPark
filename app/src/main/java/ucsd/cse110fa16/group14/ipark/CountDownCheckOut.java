package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CountDownCheckOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_check_out);

        Button checkoutButt = (Button) findViewById(R.id.CheckoutButton);
        Button moreTimeButt = (Button) findViewById(R.id.MoreTimeButton);
        Button mapButt = (Button) findViewById(R.id.mapButton);

        checkoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, activity_review.class);
                startActivity(intent);
            }
        });

        moreTimeButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, Clockin.class);
                startActivity(intent);
            }
        });

        mapButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownCheckOut.this, MapDirectional.class);
                startActivity(intent);
            }
        });

    }
}
