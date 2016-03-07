package sharemyscreen.sharemyscreen.SignUp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 23/09/2015.
 */
public class SignUpActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener, ISignUpView {

    private Manager _manager;
    private SignUpPresenter _signUpPresenter;

    EditText EditUsername = null;
    EditText EditEmail = null;
    EditText EditPassword = null;
    EditText EditConfirPassword = null;

    ActionProcessButton signup_submit = null;
    Button signup_cancel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        this._manager = new Manager(getApplicationContext());

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

        this._signUpPresenter = new SignUpPresenter(this, _manager);
//        this.signInPresenter = new SignInPresenter(this);

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
                this._signUpPresenter.onSignUpClicked();
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
            this._signUpPresenter.onSignUpClicked();
        }
        return false;
    }

    @Override
    public String getEmail() {
        return this.EditEmail.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return this.EditConfirPassword.getText().toString();
    }

    @Override
    public void setErrorEmail(int resId) {
        TextInputLayout signup_email_inputLayout = (TextInputLayout) findViewById(R.id.signup_email_inputLayout);
        signup_email_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorConfirmPassword(int resId) {
        TextInputLayout signup_confirmPassword_inputLayout = (TextInputLayout) findViewById(R.id.signup_confirmPassword_inputLayout);
        signup_confirmPassword_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public String getUsername() {
        return this.EditUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return this.EditPassword.getText().toString();
    }

    @Override
    public void setErrorUsername(int resId) {
        TextInputLayout signup_username_inputLayout = (TextInputLayout) findViewById(R.id.signup_username_inputLayout);
        signup_username_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorPassword(int resId) {
        TextInputLayout signup_password_inputLayout = (TextInputLayout) findViewById(R.id.signup_password_inputLayout);
        signup_password_inputLayout.setError((resId == 0 ? null : getString(resId)));

    }

    @Override
    public void initializeInputLayout() {
        this.setErrorUsername(0);
        this.setErrorEmail(0);
        this.setErrorPassword(0);
        this.setErrorConfirmPassword(0);
    }

    @Override
    public void setProcessLoadingButton(int process) {
        ActionProcessButton actionProcessButton = (ActionProcessButton) findViewById(R.id.signup_submit);
        actionProcessButton.setProgress(process);
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return (CoordinatorLayout) findViewById(R.id.display_snackbar);
    }

    @Override
    public ActionProcessButton getActionProcessButton() {
        return (ActionProcessButton) findViewById(R.id.signup_submit);
    }

    @Override
    public void startRoomActivity() {
        this.finish();
//        RoomActivity roomActivity = new RoomActivity(_manager);
//        Intent intent = new Intent(SignUpActivity.this, roomActivity.getClass());
//        startActivity(intent);
    }
}