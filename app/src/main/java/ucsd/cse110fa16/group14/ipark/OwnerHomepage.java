package ucsd.cse110fa16.group14.ipark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Mag on 10/19/2016.
 */

public class OwnerHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_owner);

        ImageButton map1 = (ImageButton) findViewById(R.id.map1);
        ImageButton settings = (ImageButton) findViewById(R.id.settings);
        ImageButton reviews = (ImageButton) findViewById(R.id.reviews);
        ImageButton compose = (ImageButton) findViewById(R.id.compose);
        ImageButton report = (ImageButton) findViewById(R.id.report);
        ImageButton emergency = (ImageButton) findViewById(R.id.emergency1);
        ImageButton logout = (ImageButton) findViewById(R.id.logout);


        map1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, BossMap.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, Settings.class);
                startActivity(intent);
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, CommentBoss.class);
                startActivity(intent);
            }
        });

        compose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, MessageUsersActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, MessageUsersActivity.class);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerHomepage.this, BossEmergency.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder Quest = new AlertDialog.Builder(OwnerHomepage.this);
                Quest.setTitle("Log out");
                Quest.setMessage(
                        "Are you sure to log out?");
                Quest.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                Quest.setNegativeButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(OwnerHomepage.this, LoginPage.class);
                        FirebaseAuth.getInstance().signOut();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Toast.makeText(OwnerHomepage.this, "Logout Successful", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    }
                });
                AlertDialog alertDialog = Quest.create();
                alertDialog.show();
            }
        });
    }
}
