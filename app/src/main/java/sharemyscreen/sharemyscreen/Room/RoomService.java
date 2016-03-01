package sharemyscreen.sharemyscreen.Room;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
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
    private final RoomsManager _roomsManager;

    private IRoomService _api;

    public RoomService(IRoomView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._roomsManager = new RoomsManager(pContext);
        this._api = ServiceGeneratorApi.createService(IRoomService.class, _tokenEntity, pContext);
    }


    public void getRooms() {
        this._view.setRefreshing(false);

        Call call = _api.getRooms();
        call.enqueue(new Callback<List<RoomEntity>>() {
            @Override
            public void onResponse(Call<List<RoomEntity>> call, Response<List<RoomEntity>> response) {
                List<RoomEntity> roomEntityList = response.body();

                if (roomEntityList == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            MyError.displayError(_view.getCoordinatorLayout(), "responseBody = " + responseBody.string(), null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MyError.displayError(_view.getCoordinatorLayout(), "responseBody = null", null);
                    }
                } else {
                    _roomsManager.add(roomEntityList);
                    _view.setRoomEntityList(roomEntityList);
                }
            }

            @Override
            public void onFailure(Call<List<RoomEntity>> call, Throwable t) {
                Log.d("code error", String.valueOf(t.getMessage()));

                MyError.displayError(_view.getCoordinatorLayout(), "error= ", null);
            }
        });
    }


    public void postRoom(HashMap<String, String> params) {

        this.updateProfileLogged();
        params.put("owner", _profileLogged.get__id());

        Call call = _api.postRooms(params);
        call.enqueue(new Callback<RoomEntity>() {
            @Override
            public void onResponse(Call<RoomEntity> call, Response<RoomEntity> response) {
                RoomEntity roomEntity = response.body();

                if (roomEntity != null) {
                    _roomsManager.add(roomEntity);
                    _view.setRoomEntity(roomEntity);
                    _view.hideDialogCreateRoomByUser();
                }
            }

            @Override
            public void onFailure(Call<RoomEntity> call, Throwable t) {

            }
        });


//        MyApi myApi = new MyApi(_profileLogged, _pContext) {
//            @Override
//            protected void onPostExecute(String str) {
//                addRoomsOnPostExecute(this);
//            }
//        };
//
//        myApi.setDataParams(params);
//        myApi.setCurrentRequest("/rooms", "POST");
//        myApi.execute();
    }

    public void deleteRoom(final RoomEntity roomEntity) {
        this.updateProfileLogged();

        Call call = _api.deleteRooms(roomEntity.get__id());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                _view.deleteRoomEntityList(roomEntity);
                _roomsManager.delete(roomEntity.get__id());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

//    private void addRoomsOnPostExecute(MyApi myApi) {
//        if (!myApi.isErrorRequest()) {
//            if (myApi.getResultJSON() != null) {
//                // TODO : refresh la liste des room
//
//            }
//        }
//        else {
//            Snackbar snackbar = MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), null);
//            this._view.setCallbackSnackbar(snackbar);
//        }
//    }
}
