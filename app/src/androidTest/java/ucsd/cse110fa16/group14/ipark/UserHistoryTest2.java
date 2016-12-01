package ucsd.cse110fa16.group14.ipark;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
/**
 * Created by Connie Su on 11/29/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserHistoryTest2 {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Rule
    public ActivityTestRule<UserReviewHistoryPage2> mActivityTestRule = new ActivityTestRule<>(UserReviewHistoryPage2.class);
    @Test
    public void clickHome() throws InterruptedException {
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imageButton3))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void clickPrev() throws InterruptedException {
        auth.signInWithEmailAndPassword("sr_misty@yahoo.com", "password");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imageButton4))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
