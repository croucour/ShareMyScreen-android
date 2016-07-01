package sharemyscreen.sharemyscreen.Profile;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileService extends MyService {

    private IProfileService _api;

    public interface IProfileService {
        @Headers("Content-Type: application/json")
        @GET("users/me")
        Call<ProfileEntity> getProfile();

        @PATCH("users/me")
        Call<ProfileEntity> patchProfile(@Body Map<String, String> params);

        @DELETE("users/me")
        Call<ProfileEntity> deleteProfile();

        @GET("users/search/{partial_email}")
        Call<ProfileEntity> searchProfile(@Path("partial_email") String partial_email);
    }

    private final IProfileView _view;

    public ProfileService(IProfileView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IProfileService.class, "api", _userEntity._tokenEntity, manager);
    }

    public void getProfileOnResponse(ProfileEntity profileEntity) {
        if (profileEntity != null) {

            if (_view != null) {
                _view.populateProfile(profileEntity);
            }

            this._userEntity.addProfile(profileEntity);
        }
    }

    public void getProfile(final String profilePassword) {
        this._userEntity.refresh();
        Call call = _api.getProfile();
        call.enqueue(new Callback<ProfileEntity>() {
            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
                ProfileEntity profileEntity = response.body();

                getProfileOnResponse(profileEntity);
//                if (profilePassword != null) {
//                    _userEntity._tokenEntity.set_profile_public_id(profileEntity.get_public_id());
//                    _userEntity.update_tokenEntity();
//                    _userEntity._profileEntity.set_password(profilePassword);
//                    _userEntity.update_profileEntity();
//                }
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
            }
        });
    }

    public void patchProfileOnResponse(Response<ProfileEntity> response, ProfileEntity profileEntityFail) {
        ProfileEntity profileEntity = response != null ? response.body() : null;

        if (profileEntity != null) {
            _manager._profileManager.modifyProfil(profileEntity);
            _view.setProcessLoadingButton(100);
        }
        else {
            _manager._profileManager.modifyProfil(profileEntityFail);
        }
        _view.finish();
    }

    public void patchProfile(HashMap<String, String> userParams) {

        final ProfileEntity profileEntityFail = new ProfileEntity(userParams);
        Call call = _api.patchProfile(userParams);
        call.enqueue(new Callback<ProfileEntity>() {

            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
                patchProfileOnResponse(response, profileEntityFail);
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
                patchProfileOnResponse(null, profileEntityFail);
            }
        });
    }

    public void deleteProfile() {
        Call call = _api.deleteProfile();
        call.enqueue(new Callback<ProfileEntity>() {

            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
            }
        });
    }

    public void searchProfile(String partial_email) {
        Call call = _api.searchProfile(partial_email);
        call.enqueue(new Callback<ProfileEntity>() {

            @Override
            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
            }

            @Override
            public void onFailure(Call<ProfileEntity> call, Throwable t) {
            }
        });
    }
}

