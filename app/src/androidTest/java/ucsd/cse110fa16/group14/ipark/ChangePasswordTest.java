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

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ChangePasswordTest {


    @Rule
    public ActivityTestRule<ForgotPassword> mActivityRule = new ActivityTestRule<>(
            ForgotPassword.class);

    @Test
    public void changePasswordNonExistingEmail() {
        // type your password into the emailField EditText
        onView(withId(R.id.emailField))
                .perform(typeText("mchemele@ucsd.edu"), ViewActions.closeSoftKeyboard());

        // click on SEND button
        onView(withId(R.id.send)).perform(click());

        // expected outcome: it informs the user this email does not exist
        // results: test passed
    }

    @Test
    public void changePasswordExistingEmail() {
        // type your password into the emailField EditText
        onView(withId(R.id.emailField))
                .perform(typeText("sr_misty@yahoo.com"), ViewActions.closeSoftKeyboard());

        // click on SEND button
        onView(withId(R.id.send)).perform(click());

        // expected outcome: email should be send to the user
        // results: test passed
    }
}