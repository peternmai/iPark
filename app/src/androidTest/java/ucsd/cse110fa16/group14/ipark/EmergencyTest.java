package ucsd.cse110fa16.group14.ipark;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class EmergencyTest {
    // ** if test does not pass when you run it, try running it again. Gradle is being hard to work with **
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<Emergency> mActivityRule = new ActivityTestRule<>(
            Emergency.class);

    // expected outcome: user sends an emergency report successfully
    // results: test passed
    @Test
    public void sendAnEmergencyReport() throws InterruptedException{
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on the Personal Safety radio Button
        onView(withId(R.id.personalSafety)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // type parking number in parkingNumber EditText
        onView(withId(R.id.parkingNumber))
                .perform(typeText("Spot011"), ViewActions.closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on the Send button
        onView(withId(R.id.button25)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
