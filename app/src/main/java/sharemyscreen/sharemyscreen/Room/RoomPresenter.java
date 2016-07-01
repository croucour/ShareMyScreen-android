package sharemyscreen.sharemyscreen.Room;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.IView;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class RoomPresenter {

    private final IView _view;
    private final RoomService _roomService;
    private final Manager _manager;

    public RoomPresenter(IView _view, Manager manager, UserEntity userEntity) {
        this._manager = manager;
        this._view = _view;
        this._roomService = new RoomService(_view, manager, userEntity);
    }

    public void onSwipedForRefreshRooms() {
//        this._roomService.getRooms();
    }

    public void onCreateRoomByUserClicked() {
//
//        boolean error = false;
//
//        String name = this._view.getNameOfCreateRoomByUser();
//
//        this._view.initializeInputLayoutCreateRoomByUser();
//        if (name.isEmpty()) {
//            this._view.setErrorNameOfCreateRoomByUser(R.string.createRoom_by_user_name_isEmpty);
//            error = true;
//        }
//
//        String user = this._view.getUserOfCreateRoomByUser();
//
//        if (user.isEmpty()) {
//            this._view.setErrorUserOfCreateRoomByUser(R.string.createRoom_by_user_user_isEmpty);
//            error = true;
//        }
//
//        if (!error) {
//            HashMap<String, String> params = this.getParamsForCreateRoomByUser(name, user);
//
//            if (params != null) {
//                this._roomService.postRoom(params);
//            }
//
//        }
    }

    private HashMap<String, String> getParamsForCreateRoomByUser(String name, String user) {
//        HashMap<String, String> params = new HashMap<>();
//
//        ProfileEntity profileSelected = _manager._profileManager.selectByEmail(user);
//        if (profileSelected != null && profileSelected.get_public_id() != null) {
//
//            String member__id = profileSelected.get_public_id();
//            params.put("name", name);
//            params.put("members", member__id);
//        }
//        else {
//            this._view.setErrorUserOfCreateRoomByUser(R.string.createRoom_by_user_user_notFound);
//        }
//
//        return params.size() == 0 ? null : params;
        return null;
    }

    public void getRooms() {
        this._roomService.getRooms();
    }

    public void onCreateClicked(String organization_public_id, String create_type_room) {
        boolean error = false;
        String name = _view.getNameRoom();

        if (name.isEmpty()) {
            _view.setErrorNameRoom(R.string.createRoom_nameEmpty);
            error = true;
        }

        if (!error) {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("private", String.valueOf(create_type_room == "private"));

            _roomService.postRoom(params, organization_public_id);
        }
    }
}
