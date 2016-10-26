package ucsd.cse110fa16.group14.ipark;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
