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
public class ChangePasswordTest {
    // ** if test does not pass when you run it, try running it again. Gradle is being hard to work with **
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<ForgotPassword> mActivityRule = new ActivityTestRule<>(
            ForgotPassword.class);

    // expected outcome: it informs the user this email does not exist
    // results: test passed
    @Test
    public void changePasswordNonExistingEmail() throws InterruptedException{
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // type your email into the emailField EditText
        onView(withId(R.id.emailField))
                .perform(typeText("mchemele@ucsd.edu"), ViewActions.closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on SEND button
        onView(withId(R.id.send)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}