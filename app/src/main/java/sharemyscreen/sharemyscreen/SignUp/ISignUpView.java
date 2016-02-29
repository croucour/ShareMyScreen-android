package sharemyscreen.sharemyscreen.SignUp;

import sharemyscreen.sharemyscreen.SignIn.ISignInSignUpView;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public interface ISignUpView extends ISignInSignUpView{
    String getEmail();

    String getConfirmPassword();

    void setErrorEmail(int resId);

    void setErrorConfirmPassword(int resId);
}
