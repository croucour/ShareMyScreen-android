package sharemyscreen.sharemyscreen.Model;

import android.content.Context;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignUpModel{

    private MyApi myApi;
    private SettingsManager settingsManager;

    public SignUpModel(Context contextApplication) {
        this.settingsManager = new SettingsManager(contextApplication);

    }

    public void createUser(HashMap<String, String> userParams)
    {
        //TODO vider le token
        this.myApi = new MyApi(this.settingsManager) {

            @Override
            protected void onPostExecute(String str) {

            }
        };

        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/users", "POST");
        this.myApi.execute();
    }
}
