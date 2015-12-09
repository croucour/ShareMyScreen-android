package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.ProfileActivity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileModel {

    private Context _contextApplication;
    private MyApi myApi;
    private ProfileManager _profileManager;
    private SettingsManager settingsManager;

    public ProfileModel(Context contextApplication) {
        this._profileManager = new ProfileManager(contextApplication);
        this.settingsManager = new SettingsManager(contextApplication);
        this._contextApplication = contextApplication;

    }

    public void getProfil(final Activity activity) {
        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {


                if (this.resultJSON != null) {
                    try {
                        String username = this.resultJSON.getString("username");
                        String email = this.resultJSON.getString("email");
                        String role = this.resultJSON.getString("role");

                        _profileManager.addProfil(username, email, role);

                        ProfileActivity profileActivity = (ProfileActivity) activity;

                        EditText editUsername = (EditText) profileActivity.findViewById(R.id.profile_username_editText);
                        EditText editEmail = (EditText) profileActivity.findViewById(R.id.profile_email_editText);

                        editUsername.setText(username);
                        editEmail.setText(email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        this.myApi.setCurrentResquest("/profile", "GET");
        this.myApi.execute();
    }

    public void saveProfile(HashMap<String, String> userParams, final Activity activity) {
        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {

//                if (this.resultJSON != null) {
//                    try {
//                        _profileManager.addProfil(username, email, role);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
                activity.finish();
            }
        };

        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/profile", "PUT");
        this.myApi.execute();
    }
}
