package sharemyscreen.sharemyscreen;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import sharemyscreen.sharemyscreen.Menu.IMenuView;

/**
 * Created by cleme_000 on 03/03/2016.
 */
public interface IView extends IMenuView {

    CoordinatorLayout getCoordinatorLayout();

    void setCallbackSnackbar(Snackbar snackbar);

    void updateRoomEntityList();

    String getNameRoom();

    void setErrorNameRoom(int resId);

    void changeOrganization(String organization_public_id);

    void changeRoom(String room_public_id);
}
