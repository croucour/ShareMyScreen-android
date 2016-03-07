package sharemyscreen.sharemyscreen.Online.SignIn;

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
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cleme_000 on 27/02/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RefreshTokenTest{

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    private Context _pContext;
    private String username = null;
    private String password = null;
    private SignInActivityTest _singnInActivity;
    private ProfileManager _profileManager;


    @Before
    public void init()
    {
        this._singnInActivity = new SignInActivityTest();

        this._pContext = mActivityRule.getActivity().getApplicationContext();
        this._singnInActivity.set_pContext(_pContext);

        GlobalManager globalManager = new GlobalManager(_pContext);

        username = globalManager.select("test_username");
        password = globalManager.select("password");

        if (username == null || password == null) {
            username = "test";
            password = "test";
        }
        this._singnInActivity.setPassword(password);
        this._singnInActivity.setUsername(username);

        _profileManager = new ProfileManager(_pContext);

    }

    @Test
    public void astep0RefreshToken()
    {
        this._singnInActivity.logout();
    }

    @Test
    public void astep1RefreshToken()
    {
        this._singnInActivity.signIn();
    }

    @Test
    public void astep2RefreshToken()
    {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        this._singnInActivity.logout();
    }

    @Test
    public void bstep1RefreshToken()
    {
        this._singnInActivity.signIn();
    }

//    @Test
//    public void bstep2RefreshToken()
//    {
//        ProfileEntity profileEntity = _profileManager.selectByUsername(username);
//
//        GlobalManager globalManager = new GlobalManager(_pContext);
//
//        long expires_in = Long.parseLong(globalManager.select("expires_in"));
//        long expireToken = Long.parseLong(profileEntity.get_expireAccess_token()) - (expires_in * 1000);
//
//        profileEntity.set_expireAccess_token(String.valueOf(expireToken));
//        _profileManager.modifyProfil(profileEntity);
//    }

    @Test
    public void bstep3RefreshToken()
    {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }
}
