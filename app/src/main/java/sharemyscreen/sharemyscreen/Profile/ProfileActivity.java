package sharemyscreen.sharemyscreen.Profile;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import sharemyscreen.sharemyscreen.DAO.GlobalManager;
import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.TokenManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.Logout.LogoutService;
import sharemyscreen.sharemyscreen.MyActivityDrawer;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileActivity extends MyActivityDrawer implements View.OnClickListener, TextView.OnEditorActionListener, IProfileView {

    private LogoutService _logoutService;
    private ProfilePresenter _profilePresenter;
    private EditText EditUsername;
    private EditText EditPhone;
    private EditText EditFirstname;
    private EditText EditLastname;
    private EditText EditEmail;
    private ActionProcessButton profile_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _layout_stub.setLayoutResource(R.layout.profile);
        _layout_stub.inflate();

        _profilePresenter = new ProfilePresenter(this, _manager, _userEntity);


        this.EditUsername = (EditText) findViewById(R.id.profile_username_editText); // TODO : supprimer le username
        this.EditEmail = (EditText) findViewById(R.id.profile_email_editText);
        this.EditPhone = (EditText) findViewById(R.id.profile_phone_editText);
        this.EditFirstname = (EditText) findViewById(R.id.profile_firstname_editText);
        this.EditLastname = (EditText) findViewById(R.id.profile_lastname_editText);

        this.profile_submit = (ActionProcessButton) findViewById(R.id.profile_submit);
        this.profile_submit.setMode(ActionProcessButton.Mode.ENDLESS);


        this.profile_submit.setOnClickListener(this);

        this.EditEmail.setOnEditorActionListener(this);

        GlobalManager globalManager = new GlobalManager(getApplicationContext());
        TokenManager tokenManager = new TokenManager(getApplicationContext());

        String token_id = globalManager.select("current_token_id");
        if (token_id != null) {
            TokenEntity tokenEntity = tokenManager.selectById(Long.parseLong(token_id));

            ProfileManager profileManager = new ProfileManager(this);
            ProfileEntity profileEntity = profileManager.selectById(tokenEntity.get_profile_id());

            if (profileEntity != null) {
                populateProfile(profileEntity);
            }
        }

        _profilePresenter.getProfile();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        navigation.setCheckedItem(R.id.navigation_profile);
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
