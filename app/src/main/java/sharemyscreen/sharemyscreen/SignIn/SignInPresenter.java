package sharemyscreen.sharemyscreen.SignIn;

import java.util.Date;
import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInPresenter{

    private final Manager _manager;
    private ISignInView _view;
    private SignInService _signInService;

    public SignInPresenter(ISignInView view, Manager manager) {
        this._view = view;
        this._manager = manager;
        this._signInService = new SignInService(view, manager);
    }


    public void onLoginClicked() {
        boolean error = false;
        String email = _view.getEmail();

        this._view.initializeInputLayout();

        if (email.isEmpty()) {
            _view.setErrorEmail(R.string.signin_emailEmpty);
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

            params.put("username", email);
            params.put("password", password);

//            ProfileEntity profileEntity = _manager._profileManager.selectByEmail(username);
//
//            if (profileEntity != null) {
//                this._signInService._userEntity.refresh(profileEntity);
////                this._signInService.set_profileLogged(profileEntity);
//                TokenEntity tokenEntity = _manager._tokenManager.selectByProfilePublicId(profileEntity.get_id());
//                if (tokenEntity != null) {
//                    this._signInService.set_tokenEntity(tokenEntity);
//                    _manager._globalManager.addGlobal("current_token_id", String.valueOf(tokenEntity.get_id()));
//                }
//            }

            this._signInService.signIn(params);
        }
    }

    public void onLoginFacebookClicked(String access_token_facebook) {
        HashMap<String, String> params = new HashMap<>();

        params.put("access_token", access_token_facebook);

        this._signInService.signInExternalApi(params, "facebook");
    }

    public void onLoginGoogleClicked(String access_token_google) {
        HashMap<String, String> params = new HashMap<>();

        params.put("access_token", access_token_google);

        this._signInService.signInExternalApi(params, "google");
    }

    public boolean isLoginWithRefreshToken() {

        ProfileEntity profileLogged = _signInService._userEntity._profileEntity;
        TokenEntity tokenEntity = _signInService._userEntity._tokenEntity;

        if (profileLogged == null || tokenEntity == null) {
            return false;
        }

        String refresh_token = tokenEntity.get_refresh_token();
        String expireAccess_token = tokenEntity.get_expire_access_token();

        if (refresh_token == null || expireAccess_token == null) {
            return false;
        }

        // TODO : faire le test sur l'expiration du token refresh

        if (isValidAccessToken(expireAccess_token)) {
            this._signInService.signInWithAccessTokenValid();
        }
        else {
            this._signInService.refreshToken();
        }
        return true;
    }

    public boolean isValidAccessToken(String expireAccess_token) {
        Date date = new Date();

        if (date.getTime() > Long.parseLong(expireAccess_token)) {
            return false;
        }
        return true;
    }

}
