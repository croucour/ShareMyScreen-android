package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;
import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.R;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by cleme_000 on 23/02/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SignInPresenterTest extends AndroidTestCase{
    @Mock
    private SignInActivity _view;
    private SignInPresenter _presenter;
    @Mock
    private Context _pContext;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(_view.getUsername()).thenReturn("");
        when(_view.getPassword()).thenReturn("test");
        _presenter.onLoginClicked();

        verify(_view).showUsernameError(R.string.signin_emailEmpty);
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
        when(_view.getUsername()).thenReturn("");
        when(_view.getPassword()).thenReturn("");
        _presenter.onLoginClicked();

        verify(_view).showUsernameError(R.string.signin_emailEmpty);
        verify(_view).showPasswordError(R.string.signin_passwordEmpty);
    }


}