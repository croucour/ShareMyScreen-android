package sharemyscreen.sharemyscreen.offline.Profile;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;
import sharemyscreen.sharemyscreen.offline.SignIn.SignInActivityTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by cleme_000 on 28/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(SignInActivity.class);

    private Context _pContext;
    private String username;
    private String password;
    private ProfileManager _profileManager;
    private SignInActivityTest _signUpActivityTest;


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

        _signUpActivityTest = new SignInActivityTest();
        this._pContext = mActivityRule.getActivity().getApplicationContext();

        _signUpActivityTest.set_pContext(_pContext);
        SettingsManager settingsManager = new SettingsManager(_pContext);

        username = settingsManager.select("test_username");
        password = settingsManager.select("password");

        if (username == null || password == null) {
            username = "test";
            password = "test";
        }

        _signUpActivityTest.setUsername(username);
        _signUpActivityTest.setPassword(password);

        _profileManager = new ProfileManager(_pContext);
    }


    public void goToProfileModification() {
        openActionBarOverflowOrOptionsMenu(_pContext);
        onView(withText(R.string.modify_profil)).perform(click());
        onView(withId(R.id.profile_submit)).check(matches(isDisplayed()));
    }

    public void setModificationProfile(String firstname, String lastname, String email, String phone, boolean save){
        onView(withId(R.id.profile_firstname_editText)).perform(replaceText(firstname));
        onView(withId(R.id.profile_lastname_editText)).perform(replaceText(lastname));
        onView(withId(R.id.profile_email_editText)).perform(replaceText(email));
        onView(withId(R.id.profile_phone_editText)).perform(replaceText(phone));

        if (save) {
            onView(withId(R.id.profile_submit)).perform(click());
        }
    }
}