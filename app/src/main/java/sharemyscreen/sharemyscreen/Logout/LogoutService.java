package sharemyscreen.sharemyscreen.Logout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
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
        Call<Void> logout();
    }

    private final IMenuView _view;

    public LogoutService(IMenuView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ILogoutService.class, _userEntity._tokenEntity, manager);
    }

    private void logoutOnPostExecute() {
        _userEntity.logout();

        _view.startSignInActivity();
    }

    public void logout() {
        Call call = _api.logout();
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                logoutOnPostExecute();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                logoutOnPostExecute();
            }
        });

    }
}
