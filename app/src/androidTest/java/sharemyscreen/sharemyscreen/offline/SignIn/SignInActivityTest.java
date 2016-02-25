package sharemyscreen.sharemyscreen.offline.SignIn;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sharemyscreen.sharemyscreen.Connexion;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by cleme_000 on 25/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignInActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    private Context _pContext;

    @Test
    public void signInWithoutConnexion() {
//        this._pContext = mActivityRule.getActivity().getApplicationContext();

        onView(withId(R.id.signin_username_editText)).perform(typeText("test"));
        onView(withId(R.id.signin_password_editText)).perform(typeText("test"));
        onView(withId(R.id.signin_submitLogin)).perform(click());
        onView(withId(R.id.display_snackbar)).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(withText(R.string.offline)));
    }
}