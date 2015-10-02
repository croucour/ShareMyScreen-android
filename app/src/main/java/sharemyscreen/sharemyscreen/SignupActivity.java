package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by cleme_000 on 23/09/2015.
 */
public class SignupActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    EditText EditUsername = null;
    EditText EditEmail = null;
    EditText EditPassword = null;
    EditText EditConfirPassword = null;

    Button signup_submit = null;
    Button signup_cancel = null;

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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signup_submit :
                try {
                    this.onSubmit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    protected boolean onSubmit() throws Exception {
        this.setErrorSubmitSignUp();

        if (this.myError.displayError())
        {
            MyApi myApi = new MyApi(this.getApplicationContext()) {
                @Override
                protected void onPostExecute(String str) {

                }
            };

            HashMap<String, String> params = new HashMap<>();

            params.put("username", this.EditUsername.getText().toString());
            params.put("password", this.EditPassword.getText().toString());
            params.put("email", this.EditEmail.getText().toString());

//            params.put("username", "tata");
//            params.put("password", "toto");
//            params.put("email", "toto@toto.fr");
            myApi.setdataParams(params);
            myApi.setCurrentResquest("/users");
            myApi.execute();

            SharedPreferences tokenFile = this.getApplicationContext().getSharedPreferences(MyApi.TOKENFILE, android.content.Context.MODE_PRIVATE);

            SharedPreferences.Editor edit = tokenFile.edit();
            edit.clear();
            edit.apply();

//             signinActivity = new MainActivity();
            myApi = new MyApi(this.getApplicationContext()) {
                @Override
                protected void onPostExecute(String str) {

                    try {
                        this.access_token = this.resultJSON.getString("access_token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences tokenFile = this.contextApplication.getSharedPreferences(TOKENFILE, android.content.Context.MODE_PRIVATE);

                    SharedPreferences.Editor edit = tokenFile.edit();
                    edit.clear();

                    if (this.access_token != null) {
                        edit.putString("access_token", this.access_token);
                    }

                    edit.apply();
                    Log.i("info", "access_token :" + this.access_token);
                    Log.i("info", "vous etes connecté");

                }
            };

            params = new HashMap<>();

            params.put("username", this.EditUsername.getText().toString());
            params.put("password", this.EditPassword.getText().toString());
            params.put("grant_type", "password");
            params.put("scope", "offline_access");

            myApi.setdataParams(params);
            myApi.setCurrentResquest("/user/login");
            myApi.encodeUsernamePassword64("5gIw88WXLKFQd4AJ", "5o7fAYf5Amqa2IYvuAMz0ZT4a4NlNgEP");
            myApi.execute();
            this.finish();

        Intent intent = new Intent(SignupActivity.this, LogoutActivity.class);
        startActivity(intent);
       }
        return false;
    }
}
