package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.ProfileActivity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileModel extends MyModel{

    public ProfileModel(Context pContext) {
        super(pContext);
    }

    public void getProfil(final Activity activity) {
        this._myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {

                if (!this.isErrorRequest()) {

                    _profileLogged.update(this.resultJSON);
                    _profileManager.modifyProfil(_profileLogged);

                    if (activity != null) {
                        ProfileActivity profileActivity = (ProfileActivity) activity;
                        profileActivity.populateProfile(_profileLogged);
                    }
                }
                else if (activity != null){
                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), null);
                }
            }
        };
        this._myApi.setCurrentResquest("/profile", "GET");
        this._myApi.execute();
    }

    public void saveProfile(HashMap<String, String> userParams, final Activity activity) {
        this._myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.profile_submit);

                if (this.is_internetConnection()) {
                    if (!this.isErrorRequest()) {
                        actionProcessButton.setProgress(100);
                        activity.finish();
                    } else {
                        MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
                    }
                }
                else {
                    activity.finish();
                }
            }
        };

        _profileLogged.update(userParams);
        _profileManager.modifyProfil(_profileLogged);

        this._myApi.setdataParams(userParams);
        this._myApi.setCurrentResquest("/profile", "PUT");
        this._myApi.execute();
    }
}
