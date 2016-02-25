package sharemyscreen.sharemyscreen.Logout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Menu.IMenuView;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.SignIn.ISignInView;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by roucou-c on 09/12/15.
 */
public class LogoutService extends MyService {

    private final IMenuView _view;

    public LogoutService(IMenuView view, Context pContext) {
        super(pContext);
        this._view = view;
    }

    private void logoutOnPostExecute(MyApi myApi) {
        // TODO modifier le test pour laiser se deconnecter
        if (!myApi.isErrorRequest()) {
            _profileLogged.set_access_token(null);
            _profileLogged.set_expireAccess_token(null);
            _profileLogged.set_logged(false);

            _profileManager.modifyProfil(_profileLogged);

            this._view.logout();
        }
        else {
            MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), null);
        }

    }

    public void logout() {

        MyApi myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                logoutOnPostExecute(this);
            }
        };

        myApi.setCurrentResquest("/logout", "GET");
        myApi.execute();
    }
}
