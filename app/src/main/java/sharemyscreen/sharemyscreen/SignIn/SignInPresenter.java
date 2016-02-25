package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInPresenter{

    private ISignInView _view;
    protected SignInService _service;

    public SignInPresenter(ISignInView view, Context pContext) {
        this._view = view;
        this._service = new SignInService(view, pContext);
    }


    public void onLoginClicked() {
        boolean error = false;
        String username = _view.getUsername();
        if (username.isEmpty()) {
            _view.showUsernameError(R.string.signin_usernameEmpty);
            error = true;
        }
        else {
            _view.disableUsernameError();
        }
        String password = _view.getPassword();
        if (password.isEmpty()) {
            _view.showPasswordError(R.string.signin_passwordEmpty);
            error = true;
        }
        else {
            _view.disablePasswordError();
        }

        if (!error) {
            _view.setProcessLoadingButton(1);
            HashMap<String, String> params = new HashMap<>();

            params.put("username", username);
            params.put("password", password);
            params.put("grant_type", "password");
            params.put("scope", "offline_access");

            this._service.signIn(params);
        }
    }

//    public void isLogin(final Activity activity) {
//        ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);
//
//        SettingsManager settingsManager = new SettingsManager(_pContext);
//
//        String lastSignInProfileId = settingsManager.select("lastSignInProfileId");
//        ProfileEntity profileEntity = null;
//
//        if (lastSignInProfileId != null) {
//            profileEntity = _profileManager.get_profileDAO().selectById(Long.parseLong(lastSignInProfileId));
//        }
//
//        String refresh_token = null;
//        String expireAccess_token = null;
//
//        if (profileEntity != null) {
//            refresh_token = profileEntity.get_refresh_token();
//            expireAccess_token = profileEntity.get_expireAccess_token();
//        }
//
//        if (expireAccess_token != null && refresh_token != null) {
//
//            Date date = new Date();
//            if (date.getTime() > Long.parseLong(expireAccess_token)) {
//                actionProcessButton.setProgress(1);
//
//                HashMap<String, String> params = new HashMap<>();
//
//                params.put("grant_type", "refresh_token");
//                params.put("refresh_token", refresh_token);
//
//                this.refreshToken(params, activity);
//            }
//            else {
//                login(activity);
//            }
//        }
//        else {
//            Log.d("info login", "pas refresh token ou de access token");
//            actionProcessButton.setProgress(0);
//        }
//    }
//
//    public void refreshToken(HashMap<String, String> userParams, final Activity activity) {
//
//        this._myApi = new MyApi(_profileLogged, _pContext) {
//            @Override
//            protected void onPostExecute(String str) {
//
//                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);
//
//                String access_token;
//
//                if (!this.isErrorRequest()) {
//                    if (this.resultJSON != null) {
//                        try {
//                            access_token = this.resultJSON.getString("access_token");
//                            _profileLogged.set_access_token(access_token);
//                            _profileManager.modifyProfil(_profileLogged);
//
//                            _profileManager.get_profileDAO().setProfileIsLogged(_profileLogged);
//
//                            actionProcessButton.setProgress(100);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    login(activity);
//                }
//                else {
//                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
//                }
//
//            }
//        };
//
//        this._myApi.setdataParams(userParams);
//        this._myApi.setCurrentResquest("/oauth2/token/", "POST");
//        this._myApi.execute();
//    }
}
