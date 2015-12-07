package sharemyscreen.sharemyscreen.Model;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsDAO;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInModel {

    private MyApi myApi;
    private SettingsManager settingsManager;

    public SignInModel(Context contextApplication) {
        this.settingsManager = new SettingsManager(contextApplication);
    }

    public void signIn(HashMap<String, String> userParams) {

        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {
                String access_token;

                if (this.resultJSON != null) {
                    try {
                        access_token = this.resultJSON.getString("access_token");
                        Log.i("access_token", access_token);
                        this.settingsManager.addSettings("access_token", access_token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        userParams.put("grant_type", "password");
        userParams.put("scope", "offline_access");

        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/oauth2/token/");
        this.myApi.encodeUsernamePassword64(userParams.get("username"), userParams.get("password"));
        this.myApi.execute();
    }
}
