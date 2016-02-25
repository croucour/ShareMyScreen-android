package sharemyscreen.sharemyscreen.Room;

import android.content.Context;

import sharemyscreen.sharemyscreen.DAO.RoomsManager;

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
}
