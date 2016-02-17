package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MainActivity;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by roucou-c on 09/12/15.
 */
public class LogoutModel {

    private MyApi myApi;
    private SettingsManager settingsManager;

    public LogoutModel(Context contextApplication) {
        this.settingsManager = new SettingsManager(contextApplication);
    }

    public void logout(final Activity activity) {

        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {
                this.settingsManager.delete("access_token");
                this.settingsManager.delete("expireToken");
                this.settingsManager.delete("refresh_token");

                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        };

        this.myApi.setCurrentResquest("/logout", "GET");
        this.myApi.execute();
    }
}
