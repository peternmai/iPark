package ucsd.cse110fa16.group14.ipark;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by yuyangchen on 11/4/16.
 */

public class ParkingLot {


    HashMap<String, ParkingSpot> parkingLot = new HashMap<String, ParkingSpot>();
    ParkingSpot mySpot;
    FirebaseAuth auth;

    boolean getParkingLotData() {

        Firebase fReference = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");

        auth = FirebaseAuth.getInstance();

        fReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Iterable<com.firebase.client.DataSnapshot> mainNodeData = dataSnapshot.getChildren();
                Iterator<DataSnapshot> spotIterator = mainNodeData.iterator();

                //Getting mainNode keys
                while (spotIterator.hasNext()) {

                    com.firebase.client.DataSnapshot node = spotIterator.next();
                    String spotNumber = node.getKey();

                    // create a new parking spot and initialize it according to firebase info
                    ParkingSpot tempSpot = new ParkingSpot(spotNumber);
                    parkingLot.put(spotNumber, tempSpot);

                    Iterable<com.firebase.client.DataSnapshot> spotInfoField = node.getChildren();
                    Iterator<com.firebase.client.DataSnapshot> fieldIterator = spotInfoField.iterator();

                    //Getting innerNodeKey's value
                    while (fieldIterator.hasNext()) {
                        com.firebase.client.DataSnapshot innerNode = fieldIterator.next();
                        String fieldName = innerNode.getKey();

                        // right now assume only one start and end time
                        if (fieldName.equals("EndTime")) {
                            String fieldVal = innerNode.getValue(String.class);
                            tempSpot.setEnd(fieldVal);
                        } else if (fieldName.equals("StartTime")) {
                            String fieldVal = innerNode.getValue(String.class);
                            tempSpot.setStart(fieldVal);
                        } else if (fieldName.equals("Illegal")) {
                            boolean fieldVal = innerNode.getValue(Boolean.class);
                            tempSpot.setIllegal(fieldVal);
                        } else if (fieldName.equals("Occupied")) {
                            boolean fieldVal = innerNode.getValue(Boolean.class);
                            tempSpot.setOccupied(fieldVal);
                        } else if (fieldName.equals("Account")) {
                            String fieldVal = innerNode.getValue(String.class);
                            if (auth.getCurrentUser() != null) {
                                String username = auth.getCurrentUser().getUid();

                                if (username.equals(fieldVal)) {
                                    mySpot = tempSpot;
                                }
                            }
                            tempSpot.setUser(fieldVal);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v("NO ACCESS ERROR", "Could not connect to Firebase");
            }
        });
        return true;
    }

    boolean pushToFirebase(int spotNumber) {

        return true;

    }

    boolean switchStatus(int spotNumber) {
        return true;
    }


}
