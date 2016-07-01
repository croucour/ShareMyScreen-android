package sharemyscreen.sharemyscreen.SignUp;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;
import sharemyscreen.sharemyscreen.SignIn.SignInService;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignUpService extends MyService{

    private final ISignUpService _api;

    public interface ISignUpService {
        @Headers("Content-Type: application/json")
        @POST("users")
        Call<ProfileEntity> signUp(@Body Map <String, String> params);
    }

    private final SignInService _signInService;
    private final ISignUpView _view;

    public SignUpService(ISignUpView view, Manager manager) {
        super(manager);
        this._view = view;
        this._signInService = new SignInService(view, manager);
        this._api = ServiceGeneratorApi.createService(ISignUpService.class, "login", manager);
    }

    public void createUser(final HashMap<String, String> userParams) {
        Call call = _api.signUp(userParams);
        call.enqueue(new Callback<ProfileEntity>() {
            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
                ProfileEntity profileEntity = response.body();

                if (profileEntity != null) {
                    _signInService.signInAfterSignUp(userParams);
                }
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
                _view.setProcessLoadingButton(-1);
            }
        });
    }
}