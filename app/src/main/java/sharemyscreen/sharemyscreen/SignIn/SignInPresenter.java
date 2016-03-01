package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;

import java.util.Date;
import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInPresenter{

    private final SettingsManager _settingsManager;
    private ISignInView _view;
    protected SignInService _signInService;

    public SignInPresenter(ISignInView view, Context pContext) {
        this._view = view;
        this._signInService = new SignInService(view, pContext);
        this._settingsManager = new SettingsManager(pContext);

    }


    public void onLoginClicked() {
        boolean error = false;
        String username = _view.getUsername();

        this._view.initializeInputLayout();

        if (username.isEmpty()) {
            _view.setErrorUsername(R.string.signin_usernameEmpty);
            error = true;
        }

        String password = _view.getPassword();
        if (password.isEmpty()) {
            _view.setErrorPassword(R.string.signin_passwordEmpty);
            error = true;
        }

        if (!error) {
            _view.setProcessLoadingButton(1);
            HashMap<String, String> params = new HashMap<>();

            params.put("username", username);
            params.put("password", password);

            this._signInService.signIn(params);
        }
    }

//    public boolean isLoginWithRefreshToken() {
//        String lastSignInProfileId = this._settingsManager.select("lastSignInProfileId");
//
//        if (lastSignInProfileId == null) {
//            return false;
//        }
//
//        ProfileManager profileManager = this._signInService.get_profileManager();
//
//        ProfileEntity lastSignInProfileEntity = profileManager.selectById(Long.parseLong(lastSignInProfileId));
//
//        if (lastSignInProfileEntity == null) {
//            return false;
//        }
//
//        String refresh_token = lastSignInProfileEntity.get_refresh_token();
//        String expireAccess_token = lastSignInProfileEntity.get_expireAccess_token();
//
//        if (refresh_token == null || expireAccess_token == null) {
//            return false;
//        }
//
//        // TODO : faire le test sur l'expiration du token refresh
//
//        if (isValidAccessToken(expireAccess_token)) {
//            this._signInService.signInWithAccessTokenValid(lastSignInProfileEntity);
//        }
//        else {
//            HashMap<String, String> params = new HashMap<>();
//
//            params.put("grant_type", "refresh_token");
//            params.put("refresh_token", refresh_token);
//
//            this._signInService.refreshToken(params, lastSignInProfileEntity);
//        }
//        return true;
//    }

//    public boolean isValidAccessToken(String expireAccess_token) {
//        Date date = new Date();
//
//        if (date.getTime() > Long.parseLong(expireAccess_token)) {
//            return false;
//        }
//        return true;
//    }

}
