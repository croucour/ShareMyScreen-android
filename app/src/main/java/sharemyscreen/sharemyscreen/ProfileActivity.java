package sharemyscreen.sharemyscreen;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.Model.LogoutModel;
import sharemyscreen.sharemyscreen.Model.ProfileModel;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private LogoutModel _logoutModel;
    private ProfileModel _profileModel;
    private EditText EditUsername;
    private EditText EditPhone;
    private EditText EditFirstname;
    private EditText EditLastname;
    private EditText EditEmail;
    private Button profile_cancel;
    private ActionProcessButton profile_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _profileModel = new ProfileModel(this);
        _logoutModel = new LogoutModel(this);

        setContentView(R.layout.profile);

        this.EditUsername = (EditText) findViewById(R.id.profile_username_editText);
        this.EditEmail = (EditText) findViewById(R.id.profile_email_editText);
        this.EditPhone = (EditText) findViewById(R.id.profile_phone_editText);
        this.EditFirstname = (EditText) findViewById(R.id.profile_firstname_editText);
        this.EditLastname = (EditText) findViewById(R.id.profile_lastname_editText);

        this.profile_submit = (ActionProcessButton) findViewById(R.id.profile_submit);
        this.profile_submit.setMode(ActionProcessButton.Mode.ENDLESS);

        this.profile_cancel = (Button) findViewById(R.id.profile_cancel);


        this.profile_submit.setOnClickListener(this);
        this.profile_cancel.setOnClickListener(this);

        this.EditEmail.setOnEditorActionListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        _profileModel.getProfil(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.profile_submit:
                this.onSubmit();
                break;
            case R.id.profile_cancel:
                this.finish();
                break;

        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.signup_confirmPassword_editText)
        {
            return this.onSubmit();
        }
        return false;
    }

    private boolean onSubmit() {
        if (setErrorBeforeSubmit()) {
            submit();
        }
        return true;
    }

    private void submit() {
        this.profile_submit.setProgress(1);

        HashMap<String, String> userParams = new HashMap<>();

        userParams.put("firstName", this.EditFirstname.getText().toString());
        userParams.put("lastName", this.EditLastname.getText().toString());
        userParams.put("phone", this.EditPhone.getText().toString());
        userParams.put("username", this.EditUsername.getText().toString());
        userParams.put("email", this.EditEmail.getText().toString());

        this._profileModel.saveProfile(userParams, this);
    }

    private boolean setErrorBeforeSubmit() {
        boolean submit = true;
        String StringUsername = this.EditUsername.getText().toString();
        String StringEmail = this.EditEmail.getText().toString();

        TextInputLayout profile_username_inputLayout = (TextInputLayout) findViewById(R.id.profile_username_inputLayout);
        TextInputLayout profile_email_inputLayout = (TextInputLayout) findViewById(R.id.profile_email_inputLayout);

        profile_username_inputLayout.setError(null);
        profile_email_inputLayout.setError(null);

        if (StringUsername.isEmpty()) {
            profile_username_inputLayout.setError(getString(R.string.profile_usernameEmpty));
            submit = false;
        }
        if (StringEmail.isEmpty()) {
            profile_email_inputLayout.setError(getString(R.string.profile_emailEmpty));
            submit = false;
        }
        return submit;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.modify_profil:
                _profileModel.getProfil(this);
                break;
            case R.id.disconnect:
                this._logoutModel.logout(this);
                break;
        }

        return false;
    }

}
