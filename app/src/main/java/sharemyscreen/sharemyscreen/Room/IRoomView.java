package sharemyscreen.sharemyscreen.Room;

import android.support.design.widget.Snackbar;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.RoomEntity;
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

    void setRoomEntityList(List<RoomEntity> roomEntityList);

    void setRoomEntity(RoomEntity roomEntity);

    void deleteRoomEntityList(RoomEntity roomEntity);

    void hideDialogCreateRoomByUser();
}
