package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by maggie on 11/14/16.
 */

public class Messages extends AppCompatActivity {

    Firebase hasChild;
    private static FirebaseAuth auth;
    private Firebase root;
    private ListView listView;
    private ArrayList<adminMessages> list = new ArrayList<>();
    private adminMessages temp;

    class adminMessages {

        String key, date, comment, user;

        adminMessages() {
            key = date = comment = user = "";
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setUser(String user) { this.user = user; }

        @Override
        public String toString() {
            return String.format("\nFROM:  %s\t\t\t\t\t\t\t\tDATE:  %s\n\nMESSAGE: %s\n", user, date, comment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ListView messages = (ListView) findViewById(R.id.list_view_messages);

        root = new Firebase("https://ipark-e243b.firebaseio.com/Messages");
        final ArrayAdapter<adminMessages> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        messages.setAdapter(arrayAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // int ctr = 1;

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    temp = new adminMessages();

                    //****************************************************
                    temp.setKey(child.child("Key").getValue(String.class));
                    //*****************************************************

                    //temp.setKey("Comment " );
                    temp.setDate(child.child("Date").getValue(String.class));
                    temp.setComment(child.child("Comment").getValue(String.class));
                    temp.setUser(child.child("User").getValue(String.class));

                    list.add(temp);

                    //ctr++;
                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }

}
