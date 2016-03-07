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

//            ProfileEntity profileEntity = _manager._profileManager.selectByUsername(username);
//
//            if (profileEntity != null) {
//                this._signInService._userEntity.refresh(profileEntity);
////                this._signInService.set_profileLogged(profileEntity);
//                TokenEntity tokenEntity = _manager._tokenManager.selectByProfileId(profileEntity.get_id());
//                if (tokenEntity != null) {
//                    this._signInService.set_tokenEntity(tokenEntity);
//                    _manager._globalManager.addGlobal("current_token_id", String.valueOf(tokenEntity.get_id()));
//                }
//            }
            this._signInService._userEntity.refresh(username);

            this._signInService.signIn(params);
        }
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
