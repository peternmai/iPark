package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class Resume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class<?> activityClass;

        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(
                    prefs.getString("lastActivity", LoginPage.class.getName()));
        } catch (ClassNotFoundException ex) {
            activityClass = LoginPage.class;
        }

        startActivity(new Intent(this, activityClass));
    }
}

