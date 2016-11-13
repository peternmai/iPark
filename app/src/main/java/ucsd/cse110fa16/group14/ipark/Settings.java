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

        //final TextView curr = (TextView) findViewById(R.id.currentPrice);
        final EditText newP = (EditText) findViewById(R.id.NEWPRICE);
        Button change = (Button) findViewById(R.id.change);


        price = iLink.getChildInfo("ParkingLot","SpotDefaultPrice");
        //if(!price.containsKey("Price")) System.out.println("This IS here, Yuri");
        //curr.setText(price.get("Price"));

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String n = newP.getText().toString();
                double newPrice= Double.parseDouble(n);
                iLink.changePrice(newPrice);

                AlertDialog.Builder updated = new AlertDialog.Builder(Settings.this);
                updated.setTitle("Help Information");
                updated.setMessage("Current Price is Now: $" + price.get("Price"));
                updated.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                //hlp.setNegativeButton("No", null);
                AlertDialog alertDialog = updated.create();
                alertDialog.show();

            }
        });

    }
}
