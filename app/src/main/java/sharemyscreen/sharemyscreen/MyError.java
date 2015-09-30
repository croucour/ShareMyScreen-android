package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.widget.Toast;

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

}
