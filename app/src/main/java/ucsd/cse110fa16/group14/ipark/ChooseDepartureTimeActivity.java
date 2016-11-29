package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class ChooseDepartureTimeActivity extends AppCompatActivity {

    private int min, hour, ampm;

    @Override
    protected void onPause() {
        super.onPause();
        iLink.getDefaultPrice();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iLink.getDefaultPrice();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_departure_time);

        Button homeButt = (Button) findViewById(R.id.button7);
        Button help = (Button) findViewById(R.id.button4);
        Button nextButt = (Button) findViewById(R.id.next);
        Button prevButt = (Button) findViewById(R.id.previous);

        // Get values stored from enter arrival time activity
        final Bundle bundle = getIntent().getExtras();

        NumberPicker hourNumPick = (NumberPicker) findViewById(R.id.hour);
        NumberPicker minNumPick = (NumberPicker) findViewById(R.id.min);
        NumberPicker amPmPick = (NumberPicker) findViewById(R.id.amPM);
        final String[] s = {"AM", "PM"};

        //Populate values from minimum and maximum value range
        hourNumPick.setMinValue(1);
        minNumPick.setMinValue(0);
        amPmPick.setMinValue(1);

        //Specify the maximum value/number
        hourNumPick.setMaxValue(12);
        minNumPick.setMaxValue(59);
        amPmPick.setMaxValue(2);

        // Display text
        amPmPick.setDisplayedValues(s);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        hourNumPick.setWrapSelectorWheel(true);
        minNumPick.setWrapSelectorWheel(true);

        // Initialize start time
        hour = 1;
        min = 0;
        ampm = 1;

        //Set a value change listener for hourNumPick
        hourNumPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
            }
        });

        //Set a value change listener for minNumPick
        minNumPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
            }
        });

        //Set a value change listener for minNumPick
        amPmPick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ampm = newVal;                       // 1 = AM, 2 = PM
            }
        });

        // disable enter feature of numberPicker
        hourNumPick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        minNumPick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        amPmPick.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // Check to ensure time enter is valid before going (after start time)
        nextButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hourEntered, minEntered;
                hourEntered = hour;
                minEntered = min;

               /* if (hour == 12 && ampm == 1) {
                    hourEntered = 0;
                }*/

                if ((ampm == 2) && (hour != 12))
                    hourEntered += 12;

                if ((ampm == 1) && hourEntered == 12)
                    hourEntered = 0;

                System.out.println("Current Time: " + bundle.getInt("arriveHour") + ":" + bundle.getInt("arriveMin"));
                System.out.println("Entered Time: " + hourEntered + ":" + minEntered);

                // Ensure hour is after arrive hour
                if (hourEntered < bundle.getInt("arriveHour")) {
                    AlertDialog.Builder invalidTimeAlert = new AlertDialog.Builder(ChooseDepartureTimeActivity.this);
                    invalidTimeAlert.setTitle("Invalid Time");
                    invalidTimeAlert.setMessage(
                            "Departure time may not be before the arrival time.");
                    invalidTimeAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = invalidTimeAlert.create();
                    alertDialog.show();
                }
                // If hour equal arrive hour, then after arrive minute
                else if ((hourEntered == bundle.getInt("arriveHour")) && (minEntered <= bundle.getInt("arriveMin"))) {
                    AlertDialog.Builder invalidTimeAlert = new AlertDialog.Builder(ChooseDepartureTimeActivity.this);
                    invalidTimeAlert.setTitle("Invalid Time");
                    invalidTimeAlert.setMessage(
                            "Departure time may not be before the arrival time.");
                    invalidTimeAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = invalidTimeAlert.create();
                    alertDialog.show();
                }
                // Else accept response
                else {
                    Intent intent = new Intent(ChooseDepartureTimeActivity.this, Payment.class);
                    intent.putExtra("arriveHour", bundle.getInt("arriveHour"));
                    intent.putExtra("arriveMin", bundle.getInt("arriveMin"));
                    intent.putExtra("departHour", hourEntered);
                    intent.putExtra("departMin", minEntered);
                    startActivity(intent);
                }
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
                AlertDialog.Builder hlp = new AlertDialog.Builder(ChooseDepartureTimeActivity.this);
                hlp.setTitle("Help Information");
                hlp.setMessage(
                        "Please choose your expected departure time.\n" +
                                "Departure time cannot be before the arrival time or after midnight.\n" +
                                "Hit next once you're done.");
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                //hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = hlp.create();
                alertDialog.show();
            }
        });


        homeButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if homeButt is pressed, send user to User Homepage
                Intent intent = new Intent(ChooseDepartureTimeActivity.this, UserHomepage.class);
                startActivity(intent);
            }
        });

        prevButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
