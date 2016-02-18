package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONException;

/**
 * Created by cleme_000 on 26/09/2015.
 */
public class MyError {

    private Toast toast = null;
    private Activity activity = null;
    protected String msg_error = null;

    public MyError(Activity activity)
    {
        this.activity = activity;
    }

    /**
     * return false => aucune erreur, true => erreur affiché
     * @return
     */
    protected boolean displayError()
    {
        if (toast != null) {
            toast.cancel();
        }

        if (msg_error != null) {
            toast = Toast.makeText(this.activity, this.msg_error, Toast.LENGTH_LONG);
            toast.show();
            this.msg_error = null;
            return false;
        }
        return true;
    }

    static public void displayErrorApi(MyApi myApi, final CoordinatorLayout coordinatorLayout, ActionProcessButton actionProcessButton) {
        if (myApi.get_responseCode() == 0) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, R.string.connexionError, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            if (actionProcessButton != null) {
                actionProcessButton.setProgress(0);
            }
        }
        else if (myApi.isErrorRequest()) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Erreur de l'api", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            if (actionProcessButton != null) {
                actionProcessButton.setProgress(0);
            }
        }
//        else if (myApi.resultJSON == null || !myApi.resultJSON.isNull("error_description")){
//            try {
//                if (myApi.resultJSON == null) {
//                    Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.api_error, Snackbar.LENGTH_INDEFINITE);
//                    snackbar.show();
//                }
//                else if (!myApi.resultJSON.isNull("error_description")) {
//                    Snackbar snackbar = Snackbar.make(coordinatorLayout, myApi.resultJSON.getString("error_description"), Snackbar.LENGTH_INDEFINITE);
//                    snackbar.show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (actionProcessButton != null) {
//                actionProcessButton.setProgress(0);
//            }
//        }
        else {
            if (actionProcessButton != null) {
                actionProcessButton.setProgress(100);
            }
        }
    }

}
