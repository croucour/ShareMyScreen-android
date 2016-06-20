package sharemyscreen.sharemyscreen.SignIn.Api;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import java.io.IOException;

import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by roucou_c on 18/06/2016.
 */
public class ApiGoogle extends AsyncTask {

    private static final java.lang.String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    private final SignInActivity _signInActivity;
    String mEmail;

    public ApiGoogle(SignInActivity signInActivity) {
        this._signInActivity = signInActivity;
    }

    public void launch() {
        _signInActivity.getSignInButtonGoogle().setOnClickListener(_signInActivity);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try{
            String token = fetchToken();
            Log.d("token", token);
            if(token != null){
                this._signInActivity._signInPresenter.onLoginGoogleClicked(token);
            }
        }catch(IOException e){
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.
        }
        return null;
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchToken()throws IOException{
        try{
            return GoogleAuthUtil.getToken(_signInActivity, mEmail, SCOPE);
        }catch(UserRecoverableAuthException userRecoverableException){
        // GooglePlayServices.apk is either old, disabled, or not present
        // so we need to show the user some UI in the activity to recover.
//            mActivity.handleException(userRecoverableException);
        }catch(GoogleAuthException fatalException){
        // Some other type of unrecoverable exception has occurred.
        // Report and log the error as appropriate for your app.
        }
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            // With the account name acquired, go get the auth token
            getToken();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // The account picker dialog closed without selecting an account.
            // Notify users that they must pick an account to proceed.
//                Toast.makeText(this, R.string.pick_account, Toast.LENGTH_SHORT).show();
        }
    }

    public void getToken() {
        if (mEmail == null) {
            pickUserAccount();
        } else {
            this.execute();
        }
    }

    public void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);
        _signInActivity.startActivityForResult(intent, ApiLogin.REQUEST_CODE_GOOGLE);
    }

}
