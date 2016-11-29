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
public class OwnerHomepageTest3 {
    // ** if test does not pass when you run it, try running it again. Gradle is being hard to work with **
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<OwnerHomepage> mActivityRule = new ActivityTestRule<>(
            OwnerHomepage.class);

    // expected outcome: owner is able to click on different buttons on the homepage
    // results: test passed
    @Test
    public void clickingHome() throws InterruptedException {
        auth.signInWithEmailAndPassword("admin@ipark.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // click on the Reviews button
        onView(withId(R.id.reviews)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}