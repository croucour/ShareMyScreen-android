package sharemyscreen.sharemyscreen.Room;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.IView;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IRoomView extends IView {
    void setRefreshing(boolean state);

    String getNameOfCreateRoomByUser();

    void setErrorNameOfCreateRoomByUser(int resId);

    void initializeInputLayoutCreateRoomByUser();

    String getUserOfCreateRoomByUser();

    void setErrorUserOfCreateRoomByUser(int resId);

    void setRoomEntityList(List<RoomEntity> roomEntityList);

    void setRoomEntity(RoomEntity roomEntity);

    void deleteRoomEntityList(RoomEntity roomEntity);

    void addRoomEntityList(RoomEntity roomEntity);

    void hideDialogCreateRoomByUser();

    void localRefreshRooms();
}
