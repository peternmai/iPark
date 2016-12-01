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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginPage> mActivityRule = new ActivityTestRule<>(
            LoginPage.class);
    // ** if test does not pass when you run it, try running it again. Gradle is being hard to work with **
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // expected outcome: user is successfully logged in
    // results: test passed
    @Test
    public void performLogin() throws InterruptedException{

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // type username into the userName EditText
        onView(withId(R.id.userName))
                .perform(typeText("mag"), ViewActions.closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // type password into the password EditText
        onView(withId(R.id.password))
                .perform(typeText("password"), ViewActions.closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        auth.signInWithEmailAndPassword("mchemele@ucsd.edu", "password");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on the login button
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        auth.signOut();
    }
}