package sharemyscreen.sharemyscreen.Room;

import java.io.IOException;
import java.net.UnknownHostException;
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

        @DELETE("rooms/{id}")
        Call<ResponseBody> deleteRooms(@Path("id") String _id);
    }

    private final IRoomView _view;

    private IRoomService _api;

    public RoomService(IRoomView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IRoomService.class, _userEntity._tokenEntity, _manager);
    }


    public void getRooms() {
        _userEntity.refresh();
        this._view.setRefreshing(false);

        Call call = _api.getRooms();
        call.enqueue(new Callback<List<RoomEntity>>() {
            @Override
            public void onResponse(Call<List<RoomEntity>> call, Response<List<RoomEntity>> response) {
                List<RoomEntity> roomEntityList = response.body();

                if (roomEntityList != null) {
                    _manager._roomsManager.add(roomEntityList);
                    _userEntity.refreshRoomEntityList();
                    _view.setRoomEntityList(roomEntityList);
                }
            }

            @Override
            public void onFailure(Call<List<RoomEntity>> call, Throwable t) {

                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                    MyError.displayErrorNoConnexion(_view, null);
                    _view.localRefreshRooms();
                }
            }
        });
    }


    private void postRoomOnResponse(Response<RoomEntity> response, RoomEntity roomEntityFail) {
        if (response == null) {
            _manager._roomsManager.add(roomEntityFail);
            _view.setRoomEntity(roomEntityFail);
        }
        else {
            RoomEntity roomEntity = response.body();

            if (roomEntity != null) {
                _manager._roomsManager.add(roomEntity);
                _view.setRoomEntity(roomEntity);
            }
        }
        _view.hideDialogCreateRoomByUser();
    }

    public void postRoom(HashMap<String, String> params) {
        this._userEntity.refreshToken();

        params.put("owner", this._userEntity._profileEntity.get__id());

        // TODO envoiyer la liste des profiles present dans params

        final RoomEntity roomEntityFail = new RoomEntity(params, null);

        Call call = _api.postRooms(params);
        call.enqueue(new Callback<RoomEntity>() {
            @Override
            public void onResponse(Call<RoomEntity> call, Response<RoomEntity> response) {
                postRoomOnResponse(response, roomEntityFail);
            }

            @Override
            public void onFailure(Call<RoomEntity> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                    postRoomOnResponse(null, roomEntityFail);
                }
            }
        });
    }

    public void deleteRoom(final RoomEntity roomEntity) {
        this._userEntity.refreshToken();

        Call call = _api.deleteRooms(roomEntity.get__id());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                _manager._roomsManager.delete(roomEntity.get__id());
                _view.deleteRoomEntityList(roomEntity);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                    MyError.displayErrorNoConnexion(_view, null);
                    _view.deleteRoomEntityList(roomEntity);
                }
            }
        });
    }
}
