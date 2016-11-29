package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class BossMap extends AppCompatActivity {


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
        setContentView(R.layout.activity_boss_map);

        // help button and status of the parking lot
        Button helpButt = (Button) findViewById(R.id.help);
        Button statusButt = (Button) findViewById(R.id.status);
        TextView verticalNums = (TextView) findViewById(R.id.verticalIndices);
        //Button priceChanger = (Button) findViewById(R.id.priceChanger);
        String text = "\r\n00\r"+"\t10\r"+"\n20\r"+"\n30\r"+"\n\n40\r"+"\t50\r"+"\n60\r"+"\n70";
        verticalNums.setText(text);


        /* information page */
        helpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(BossMap.this);
                hlp.setTitle("Instruction");
                hlp.setMessage(
                        //"\t\t\t\tLong press to show detail information of parking spot.\n" +
                        "Green  - Available\n" +
                                "Yellow - Occupied\n" +
                                "White  - Owner Reserve\n" +
                                "Red     -  Illegal\n" +
                                "Long press spots on the map to change status.\n"
                );
                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });

        /* status of parking lot*/
        statusButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hlp = new AlertDialog.Builder(BossMap.this);
                hlp.setTitle("Parking Status");

                int ownerReserved = 0;
                int available = 0;
                int occupied = 0;
                int illegalParking = 0;
                String availStr = "";
                String occupyStr = "";
                String reserveStr = "";
                String illegalStr = "";

                long curTimeInSec = iLink.getCurTimeInSec();
                int parkingLotStatus[] = iLink.getParkingLotStatus(curTimeInSec, curTimeInSec);

                for (int i = 0; i < iLink.NUM_SPOTS; i++) {
                    if (parkingLotStatus[i] == iLink.AVAILABLE)
                        available++;
                    else if (parkingLotStatus[i] == iLink.OCCUPIED)
                        occupied++;
                    else if (parkingLotStatus[i] == iLink.OWNER_RESERVED)
                        ownerReserved++;
                    else
                        illegalParking++;
                }

                availStr = String.format("%02d", available);
                occupyStr = String.format("%02d", occupied);
                reserveStr = String.format("%02d", ownerReserved);
                illegalStr = String.format("%02d", illegalParking);

                hlp.setMessage("Available parking: " + availStr +
                        "\nOccupied:              " + occupyStr +
                        "\nOwner reserved:   " + reserveStr +
                        "\nIllegal parking:      " + illegalStr + "\n");

                hlp.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = hlp.create();
                alertDialog.show();

            }
        });



        /*change the price */
        /*priceChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder pC = new AlertDialog.Builder(BossMap.this);
                pC.setTitle("Change Price");
                final EditText input = new EditText(BossMap.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                //pC.setMessage("");
                pC.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newp = input.getText().toString();
                        double newPrice = Double.parseDouble(newp);
                        iLink.changePrice(newPrice);
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = pC.create();
                alertDialog.show();

            }
        });*/

    }
}
