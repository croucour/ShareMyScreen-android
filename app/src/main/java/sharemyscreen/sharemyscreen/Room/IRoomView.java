package sharemyscreen.sharemyscreen.Room;

import android.support.design.widget.Snackbar;

import sharemyscreen.sharemyscreen.Menu.IMenuView;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IRoomView extends IMenuView {
    void setRefreshing(boolean state);

    void setCallbackSnackbar(Snackbar snackbar);

    String getNameOfCreateRoomByUser();

    void setErrorNameOfCreateRoomByUser(int resId);

    void initializeInputLayoutCreateRoomByUser();

    String getUserOfCreateRoomByUser();

    void setErrorUserOfCreateRoomByUser(int resId);
}
