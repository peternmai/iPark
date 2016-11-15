package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class Settings extends AppCompatActivity {
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
        setContentView(R.layout.activity_settings);
        final HashMap<String,String> price;

        iLink.getDefaultPrice();
        //final TextView curr = (TextView) findViewById(R.id.currentPrice);
        final EditText newP = (EditText) findViewById(R.id.NEWPRICE);
        Button change = (Button) findViewById(R.id.change);
        final TextView currP = (TextView) findViewById(R.id.currentPrice);



        //price = iLink.getChildInfo("ParkingLot","SpotDefaultPrice");
        //if(!price.containsKey("Price")) System.out.println("This IS here, Yuri");
        String defaultP= Double.toString(OwnerHomepage.currPrice);
        currP.setText(defaultP);

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String n = newP.getText().toString();
                final double newPrice= Double.parseDouble(n);
                iLink.changePrice(newPrice);

                double neww = iLink.getDefaultPrice();
                System.out.println("HEY " + neww);
                //currP.setText(Double.toString(iLink.defaultPrice));

                AlertDialog.Builder updated = new AlertDialog.Builder(Settings.this);
                updated.setTitle("Help Information");
                updated.setMessage("Current Price is Now: $" + newPrice);
                updated.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView currPP = (TextView) findViewById(R.id.currentPrice);
                        currPP.setText(Double.toString(newPrice));
                        currP.setText(Double.toString(newPrice));
                        dialog.cancel();
                    }
                });

                //hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = updated.create();
                alertDialog.show();


            }
        });


    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Settings.this, OwnerHomepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
