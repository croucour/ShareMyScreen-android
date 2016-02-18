package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONException;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
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

                if (!this.isErrorRequest()) {
                    try {

                        String firstname = null;
                        String lastname = null;
                        String phone = null;

                        if (!this.resultJSON.isNull("firstName")) {
                            firstname = this.resultJSON.getString("firstName");
                        }
                        if (!this.resultJSON.isNull("lastName")) {
                            lastname = this.resultJSON.getString("lastName");
                        }
                        if (!this.resultJSON.isNull("phone")) {
                            phone = this.resultJSON.getString("phone");
                        }

                        String username = this.resultJSON.getString("username");
                        String email = this.resultJSON.getString("email");
//                        String role = this.resultJSON.getString("role");

//                        _profileManager.addProfil(username, email, role);

                        ProfileActivity profileActivity = (ProfileActivity) activity;

                        EditText editUsername = (EditText) profileActivity.findViewById(R.id.profile_username_editText);
                        EditText editEmail = (EditText) profileActivity.findViewById(R.id.profile_email_editText);

                        if (firstname != null) {
                            EditText editFirstname = (EditText) profileActivity.findViewById(R.id.profile_firstname_editText);
                            editFirstname.setText(firstname);
                        }

                        if (lastname != null) {
                            EditText editLastname = (EditText) profileActivity.findViewById(R.id.profile_lastname_editText);
                            editLastname.setText(lastname);
                        }

                        if (phone != null) {
                            EditText editPhone = (EditText) profileActivity.findViewById(R.id.profile_phone_editText);
                            editPhone.setText(phone);
                        }

                        editUsername.setText(username);
                        editEmail.setText(email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), null);
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
                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.profile_submit);

                if (!this.isErrorRequest()) {
                    actionProcessButton.setProgress(100);
                    activity.finish();
                }
                else {
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
                }
            }
        };

        this.myApi.setdataParams(userParams);
        this.myApi.setCurrentResquest("/profile", "PUT");
        this.myApi.execute();
    }
}
