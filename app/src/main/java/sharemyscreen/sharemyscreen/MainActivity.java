package sharemyscreen.sharemyscreen;

import android.app.Activity;
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

public class MainActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    EditText signin_username = null;
    EditText signin_password = null;

    Button signin_submitLogin = null;
    Button signin_signup = null;
    Button signin_settings = null;


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

//        Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
//        startActivity(intent);
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
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.signin_settings)
        {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    protected boolean login() {
        MyApi myApi = new MyApi(this.getApplicationContext()) {
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

                Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                startActivity(intent);
            }
        };

        HashMap<String, String> params = new HashMap<>();

        params.put("username", this.signin_username.getText().toString());
        params.put("password", this.signin_password.getText().toString());

        params.put("password", this.signin_password.getText().toString());
        params.put("grant_type", "password");
        params.put("scope", "offline_access");

        myApi.setdataParams(params);
        myApi.setCurrentResquest("/user/login");
        myApi.encodeUsernamePassword64("test", "test");

//        myApi.encodeUsernamePassword64("5gIw88WXLKFQd4AJ", "5o7fAYf5Amqa2IYvuAMz0ZT4a4NlNgEP");
        myApi.execute();
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