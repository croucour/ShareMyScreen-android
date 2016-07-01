package sharemyscreen.sharemyscreen.RoomSettings;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class RoomSettingsPresenter {

    private final IRoomSettingsView _view;
    private final RoomSettingsService _roomService;
    private final Manager _manager;

    public RoomSettingsPresenter(IRoomSettingsView _view, Manager manager, UserEntity userEntity) {
        this._manager = manager;
        this._view = _view;
        this._roomService = new RoomSettingsService(_view, manager, userEntity);
    }



    public void deleteRoomOnClicked(String room_public_id) {
        _roomService.deleteRoom(room_public_id);
    }
}
