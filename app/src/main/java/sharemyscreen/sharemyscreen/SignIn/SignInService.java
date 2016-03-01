package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.Profile.ProfileService;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by cleme_000 on 23/02/2016.
 */
public class SignInService extends MyService {

    private final ISignInService _api;

    public interface ISignInService {
        @Headers("Content-Type: application/json")
        @POST("oauth2/token")
        Call<TokenEntity> signIn(@Body Map<String, String> params);

    }
    private final ISignInSignUpView _view;

    public SignInService(ISignInSignUpView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ISignInService.class, pContext);
    }


    // TODO immplementer le loginWithoutConnexion
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

//        _settingsManager.addSettings("current_token_id", String.valueOf(token_id));


        this._view.startRoomActivity();
        return true;
    }

    protected void signInOnResponse(Response<TokenEntity> response){
        TokenEntity tokenEntity = response.body();

        if (tokenEntity != null) {
            long token_id = _tokenManager.add(tokenEntity);

            _settingsManager.addSettings("current_token_id", String.valueOf(token_id));

            ProfileService profileModel = new ProfileService(null, _pContext);
            profileModel.getProfile();

            _view.setProcessLoadingButton(100);

            _view.startRoomActivity();
        }
    }

    public void signIn(HashMap<String, String> userParams) {
        userParams.put("grant_type", "password");
        userParams.put("scope", "offline_access");

        Call call = _api.signIn(userParams);
        call.enqueue(new Callback<TokenEntity>() {
            @Override
            public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
                signInOnResponse(response);
            }

            @Override
            public void onFailure(Call<TokenEntity> call, Throwable t) {
            }
        });
    }

//    protected void refreshTokenPostExecute(MyApi myApi, ProfileEntity profileEntity){
//
//        String access_token;
//
//        if (!myApi.isErrorRequest()) {
//            if (myApi.getResultJSON() != null) {
//                try {
//                    access_token = myApi.getResultJSON().getString("access_token");
//                    profileEntity.set_access_token(access_token);
//                    _profileManager.modifyProfil(profileEntity);
//
//                    _profileManager.setProfileIsLogged(profileEntity);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            this._view.startRoomActivity();
//        }
//        else {
//            MyError.displayErrorApi(myApi, this._view.getCoordinatorLayout(), null);
//        }
//
//    }

//    public void refreshToken(HashMap<String, String> userParams, final ProfileEntity profileEntity) {
//
//        MyApi myApi = new MyApi(profileEntity, _pContext) {
//            @Override
//            protected void onPostExecute(String str) {
//                refreshTokenPostExecute(this, profileEntity);
//            }
//        };
//
//        myApi.setDataParams(userParams);
//        myApi.setCurrentRequest("/oauth2/token/", "POST");
//        myApi.execute();
//    }
//
//    public void signInWithAccessTokenValid(ProfileEntity profileEntity) {
//        _profileManager.setProfileIsLogged(profileEntity);
//        this._view.startRoomActivity();
//    }

    public void signInAfterSignUp(HashMap<String, String> userParams) {
        this.signIn(userParams);
    }
}
