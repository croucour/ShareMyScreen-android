package sharemyscreen.sharemyscreen.offline.Service;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineDAO;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.RandomString;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;
import sharemyscreen.sharemyscreen.SignUp.SignUpActivity;
import sharemyscreen.sharemyscreen.offline.Profile.ProfileActivityTest;
import sharemyscreen.sharemyscreen.offline.SignIn.SignInActivityTest;
import sharemyscreen.sharemyscreen.offline.SignUp.SignUpActivityTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by cleme_000 on 28/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class OfflineTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(SignInActivity.class);

    private Context _pContext;
    private String username;
    private String password;
    private ProfileManager _profileManager;
    private RequestOfflineManager _requestOfflineManager;
    private SignInActivityTest _signUpActivityTest;
    private ProfileActivityTest _profileActivityTest;
    private RandomString randomString;

    @Before
    public void init(){

        _signUpActivityTest = new SignInActivityTest();
        _profileActivityTest = new ProfileActivityTest();
        this._pContext = mActivityRule.getActivity().getApplicationContext();

        _signUpActivityTest.set_pContext(_pContext);
        _profileActivityTest.set_pContext(_pContext);
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
        _requestOfflineManager = new RequestOfflineManager(_pContext);

        randomString = new RandomString(10);

    }

    @Test
    public void astep1_addRequest() {
        ArrayList<RequestOfflineEntity> requestOfflineEntities = _requestOfflineManager.selectAll();

        this._signUpActivityTest.astep1signInWithoutConnexion();
        this._profileActivityTest.goToProfileModification();
        this._profileActivityTest.setModificationProfile(randomString.nextString(), randomString.nextString(), randomString.nextString(), "06", true);
        onView(withId(R.id.fab)).check(matches(isDisplayed()));

        ArrayList<RequestOfflineEntity> afterRequestOfflineEntities = _requestOfflineManager.selectAll();

        assertNotEquals(requestOfflineEntities.size(), afterRequestOfflineEntities.size());
    }

}
