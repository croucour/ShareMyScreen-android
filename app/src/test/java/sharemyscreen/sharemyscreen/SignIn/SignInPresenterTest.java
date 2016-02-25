package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import sharemyscreen.sharemyscreen.DAO.DAOBase;
import sharemyscreen.sharemyscreen.DAO.DatabaseHandler;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.R;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by cleme_000 on 23/02/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SignInPresenterTest extends InstrumentationTestCase{
    @Mock
    private SignInActivity _view;
    private SignInPresenter _presenter;
    private Context _pContext;

    @Before
    public void setUp() throws Exception {
        _pContext = new MockContext();
        this._presenter = new SignInPresenter(_view, _pContext);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(_view.getUsername()).thenReturn("");
        when(_view.getPassword()).thenReturn("test");
        _presenter.onLoginClicked();

        verify(_view).showUsernameError(R.string.signin_usernameEmpty);
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(_view.getUsername()).thenReturn("test");
        when(_view.getPassword()).thenReturn("");
        _presenter.onLoginClicked();

        verify(_view).showPasswordError(R.string.signin_passwordEmpty);
    }

    @Test
    public void shouldShowLoginErrorWhenUsernameAndPasswordAreInvalid() throws Exception {
    }

}