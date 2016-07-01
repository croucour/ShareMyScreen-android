package sharemyscreen.sharemyscreen.Room;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.IView;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 16/02/16.
 */
public class RoomService extends MyService{

    public interface IRoomService {
        @Headers("Content-Type: application/json")
        @GET("rooms")
        Call<List<RoomEntity>> getRooms();
        @POST("rooms")
        Call<RoomEntity> postRooms(@Body Map<String, String> params);
    }

    private final IView _view;

    private IRoomService _api;

    public RoomService(IView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IRoomService.class, "api", _userEntity._tokenEntity, _manager);
    }


    public void getRooms() {
//        _userEntity.refresh();
//        this._view.setRefreshing(false);
//
//        Call call = _api.getRooms();
//        call.enqueue(new Callback<List<RoomEntity>>() {
//            @Override
//            public void onResponse(Call<List<RoomEntity>> call, Response<List<RoomEntity>> response) {
//                List<RoomEntity> roomEntityList = response.body();
//
//                if (roomEntityList != null) {
//                    _manager._roomsManager.add(roomEntityList);
//                    _userEntity.refreshRoomEntityList();
//                    _view.setRoomEntityList(roomEntityList);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RoomEntity>> call, Throwable t) {
//
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                    MyError.displayErrorNoConnexion(_view, null);
//                    _view.localRefreshRooms();
//                }
//            }
//        });
        _view.updateRoomEntityList();
    }


    private void postRoomOnResponse(RoomEntity roomEntity, RoomEntity roomEntityFail, String organization_public_id) {
        if (roomEntity == null) {
            _manager._roomsManager.add(roomEntityFail, organization_public_id);
//            _view.setRoomEntity(roomEntityFail);
        }
        else {
//            RoomEntity roomEntity = response.body();

            if (roomEntity != null) {
                _manager._roomsManager.add(roomEntity, organization_public_id);
//                _view.setRoomEntity(roomEntity);
                _view.changeRoom(roomEntity.get_public_id());
            }
        }

        _view.closeDialog("createRoom");
//        _view.hideDialogCreateRoomByUser();
    }

    public void postRoom(HashMap<String, String> params, String organization_public_id) {
//        this._userEntity.refreshToken();
//
//        params.put("owner", this._userEntity._profileEntity.get_public_id());
//
//        // TODO envoiyer la liste des profiles present dans params
//
//        final RoomEntity roomEntityFail = new RoomEntity(params, null);
//
//        Call call = _api.postRooms(params);
//        call.enqueue(new Callback<RoomEntity>() {
//            @Override
//            public void onResponse(Call<RoomEntity> call, Response<RoomEntity> response) {
//                postRoomOnResponse(response, roomEntityFail);
//            }
//
//            @Override
//            public void onFailure(Call<RoomEntity> call, Throwable t) {
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                    postRoomOnResponse(null, roomEntityFail);
//                }
//            }
//        });


        SecureRandom random = new SecureRandom();

        RoomEntity roomEntity = new RoomEntity(52, params.get("name"));
        roomEntity.set_private(Boolean.parseBoolean(params.get("private")));
        roomEntity.set_public_id(new BigInteger(130, random).toString(32));
        postRoomOnResponse(roomEntity, roomEntity, organization_public_id);
    }
}
