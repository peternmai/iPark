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
import android.widget.TextView;
import android.widget.Toast;
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
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

        final EditText newP = (EditText) findViewById(R.id.NEWPRICE);
        Button change = (Button) findViewById(R.id.change);
        final TextView currP = (TextView) findViewById(R.id.currentPrice);

        //price = iLink.getChildInfo("ParkingLot","SpotDefaultPrice");
        //if(!price.containsKey("Price")) System.out.println("This IS here, Yuri");
        double priceVal = iLink.defaultPrice;
        currP.setText(String.format("$%.2f", priceVal));

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = newP.getText().toString();
                if (n.equals("")) {
                    Toast.makeText(Settings.this, "Please enter a valid price", Toast.LENGTH_LONG).show();
                } else {
                    final double newPrice = Double.parseDouble(n);
                    iLink.changePrice(newPrice);
                    iLink.defaultPrice = newPrice;

                    AlertDialog.Builder updated = new AlertDialog.Builder(Settings.this);
                    updated.setTitle("Help Information");
                    updated.setMessage("Current Price is Now: $" + newPrice);
                    updated.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            newP.setText("");
                            dialog.cancel();
                        }
                    });
                    updated.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currP.setText(String.format("$%.2f", newPrice));
                            newP.setText("");
                            dialog.cancel();
                        }
                    });
                    //hlp.setNegativeButton("No", null);
                    AlertDialog alertDialog = updated.create();
                    alertDialog.show();
                }
                hideKeyboard(v);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, OwnerHomepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
