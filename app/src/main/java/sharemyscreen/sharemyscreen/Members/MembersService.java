package sharemyscreen.sharemyscreen.Members;

import android.util.Log;

import java.net.UnknownHostException;
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
import retrofit2.http.POST;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou-c on 16/02/16.
 */
public class MembersService extends MyService{


    public interface IRoomService {
        @Headers("Content-Type: application/json")
        @GET("users/search/{partial_email}")
        Call<List<ProfileEntity>> getSearchUsers(@Path("partial_email") String partial_email);

        @Headers("Content-Type: application/json")
        @POST("organizations/{organization_public_id}/members")
        Call<String> postInvitationOrganization(@Path("organization_public_id") String organization_public_id, @Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @GET("organizations/{organization_public_id}/members")
        Call<List<ProfileEntity>> getOrganizationMembers(@Path("organization_public_id") String organization_public_id);

        @Headers("Content-Type: application/json")
        @DELETE("organizations/{organization_public_id}/members")
        Call<String> deleteOrganizationMembers(@Path("organization_public_id") String organization_public_id, @Body Map<String, String> params);
    }

    private final IMembersView _view;

    private IRoomService _api;

    public MembersService(IMembersView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IRoomService.class, "api", _userEntity._tokenEntity, _manager);
    }

    public void getSearchUsers(String partial_email) {
        this._userEntity.refreshToken();

        Call call = _api.getSearchUsers(partial_email);
        call.enqueue(new Callback<List<ProfileEntity>>() {
            @Override
            public void onResponse(Call<List<ProfileEntity>> call, Response<List<ProfileEntity>> response) {
                List<ProfileEntity> profileEntityList = response.body();
                _view.setMembersOrganization(profileEntityList);
            }

            @Override
            public void onFailure(Call<List<ProfileEntity>> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });
    }

    public void postInvitationOrganization(HashMap<String, String> params, String organization_public_id) {
        _userEntity.refresh();

        Call<String> call = _api.postInvitationOrganization(organization_public_id, params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });
        _view.restartSearch();
    }

    public void deleteMembersOrganization(HashMap<String, String> params, String organization_public_id) {
        _userEntity.refresh();

        Call<String> call = _api.deleteOrganizationMembers(organization_public_id, params);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });
        _view.restartSearch();
    }

    public void getMembersOrganization(String organization_public_id) {
        this._userEntity.refreshToken();

        Call call = _api.getOrganizationMembers(organization_public_id);
        call.enqueue(new Callback<List<ProfileEntity>>() {
            @Override
            public void onResponse(Call<List<ProfileEntity>> call, Response<List<ProfileEntity>> response) {
                List<ProfileEntity> profileEntityList = response.body();
                _view.setMembersOrganization(profileEntityList);
            }

            @Override
            public void onFailure(Call<List<ProfileEntity>> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });
    }

}
