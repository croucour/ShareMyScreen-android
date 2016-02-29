package sharemyscreen.sharemyscreen.SignUp;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyString;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.ISignInView;
import sharemyscreen.sharemyscreen.SignIn.SignInService;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public class SignUpPresenter {

    private ISignUpView _view;
    protected SignUpService _signUpService;

    public SignUpPresenter(ISignUpView view, Context pContext) {
        this._view = view;
        this._signUpService = new SignUpService(view, pContext);
    }

    public void onSignUpClicked() {
        boolean submit = true;
        String username = this._view.getUsername();
        String email = this._view.getEmail();
        MyString myStringEmail = new MyString(email);
        String password = this._view.getPassword();
        String confirmPassword = this._view.getConfirmPassword();

//        TextInputLayout signup_username_inputLayout = (TextInputLayout) findViewById(R.id.signup_username_inputLayout);
//        TextInputLayout signup_email_inputLayout = (TextInputLayout) findViewById(R.id.signup_email_inputLayout);
//        TextInputLayout signup_password_inputLayout = (TextInputLayout) findViewById(R.id.signup_password_inputLayout);
//        TextInputLayout signup_confirmPassword_inputLayout = (TextInputLayout) findViewById(R.id.signup_confirmPassword_inputLayout);

//        signup_username_inputLayout.setError(null);
//        signup_email_inputLayout.setError(null);
//        signup_password_inputLayout.setError(null);
//        signup_confirmPassword_inputLayout.setError(null);

        if (username.isEmpty()) {
            this._view.setErrorUsername(R.string.signup_usernameEmpty);
            submit = false;
        }
        if (email.isEmpty()) {
            this._view.setErrorEmail(R.string.signup_emailEmpty);
            submit = false;
        }
        else if (!myStringEmail.isEmailValid()) {
            this._view.setErrorEmail(R.string.signup_emailNotValid);
            submit = false;
        }
        if (password.isEmpty()) {
            this._view.setErrorPassword(R.string.signup_passwordEmpty);
            submit = false;
        }
        if (confirmPassword.isEmpty()) {
            this._view.setErrorConfirmPassword(R.string.signup_confirmPasswordEmpty);
            submit = false;
        }
        else if (!password.equals(confirmPassword)) {
            this._view.setErrorConfirmPassword(R.string.signup_confirmPasswordNotMatch);
            submit = false;
        }

        if (submit) {
            this._view.setProcessLoadingButton(1);

            HashMap<String, String> userParams = new HashMap<>();

            userParams.put("username", username);
            userParams.put("password", password);
            userParams.put("email", email);

            this._signUpService.createUser(userParams);
        }
    }
}
