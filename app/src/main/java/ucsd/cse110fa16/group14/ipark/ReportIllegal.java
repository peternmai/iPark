package ucsd.cse110fa16.group14.ipark;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.id;

public class ReportIllegal extends AppCompatActivity {
    private Context cContext;
    public void ViewBreakout(Context context) {
        this.cContext = context;
    }

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
        Button cancelButt = (Button) findViewById(R.id.cancel);



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

                LayoutInflater inflate = getLayoutInflater();
                View alertLayout = inflate.inflate(R.layout.confirmreport, null);
                AlertDialog.Builder confirm = new AlertDialog.Builder(ReportIllegal.this);
                    confirm.setTitle(R.id.confirmation);
                    confirm.setPositiveButton(R.id.dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                         Intent intent = new Intent(cContext, UserReviewHistory.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = confirm.create();
                alert.show();
                alert.setContentView(R.layout.confirmreport);
            }
        });
    }
}
