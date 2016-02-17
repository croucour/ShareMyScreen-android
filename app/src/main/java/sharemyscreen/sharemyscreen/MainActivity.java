package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.HashMap;
import java.util.List;

import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.Room;
import sharemyscreen.sharemyscreen.Model.SignInModel;

/**
 * Created by roucou-c on 07/12/15.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private MyError myError = new MyError(this);

    EditText signin_username = null;
    EditText signin_password = null;

    ActionProcessButton signin_submitLogin = null;
    Button signin_signup = null;
    Button signin_settings = null;

    Context applicationContext = null;

    private SignInModel signInModel;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RoomsManager roomsManager = new RoomsManager(this);

        List<Room> listRooms = roomsManager.selectAll(null);

        if (listRooms == null || listRooms.size() == 0) {
            roomsManager.addRoom("Room 1");
            roomsManager.addRoom("Room 2");
            roomsManager.addRoom("Room 3");
            roomsManager.addRoom("Room 4");
        }

        this.signInModel = new SignInModel(this);

        setContentView(R.layout.signin);

        this.signInModel.isLogin(this);


        this.signin_submitLogin = (ActionProcessButton) findViewById(R.id.signin_submitLogin);
        this.signin_submitLogin.setMode(ActionProcessButton.Mode.ENDLESS);

        this.signin_signup = (Button) findViewById(R.id.signin_signup);
        this.signin_settings = (Button) findViewById(R.id.signin_settings);

        this.signin_password = (EditText) findViewById(R.id.signin_password_editText);


        this.signin_username = (EditText) findViewById(R.id.signin_username_editText);
        this.signin_username.setText("test");
        this.signin_password.setText("test");

        this.signin_submitLogin.setOnClickListener(this);
        this.signin_signup.setOnClickListener(this);
        this.signin_settings.setOnClickListener(this);

        this.signin_password.setOnEditorActionListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signin_submitLogin) {

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            this.signin_submitLogin.setProgress(1);
            this.onSubmit();
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

        this.signInModel.signIn(params, this);

        return true;
    }

    protected boolean onSubmit() {
        if (setErrorBeforeSubmit()) {
            submit();
        }
        return true;
    }

    private void submit() {
        login();
    }

    private boolean setErrorBeforeSubmit() {
        boolean submit = true;

        String StringUsername = this.signin_username.getText().toString();
        String StringPassword = this.signin_password.getText().toString();

        TextInputLayout signin_username_inputLayout = (TextInputLayout) findViewById(R.id.signin_username_inputLayout);
        TextInputLayout signin_password_inputLayout = (TextInputLayout) findViewById(R.id.signin_password_inputLayout);

        signin_username_inputLayout.setError(null);
        signin_password_inputLayout.setError(null);

        if (StringUsername.isEmpty()) {
            signin_username_inputLayout.setError(getString(R.string.signin_usernameEmpty));
            submit = false;
        }
        if (StringPassword.isEmpty()) {

            signin_password_inputLayout.setError(getString(R.string.signin_passwordEmpty));
            submit = false;
        }

        return submit;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.signin_password_editText)
        {
            return this.onSubmit();
        }
        return false;
    }
}

