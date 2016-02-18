package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONException;

import java.util.Date;
import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
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
                String refresh_token;

                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);
                if (actionProcessButton == null) {
                    actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signup_submit);
                }

                if (!this.isErrorRequest()) {
                    if (this.resultJSON != null) {
                        try {
                            access_token = this.resultJSON.getString("access_token");
                            refresh_token = this.resultJSON.getString("refresh_token");
                            this.settingsManager.addSettings("access_token", access_token);
                            this.settingsManager.addSettings("refresh_token", refresh_token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    actionProcessButton.setProgress(100);
                    login(activity);
                }
                else {
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
                }

//                else if (this.get_responseCode() == 0) {
//                    Snackbar snackbar = Snackbar
//                            .make(activity.findViewById(R.id.display_snackbar), R.string.connexionError, Snackbar.LENGTH_INDEFINITE);
//                    snackbar.show();
//                    actionProcessButton.setProgress(0);
//                }
//
//                else if (this.resultJSON == null || this.resultJSON.isNull("error_description")){
//                    try {
//                        if (this.resultJSON == null) {
//                            Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.display_snackbar), R.string.api_error, Snackbar.LENGTH_INDEFINITE);
//                            snackbar.show();
//                        }
//                        else if (!this.resultJSON.isNull("error_description")) {
//                            Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.display_snackbar), this.resultJSON.getString("error_description"), Snackbar.LENGTH_INDEFINITE);
//                            snackbar.show();
//                        }
//                        actionProcessButton.setProgress(0);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

                //TODO set une erreur
            }
        };

        userParams.put("grant_type", "password");
        userParams.put("scope", "offline_access");


//        this.myApi.encodeKeySecret64("QSfJrDjK2E1IGnu0", "3NGWXYIPr0ioUHdcfHLPCwv1eNSuGkML");
//        this.myApi.encodeKeySecret64("CJtYXgR8GlFWZfTr", "YNUnOblELFjjJmUIvyeVzmPIlY3VlH3W"); // local clement
        this.myApi.encodeKeySecret64("sqE1rRxhjPbmwgWc", "TvfCZag4DRfqLsa8anETSxRNWstscQQK"); // heroku

        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/oauth2/token/", "POST");
        this.myApi.execute();
    }

    private void login(Activity activity){
        activity.finish();

        Intent intent = new Intent(activity, RoomActivity.class);
        activity.startActivity(intent);
    }

    public void isLogin(final Activity activity) {

        HashMap<String, String> params = new HashMap<>();


        String refresh_token = this.settingsManager.select("refresh_token");

        ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);

        if (refresh_token == null) {
            actionProcessButton.setProgress(0);
            return;
        }
        actionProcessButton.setProgress(1);

        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refresh_token);

        this.refreshToken(params, activity);

//        String expireToken = this.settingsManager.select("expireToken");
//
//        if (expireToken == null) {
//            return false;
//        }
//
//        Date date = new Date();
//        if (date.getTime() < Long.parseLong(expireToken)) {
//            login(activity);
//            return true;
//        }
//
//        return false;
    }

    public void refreshToken(HashMap<String, String> userParams, final Activity activity) {
        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {

                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signin_submitLogin);

                String access_token;

                if (!this.isErrorRequest()) {
                    if (this.resultJSON != null) {
                        try {
                            access_token = this.resultJSON.getString("access_token");
                            this.settingsManager.addSettings("access_token", access_token);
                            actionProcessButton.setProgress(100);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    login(activity);
                }
                else {
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
                }

            }
        };

//        this.myApi.encodeKeySecret64("CJtYXgR8GlFWZfTr", "YNUnOblELFjjJmUIvyeVzmPIlY3VlH3W"); // local clement
        this.myApi.encodeKeySecret64("sqE1rRxhjPbmwgWc", "TvfCZag4DRfqLsa8anETSxRNWstscQQK"); // heroku
        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/oauth2/token/", "POST");
        this.myApi.execute();
    }
}
