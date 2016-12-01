package ucsd.cse110fa16.group14.ipark;

import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Connie Su on 11/30/2016.
 */

public class MessageTest {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Rule
    public ActivityTestRule<UserReviewHistory> mActivityTestRule = new ActivityTestRule<>(UserReviewHistory.class);
    
    @Test
    public void clickMessage() throws InterruptedException {
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.imageButton5))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
