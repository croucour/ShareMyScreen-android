package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.Room;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.Profile.ProfileService;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by cleme_000 on 23/02/2016.
 */
public class SignInService extends MyService {

    public interface ISignInService {
        @Headers("Content-Type: application/json")
        @POST("v1/oauth2/token")
        Call<List<Room>> getRooms();
    }
    private final ISignInSignUpView _view;

    public SignInService(ISignInSignUpView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ISignInService.class);

    }

    protected void loginPostExecute(MyApi myApi){
        if (!myApi.is_internetConnection()) {
            this.loginWithoutConnexion();
        }
        else {
            if (!myApi.isErrorRequest()) {
                if (myApi.getResultJSON() != null) {
                    try {
                        String access_token = myApi.getResultJSON().getString("access_token");
                        String refresh_token = myApi.getResultJSON().getString("refresh_token");

                        String username = _view.getUsername();
                        String password = _view.getPassword();

                        ProfileEntity profileEntity = _profileManager.modifyProfil(username, password); // sauvegarde du password en fonction de username
                        profileEntity.set_access_token(access_token);
                        profileEntity.set_refresh_token(refresh_token);
                        profileEntity.set_expireAccess_token(myApi.getNewExpireToken());
                        _profileManager.modifyProfil(profileEntity);
                        _profileManager.setProfileIsLogged(profileEntity);

                        SettingsManager settingsManager = new SettingsManager(_pContext);
                        settingsManager.addSettings("lastSignInProfileId", String.valueOf(profileEntity.get_id()));

                        ProfileService profileModel = new ProfileService(null, _pContext);
                        profileModel.getProfile();

                        _view.setProcessLoadingButton(100);

                        _view.startRoomActivity();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), _view.getActionProcessButton());
            }
        }
    }

    private boolean loginWithoutConnexion() {
        String username = this._view.getUsername();
        String password = this._view.getPassword();

        ProfileEntity profileEntity = _profileManager.selectByUsername(username);

        if (profileEntity == null || !Objects.equals(profileEntity.get_password(), password)) {
            MyError.displayError(_view.getCoordinatorLayout(), R.string.connexionOfflline_errorUsernameOrPassword, _view.getActionProcessButton());
            return false;
        }

        if (profileEntity.get_refresh_token() == null) {
            // TODO : faire le test sur l'expiration du token refresh
            // impossible de se connecter en mote hors ligne
            return false;
        }

        _profileManager.setProfileIsLogged(profileEntity);

        this._view.startRoomActivity();
        return true;
    }

    public void signIn(HashMap<String, String> userParams) {
        MyApi myApi = new MyApi(_pContext) {
            @Override
            protected void onPostExecute(String str) {
                loginPostExecute(this);
            }
        };

        userParams.put("grant_type", "password");
        userParams.put("scope", "offline_access");

        myApi.setDataParams(userParams);
        myApi.setCurrentRequest("/oauth2/token/", "POST");
        myApi.execute();
    }

    protected void refreshTokenPostExecute(MyApi myApi, ProfileEntity profileEntity){

        String access_token;

        if (!myApi.isErrorRequest()) {
            if (myApi.getResultJSON() != null) {
                try {
                    access_token = myApi.getResultJSON().getString("access_token");
                    profileEntity.set_access_token(access_token);
                    _profileManager.modifyProfil(profileEntity);

                    _profileManager.setProfileIsLogged(profileEntity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this._view.startRoomActivity();
        }
        else {
            MyError.displayErrorApi(myApi, this._view.getCoordinatorLayout(), null);
        }

    }
        public void refreshToken(HashMap<String, String> userParams, final ProfileEntity profileEntity) {

        MyApi myApi = new MyApi(profileEntity, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                refreshTokenPostExecute(this, profileEntity);
            }
        };

        myApi.setDataParams(userParams);
        myApi.setCurrentRequest("/oauth2/token/", "POST");
        myApi.execute();
    }

    public void signInWithAccessTokenValid(ProfileEntity profileEntity) {
        _profileManager.setProfileIsLogged(profileEntity);
        this._view.startRoomActivity();
    }

    public void signInAfterSignUp(HashMap<String, String> userParams) {
        this.signIn(userParams);
    }
}
