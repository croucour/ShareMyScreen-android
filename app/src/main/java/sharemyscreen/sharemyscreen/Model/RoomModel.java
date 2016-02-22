package sharemyscreen.sharemyscreen.Model;

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
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.MyApi;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 16/02/16.
 */
public class RoomModel {

    private MyApi myApi;
    private SettingsManager settingsManager;
    private Context _pContext = null;

    public RoomModel(Context pContext) {
        this.settingsManager = new SettingsManager(pContext);
        this._pContext = pContext;
    }

    public void getRooms(final Activity activity) {
        this.myApi = new MyApi(null, _pContext) {
            @Override
            protected void onPostExecute(String str) {

                SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefresh);
                swipeRefreshLayout.setRefreshing(false);

                if (!this.isErrorRequest()) {
                    // TODO : refresh la liste des room
                }

                else if (this.get_responseCode() == 0) {
                    Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.display_snackbar), R.string.connexionError, Snackbar.LENGTH_INDEFINITE);

                    snackbar.setCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            super.onDismissed(snackbar, event);
                            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
                            fab.animate().translationYBy((snackbar.getView().getHeight()));
                        }

                        @Override
                        public void onShown(Snackbar snackbar) {
                            super.onShown(snackbar);
                            FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
                            fab.animate().translationYBy(-(snackbar.getView().getHeight()));
                        }
                    });
                        snackbar.show();
                }
            }
        };
        this.myApi.setCurrentResquest("/rooms", "GET");
        this.myApi.execute();
    }
}
