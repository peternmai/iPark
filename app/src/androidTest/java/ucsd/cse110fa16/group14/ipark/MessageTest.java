package ucsd.cse110fa16.group14.ipark;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Connie Su on 11/30/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MessageTest {
    @Rule
    public ActivityTestRule<UserHomepage> mActivityTestRule = new ActivityTestRule<>(UserHomepage.class);
    
    @Test
    public void clickMessage() throws InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.imageButton5)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
