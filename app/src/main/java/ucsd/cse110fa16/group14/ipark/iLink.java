package ucsd.cse110fa16.group14.ipark;

import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Abhigya Ghimire on 11/3/2016.
 */

public class iLink {
    private static String usersNode = "https://ipark-e243b.firebaseio.com/Users/";
    private static String parkingLot = "https://ipark-e243b.firebaseio.com/ParkingLot/";

    private static FirebaseAuth auth;
    private Firebase parkingRef = new Firebase("https://ipark-e243b.firebaseio.com/ParkingLot");

    public static Task changePassword(String username, String newPassword) {
        auth = FirebaseAuth.getInstance();
        Task task = auth.getCurrentUser().updatePassword(newPassword);
        if(task.isSuccessful()){
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

    public static void changeStartTime(String username, String newPassword) {
        Firebase passwordRef = new Firebase(usersNode + username + "/password");
        passwordRef.setValue(newPassword);
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser().updatePassword(newPassword);
    }


}
