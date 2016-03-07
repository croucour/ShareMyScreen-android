package sharemyscreen.sharemyscreen.SignIn;

import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.Profile.ProfileService;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by cleme_000 on 23/02/2016.
 */
public class SignInService extends MyService {

    private ISignInService _api;

    public interface ISignInService {
        @Headers("Content-Type: application/json")
        @POST("oauth2/token")
        Call<TokenEntity> signIn(@Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @POST("oauth2/token")
        Call<TokenEntity> refreshToken(@Body Map<String, String> params);

    }
    private final ISignInSignUpView _view;

    public SignInService(ISignInSignUpView view, Manager manager) {
        super(manager);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ISignInService.class, manager);
    }


    // TODO immplementer le loginWithoutConnexion
    private void signInWithoutConnexion() {
        String username = this._view.getUsername();
        String password = this._view.getPassword();

        ProfileEntity profileEntity = _manager._profileManager.selectByUsername(username);

        if (profileEntity == null || !Objects.equals(profileEntity.get_password(), password)) {
            this._view.setErrorPassword(R.string.connexionOfflline_errorUsernameOrPassword);
            this._view.setProcessLoadingButton(0);
            return;
        }

//        if (profileEntity.get_refresh_token() == null) {
//            // TODO : faire le test sur l'expiration du token refresh
//            // impossible de se connecter en mote hors ligne
//            return;
//        }

        TokenEntity tokenEntity = _manager._tokenManager.selectByProfileId(profileEntity.get_id());

        if (tokenEntity == null) {
            this._view.setErrorPassword(R.id.connexionOfflline_error);
            this._view.setProcessLoadingButton(0);
            return;
        }
//        this._tokenEntity = tokenEntity;
        _manager._globalManager.addGlobal("current_token_id", String.valueOf(tokenEntity.get_id()));
        _userEntity.refresh();

        this._view.startRoomActivity();
    }

    protected void signInOnResponse(Response<TokenEntity> response){
        TokenEntity tokenEntity = response.body();

        if (tokenEntity != null) {

            if (_userEntity._tokenEntity == null) {
                this._userEntity.addTokenEntity(tokenEntity);
            }
            else {
                tokenEntity.set_profile_id(_userEntity._tokenEntity.get_profile_id());
                tokenEntity.set_id(_userEntity._tokenEntity.get_id());

                _userEntity.set_tokenEntity(tokenEntity);
            }

            String profilePassword = this._view.getPassword();
            ProfileService profileModel = new ProfileService(null, _manager, _userEntity);
            profileModel.getProfile(profilePassword);

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
                if (t instanceof UnknownHostException){
                    signInWithoutConnexion();
                }
            }
        });
    }

    public void refreshToken() {

        if (_userEntity._tokenEntity == null || _userEntity._tokenEntity.get_refresh_token() == null) {
            return;
        }

        HashMap<String, String> params = new HashMap<>();

        params.put("grant_type", "refresh_token");
        params.put("refresh_token", _userEntity._tokenEntity.get_refresh_token());

        this._api = ServiceGeneratorApi.createService(ISignInService.class, _manager);

        Call call = _api.refreshToken(params);
        call.enqueue(new Callback<TokenEntity>() {
            @Override
            public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
                TokenEntity tokenEntity = response.body();
                if (tokenEntity != null) {
                    _userEntity._tokenEntity.set_access_token(tokenEntity.get_access_token());
                    _userEntity.update_tokenEntity();

                    ProfileService profileModel = new ProfileService(null, _manager, _userEntity);
                    profileModel.getProfile(null);

                    _view.setProcessLoadingButton(100);
                    _view.startRoomActivity();
                }
            }

            @Override
            public void onFailure(Call<TokenEntity> call, Throwable t) {
                Log.d("erreur", "erreur lors du refresh token");
            }
        });
    }

    public String refreshTokenSync(TokenEntity tokenEntity) {
        if (tokenEntity== null || tokenEntity.get_refresh_token() == null) {
            return null;
        }

        HashMap<String, String> params = new HashMap<>();

        params.put("grant_type", "refresh_token");
        params.put("refresh_token", tokenEntity.get_refresh_token());

        this._api = ServiceGeneratorApi.createService(ISignInService.class, _manager);

        Call<TokenEntity> call = _api.refreshToken(params);

        TokenEntity resTokenEntity = null;
        try {
            Response<TokenEntity> execute = call.execute();
            resTokenEntity = execute.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (resTokenEntity == null) {
            return null;
        }

        tokenEntity.set_access_token(resTokenEntity.get_access_token());
        _manager._tokenManager.modify(tokenEntity);
        return resTokenEntity.get_access_token();
    }


    public void signInWithAccessTokenValid() {
        this._view.startRoomActivity();
    }

    public void signInAfterSignUp(HashMap<String, String> userParams) {
        this.signIn(userParams);
    }
}
