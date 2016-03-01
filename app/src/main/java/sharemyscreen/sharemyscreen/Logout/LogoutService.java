package sharemyscreen.sharemyscreen.Logout;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Menu.IMenuView;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 09/12/15.
 */
public class LogoutService extends MyService {

    private final ILogoutService _api;

    public interface ILogoutService {
        @Headers("Content-Type: application/json")
        @GET("logout")
        Call<String> logout();
    }

    private final IMenuView _view;

    public LogoutService(IMenuView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ILogoutService.class, _tokenEntity, pContext);
    }

    private void logoutOnPostExecute() {
        _tokenEntity.set_access_token(null);
        _tokenEntity.set_expire_access_token(null);
        _tokenEntity.set_refresh_token(null);
        _tokenManager.modify(_tokenEntity);

        SettingsManager settingsManager = new SettingsManager(_pContext);

        settingsManager.delete("current_token_id");

        _view.logout();
    }

    public void logout() {
        Call call = _api.logout();
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                logoutOnPostExecute();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                logoutOnPostExecute();
            }
        });

    }
}
