package sharemyscreen.sharemyscreen.Room;

import android.content.Context;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class RoomPresenter {

    private final Context _pContext;
    private final IRoomView _view;
    private final RoomService _roomService;

    public RoomPresenter(IRoomView _view, Context _pContext) {
        this._pContext = _pContext;
        this._view = _view;
        this._roomService = new RoomService(_view, _pContext);
    }

    public void onSwipedForRefreshRooms() {
        this._roomService.getRooms();
    }

    public void onCreateRoomByUserClicked() {

        boolean error = false;

        String name = this._view.getNameOfCreateRoomByUser();

        this._view.initializeInputLayoutCreateRoomByUser();
        if (name.isEmpty()) {
            this._view.setErrorNameOfCreateRoomByUser(R.string.createRoom_by_user_name_isEmpty);
            error = true;
        }

        String user = this._view.getUserOfCreateRoomByUser();

        if (user.isEmpty()) {
            this._view.setErrorUserOfCreateRoomByUser(R.string.createRoom_by_user_user_isEmpty);
            error = true;
        }

        if (!error) {
            HashMap<String, String> params = this.getParamsForCreateRoomByUser(name, user);

            if (params != null) {
                this._roomService.addRoom(params);
            }

        }
    }

    private HashMap<String, String> getParamsForCreateRoomByUser(String name, String user) {
        HashMap<String, String> params = new HashMap<>();

        ProfileEntity profileLogged = this._roomService.get_profileLogged();

        if (profileLogged != null) {
            ProfileManager profileManager = this._roomService.get_profileManager();
            String owner__id = profileLogged.get__id();
            ProfileEntity profileSelected = profileManager.selectByUsername(user);
            if (profileSelected != null && profileSelected.get__id() != null) {

                String member__id = profileSelected.get__id();
                params.put("name", name);
                params.put("owner", owner__id);
                params.put("members", member__id);
            }
            else {
                this._view.setErrorUserOfCreateRoomByUser(R.string.createRoom_by_user_user_notFound);
            }
        }

        return params.size() == 0 ? null : params;
    }
}
