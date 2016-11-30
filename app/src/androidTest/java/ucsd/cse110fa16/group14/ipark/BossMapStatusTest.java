package ucsd.cse110fa16.group14.ipark;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

/**
 * Created by yuriguan on 11/30/16.
 */

public class BossMapStatusTest {
    // ** if test does not pass when you run it, try running it again. Gradle is being hard to work with **
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<BossMap> mActivityRule = new ActivityTestRule<>(
            BossMap.class);

    // expected outcome: owner is able to check the status of the parking lot
    // results: test passed
    @Test
    public void clickingHome() throws InterruptedException {
        auth.signInWithEmailAndPassword("admin@ipark.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on the status button to see status of the parking lot
        onView(withId(R.id.status)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
