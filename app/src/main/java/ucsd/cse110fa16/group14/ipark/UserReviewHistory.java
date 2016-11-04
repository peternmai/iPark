package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Mag on 10/19/2016.
 */

public class UserReviewHistory extends AppCompatActivity {

    private Firebase root;
    private ListView listView;
    private ArrayList<ResObj> list = new ArrayList<>();
    private ResObj temp;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review_history);

        listView = (ListView) findViewById(R.id.list_view);
        //home = (ImageButton) findViewById(R.id.home);

        root = new Firebase("https://ipark-e243b.firebaseio.com/History");

        final ArrayAdapter<ResObj> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        //*
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int ctr = 1;

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    temp = new ResObj();

                    temp.setKey("RESERVATION " + ctr + ":");
                    temp.setDate(child.child("Date").getValue(String.class));
                    temp.setRate(child.child("Rate").getValue(String.class));
                    temp.setClockIn(child.child("Clockin").getValue(String.class));
                    temp.setClockOut(child.child("Clockout").getValue(String.class));

                    list.add(temp);

                    ctr++;
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



     /*   Button homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserReviewHistory.this, UserHomepage.class);
                startActivity(intent);
            }
        });*/
    }
}

class ResObj {

    String key, date, clockIn, clockOut, rate;

    ResObj() {

        key = date = clockIn = clockOut = rate = "";
    }

    public void setKey(String key) {
        this.key = key;
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