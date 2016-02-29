package sharemyscreen.sharemyscreen.Room;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import sharemyscreen.sharemyscreen.ApiService;
import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.Entities.Profile;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.Room;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 16/02/16.
 */
public class RoomService extends MyService{

    public interface IRoomService {
        @Headers("Content-Type: application/json")
        @GET("v1/rooms")
        Call<List<Room>> getRooms();
    }

    private final IRoomView _view;
    private final RoomsManager _roomsManager;

    private IRoomService _api;

    public RoomService(IRoomView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._roomsManager = new RoomsManager(pContext);
        this._api = ServiceGeneratorApi.createService(IRoomService.class, _profileLogged);
    }


    public void getRooms() {
        Call call = _api.getRooms();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                List<Room> model = response.body();

                Log.d("code error", String.valueOf(response.code()));
//                Log.d("code error", response.headers().toString());
//                Log.d("code error", String.valueOf(response.));


                if (model == null) {
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
                    for (Room room : model) {
                        Log.d("room id = ", room.get__id());
                        Log.d("room name = ", room.get_name());
                        Log.d("room createdAt = ", room.get_createdAt());
                        Log.d("room updatedAt = ", room.get_updatedAt());
                        Log.d("room owner = ", room.get_owner());
                        List<ProfileEntity> members = room.get_members();
                        for (ProfileEntity user : members) {
                            Log.d("user id = ", user.get__id());

                        }

                    }

                    Log.d("api result = ", "ok");
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.d("code error", String.valueOf(t.getMessage()));

                MyError.displayError(_view.getCoordinatorLayout(), "error= ", null);
            }
        });






//        MyApi myApi = new MyApi(_profileLogged, _pContext) {
//            @Override
//            protected void onPostExecute(String str) {
//                getRoomsOnPostExecute(this);
//            }
//        };
//        myApi.setCurrentRequest("/rooms", "GET");
//        myApi.execute();
    }

    private void getRoomsOnPostExecute(MyApi myApi) {
        this._view.setRefreshing(false);

        if (!myApi.isErrorRequest()) {
            if (myApi.getResultJSON() != null) {
                // TODO : refresh la liste des room
                JSONObject result = myApi.getResultJSON();

                String s = result.toString();

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; jsonArray.length() != i; ++i) {
                        Log.d("jsonArray", String.valueOf(jsonArray.get(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Snackbar snackbar = MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), null);
            this._view.setCallbackSnackbar(snackbar);
        }
    }

    public void addRoom(HashMap<String, String> params) {
        MyApi myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                addRoomsOnPostExecute(this);
            }
        };

        myApi.setDataParams(params);
        myApi.setCurrentRequest("/rooms", "POST");
        myApi.execute();
    }

    private void addRoomsOnPostExecute(MyApi myApi) {
        if (!myApi.isErrorRequest()) {
            if (myApi.getResultJSON() != null) {
                // TODO : refresh la liste des room

            }
        }
        else {
            Snackbar snackbar = MyError.displayErrorApi(myApi, _view.getCoordinatorLayout(), null);
            this._view.setCallbackSnackbar(snackbar);
        }
    }
}
