package sharemyscreen.sharemyscreen;

import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

/**
 * Created by cleme_000 on 05/03/2016.
 */
public class MySnacbarCallBack extends Snackbar.Callback {

    private final IView _view;

    public MySnacbarCallBack(IView iView) {
        this._view = iView;

    }

    @Override
    public void onDismissed(Snackbar snackbar, int event) {
        super.onDismissed(snackbar, event);
        Fab fab = this._view.getFab();

        if (fab != null) {
            fab.animate().translationYBy((snackbar.getView().getHeight()));
        }
    }

    @Override
    public void onShown(Snackbar snackbar) {
        super.onShown(snackbar);
        Fab fab = this._view.getFab();
        if (fab != null) {
            fab.animate().translationYBy(-(snackbar.getView().getHeight()));
        }
    }
}
