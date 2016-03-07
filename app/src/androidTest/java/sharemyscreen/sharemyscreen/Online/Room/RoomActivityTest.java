package sharemyscreen.sharemyscreen.Online.Room;

import android.content.Context;
import android.os.SystemClock;
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
import sharemyscreen.sharemyscreen.Online.SignIn.SignInActivityTest;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.RandomString;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by cleme_000 on 01/03/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoomActivityTest {

    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);

    private Context _pContext;
    private String username = null;
    private String password = null;
    private SignInActivityTest _singnInActivity;
    private ProfileManager _profileManager;
    RandomString _randomString = new RandomString(10);


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

        _singnInActivity.signIn();
    }

    @Test
    public void addRoom() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));

        onView(withId(R.id.fab)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.fab_sheet)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_sheet_item_add_by_user)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.createRoom_by_user_name_editText)).perform(typeText(_randomString.nextString()));
        onView(withId(R.id.createRoom_by_user_choose_user)).perform(click());
        onData(withText("test98")).perform(click());
//        onView(withId(R.id.createRoom_by_user_choose_user)).withSpinnerText("test98")).perform(click());
        SystemClock.sleep(100);
        onView(withId(R.id.createRoom_by_user_submit)).perform(click());
        SystemClock.sleep(1000);

    }
}