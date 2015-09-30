package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    EditText signin_username = null;
    EditText signin_password = null;

    Button signin_submitLogin = null;
    Button signin_signup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.signin_submitLogin = (Button) findViewById(R.id.signin_submitLogin);
        this.signin_signup = (Button) findViewById(R.id.signin_signup);

        this.signin_password = (EditText) findViewById(R.id.signin_password);


        this.signin_username = (EditText) findViewById(R.id.signin_username);
        this.signin_username.setText("toto@toto.fr");
        this.signin_password.setText("toto");

        this.signin_submitLogin.setOnClickListener(this);
        this.signin_signup.setOnClickListener(this);
        this.signin_password.setOnEditorActionListener(this);

//        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
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
    }

    protected boolean onSubmit() throws Exception {
        this.setErrorSubmitSignin();

        if (this.myError.displayError())
        {
            MyApi myApi = new MyApi() {
                @Override
                protected void onPostExecute(String str) {
                    Log.i("info", this.currentResquest);
                }
            };

            HashMap<String, String> params = new HashMap<>();

            params.put("username", this.signin_username.getText().toString());
            params.put("password", this.signin_password.getText().toString());

            myApi.setdataParams(params);
            myApi.setCurrentResquest("/users/login");
            myApi.execute();
            return true;
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
            try {
                return this.onSubmit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}