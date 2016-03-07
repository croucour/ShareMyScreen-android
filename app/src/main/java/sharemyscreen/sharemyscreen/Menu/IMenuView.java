package sharemyscreen.sharemyscreen.Menu;

import android.support.design.widget.CoordinatorLayout;

import sharemyscreen.sharemyscreen.IView;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IMenuView {
    void startRoomActivity();

    void startProfileActivity();

    void startSettingsActivity();

    void startLogOfflineActivity();

    void logout();

    void startSignInActivity();

}
