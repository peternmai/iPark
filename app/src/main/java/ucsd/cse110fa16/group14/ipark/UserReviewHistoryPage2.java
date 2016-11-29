package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/*
Sources:
  http://stackoverflow.com/questions/2441203/how-to-make-an-android-app-return-to-the-last-open-activity-when-relaunched
 */
public class UserReviewHistoryPage2 extends AppCompatActivity {
    private static FirebaseAuth auth;
    private ListView reportView;
    private ArrayList<RepoObj> list2 = new ArrayList<>();
    private Firebase root;
    private RepoObj temp2;

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    class RepoObj{
        String key;
        String date;
        String user;
        String spot;
        String time;

        RepoObj(){
            key = date = spot = user = "";
        }

        public void setKey(String key) { this.key = key; }

        public void setUser(String user) { this.user = user; }

        public void setDate(String date) { this. date = date; }

        public void setSpot(String spot) { this.spot = spot; }

        public void setTime(String time) { this.time = time; }
        @Override
        public String toString() {
            return String.format("\n%s\t\t\t\t\t\t\tDATE: %s\n\tTIME: %s\n\t Spot: %s\n\n",
                    key, date, time, spot);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review_history_page2);

        reportView = (ListView) findViewById(R.id.list_view);
        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();
        //Task task = auth.getCurrentUser().updatePassword(newPassword);
        //root = new Firebase("https://ipark-e243b.firebaseio.com/Users/"+bundle.getString("Username")+"/History");
        root = new Firebase("https://ipark-e243b.firebaseio.com/Users/" + userName + "/History");

        final ArrayAdapter<RepoObj> repoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list2);

        reportView.setAdapter(repoAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int num = 1;

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.hasChild("Spot")){
                        temp2 = new RepoObj();
                        temp2.setKey("REPORT " + num + ":");
                        temp2.setDate(child.child("Date").getValue(String.class));
                        temp2.setSpot(child.child("Spot").getValue(String.class));
                        temp2.setUser(child.child("User").getValue(String.class));
                        temp2.setTime(child.child("Time").getValue(String.class));
                        list2.add(temp2);
                        num++;

                        repoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ImageButton homeButton = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton prevButton = (ImageButton) findViewById(R.id.imageButton4);


        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserReviewHistoryPage2.this, UserHomepage.class);
                startActivity(intent);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserReviewHistoryPage2.this, UserReviewHistory.class);
                startActivity(intent);
            }
        });
    }
}
