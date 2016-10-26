package ucsd.cse110fa16.group14.ipark;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mag on 10/13/2016.
 */

public class CommentBoss extends AppCompatActivity {
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

}
