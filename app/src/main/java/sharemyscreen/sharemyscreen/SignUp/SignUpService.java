package sharemyscreen.sharemyscreen.SignUp;

import android.content.Context;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.SignIn.SignInService;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignUpService extends MyService{
    private final SignInService _signInService;
    private final ISignUpView _view;

    public SignUpService(ISignUpView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._signInService = new SignInService(view, pContext);
    }

    protected void createUserOnPostExecute(MyApi myApi, HashMap<String, String> userParams) {
        if (!myApi.isErrorRequest()) {
            _signInService.signInAfterSignUp(userParams);
        }
        else {
            MyError.displayErrorApi(myApi, this._view.getCoordinatorLayout(), this._view.getActionProcessButton());
        }
    }

    public void createUser(final HashMap<String, String> userParams) {
        MyApi myApi = new MyApi(_pContext) {

            @Override
            protected void onPostExecute(String str) {
                createUserOnPostExecute(this, userParams);
            }
        };

        myApi.setDataParams(userParams);
        myApi.setCurrentRequest("/users", "POST");
        myApi.execute();
    }
}
