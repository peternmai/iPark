package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Button commentButt = (Button) findViewById(R.id.commentButton);
        Button nopButt = (Button) findViewById(R.id.nopButton);

        commentButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_review.this, comment.class);
                startActivity(intent);
            }
        });

        nopButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_review.this, Clockin.class);
                startActivity(intent);
            }
        });

    }
}
