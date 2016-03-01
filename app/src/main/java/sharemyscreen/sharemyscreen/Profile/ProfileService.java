package sharemyscreen.sharemyscreen.Profile;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileService extends MyService {

    private final IProfileService _api;

    public interface IProfileService {
        @Headers("Content-Type: application/json")
        @GET("profile")
        Call<ProfileEntity> getProfile();

        @PUT("profile")
        Call<ProfileEntity> putProfile(@Body Map<String, String> params);
    }

    private final IProfileView _view;

    public ProfileService(IProfileView view, Context pContext) {
        super(pContext);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IProfileService.class, _tokenEntity, pContext);
    }

    public void getProfileOnResponse(Response<ProfileEntity> response) {
        ProfileEntity profileEntity = response.body();
        if (profileEntity != null) {
            if (_view != null) {
                _view.populateProfile(profileEntity);
            }

            long profile_id = _profileManager.add(profileEntity);
            if (profile_id != 0) {
                _tokenEntity.set_profile_id(profile_id);
                _tokenManager.modify(_tokenEntity);
            }
        }
    }

    public void getProfile() {
        Call call = _api.getProfile();
        call.enqueue(new Callback<ProfileEntity>() {
            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
                getProfileOnResponse(response);
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
            }
        });
    }

    public void putProfileOnResponse(Response<ProfileEntity> response, ProfileEntity profileEntityFail) {
        ProfileEntity profileEntity = response != null ? response.body() : null;

        if (profileEntity != null) {
            _profileManager.modifyProfil(profileEntity);
            _view.setProcessLoadingButton(100);
        }
        else {
            _profileManager.modifyProfil(profileEntityFail);
        }
        _view.finish();
    }

    public void saveProfile(HashMap<String, String> userParams) {
        userParams.put("username", _profileLogged.get_username());

        final ProfileEntity profileEntityFail = new ProfileEntity(userParams);
        Call call = _api.putProfile(userParams);
        call.enqueue(new Callback<ProfileEntity>() {

            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
                putProfileOnResponse(response, profileEntityFail);
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
                putProfileOnResponse(null, profileEntityFail);
            }
        });
    }
}
