package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONException;

import java.util.Date;
import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.RoomActivity;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInModel extends MyModel{

    public SignInModel(Context pContext) {
        super(pContext);
    }

    public void signIn(HashMap<String, String> userParams, final Activity activity) {
        this._myApi = new MyApi(_pContext) {
            @Override
            protected void onPostExecute(String str) {
                String access_token;
                String refresh_token;

                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);
                if (actionProcessButton == null) {
                    actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signup_submit);
                }

                if (!this.isErrorRequest()) {
                    if (this.resultJSON != null) {
                        try {
                            access_token = this.resultJSON.getString("access_token");
                            refresh_token = this.resultJSON.getString("refresh_token");

                            EditText editTextUsername = (EditText) activity.findViewById(R.id.signin_username_editText);
                            EditText editTextPassword = (EditText) activity.findViewById(R.id.signin_password_editText);

                            String username = editTextUsername.getText().toString();
                            String password = editTextPassword.getText().toString();

                            ProfileEntity profileEntity = _profileManager.modifyProfil(username, password); // sauvegarde du password en fonction de username
                            profileEntity.set_access_token(access_token);
                            profileEntity.set_refresh_token(refresh_token);
                            profileEntity.set_expireAccess_token(this.getNewExpireToken());
                            _profileManager.modifyProfil(profileEntity);
                            _profileManager.get_profileDAO().setProfileIsLogged(profileEntity);

                            SettingsManager settingsManager = new SettingsManager(_pContext);
                            settingsManager.addSettings("lastSignInProfileId", String.valueOf(profileEntity.get_id()));

                            ProfileModel profileModel = new ProfileModel(_pContext);
                            profileModel.getProfil(null);
                            actionProcessButton.setProgress(100);
                            login(activity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
                }
            }
        };

        userParams.put("grant_type", "password");
        userParams.put("scope", "offline_access");


        this._myApi.setdataParams(userParams);
        this._myApi.setCurrentResquest("/oauth2/token/", "POST");
        this._myApi.execute();
    }

    private void login(Activity activity){
        activity.finish();

        Intent intent = new Intent(activity, RoomActivity.class);
        activity.startActivity(intent);
    }

    public void isLogin(final Activity activity) {
        ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);

        SettingsManager settingsManager = new SettingsManager(_pContext);

        String lastSignInProfileId = settingsManager.select("lastSignInProfileId");
        ProfileEntity profileEntity = null;

        if (lastSignInProfileId != null) {
            profileEntity = _profileManager.get_profileDAO().selectById(Long.parseLong(lastSignInProfileId));
        }

        String refresh_token = null;
        String expireAccess_token = null;

        if (profileEntity != null) {
            refresh_token = profileEntity.get_refresh_token();
            expireAccess_token = profileEntity.get_expireAccess_token();
        }

        if (expireAccess_token != null && refresh_token != null) {

            Date date = new Date();
            if (date.getTime() > Long.parseLong(expireAccess_token)) {
                actionProcessButton.setProgress(1);

                HashMap<String, String> params = new HashMap<>();

                params.put("grant_type", "refresh_token");
                params.put("refresh_token", refresh_token);

                this.refreshToken(params, activity);
            }
            else {
                login(activity);
            }
        }
        else {
            Log.d("info login", "pas refresh token ou de access token");
            actionProcessButton.setProgress(0);
        }
    }

    public void refreshToken(HashMap<String, String> userParams, final Activity activity) {

        this._myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {

                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);

                String access_token;

                if (!this.isErrorRequest()) {
                    if (this.resultJSON != null) {
                        try {
                            access_token = this.resultJSON.getString("access_token");
                            _profileLogged.set_access_token(access_token);
                            _profileManager.modifyProfil(_profileLogged);

                            _profileManager.get_profileDAO().setProfileIsLogged(_profileLogged);

                            actionProcessButton.setProgress(100);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    login(activity);
                }
                else {
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
                }

            }
        };

        this._myApi.setdataParams(userParams);
        this._myApi.setCurrentResquest("/oauth2/token/", "POST");
        this._myApi.execute();
    }
}
