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
public class UserHomepageTest {

    // results: all tests are passing

    @Rule
    public ActivityTestRule<UserHomepage> mActivityRule = new ActivityTestRule<>(
            UserHomepage.class);

    @Test
    public void clickOnPersonalInfo() {

        // click on the PersonalInfo button
        onView(withId(R.id.personalInfo)).perform(click());
    }

    @Test
    public void clickOnReserve() {
        // click on the Reserve button
        onView(withId(R.id.reserveButton)).perform(click());
    }

    @Test
    public void clickOnHelp() {
        // click on the Help button
        onView(withId(R.id.imageButton)).perform(click());
    }

    @Test
    public void clickOnReviewHistory() {
        // click on the Reivew History button
        onView(withId(R.id.reviewHistory)).perform(click());
    }

    @Test
    public void clickOnViewMap() {
        // click on the View Map button
        onView(withId(R.id.viewMap)).perform(click());
    }

    @Test
    public void clickOnStatus() {
        // click on the check Status button
        onView(withId(R.id.checkStatus)).perform(click());
    }

    @Test
    public void clickOnEmergency() {
        // click on the Emergency button
        onView(withId(R.id.emergency)).perform(click());
    }

    @Test
    public void clickOnLogout() {
        // click on the Logout button
        onView(withId(R.id.logoutButton)).perform(click());
    }
}
