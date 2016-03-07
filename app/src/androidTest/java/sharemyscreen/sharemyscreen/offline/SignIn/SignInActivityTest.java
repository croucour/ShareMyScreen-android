package sharemyscreen.sharemyscreen.offline.SignIn;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.GlobalManager;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by cleme_000 on 25/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignInActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    private Context _pContext;
    private String username;
    private String password;
    private ProfileManager _profileManager;

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
    public void init()
    {

        this._pContext = mActivityRule.getActivity().getApplicationContext();

        GlobalManager globalManager = new GlobalManager(_pContext);

        username = globalManager.select("test_username");
        password = globalManager.select("password");

        if (username == null || password == null) {
            username = "test";
            password = "test";
        }

        _profileManager = new ProfileManager(_pContext);
    }

    @Test
    public void astep1signInWithoutConnexion() {
        onView(withId(R.id.signin_username_editText)).perform(typeText(username));
        onView(withId(R.id.signin_password_editText)).perform(typeText(password));
        onView(withId(R.id.signin_submitLogin)).perform(click());
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }

    @Test
    public void astep2signInWithoutConnexion() {
        String tmpusername = username.substring(1);
        onView(withId(R.id.signin_username_editText)).perform(typeText(tmpusername));
        onView(withId(R.id.signin_password_editText)).perform(typeText(password));
        onView(withId(R.id.signin_submitLogin)).perform(click());
        onView(withId(R.id.display_snackbar)).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(withText(R.string.connexionOfflline_errorUsernameOrPassword)));
    }
}