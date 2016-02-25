package sharemyscreen.sharemyscreen.Menu;

import android.support.design.widget.CoordinatorLayout;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IMenuView {
    void logout();
    void profile();
    void logOffline();

    CoordinatorLayout getCoordinatorLayout();
}
