package sharemyscreen.sharemyscreen.SignUp;

import sharemyscreen.sharemyscreen.SignIn.ISignInSignUpView;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public interface ISignUpView extends ISignInSignUpView{

    String getFirstName();

    String getLastName();

    String getConfirmPassword();

    void setErrorConfirmPassword(int resId);

    void setErrorFirstName(int resId);

    void setErrorLastName(int resId);
}
