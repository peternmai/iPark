package ucsd.cse110fa16.group14.ipark;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 *  Used for the emergency popup window in Owner Homepage
 *  sources: https://www.youtube.com/watch?v=tOn5HsQPhUY&t=6s
 *  https://www.youtube.com/watch?v=fn5OlqQuOCk
 */
public class PopUp extends Activity {

    private Firebase root;
    private ListView listView;
    private ArrayList<PopUp.emergency> list = new ArrayList<>();
    private PopUp.emergency temp;

    // inner class used to retrieve data from Firebase for display in the Owner Homepage
    class emergency {
        String date, emergencyType, parkingSpot, user;

        emergency() {
            date = emergencyType = parkingSpot = user = "";
        }

        // setter methods
        public void setDate(String date) {
            this.date = date;
        }

        public void setEmergencyType(String comment) {
            this.emergencyType = comment;
        }

        public void setParkingSpot(String rating) {
            this.parkingSpot = rating;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return String.format("\nEMERGENCY ALERT\n_-_-_-_-_-_-_-_-_-_-_-_-_-\n\nUSER:  %s\t\t\tDATE:  %s\n\nEMERGENCY TYPE: %s\nPARKING SPOT: %s\n", user, date, emergencyType, parkingSpot);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;    // width of popup window
        int height = dm.heightPixels;  // height of popup window

        getWindow().setLayout((int)(width*.8),(int)(height*.3));  // size of popup window

        listView = (ListView) findViewById(R.id.listViewWindow);
        root = new Firebase("https://ipark-e243b.firebaseio.com/NewEmergency");
        final ArrayAdapter<PopUp.emergency> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        // populate emergency objects and add to list for display by array adapter
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                        temp = new emergency();

                        temp.setDate(child.child("Date").getValue(String.class));
                        temp.setEmergencyType(child.child("EmergencyType").getValue(String.class));
                        temp.setParkingSpot(child.child("ParkingNumber").getValue(String.class));
                        temp.setUser(child.child("User").getValue(String.class));

                        list.add(temp);
                        arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }
}
