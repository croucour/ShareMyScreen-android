package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import org.json.JSONException;

import java.util.Date;
import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.RoomActivity;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInModel {

    private MyApi myApi;
    private SettingsManager settingsManager;

    public SignInModel(Context contextApplication) {
        this.settingsManager = new SettingsManager(contextApplication);
    }

    public void signIn(HashMap<String, String> userParams, final Activity activity) {

        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {
                String access_token;

                if (!this.isErrorRequest()) {
                    if (this.resultJSON != null) {
                        try {
                            access_token = this.resultJSON.getString("access_token");
                            Log.i("access_token", access_token);
                            this.settingsManager.addSettings("access_token", access_token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    login(activity);
                }
                else if (this.get_responseCode() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(activity.findViewById(R.id.display_snackbar), R.string.connexionError, Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }
                else if (!this.resultJSON.isNull("error_description")){
                    Snackbar snackbar = null;
                    try {
                        snackbar = Snackbar
                                .make(activity.findViewById(R.id.display_snackbar), this.resultJSON.getString("error_description"), Snackbar.LENGTH_INDEFINITE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    snackbar.show();
                }

                //TODO set une erreur
            }
        };

        userParams.put("grant_type", "password");
        userParams.put("scope", "offline_access");


//        this.myApi.encodeKeySecret64("QSfJrDjK2E1IGnu0", "3NGWXYIPr0ioUHdcfHLPCwv1eNSuGkML");
        this.myApi.encodeKeySecret64("CJtYXgR8GlFWZfTr", "YNUnOblELFjjJmUIvyeVzmPIlY3VlH3W");
        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/oauth2/token/", "POST");
        this.myApi.execute();
    }

    private void login(Activity activity){
        activity.finish();

        Intent intent = new Intent(activity, RoomActivity.class);
        activity.startActivity(intent);
    }

    public boolean isLogin(final Activity activity) {
        String expireToken = this.settingsManager.select("expireToken");

        if (expireToken == null) {
            return false;
        }

        Date date = new Date();
        if (date.getTime() < Long.parseLong(expireToken)) {
            login(activity);
            return true;
        }

        return false;
    }
}
