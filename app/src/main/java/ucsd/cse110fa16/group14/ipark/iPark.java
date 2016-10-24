package ucsd.cse110fa16.group14.ipark;

import android.app.Application;

import com.firebase.client.Firebase;


public class iPark extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
