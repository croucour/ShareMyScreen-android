package sharemyscreen.sharemyscreen.Model;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import org.json.JSONException;

import java.util.HashMap;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.ProfileActivity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 16/02/16.
 */
public class RoomModel {

    private Context _contextApplication;
    private MyApi myApi;
    private ProfileManager _profileManager;
    private SettingsManager settingsManager;

    public RoomModel(Context contextApplication) {
//        this._profileManager = new ProfileManager(contextApplication);
        this.settingsManager = new SettingsManager(contextApplication);
        this._contextApplication = contextApplication;

    }

    public void getRooms(final Activity activity) {
        this.myApi = new MyApi(this.settingsManager) {
            @Override
            protected void onPostExecute(String str) {

                WaveSwipeRefreshLayout mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) activity.findViewById(R.id.main_swipe);
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        };
        this.myApi.setCurrentResquest("/rooms", "GET");
        this.myApi.execute();
    }
}
