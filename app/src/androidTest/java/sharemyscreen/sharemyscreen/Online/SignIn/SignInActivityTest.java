package sharemyscreen.sharemyscreen.Online.SignIn;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sharemyscreen.sharemyscreen.DAO.GlobalManager;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cleme_000 on 24/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignInActivityTest{
    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    private Context _pContext;
    private String username = null;
    private String password = null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void set_pContext(Context _pContext) {
        this._pContext = _pContext;
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
    }

    @Test
    public void signIn() {
        onView(withId(R.id.signin_username_editText)).perform(typeText(username));
        onView(withId(R.id.signin_password_editText)).perform(typeText(password));
        onView(withId(R.id.signin_submitLogin)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.fab)).check(matches(isDisplayed()));

//        ProfileManager _proProfileManager = new ProfileManager(_pContext);
//        ProfileEntity profileEntity = _proProfileManager.selectProfileLogged();
//        assertNotNull(profileEntity);
//        assertEquals(profileEntity.get_username(), username);
//        assertEquals(profileEntity.get_password(), password);
    }

//    @Test
//    public void signInWithLogout() {
//        this.signIn();
//
//        this.logout();
//    }

    protected void logout() {
        openActionBarOverflowOrOptionsMenu(_pContext);
        onView(withText(R.string.disconnect)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.signin_submitLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void signInFail() {
        String usernameFail = username.substring(1);

        onView(withId(R.id.signin_username_editText)).perform(typeText(usernameFail));
        onView(withId(R.id.signin_password_editText)).perform(typeText(password));
        onView(withId(R.id.signin_submitLogin)).perform(click());
        onView(withId(R.id.display_snackbar)).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(withText(R.string.api_error)));
    }

    @Test
    public void SignUp() {
        onView(withId(R.id.signin_signup)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.signup_submit)).check(matches(isDisplayed()));
    }

}