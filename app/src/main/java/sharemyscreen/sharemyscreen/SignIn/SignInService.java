package sharemyscreen.sharemyscreen.SignIn;

import android.content.Context;

import org.json.JSONException;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.Profile.ProfileService;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;

/**
 * Created by cleme_000 on 23/02/2016.
 */
public class SignInService extends MyService {

    private final ISignInView _view;

    public SignInService(ISignInView view, Context pContext) {
        super(pContext);
        this._view = view;
    }

    protected void loginPostExecute(MyApi myApi){
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
        }
        else {
            MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), _view.getActionProcessButton());
        }

    }

    public void signIn(HashMap<String, String> userParams) {
        MyApi myApi = new MyApi(_pContext) {
            @Override
            protected void onPostExecute(String str) {
                loginPostExecute(this);
            }
        };


        myApi.setdataParams(userParams);
        myApi.setCurrentResquest("/oauth2/token/", "POST");
        myApi.execute();
    }
}
