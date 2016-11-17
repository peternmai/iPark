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
public class RegistrationTest {
    @Rule
    public ActivityTestRule<DriverRegistration> mActivityRule = new ActivityTestRule<>(
            DriverRegistration.class);

    @Test
    public void tryRegisteringWithExistingEmail() {

        // type first name in firstName EditText
       onView(withId(R.id.firstName))
                .perform(typeText("maggie"), ViewActions.closeSoftKeyboard());

        // type last name in lastName EditText
        onView(withId(R.id.lastName))
                .perform(typeText("chem"), ViewActions.closeSoftKeyboard());

        // type email in emailAddress EditText
        onView(withId(R.id.emailAddress))
                .perform(typeText("sr_misty@yahoo.com"), ViewActions.closeSoftKeyboard());

        // type license in license EditText
        onView(withId(R.id.license))
                .perform(typeText("A119999"), ViewActions.closeSoftKeyboard());

        // type username in userName EditText
        onView(withId(R.id.userName))
                .perform(typeText("maggie1"), ViewActions.closeSoftKeyboard());

        // type password in userPassword EditText
        onView(withId(R.id.userPassword))
                .perform(typeText("password"), ViewActions.closeSoftKeyboard());

        // click on not a robot
        onView(withId(R.id.notARobot)).perform(click());

        // click on submit button
        onView(withId(R.id.submit_registration)).perform(click());

        // expected result: user is informed the email already exists => can't register
        // results: test passed
    }

    @Test
    public void tryRegisterWithNewInfo() {
        onView(withId(R.id.firstName))
                .perform(typeText("maggie"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.lastName))
                .perform(typeText("chem"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.emailAddress))
                .perform(typeText("mchemele@ucsd.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.license))
                .perform(typeText("A119999"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userName))
                .perform(typeText("maggie1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userPassword))
                .perform(typeText("password"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.notARobot)).perform(click());

        onView(withId(R.id.submit_registration)).perform(click());

        // expected result: user is able to register
        // results: test passed
    }
}
