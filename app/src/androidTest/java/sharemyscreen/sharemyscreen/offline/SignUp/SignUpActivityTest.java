package sharemyscreen.sharemyscreen.offline.SignUp;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.RandomString;
import sharemyscreen.sharemyscreen.SignUp.SignUpActivity;

import static android.support.test.espresso.Espresso.onView;
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

    public void set_pContext(Context _pContext) {
        this._pContext = _pContext;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
        onView(withId(R.id.signup_submit)).perform(click());

        onView(withId(R.id.display_snackbar)).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(withText(R.string.offline)));
    }
}