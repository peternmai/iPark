package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mag on 10/13/2016.
 */

public class CommentBoss extends AppCompatActivity {

    private Firebase root;
    private ListView listView;
    private ArrayList<commentObj> list = new ArrayList<>();
    private commentObj temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_boss);

        listView = (ListView) findViewById(R.id.list_view2);

        ImageButton home = (ImageButton) findViewById(R.id.homeBB);
        ImageButton next = (ImageButton) findViewById(R.id.nextBB);

        home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentBoss.this, OwnerHomepage.class);
                startActivity(intent);
            }
        });

        root = new Firebase("https://ipark-e243b.firebaseio.com/Comments");
        final ArrayAdapter<commentObj> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);



        listView.setAdapter(arrayAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // int ctr = 1;

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    temp = new commentObj();

                    //****************************************************
                    temp.setKey(child.child("Key").getValue(String.class));
                    //*****************************************************

                    //temp.setKey("Comment " );
                    temp.setDate(child.child("Date").getValue(String.class));
                    temp.setComment(child.child("Comment").getValue(String.class));
                    temp.setRating(child.child("Rating").getValue(String.class));

                    list.add(temp);

                    //ctr++;
                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });


/*

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Date date = new Date();
                //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                //Firebase hasChild = root.child(sdf.format(date));


                root.child(list.get(i).getKey()).removeValue();
                list.remove(i);

                //Toast.makeText(CommentBoss.this, list.get(i).getKey() + "_" + list.get(i).toString(),
                //        Toast.LENGTH_LONG).show();

                arrayAdapter.notifyDataSetChanged();

                return true;
            }
        });

*/

   }
    //root.child("Key").removeValue();



    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

}

class commentObj {

    String key, date, comment, rating;

    commentObj() {
        key = date = comment = rating = "";
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() { return this.key; }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("\t\t\t\t\t\t\t\nDATE:  %s\n\nCOMMENT: %s\nRATING: %s\n", date, comment, rating);
    }
}
