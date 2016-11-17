package ucsd.cse110fa16.group14.ipark;

/**
 * Created by maggie on 11/17/16.
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

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PersonalInfoTest {

    @Rule
    public ActivityTestRule<PersonalInfo> mActivityRule = new ActivityTestRule<>(
            PersonalInfo.class);

    @Test
    public void clickOnChangePassword() {
        // click on the Change password button
        onView(withId(R.id.button21)).perform(click());

        // expected outcome: user is sent to Change Password page
        // results: test passed
    }
}
