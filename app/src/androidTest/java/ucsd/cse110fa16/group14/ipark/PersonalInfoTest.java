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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Connie Su on 11/28/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PersonalInfoTest {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<PersonalInfo> mActivityRule = new ActivityTestRule<>(
            PersonalInfo.class);

    @Test
    public void clickHome() throws InterruptedException{
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on home button
        onView(withId(R.id.button19)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void clickHelp() throws InterruptedException{
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on help button
        onView(withId(R.id.help)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Help Information"))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click done on dialog message
        onView(withId(android.R.id.button1))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clickChangePassword() throws InterruptedException{
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on change password
        onView(withId(R.id.button21)).perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
