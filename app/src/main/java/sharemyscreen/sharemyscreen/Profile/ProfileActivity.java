package sharemyscreen.sharemyscreen.Profile;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.MyActivityDrawer;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileActivity extends MyActivityDrawer implements View.OnClickListener, TextView.OnEditorActionListener, IProfileView {

    private ProfilePresenter _profilePresenter;
    private EditText _editFirstName;
    private EditText _editLastName;
    private EditText _editEmail;
    private ActionProcessButton profile_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _layout_stub.setLayoutResource(R.layout.profile);
        _layout_stub.inflate();

        _profilePresenter = new ProfilePresenter(this, _manager, _userEntity);


        this._editEmail = (EditText) findViewById(R.id.profile_email_editText);
        this._editFirstName = (EditText) findViewById(R.id.profile_firstname_editText);
        this._editLastName = (EditText) findViewById(R.id.profile_lastname_editText);

        this.profile_submit = (ActionProcessButton) findViewById(R.id.profile_submit);
        this.profile_submit.setMode(ActionProcessButton.Mode.ENDLESS);

        this.profile_submit.setOnClickListener(this);

        this._editEmail.setOnEditorActionListener(this);

        String token_id = _manager._globalManager.select("current_token_id");
        if (token_id != null) {
            TokenEntity tokenEntity = _manager._tokenManager.selectById(Long.parseLong(token_id));

            ProfileEntity profileEntity = _manager._profileManager.selectByPublic_id(tokenEntity.get_profile_public_id());

            if (profileEntity != null) {
                populateProfile(profileEntity);
            }
        }

        _profilePresenter.getProfile();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        navigation.setCheckedItem(R.id.navigation_profile);
    }

    @Override
    public void populateProfile(ProfileEntity profile) {
        this._editEmail.setText(profile.get_email());
        this._editFirstName.setText(profile.get_firstName());
        this._editLastName.setText(profile.get_lastName());
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
        return this._editFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return this._editLastName.getText().toString();
    }

    @Override
    public String getEmail() {
        return this._editEmail.getText().toString();
    }

    @Override
    public void setProcessLoadingButton(int process) {

    }

    public void initializeInputLayout() {
        this.setErrorFirstName(0);
        this.setErrorLastName(0);
        this.setErrorEmail(0);
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
    public ActionProcessButton getActionProcessButton() {
        return (ActionProcessButton) findViewById(R.id.profile_submit);
    }
}
