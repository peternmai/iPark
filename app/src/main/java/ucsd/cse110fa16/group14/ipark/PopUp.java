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
 * Created by maggie on 11/18/16.
 */
public class PopUp extends Activity {

    private Firebase root;
    private ListView listView;
    private ArrayList<PopUp.emergency> list = new ArrayList<>();
    private PopUp.emergency temp;

    class emergency {

        String date, emergencyType, parkingSpot, user;

        emergency() {
            date = emergencyType = parkingSpot = user = "";
        }

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
            return String.format("***EMERGENCY ALERT***\nUSER:  %s\t\t\tDATE:  %s\nEMERGENCY TYPE: %s\nPARKING SPOT: %s\n", user, date, emergencyType, parkingSpot);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        listView = (ListView) findViewById(R.id.listViewWindow);
        root = new Firebase("https://ipark-e243b.firebaseio.com/NewEmergency");

        final ArrayAdapter<PopUp.emergency> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);

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
