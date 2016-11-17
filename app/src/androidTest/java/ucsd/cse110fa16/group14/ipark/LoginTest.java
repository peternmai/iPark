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
@LargeTest
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginPage> mActivityRule = new ActivityTestRule<>(
            LoginPage.class);

    @Test
    public void performLogin() {
        // type username into the userName EditText
        onView(withId(R.id.userName))
                .perform(typeText("maggie"), ViewActions.closeSoftKeyboard());

        // type password into the password EditText
        onView(withId(R.id.password))
                .perform(typeText("password"), ViewActions.closeSoftKeyboard());

        // click on the login button
        onView(withId(R.id.loginButton)).perform(click());

        // expected outcome: user is logged in
        // results: *** test failed
    }

    @Test
    public void clickOnRegisterButton() {
        // click on the register button
         onView(withId(R.id.registerButton)).perform(click());

        // expected outcome: user is sent to the Registration page
        // results: test passed
    }
}