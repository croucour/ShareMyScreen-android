package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.MainActivity;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by roucou-c on 09/12/15.
 */
public class LogoutModel {

    private final ProfileManager _profileManager;
    private MyApi myApi;
    private Context _pContext = null;

    public LogoutModel(Context pContext) {
        this._profileManager = new ProfileManager(pContext);
        this._pContext = pContext;
    }

    public void logout(final Activity activity) {
        final ProfileEntity profileEntity = _profileManager.get_profileDAO().selectProfileLogged();

        this.myApi = new MyApi(profileEntity, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                profileEntity.set_access_token(null);
                profileEntity.set_expireAccess_token(null);
                profileEntity.set_refresh_token(null);
                profileEntity.set_expireRefresh_token(null);

                _profileManager.modifyProfil(profileEntity);

                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        };

        this.myApi.setCurrentResquest("/logout", "GET");
        this.myApi.execute();
    }
}
