package sharemyscreen.sharemyscreen.Room;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.EditText;

import com.melnykov.fab.FloatingActionButton;

import org.json.JSONException;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 16/02/16.
 */
public class RoomService extends MyService{

    private final IRoomView _view;
    private final RoomsManager _roomsManager;

    public RoomService(IRoomView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._roomsManager = new RoomsManager(pContext);
    }


    public void getRooms() {
        MyApi myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                getRoomsOnPostExecute(this);
            }
        };
        myApi.setCurrentResquest("/rooms", "GET");
        myApi.execute();
    }

    private void getRoomsOnPostExecute(MyApi myApi) {
        this._view.setRefreshing(false);

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
