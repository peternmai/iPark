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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
/**
 * Created by Connie Su on 11/30/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserHelpTest {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Rule
    public ActivityTestRule<UserHomepage> mActivityTestRule = new ActivityTestRule<>(UserHomepage.class);
    @Test
    public void clickHelpFromHome() throws InterruptedException {
        auth.signInWithEmailAndPassword("connieli2014@gmail.com", "aaaaaa");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imageButton))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.imageButton))
                .perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
