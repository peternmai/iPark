package ucsd.cse110fa16.group14.ipark;

/**
 * Created by yuriguan on 11/28/16.
 */

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
public class PriceSettingTest {
    // ** if test does not pass when you run it, try running it again. Gradle is being hard to work with **
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<Settings> mActivityRule = new ActivityTestRule<>(
            Settings.class);

    // expected outcome: owner is able to change the price
    // results: test passed
    @Test
    public void changePrice() throws InterruptedException{
        auth.signInWithEmailAndPassword("admin@ipark.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // type new price
        onView(withId(R.id.NEWPRICE))
                .perform(typeText("3.00"), ViewActions.closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on the Change button
        onView(withId(R.id.change)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
