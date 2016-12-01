package ucsd.cse110fa16.group14.ipark;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.NumberPicker;
import com.google.firebase.auth.FirebaseAuth;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by Connie Su on 11/29/2016.
 =======
 import static android.support.test.espresso.assertion.ViewAssertions.matches;
 import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
 import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
 import static android.support.test.espresso.matcher.ViewMatchers.withId;
 import static android.support.test.espresso.matcher.ViewMatchers.withText;
 /**
 * Created by Connie Su on 11/29/2016.
 *
 * Sources: http://stackoverflow.com/questions/24074495/automating-number-picker-in-android-using-espresso
 >>>>>>> 9db19ce
 =======
 import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
 import static android.support.test.espresso.matcher.ViewMatchers.withId;
 /**
 * Created by Connie Su on 11/29/2016.
 >>>>>>> fix conflict with mapreport
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ClockinTest {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Rule
    public ActivityTestRule<UserHomepage> mActivityTestRule = new ActivityTestRule<>(UserHomepage.class);

    @Test
    public void selectReserve() throws InterruptedException {
        auth.signInWithEmailAndPassword("mchemele@ucsd.edu", "password");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.reserveButton))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction hourAPicker = onView(withId(R.id.hour));
        hourAPicker.perform(setNum(11));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction minAPicker = onView(withId(R.id.min));
        minAPicker.perform(setNum(40));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction dayAPicker = onView(withId(R.id.amPM));
        dayAPicker.perform(setNum(2));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.next))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction hourPPicker = onView(withId(R.id.hour));
        hourPPicker.perform(setNum(11));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction minPPicker = onView(withId(R.id.min));
        minPPicker.perform(setNum(50));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction dayPPicker = onView(withId(R.id.amPM));
        dayPPicker.perform(setNum(2));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.next))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.confirm))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.countdownCheckoutButton))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Cancel order?"))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(android.R.id.button2))
                .perform(click());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static ViewAction setNum(final int num) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker np = (NumberPicker) view;
                np.setValue(num);
            }
            @Override
            public String getDescription() {
                return "Set the passed number into the NumberPicker";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }
}
