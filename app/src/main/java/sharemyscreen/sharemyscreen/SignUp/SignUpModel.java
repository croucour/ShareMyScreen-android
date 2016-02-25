package sharemyscreen.sharemyscreen.SignUp;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInPresenter;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignUpModel{

//    private final SignInPresenter _signInPresenter;
    private MyApi _myApi;
    private SettingsManager _settingsManager;
    private Context _pContext = null;

    public SignUpModel(Context pContext) {
        this._settingsManager = new SettingsManager(pContext);
//        this._signInPresenter = new SignInPresenter(pContext);
        this._pContext = pContext;
    }

    public void createUser(final HashMap<String, String> userParams, final Activity activity)
    {
//        this._settingsManager.delete("access_token");
//        this._settingsManager.delete("expireToken");
//        this._settingsManager.delete("refresh_token");

//        this._myApi = new MyApi(null, _pContext) {
//
//            @Override
//            protected void onPostExecute(String str) {
//
//                if (!this.isErrorRequest()) {
////                    _signInPresenter.signIn(userParams, activity);
//                }
//                else {
//                    ActionProcessButton actionProcessButton = (ActionProcessButton) activity.findViewById(R.id.signup_submit);
//                    MyError.displayErrorApi(this, (CoordinatorLayout) activity.findViewById(R.id.display_snackbar), actionProcessButton);
//                }
//
//            }
//        };
//
//        this._myApi.setdataParams(userParams);
//        this._myApi.setCurrentResquest("/users", "POST");
//        this._myApi.execute();
    }
}
