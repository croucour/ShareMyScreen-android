package sharemyscreen.sharemyscreen.SignUp;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.MyString;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public class SignUpPresenter {

    private ISignUpView _view;
    protected SignUpService _signUpService;

    public SignUpPresenter(ISignUpView view, Manager manager) {
        this._view = view;
        this._signUpService = new SignUpService(view, manager);
    }

    public void onSignUpClicked() {
        boolean submit = true;
        String firstName = this._view.getFirstName();
        String lastName = this._view.getLastName();
        String email = this._view.getEmail();
        MyString myStringEmail = new MyString(email);
        String password = this._view.getPassword();
        String confirmPassword = this._view.getConfirmPassword();

        if (firstName.isEmpty()) {
            this._view.setErrorFirstName(R.string.signup_firstnameEmpty);
            submit = false;
        }
        if (lastName.isEmpty()) {
            this._view.setErrorLastName(R.string.signup_lastnameEmpty);
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

            userParams.put("first_name", firstName);
            userParams.put("last_name", lastName);
            userParams.put("password", password);
            userParams.put("email", email);
            userParams.put("username", email); // TODO à supprimer
            userParams.put("phone", "0685744111"); // TODO à supprimer

            this._signUpService.createUser(userParams);
        }
    }
}
