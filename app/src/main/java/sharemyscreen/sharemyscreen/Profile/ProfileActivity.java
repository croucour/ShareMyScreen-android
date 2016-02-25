package sharemyscreen.sharemyscreen.Profile;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Logout.LogoutService;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, IProfileView {

    private LogoutService _logoutService;
    private ProfilePresenter _profilePresenter;
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

        _profilePresenter = new ProfilePresenter(this, getApplicationContext());
//        _logoutService = new LogoutService(this);

        setContentView(R.layout.profile);

        this.EditUsername = (EditText) findViewById(R.id.profile_username_editText); // TODO : supprimer le username
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


        ProfileManager profileManager = new ProfileManager(this);
        ProfileEntity profile = profileManager.selectProfileLogged();

        if (profile != null) {
            populateProfile(profile);
        }

        _profilePresenter.getProfile();
    }

    @Override
    public void populateProfile(ProfileEntity profile) {
        this.EditUsername.setText(profile.get_username());
        this.EditEmail.setText(profile.get_email());
        this.EditFirstname.setText(profile.get_firstName());
        this.EditLastname.setText(profile.get_lastName());
        this.EditPhone.setText(profile.get_phone());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.profile_submit:
                this._profilePresenter.onSaveClicked();
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
            this._profilePresenter.onSaveClicked();
        }
        return false;
    }


//    private void submit() {
//        this.profile_submit.setProgress(1);
//
//        HashMap<String, String> userParams = new HashMap<>();
//
//        userParams.put("firstName", this.EditFirstname.getText().toString());
//        userParams.put("lastName", this.EditLastname.getText().toString());
//        userParams.put("phone", this.EditPhone.getText().toString());
//        userParams.put("username", this.EditUsername.getText().toString());
//        userParams.put("email", this.EditEmail.getText().toString());
//
////        this._profileModel.saveProfile(userParams, this);
//    }

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
//                _profileModel.getProfil();
                break;
            case R.id.disconnect:
//                this._logoutService.logout(this);
                break;
        }

        return false;
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return (CoordinatorLayout) findViewById(R.id.display_snackbar);
    }

    @Override
    public String getFirstName() {
        return this.EditFirstname.getText().toString();
    }

    @Override
    public String getLastName() {
        return this.EditLastname.getText().toString();
    }

    @Override
    public String getEmail() {
        return this.EditEmail.getText().toString();
    }

    @Override
    public String getPhone() {
        return this.EditPhone.getText().toString();
    }

    @Override
    public void setProcessLoadingButton(int process) {

    }

    public void initializeInputLayout() {
        this.setErrorFirstName(0);
        this.setErrorLastName(0);
        this.setErrorEmail(0);
        this.setErrorPhone(0);
    }


    @Override
    public void setErrorFirstName(int resId) {
        TextInputLayout profile_firstName_inputLayout = (TextInputLayout) findViewById(R.id.profile_firstname_inputLayout);
        profile_firstName_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorLastName(int resId) {
        TextInputLayout profile_lastname_inputLayout = (TextInputLayout) findViewById(R.id.profile_lastname_inputLayout);
        profile_lastname_inputLayout.setError((resId == 0 ? null : getString(resId)));

    }

    @Override
    public void setErrorEmail(int resId) {
        TextInputLayout profile_email_inputLayout = (TextInputLayout) findViewById(R.id.profile_email_inputLayout);
        profile_email_inputLayout.setError((resId == 0 ? null : getString(resId)));

    }

    @Override
    public void setErrorPhone(int resId) {
        TextInputLayout profile_phone_inputLayout = (TextInputLayout) findViewById(R.id.profile_phone_inputLayout);
        profile_phone_inputLayout.setError((resId == 0 ? null : getString(resId)));

    }

    @Override
    public ActionProcessButton getActionProcessButton() {
        return (ActionProcessButton) findViewById(R.id.profile_submit);
    }
}
