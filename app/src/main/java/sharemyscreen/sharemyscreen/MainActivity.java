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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button signin_submitLogin = (Button) findViewById(R.id.signin_submitLogin);
        Button signin_signup = (Button) findViewById(R.id.signin_signup);
        EditText signin_password = (EditText) findViewById(R.id.signin_password);


        EditText signin_email = (EditText) findViewById(R.id.signin_email);
        signin_email.setText("toto@toto.fr");
        signin_password.setText("toto");

        signin_submitLogin.setOnClickListener(this);
        signin_signup.setOnClickListener(this);
        signin_password.setOnEditorActionListener(this);

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

            params.put("username", "cleemec66nt");
            params.put("password", "tata");
            params.put("email", "665656111clementcaca@tutu.fr");
            params.put("phone", "0625212121");


            myApi.setdataParams(params);
            myApi.setCurrentResquest("/users");
            myApi.execute();
            return true;
        }
        return false;
    }

    protected boolean setErrorSubmitSignin()
    {
        EditText email = (EditText) findViewById(R.id.signin_email);

        MyString MyStringEmail = new MyString(email.getText().toString());

        String msg_error = (email.length() == 0 ? getString(R.string.signin_emailEmpty) : null);
        msg_error = (msg_error == null && !MyStringEmail.isEmailValid() ? getString(R.string.signin_emailNotValid) : msg_error);

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