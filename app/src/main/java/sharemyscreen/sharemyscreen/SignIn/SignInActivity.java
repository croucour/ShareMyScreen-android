package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.Room.RoomActivity;
import sharemyscreen.sharemyscreen.Services.MyService;
import sharemyscreen.sharemyscreen.SignUp.SignUpActivity;

/**
 * Created by roucou-c on 07/12/15.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, ISignInView {

    EditText signin_username = null;
    EditText signin_password = null;

    ActionProcessButton signin_submitLogin = null;
    Button signin_signup = null;
    Button signin_settings = null;

    private SignInPresenter _signInPresenter;
    private Manager _manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _manager = new Manager(getApplicationContext());

        this._signInPresenter = new SignInPresenter(this, _manager);

        setContentView(R.layout.signin);

        this._signInPresenter.isLoginWithRefreshToken();

        this.signin_submitLogin = (ActionProcessButton) findViewById(R.id.signin_submitLogin);
        this.signin_submitLogin.setMode(ActionProcessButton.Mode.ENDLESS);

        this.signin_signup = (Button) findViewById(R.id.signin_signup);

        this.signin_password = (EditText) findViewById(R.id.signin_password_editText);


        this.signin_username = (EditText) findViewById(R.id.signin_username_editText);
        this.signin_username.setText("test");
        this.signin_password.setText("test");

        this.signin_submitLogin.setOnClickListener(this);
        this.signin_signup.setOnClickListener(this);

        this.signin_password.setOnEditorActionListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signin_submitLogin) {

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            this._signInPresenter.onLoginClicked();
        }
        else if (v.getId() == R.id.signin_signup)
        {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.signin_password_editText)
        {
            this._signInPresenter.onLoginClicked();
        }
        return false;
    }

    @Override
    public String getUsername() {
        return this.signin_username.getText().toString();
    }

    @Override
    public String getPassword() {
        return this.signin_password.getText().toString();
    }

    @Override
    public void setErrorUsername(int resId) {
        TextInputLayout signin_username_inputLayout = (TextInputLayout) findViewById(R.id.signin_username_inputLayout);
        signin_username_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorPassword(int resId) {
        TextInputLayout signin_password_inputLayout = (TextInputLayout) findViewById(R.id.signin_password_inputLayout);
        signin_password_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setProcessLoadingButton(int process) {
        ActionProcessButton actionProcessButton = (ActionProcessButton) findViewById(R.id.signin_submitLogin);
        actionProcessButton.setProgress(process);
    }

    @Override
    public void initializeInputLayout() {
        this.setErrorUsername(0);
        this.setErrorPassword(0);
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return (CoordinatorLayout) findViewById(R.id.display_snackbar);
    }

    @Override
    public ActionProcessButton getActionProcessButton() {
        return (ActionProcessButton) findViewById(R.id.signin_submitLogin);
    }

    @Override
    public void startRoomActivity() {
        this.finish();
        Intent intent = new Intent(SignInActivity.this, RoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}