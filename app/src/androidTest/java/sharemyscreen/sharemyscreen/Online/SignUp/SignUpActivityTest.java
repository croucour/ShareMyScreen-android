package sharemyscreen.sharemyscreen.Online.SignUp;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.RandomString;
import sharemyscreen.sharemyscreen.SignUp.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by cleme_000 on 27/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule = new ActivityTestRule<>(SignUpActivity.class);

    private Context _pContext;

    private String username = null;
    private String email = null;
    private String password = null;

    @Before
    public void init(){
        RandomString randomString = new RandomString(10);
        username = randomString.nextString() + "test";
        email = randomString.nextString() + "@test.fr";
        password = randomString.nextString() + "test";

        this._pContext = mActivityRule.getActivity().getApplicationContext();
    }

    @Test
    public void signUp() {

        onView(withId(R.id.signup_username_editText)).perform(typeText(username));
        onView(withId(R.id.signup_email_editText)).perform(typeText(email));
        onView(withId(R.id.signup_password_editText)).perform(typeText(password));
        onView(withId(R.id.signup_confirmPassword_editText)).perform(typeText(password));
        SystemClock.sleep(1000);
        onView(withId(R.id.signup_submit)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        SettingsManager settingsManager = new SettingsManager(_pContext);
        settingsManager.addSettings("test_username", username);
        settingsManager.addSettings("password", password);

        this.logout();
    }

    private void logout() {
        openActionBarOverflowOrOptionsMenu(_pContext);
        onView(withText(R.string.disconnect)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.signin_submitLogin)).check(matches(isDisplayed()));
    }

}