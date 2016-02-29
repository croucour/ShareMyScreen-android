package sharemyscreen.sharemyscreen.Online;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import sharemyscreen.sharemyscreen.DAO.DAOBase;
import sharemyscreen.sharemyscreen.DAO.DatabaseHandler;
import sharemyscreen.sharemyscreen.Online.SignIn.SignInActivityTest;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;
import sharemyscreen.sharemyscreen.SignIn.SignInPresenter;
import sharemyscreen.sharemyscreen.SignIn.SignInService;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

//    @Rule
//    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
//            SignInActivity.class);
//
//    SignInActivityTest signInActivityTest;
//    public ApplicationTest() {
//        signInActivityTest = new SignInActivityTest();
//    }
//
}