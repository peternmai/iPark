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
  https://www.youtube.com/watch?v=tOn5HsQPhUY&t=6s
 */
public class UserReviewHistory extends AppCompatActivity {
    private Firebase root;
    private ListView listView;
    private ArrayList<ResObj> list = new ArrayList<>();
    private ResObj temp;
    private static FirebaseAuth auth;

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    // inner class used to retrieve data from Firebase for display in the User Review layout
    class ResObj {
        String key, date, clockIn, clockOut, rate, user;
        ResObj() {
            key = date = clockIn = clockOut = rate = user = "";
        }

        // setter methods
        public void setKey(String key) {
            this.key = key;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setClockIn(String clockIn) {
            this.clockIn = clockIn;
        }

        public void setClockOut(String clockOut) {
            this.clockOut = clockOut;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        @Override
        public String toString() {
            return String.format("\n%s\t\t\t\t\t\t\tDATE: %s\n\tTIME: %s - %s\n\tRATE: %s\n\n",
                    key, date, clockIn, clockOut, rate);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review_history);

        final Bundle bundle = getIntent().getExtras();
        listView = (ListView) findViewById(R.id.list_view);
        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();    // get current user's username
        root = new Firebase("https://ipark-e243b.firebaseio.com/Users/" + userName + "/History");     // set up path for History
        final ArrayAdapter<ResObj> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        // populate ResObjs and add to list for display by array adapter
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ctr = 1;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.hasChild("Rate")){
                        temp = new ResObj();

                        temp.setKey("RESERVATION " + ctr + ":");
                        temp.setDate(child.child("Date").getValue(String.class));
                        temp.setRate(child.child("Rate").getValue(String.class));
                        temp.setClockIn(child.child("Clockin").getValue(String.class));
                        temp.setClockOut(child.child("Clockout").getValue(String.class));
                        temp.setUser(child.child("User").getValue(String.class));

                        list.add(temp);

                        ctr++;
                        arrayAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        ImageButton homeButton = (ImageButton) findViewById(R.id.homeB);
        ImageButton nextButton = (ImageButton) findViewById(R.id.next);

        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if homeButton pressed, send user to User Homepage
                Intent intent = new Intent(UserReviewHistory.this, UserHomepage.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // if nextButton send, send user to UserReviewHistory2 page
                Intent intent = new Intent(UserReviewHistory.this, UserReviewHistoryPage2.class);
                startActivity(intent);
            }
        });
    }
}
