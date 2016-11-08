package ucsd.cse110fa16.group14.ipark;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MapDirectional extends AppCompatActivity {
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
        setContentView(R.layout.activity_map_directional);

        final Bundle bundle = getIntent().getExtras();

        Button emergencyButt = (Button) findViewById(R.id.button9);
        Button homeButt = (Button) findViewById(R.id.button14);
        Button reportButt = (Button) findViewById(R.id.send);
        final EditText reportSpotTextEdit = (EditText) findViewById(R.id.editText);

        /* click emergency */
        emergencyButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDirectional.this, Emergency.class);
                intent.putExtra("Username", bundle.getString("Username"));
                startActivity(intent);

            }
        });

        /* click home button and go to home page */
        homeButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDirectional.this, UserHomepage.class);
                startActivity(intent);

            }
        });


        /* report illegal parking of other spots */
        reportButt.setOnClickListener(new View.OnClickListener() {

            // should set the corresponding parking spot to red
            // TO DO


            // then add this log to history
            // TO DO


            // and then pop out a window indicating success report
            @Override
            public void onClick(View v) {


                String report_num_text = reportSpotTextEdit.getText().toString().trim();
                boolean reportSuccess;

                if( report_num_text.isEmpty() ) {

                    AlertDialog.Builder respond = new AlertDialog.Builder(MapDirectional.this);
                    respond.setTitle("Report Failed");
                    respond.setMessage("Did not specify which parking spot to report.");
                    respond.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = respond.create();
                    alertDialog.show();
                }
                else {
                    int report_num = Integer.parseInt(report_num_text);
                    reportSuccess = iLink.reportOther(report_num);

                    if( reportSuccess ) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ?
                                null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        AlertDialog.Builder respond = new AlertDialog.Builder(MapDirectional.this);
                        respond.setTitle("Successful Report");
                        respond.setMessage("Your report has been successfully recorded.\n" +
                                "A reward will soon be delivered to your account.\n" +
                                "You can view this activity in account history now.\n");
                        respond.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = respond.create();
                        alertDialog.show();
                    }
                    else {
                        AlertDialog.Builder respond = new AlertDialog.Builder(MapDirectional.this);
                        respond.setTitle("Report Failed");
                        respond.setMessage("Invalid parking spot number entered. Cannot report specified parking spot.");
                        respond.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = respond.create();
                        alertDialog.show();
                    }
                }



            }


        });


        //Button illegalButt = (Button) findViewById(R.id.illegalButton);
        //Button checkoutButt = (Button) findViewById(R.id.checkoutButton);

        /*illegalButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDirectional.this, ReportIllegal.class);
                startActivity(intent);
            }
        });

        checkoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDirectional.this, activity_review.class);
                startActivity(intent);
            }
        });*/
    }
}
