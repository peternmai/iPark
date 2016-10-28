package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class Clockin extends AppCompatActivity {

    /*private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;
*/

    public void dialogEvent(View view) {

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
        setContentView(R.layout.activity_clockin);


        Button logoutButt = (Button) findViewById(R.id.button7);
        Button reset = (Button)findViewById(R.id.button3);
        Button help = (Button) findViewById(R.id.button4);
        Button confirmButt = (Button) findViewById(R.id.button);


        confirmButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altial = new AlertDialog.Builder(Clockin.this);
                altial.setMessage("$2.50/hr").setCancelable(false)
                        .setPositiveButton("Okay, charge me!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Clockin.this, Payment.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altial.create();
                alert.setTitle("Current Rate:");
                alert.show();


            }
            //@Override
            //public void onClick(View v) {
              //Intent intent = new Intent(Clockin.this, Payment.class);
              //startActivity(intent);



            //}
        });



        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(Clockin.this);
                hlp.setTitle("Help Information");
                hlp.setMessage("Type in the time you arrive to your parking spot.\n" +
                        "Type in the time you expect to leave your parking spot.\n"+
                " 'CONFIRM' will bring you to our confirmation page and you will recieve a parking number.\n"
                + "'CLEAR' will reset all the information you typed in.\n");
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });

                hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });



        logoutButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Clockin.this, LoginPage.class);
                startActivity(intent);

            }
        });


        final EditText startTime = (EditText)findViewById(R.id.editText2);
        final EditText endTime = (EditText)findViewById(R.id.editText3);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime.setText("");
                endTime.setText("");


            }
        });

    }

    /*public void showDialog(View v) {

                AlertDialog.Builder altial = new AlertDialog.Builder(Clockin.this);
                altial.setMessage("$2.50/hr").setCancelable(false)
                        .setPositiveButton("Okay, charge me!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Clockin.this, Payment.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert = altial.create();
                alert.setTitle("Current Rate:");
                alert.show();




        }*/
}
