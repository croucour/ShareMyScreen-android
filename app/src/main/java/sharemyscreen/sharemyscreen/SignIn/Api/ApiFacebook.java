package sharemyscreen.sharemyscreen.SignIn.Api;

import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class ApiFacebook {

    SignInActivity _signInActivity;

    private CallbackManager _callbackManager;

    public ApiFacebook(SignInActivity signInActivity) {
        this._signInActivity = signInActivity;

        this.init();
    }

    public void init() {
        FacebookSdk.sdkInitialize(_signInActivity.getApplicationContext());
        AppEventsLogger.activateApp(_signInActivity);
    }

    public void launch() {
        _callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = _signInActivity.getSignInButtonFacebook();

        if (loginButton != null) {
            loginButton.setReadPermissions("email");

            loginButton.registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    String access_token_facebook = loginResult.getAccessToken().getToken();

                    Log.d("token facebook", access_token_facebook);
                    _signInActivity._signInPresenter.onLoginFacebookClicked(access_token_facebook);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
