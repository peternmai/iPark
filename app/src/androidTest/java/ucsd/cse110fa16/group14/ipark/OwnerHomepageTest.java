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
public class OwnerHomepageTest {

    // results: all tests are passing

    @Rule
    public ActivityTestRule<OwnerHomepage> mActivityRule = new ActivityTestRule<>(
            OwnerHomepage.class);

    @Test
    public void clickOnMap() {

        // click on the Map button
        onView(withId(R.id.map1)).perform(click());
    }

    @Test
    public void clickOnReviews() {
        // click on the Reviews button
        onView(withId(R.id.reviews)).perform(click());
    }

    @Test
    public void clickOnCompose() {
        // click on the Compose button
        onView(withId(R.id.compose)).perform(click());
    }

    @Test
    public void clickOnEmergency1() {
        // click on the Emergency button
        onView(withId(R.id.emergency1)).perform(click());
    }

    @Test
    public void clickOnLogout() {
        // click on the View Map button
        onView(withId(R.id.logout)).perform(click());
    }

}
