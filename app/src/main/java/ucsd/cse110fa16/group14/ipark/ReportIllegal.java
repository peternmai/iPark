package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class ReportIllegal extends AppCompatActivity {

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
        setContentView(R.layout.activity_report_illegal);

        Button reportButt = (Button) findViewById(R.id.reportButton);
        Button cancelButt = (Button) findViewById(R.id.paymentCancelButton);

        cancelButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportIllegal.this, MapDirectional.class);
                startActivity(intent);

            }
        });

        reportButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirm = new AlertDialog.Builder(ReportIllegal.this);
                confirm.setMessage("Thank you for reporting! You have received $3.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ReportIllegal.this, UserReviewHistory.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = confirm.create();
                alert.setTitle("Confirmation");
                alert.show();
            }
        });
    }
}
