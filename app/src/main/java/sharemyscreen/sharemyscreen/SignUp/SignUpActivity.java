package sharemyscreen.sharemyscreen.SignUp;

import android.app.Activity;
import android.content.Intent;
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
import sharemyscreen.sharemyscreen.Organization.OrganizationActivity;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by cleme_000 on 23/09/2015.
 */
public class SignUpActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener, ISignUpView {

    private Manager _manager;
    private SignUpPresenter _signUpPresenter;

    private EditText EditEmail = null;
    private EditText EditPassword = null;
    private EditText EditConfirPassword = null;
    private EditText EditFirstName;
    private EditText EditLastName;

    ActionProcessButton signup_submit = null;
    Button signup_cancel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        this._manager = new Manager(getApplicationContext());

        this.EditFirstName = (EditText) findViewById(R.id.signup_firstname__editText);
        this.EditLastName = (EditText) findViewById(R.id.signup_lastname_editText);
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
    public String getLastName() {
        return this.EditLastName.getText().toString();
    }

    @Override
    public String getFirstName() {
        return this.EditFirstName.getText().toString();
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
    public void setErrorFirstName(int resId) {
        TextInputLayout signup_firstName_inputLayout = (TextInputLayout) findViewById(R.id.signup_firstname_inputLayout);
        signup_firstName_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorLastName(int resId) {
        TextInputLayout signup_lastName_inputLayout = (TextInputLayout) findViewById(R.id.signup_lastname_inputLayout);
        signup_lastName_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public String getPassword() {
        return this.EditPassword.getText().toString();
    }

    @Override
    public void setErrorPassword(int resId) {
        TextInputLayout signup_password_inputLayout = (TextInputLayout) findViewById(R.id.signup_password_inputLayout);
        signup_password_inputLayout.setError((resId == 0 ? null : getString(resId)));

    }

    @Override
    public void initializeInputLayout() {
        this.setErrorFirstName(0);
        this.setErrorLastName(0);
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

    @Override
    public void startOrganizationActivity() {
        this.finish();
        Intent intent = new Intent(SignUpActivity.this, OrganizationActivity.class);
        startActivity(intent);
    }
}