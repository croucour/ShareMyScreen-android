package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Context;
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

/**
 * Created by roucou-c on 07/12/15.
 */
public class MainActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    EditText signin_username = null;
    EditText signin_password = null;

    Button signin_submitLogin = null;
    Button signin_signup = null;
    Button signin_settings = null;

    Context applicationContext = null;

    private SignInModel signInModel;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        this.signin_submitLogin = (Button) findViewById(R.id.signin_submitLogin);
        this.signin_signup = (Button) findViewById(R.id.signin_signup);
        this.signin_settings = (Button) findViewById(R.id.signin_settings);

        this.signin_password = (EditText) findViewById(R.id.signin_password);


        this.signin_username = (EditText) findViewById(R.id.signin_username);
        this.signin_username.setText("test");
        this.signin_password.setText("test");

        this.signin_submitLogin.setOnClickListener(this);
        this.signin_signup.setOnClickListener(this);
        this.signin_settings.setOnClickListener(this);

        this.signin_password.setOnEditorActionListener(this);

        this.signInModel = new SignInModel(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signin_submitLogin) {

            try {
                this.onSubmit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (v.getId() == R.id.signin_signup)
        {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.signin_settings)
        {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    protected boolean login() {

        HashMap<String, String> params = new HashMap<>();

        params.put("username", this.signin_username.getText().toString());
        params.put("password", this.signin_password.getText().toString());

        this.signInModel.signIn(params);

        return true;
    }

    protected boolean onSubmit() {
        this.setErrorSubmitSignin();

        if (this.myError.displayError())
        {
            return this.login();
        }
        return false;
    }

    protected boolean setErrorSubmitSignin()
    {
        String StringUsername = this.signin_username.getText().toString();
        String StringPassword = this.signin_password.getText().toString();

        String msg_error = (StringUsername.isEmpty() ? getString(R.string.signin_usernameEmpty) : null);
        msg_error = (msg_error == null && StringPassword.isEmpty() ? getString(R.string.signin_passwordEmpty) : msg_error);

        this.myError.msg_error = msg_error;

        return msg_error == null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.signin_password)
        {
            return this.onSubmit();
        }
        return false;
    }
}