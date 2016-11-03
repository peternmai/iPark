package ucsd.cse110fa16.group14.ipark;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class iLink {
    private static String usersNode = "https://ipark-e243b.firebaseio.com/Users/";
    private static String parkingLot = "https://ipark-e243b.firebaseio.com/ParkingLot/";

    private static FirebaseAuth auth;
    private Firebase parkingRef = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");

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

}


