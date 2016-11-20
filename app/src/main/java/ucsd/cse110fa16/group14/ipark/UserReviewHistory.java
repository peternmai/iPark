package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
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

/**
 * Created by Mag on 10/19/2016.
 */

public class UserReviewHistory extends AppCompatActivity {

    private Firebase root;
    private ListView listView;
    private ArrayList<ResObj> list = new ArrayList<>();
    private ResObj temp;
    private static FirebaseAuth auth;
    private ListView reportView;
    private ArrayList<RepoObj> list2 = new ArrayList<>();
    private RepoObj temp2;

    class ResObj {

        String key;
        String date;
        String clockIn;
        String clockOut;
        String rate;
        String user;

        ResObj() {

            key = date = clockIn = clockOut = rate = user = "";
        }

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
        setContentView(R.layout.activity_user_review_history);

        final Bundle bundle = getIntent().getExtras();

        listView = (ListView) findViewById(R.id.list_view);
        reportView = (ListView) findViewById(R.id.listView2);

        auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();
        //Task task = auth.getCurrentUser().updatePassword(newPassword);
        //root = new Firebase("https://ipark-e243b.firebaseio.com/Users/"+bundle.getString("Username")+"/History");
        root = new Firebase("https://ipark-e243b.firebaseio.com/Users/" + userName + "/History");

        final ArrayAdapter<ResObj> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        final ArrayAdapter<RepoObj> repoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list2);

        listView.setAdapter(arrayAdapter);
        reportView.setAdapter(repoAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int ctr = 1;
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
                    else {
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

        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserReviewHistory.this, UserHomepage.class);
                startActivity(intent);
            }
        });
    }
}
