package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

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

    static public Snackbar displayErrorNoConnexion(final IView view, ActionProcessButton actionProcessButton) {

        CoordinatorLayout coordinatorLayout = view.getCoordinatorLayout();
        if (coordinatorLayout != null) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Vous êtes actuellement hors connexion", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("en savoir plus", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.startSettingsActivity();
                }
            });
            view.setCallbackSnackbar(snackbar);
            snackbar.show();
            return snackbar;
        }
        return null;
    }

    public static void displayError(CoordinatorLayout coordinatorLayout, int resId, ActionProcessButton actionProcessButton) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, resId, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        actionProcessButton.setProgress(0);
    }

    public static void displayError(CoordinatorLayout coordinatorLayout, String msg, ActionProcessButton actionProcessButton) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        if (actionProcessButton != null) {
            actionProcessButton.setProgress(0);
        }
    }
}
