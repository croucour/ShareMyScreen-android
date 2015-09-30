package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by cleme_000 on 23/09/2015.
 */
public class SignupActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        Button signup_submit = (Button) findViewById(R.id.signup_submit);
        Button signup_cancel = (Button) findViewById(R.id.signup_cancel);
        EditText signup_confirmPassword = (EditText) findViewById(R.id.signup_confirmPassword);

        signup_submit.setOnClickListener(this);
        signup_cancel.setOnClickListener(this);
        signup_confirmPassword.setOnEditorActionListener(this);
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
            return this.onSubmit();
        }
        return false;
    }

    protected boolean setErrorSubmitSignUp()
    {
        EditText EditUsername = (EditText) findViewById(R.id.signup_username);
        String StringUsername = EditUsername.getText().toString();

        EditText EditEmail = (EditText) findViewById(R.id.signup_email);
        String StringEmail = EditEmail.getText().toString();
        MyString MyStringEmail = new MyString(StringEmail);

        EditText EditPassword = (EditText) findViewById(R.id.signup_password);
        String StringPassword = EditPassword.getText().toString();

        EditText EditConfirPassword = (EditText) findViewById(R.id.signup_confirmPassword);
        String StringConfirmPassword = EditConfirPassword.getText().toString();

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

    protected boolean onSubmit ()
    {
        this.setErrorSubmitSignUp();

        if (this.myError.displayError())
        {
            // appel a l'api pour la creation d'un compte
            return true;
        }
        return false;
    }
}
