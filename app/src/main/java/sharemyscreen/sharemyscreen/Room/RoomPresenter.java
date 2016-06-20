package sharemyscreen.sharemyscreen.Room;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.MySnacbarCallBack;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class RoomPresenter {

    private final IRoomView _view;
    private final RoomService _roomService;
    private final Manager _manager;

    public RoomPresenter(IRoomView _view, Manager manager, UserEntity userEntity) {
        this._manager = manager;
        this._view = _view;
        this._roomService = new RoomService(_view, manager, userEntity);
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
                this._roomService.postRoom(params);
            }

        }
    }

    private HashMap<String, String> getParamsForCreateRoomByUser(String name, String user) {
        HashMap<String, String> params = new HashMap<>();

        ProfileEntity profileSelected = _manager._profileManager.selectByEmail(user);
        if (profileSelected != null && profileSelected.get__id() != null) {

            String member__id = profileSelected.get__id();
            params.put("name", name);
            params.put("members", member__id);
        }
        else {
            this._view.setErrorUserOfCreateRoomByUser(R.string.createRoom_by_user_user_notFound);
        }

        return params.size() == 0 ? null : params;
    }

    public void deleteRoomOnClicked(final RoomEntity roomEntity) {

        _view.deleteRoomEntityList(roomEntity);

        Snackbar snackbar = Snackbar.make(this._view.getCoordinatorLayout(), "Suppresion d'une conversation", Snackbar.LENGTH_LONG);
        snackbar.setAction("Annuler", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _view.addRoomEntityList(roomEntity);
            }
        });

        snackbar.setCallback(new MySnacbarCallBack(_view) {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (event != DISMISS_EVENT_ACTION) {
                    _roomService.deleteRoom(roomEntity);
                }
            }
        });
        snackbar.show();
    }
}
