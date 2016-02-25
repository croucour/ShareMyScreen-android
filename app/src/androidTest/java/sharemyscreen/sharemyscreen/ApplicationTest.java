package sharemyscreen.sharemyscreen;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.test.mock.MockContext;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import sharemyscreen.sharemyscreen.DAO.DAOBase;
import sharemyscreen.sharemyscreen.DAO.DatabaseHandler;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;
import sharemyscreen.sharemyscreen.SignIn.SignInPresenter;
import sharemyscreen.sharemyscreen.SignIn.SignInService;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}