package sharemyscreen.sharemyscreen.SignIn.Api;

import android.content.Intent;

import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by roucou_c on 17/06/2016.
 */
public class ApiLogin {
    static final int REQUEST_CODE_GOOGLE = 1000;
    static final int REQUEST_CODE_FACEBOOK = 64206;

    public SignInActivity _signInActivity;

    ApiFacebook _apiFacebook;
    ApiGoogle _apiGoogle;

    public ApiLogin(SignInActivity signInActivity) {
        this._signInActivity = signInActivity;

        _apiFacebook = new ApiFacebook(_signInActivity);
        _apiGoogle = new ApiGoogle(_signInActivity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GOOGLE) {
            _apiGoogle.onActivityResult(requestCode, resultCode, data);
        }
        else if (requestCode == REQUEST_CODE_FACEBOOK){
            _apiFacebook.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void launch() {
        this._apiFacebook.launch();
        this._apiGoogle.launch();
    }

    public void login(String api) {
        switch (api) {
            case "google" :
                _apiGoogle.getToken();
                break;
        }
    }
}
