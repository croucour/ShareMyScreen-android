package sharemyscreen.sharemyscreen.RoomSettings;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 16/02/16.
 */
public class RoomSettingsService extends MyService{

    public interface IRoomService {
        @Headers("Content-Type: application/json")
        @DELETE("rooms/{id}")
        Call<ResponseBody> deleteRooms(@Path("id") String _id);
    }

    private final IRoomSettingsView _view;

    private IRoomService _api;

    public RoomSettingsService(IRoomSettingsView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IRoomService.class, "api", _userEntity._tokenEntity, _manager);
    }

    public void deleteRoom(final String room_public_id) {
//        this._userEntity.refreshToken();
//
//        Call call = _api.deleteRooms(roomEntity.get_public_id());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                _manager._roomsManager.delete(room_public_id);
        _manager._globalManager.deleteGlobal("room_public_id_selected");
        _view.finish();
//                _view.deleteRoomEntityList(roomEntity);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                    MyError.displayErrorNoConnexion(_view, null);
//                    _view.deleteRoomEntityList(roomEntity);
//                }
//            }
//        });
    }
}
