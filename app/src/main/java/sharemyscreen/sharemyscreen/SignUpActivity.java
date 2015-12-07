package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    Button signup_submit = null;
    Button signup_cancel = null;

    private SettingsManager settingsManager = new SettingsManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        this.EditUsername = (EditText) findViewById(R.id.signup_username);
        this.EditEmail = (EditText) findViewById(R.id.signup_email);
        this.EditPassword = (EditText) findViewById(R.id.signup_password);
        this.EditConfirPassword = (EditText) findViewById(R.id.signup_confirmPassword);

        this.signup_submit = (Button) findViewById(R.id.signup_submit);
        this.signup_cancel = (Button) findViewById(R.id.signup_cancel);

        this.signup_submit.setOnClickListener(this);
        this.signup_cancel.setOnClickListener(this);
        this.EditConfirPassword.setOnEditorActionListener(this);

        this.signUpModel = new SignUpModel(this);
        this.signInModel = new SignInModel(this);


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
        if (v.getId() == R.id.signup_confirmPassword)
        {
            try {
                return this.onSubmit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    protected boolean setErrorSubmitSignUp()
    {
        String StringUsername = this.EditUsername.getText().toString();
        String StringEmail = this.EditEmail.getText().toString();
        MyString MyStringEmail = new MyString(StringEmail);
        String StringPassword = this.EditPassword.getText().toString();
        String StringConfirmPassword = this.EditConfirPassword.getText().toString();

        // test sur la validité des champs
        String msg_error = (StringUsername.isEmpty() ? getString(R.string.signup_usernameEmpty) : null);
        msg_error = (msg_error == null && StringEmail.isEmpty() ? getString(R.string.signup_emailEmpty) : msg_error);
        msg_error = (msg_error == null && !MyStringEmail.isEmailValid() ? getString(R.string.signup_emailNotValid) : msg_error);
        msg_error = (msg_error == null && StringPassword.isEmpty() ? getString(R.string.signup_passwordEmpty) : msg_error);
        msg_error = (msg_error == null && StringConfirmPassword.isEmpty() ? getString(R.string.signup_confirmPasswordEmpty) : msg_error);
        msg_error = (msg_error == null && !StringPassword.equals(StringConfirmPassword) ? getString(R.string.signup_confirmPasswordNotMatch) : msg_error);

        this.myError.msg_error = msg_error;

        return msg_error == null;
    }

    protected boolean onSubmit() {
        this.setErrorSubmitSignUp();

        if (this.myError.displayError())
        {

            HashMap<String, String> params = new HashMap<>();

            //            params.put("username", this.EditUsername.getText().toString());
            //            params.put("password", this.EditPassword.getText().toString());
            //            params.put("email", this.EditEmail.getText().toString());

            params.put("username", "tata");
            params.put("password", "toto");
            params.put("email", "toto@toto.fr");

            this.signUpModel.createUser(params);

            params = new HashMap<>();

            params.put("username", "tata");
            params.put("password", "toto");

            this.signInModel.signIn(params);

            this.finish();

            Intent intent = new Intent(SignUpActivity.this, LogoutActivity.class);
            startActivity(intent);
        }
        return false;
    }
}