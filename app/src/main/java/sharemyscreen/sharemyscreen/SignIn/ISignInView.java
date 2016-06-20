package sharemyscreen.sharemyscreen.SignIn;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

/**
 * Created by cleme_000 on 23/02/2016.
 */
public interface ISignInView extends ISignInSignUpView{

    LoginButton getSignInButtonFacebook();
    SignInButton getSignInButtonGoogle();
}
