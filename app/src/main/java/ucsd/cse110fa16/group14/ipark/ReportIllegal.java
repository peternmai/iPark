package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportIllegal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_illegal);

        Button reportButt = (Button) findViewById(R.id.reportButton);
        Button cancelButt = (Button) findViewById(R.id.cancel);



        cancelButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportIllegal.this, MapDirectional.class);
                startActivity(intent);
            }
        });
    }
}
