package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Model.SignInModel;
import sharemyscreen.sharemyscreen.Model.SignUpModel;

/**
 * Created by cleme_000 on 23/09/2015.
 */
public class SignUpActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    private SignUpModel signUpModel;
    private SignInModel signInModel;


    EditText EditUsername = null;
    EditText EditEmail = null;
    EditText EditPassword = null;
    EditText EditConfirPassword = null;

    ActionProcessButton signup_submit = null;
    Button signup_cancel = null;

    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.settingsManager = new SettingsManager(this);
        setContentView(R.layout.signup);

        this.EditUsername = (EditText) findViewById(R.id.signup_username_editText);
        this.EditEmail = (EditText) findViewById(R.id.signup_email_editText);
        this.EditPassword = (EditText) findViewById(R.id.signup_password_editText);
        this.EditConfirPassword = (EditText) findViewById(R.id.signup_confirmPassword_editText);

        this.signup_submit = (ActionProcessButton) findViewById(R.id.signup_submit);
        this.signup_submit.setMode(ActionProcessButton.Mode.ENDLESS);

        this.signup_cancel = (Button) findViewById(R.id.signup_cancel);

        this.signup_submit.setOnClickListener(this);
        this.signup_cancel.setOnClickListener(this);
        this.EditConfirPassword.setOnEditorActionListener(this);

        this.signUpModel = new SignUpModel(this);
        this.signInModel = new SignInModel(this);

//        this.EditUsername.setText("test2");
//        this.EditEmail.setText("test2@test.fr");
//        this.EditPassword.setText("test2");
//        this.EditConfirPassword.setText("test2");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signup_submit :
                this.onSubmit();
                break;
            case R.id.signup_cancel :
                this.finish();

                break;
            default:
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.signup_confirmPassword_editText)
        {
            return this.onSubmit();
        }
        return false;
    }

    protected boolean onSubmit() {
        if (setErrorBeforeSubmit()) {
            submit();
        }
        return true;
    }

    private void submit() {
        this.signup_submit.setProgress(1);
        signup();
    }

    private void signup() {
        HashMap<String, String> params = new HashMap<>();

        params.put("username", this.EditUsername.getText().toString());
        params.put("password", this.EditPassword.getText().toString());
        params.put("email", this.EditEmail.getText().toString());

        this.signUpModel.createUser(params, this);
    }

    private boolean setErrorBeforeSubmit() {
        boolean submit = true;
        String StringUsername = this.EditUsername.getText().toString();
        String StringEmail = this.EditEmail.getText().toString();
        MyString MyStringEmail = new MyString(StringEmail);
        String StringPassword = this.EditPassword.getText().toString();
        String StringConfirmPassword = this.EditConfirPassword.getText().toString();

        TextInputLayout signup_username_inputLayout = (TextInputLayout) findViewById(R.id.signup_username_inputLayout);
        TextInputLayout signup_email_inputLayout = (TextInputLayout) findViewById(R.id.signup_email_inputLayout);
        TextInputLayout signup_password_inputLayout = (TextInputLayout) findViewById(R.id.signup_password_inputLayout);
        TextInputLayout signup_confirmPassword_inputLayout = (TextInputLayout) findViewById(R.id.signup_confirmPassword_inputLayout);

        signup_username_inputLayout.setError(null);
        signup_email_inputLayout.setError(null);
        signup_password_inputLayout.setError(null);
        signup_confirmPassword_inputLayout.setError(null);

        if (StringUsername.isEmpty()) {
            signup_username_inputLayout.setError(getString(R.string.signup_usernameEmpty));
            submit = false;
        }
        if (StringEmail.isEmpty()) {
            signup_email_inputLayout.setError(getString(R.string.signup_emailEmpty));
            submit = false;
        }
        else if (!MyStringEmail.isEmailValid()) {
            signup_email_inputLayout.setError(getString(R.string.signup_emailNotValid));
            submit = false;
        }
        if (StringPassword.isEmpty()) {
            signup_password_inputLayout.setError(getString(R.string.signup_passwordEmpty));
            submit = false;
        }
        if (StringConfirmPassword.isEmpty()) {
            signup_confirmPassword_inputLayout.setError(getString(R.string.signup_confirmPasswordEmpty));
            submit = false;
        }
        else if (!StringPassword.equals(StringConfirmPassword)) {
            signup_confirmPassword_inputLayout.setError(getString(R.string.signup_confirmPasswordNotMatch));
            submit = false;
        }

        return submit;
    }
}