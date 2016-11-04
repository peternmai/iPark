package ucsd.cse110fa16.group14.ipark;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

public class iLink {
    private static String usersNode = "https://ipark-e243b.firebaseio.com/Users/";
    private static String parkingLot = "https://ipark-e243b.firebaseio.com/ParkingLot/";
    private static User user;
    private static FirebaseAuth auth;
    static ArrayList<String> parkingspots = new ArrayList<>();
    static ArrayList<String> avail = new ArrayList<>();
    HashMap<String, Integer> incase = new HashMap<>();

    public static Task changePassword(String username, String newPassword) {
        auth = FirebaseAuth.getInstance();
        Task task = auth.getCurrentUser().updatePassword(newPassword);
        if (task.isSuccessful()) {
            Firebase passwordRef = new Firebase(usersNode + username + "/password");
            passwordRef.setValue(newPassword);
        }
        return task;
    }

    public static Task changeEmail(String username, String newEmail) {
        auth = FirebaseAuth.getInstance();
        Task task = auth.getCurrentUser().updatePassword(newEmail);
        if (task.isSuccessful()) {
            Firebase emailRef = new Firebase(usersNode + username + "/email");
            emailRef.setValue(newEmail);
            DriverRegistration.uMapEmail.put(username, newEmail);
        }
        return task;
    }

    public static void changeStartTime(String spot, String newStartTime) {
        Firebase startTimeRef = new Firebase(usersNode + spot + "/StartTime");
        startTimeRef.setValue(newStartTime);
    }

    public static void changeEndTime(String spot, String newEndTime) {
        Firebase endTimeRef = new Firebase(parkingLot + spot + "/EndTime");
        endTimeRef.setValue(newEndTime);
    }

    public static void changePrice(String spot, String newPrice) {
        Firebase priceRef = new Firebase(parkingLot + spot + "/Price");
        priceRef.setValue(newPrice);
    }

    public static void changeLegalStatus(String spot, boolean newStatus) {
        Firebase legalRef = new Firebase(parkingLot + spot + "/Illegal");
        legalRef.setValue(newStatus);
    }

    public static void changeReserveStatus(String spot, boolean newStatus) {
        Firebase reserveRef = new Firebase(parkingLot + spot + "/Reserved");
        reserveRef.setValue(newStatus);
    }

    public static void Reserve(){
        Firebase parkinglot = new Firebase(parkingLot);
        int count;


        parkinglot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> spots = dataSnapshot.getChildren();
                Iterator<DataSnapshot> spotIter = spots.iterator();

                while (spotIter.hasNext()) {
                    DataSnapshot node = spotIter.next();
                    String key = node.getKey();
                    parkingspots.add(key);

                    Iterable<DataSnapshot> reserved = node.getChildren();
                    Iterator<DataSnapshot> reserIter = reserved.iterator();

                    /*while (reserIter.hasNext()) {
                        DataSnapshot snap = reserIter.next();
                        String status = snap.getKey();

                        if (status.equals("Reserved")) {
                            Boolean free = snap.getValue(Boolean.class);
                            if (free.equals(false)) {
                                avail.add(key);
                            }

                        }
                    }*/
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }
/*
    public static User getCurrentUser(){
        user = new User();
        auth = FirebaseAuth.getInstance();
        String name = auth.getCurrentUser().getDisplayName();
        Firebase userRef = new Firebase(usersNode + name);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> info = dataSnapshot.getChildren();
                Iterator<DataSnapshot> infoIterator = info.iterator();
                while(infoIterator.hasNext()){
                    DataSnapshot node = infoIterator.next();
                    String vals = node.getKey();

                    switch (vals){
                        case "email":
                            user.setStringEmail(node.getValue(String.class));
                            break;
                        case "license":
                            user.setStringLicense(node.getValue(String.class));
                            break;
                        case "name":
                            break;
                        case "username":
                            user.setStringUsername(node.getValue(String.class));
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("","Error retrieving data");
            }
        });


        return user;
    }
    */
}