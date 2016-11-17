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

public class CommentBossTest {

    @Rule
    public ActivityTestRule<comment> mActivityRule = new ActivityTestRule<>(
            comment.class);

    @Test
    public void sendAComment() {
        // type comment into the userComment EditText
        onView(withId(R.id.userComment))
                .perform(typeText("awesome app!"), ViewActions.closeSoftKeyboard());

       //  click on rating
        onView(withId(R.id.rating)).perform(click());

        // click on the submit button
        onView(withId(R.id.submitButton)).perform(click());

        // expected outcome: comment is successfully sent
        // results: test passed
    }

    @Test
    public void cancelAComment() {
        // type comment into the userComment EditText
        onView(withId(R.id.userComment))
                .perform(typeText("THE BEST!!"), ViewActions.closeSoftKeyboard());

        //  click on rating
        onView(withId(R.id.rating)).perform(click());

        // click on the submit button
        onView(withId(R.id.cancelButton)).perform(click());

        // expected outcome: user is send back to the homepage
        // results: test passed
    }
}
