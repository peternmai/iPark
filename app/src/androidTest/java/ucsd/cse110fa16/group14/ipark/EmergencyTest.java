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

public class EmergencyTest {

    @Rule
    public ActivityTestRule<Emergency> mActivityRule = new ActivityTestRule<>(
            Emergency.class);

    @Test
    public void sendAnEmergencyReport() {

        // click on the Personal Safety radio Button
        onView(withId(R.id.personalSafety)).perform(click());

        // type parking number in parkingNumber EditText
        onView(withId(R.id.parkingNumber))
                .perform(typeText("Spot000"), ViewActions.closeSoftKeyboard());

        // click on the Send button
        onView(withId(R.id.button25)).perform(click());

        // expected outcome: user sends an emergency report successfully
        // results: test passed
    }

    @Test
    public void cancelAnemergencyReport() {

        // type parking number in parkingNumber EditText
        onView(withId(R.id.hour))
                .perform(typeText("5"), ViewActions.closeSoftKeyboard());

        // type parking number in parkingNumber EditText
        onView(withId(R.id.min))
                .perform(typeText("20"), ViewActions.closeSoftKeyboard());

        // type parking number in parkingNumber EditText
        onView(withId(R.id.amPM))
                .perform(typeText("PM"), ViewActions.closeSoftKeyboard());

        // click on the Cancel button
        onView(withId(R.id.next)).perform(click());

        // expected outcome: fire report is canceled and user is send back to homepage
        // results: test passed
    }
}
