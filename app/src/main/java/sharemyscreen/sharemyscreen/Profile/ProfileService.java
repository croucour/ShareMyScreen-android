package sharemyscreen.sharemyscreen.Profile;

import android.content.Context;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileService extends MyService {

    private final IProfileView _view;

    public ProfileService(IProfileView view, Context pContext) {
        super(pContext);
        this._view = view;
    }

    public void getProfileOnPostExecute(MyApi myApi) {
        if (!myApi.isErrorRequest()) {

            _profileLogged.update(myApi.getResultJSON());
            _profileManager.modifyProfil(_profileLogged);

            if (_view != null) {
                _view.populateProfile(_profileLogged);
            }
        }
        else if (_view != null){
            MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), null);
        }


    }
    public void getProfile() {
        MyApi myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                getProfileOnPostExecute(this);
            }
        };
        myApi.setCurrentResquest("/profile", "GET");
        myApi.execute();
    }

    public void saveProfileOnPostExecute(MyApi myApi) {

        if (myApi.is_internetConnection()) {
            if (!myApi.isErrorRequest()) {
                this._view.setProcessLoadingButton(100);
                this._view.finish();
            } else {
                MyError.displayErrorApi(myApi, this._view.getCoordinatorLayout(), this._view.getActionProcessButton());
            }
        }
        else {
            this._view.finish();
        }
    }

    public void saveProfile(HashMap<String, String> userParams) {
        MyApi myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                saveProfileOnPostExecute(this);
            }
        };

        _profileLogged.update(userParams);
        _profileManager.modifyProfil(_profileLogged);

        userParams.put("username", _profileLogged.get_username());

        myApi.setdataParams(userParams);
        myApi.setCurrentResquest("/profile", "PUT");
        myApi.execute();
    }
}
