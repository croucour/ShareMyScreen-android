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

    static public Snackbar displayErrorApi(MyApi myApi, final CoordinatorLayout coordinatorLayout, ActionProcessButton actionProcessButton) {

        int msgId = 0;

        if (myApi.is_internetConnection()) {
            if (myApi.get_responseCode() == 0) { // impossible de contacter l'api
                msgId = R.string.connexionError;

                if (actionProcessButton != null) {
                    actionProcessButton.setProgress(0);
                }
            } else if (myApi.isErrorRequest()) { // erreur de l'api
                msgId = R.string.api_error;

                if (actionProcessButton != null) {
                    actionProcessButton.setProgress(0);
                }
            }
            else {
                if (actionProcessButton != null) {
                    actionProcessButton.setProgress(100);
                }
            }
        }
        else { // pas de connexion internet
            msgId = R.string.offline;

            if (actionProcessButton != null) {
                actionProcessButton.setProgress(0);
            }
        }

        if (msgId != 0) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, msgId, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            return snackbar;
        }
        return null;
    }

}
