package sharemyscreen.sharemyscreen.Organization;

import android.util.Log;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.IView;
import sharemyscreen.sharemyscreen.Menu.IMenuView;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationService extends MyService{

    private IOrganizationService _api;



    public interface IOrganizationService {
        @Headers("Content-Type: application/json")
        @POST("organizations")
        Call<OrganizationEntity> postOrganization(@Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @GET("organizations")
        Call<List<OrganizationEntity>> getOrganizations();

        @Headers("Content-Type: application/json")
        @DELETE("organizations/{organization_public_id}")
        Call<ResponseBody> deleteOrganization(@Path("organization_public_id") String organization_public_id);

        @Headers("Content-Type: application/json")
        @POST("organizations/{organization_public_id}")
        Call<String> postInvitationOrganization(@Path("organization_public_id") String organization_public_id, @Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @DELETE("organizations/{organization_public_id}")
        Call<String> deleteMemberOrganization(@Path("organization_public_id") String organization_public_id, @Body Map<String, String> params);



        @Headers("Content-Type: application/json")
        @GET("users/search/{partial_email}")
        Call<List<ProfileEntity>> getSearchUsers(@Path("partial_email") String partial_email);

    }

    private final IView _view;

    public OrganizationService(IView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IOrganizationService.class, "api", _userEntity._tokenEntity, manager);
    }

    public void postOrganization(HashMap<String, String> params) {
        this._userEntity.refreshToken();

        Call call = _api.postOrganization(params);
        call.enqueue(new Callback<OrganizationEntity>() {
            @Override
            public void onResponse(Call<OrganizationEntity> call, Response<OrganizationEntity> response) {
                postOrganizationOnResponse(response);
            }

            @Override
            public void onFailure(Call<OrganizationEntity> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });
    }

    private void postOrganizationOnResponse(Response<OrganizationEntity> response) {
        OrganizationEntity organizationEntity = response.body();

        if (organizationEntity != null) {

            _manager._organizationManager.add(organizationEntity);
            _view.changeOrganization(organizationEntity.get_public_id());

            _view.closeDialog("createOrganization");
        }
    }

    public void getSearchUsers(String partial_email) {
        this._userEntity.refreshToken();

        Call call = _api.getSearchUsers(partial_email);
        call.enqueue(new Callback<List<ProfileEntity>>() {
            @Override
            public void onResponse(Call<List<ProfileEntity>> call, Response<List<ProfileEntity>> response) {
                List<ProfileEntity> profileEntityList = response.body();
//                _view.setMembersOrganization(profileEntityList);
            }

            @Override
            public void onFailure(Call<List<ProfileEntity>> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });

//        ProfileEntity profileEntity = new ProfileEntity(0, "test2", "clement.roucour@gmail.com", "");
//        profileEntity.set_firstName("Clément");
//        profileEntity.set_lastName("Roucour");
//
//        List<ProfileEntity> profileEntityList = new ArrayList<>();
//        profileEntity.set_public_id("008");
//        profileEntityList.add(profileEntity);
//        profileEntityList.add(profileEntity);
//        profileEntityList.add(profileEntity);
//        profileEntityList.add(profileEntity);
//        profileEntityList.add(profileEntity);
//        profileEntityList.add(profileEntity);



    }


    public void getOrganizations() {
        _userEntity.refresh();
//
//        this._view.setRefreshing(false);

        Call call = _api.getOrganizations();
        call.enqueue(new Callback<List<OrganizationEntity>>() {
            @Override
            public void onResponse(Call<List<OrganizationEntity>> call, Response<List<OrganizationEntity>> response) {
                List<OrganizationEntity> organizationEntityList = response.body();

                if (organizationEntityList != null) {
                    _manager._organizationManager.add(organizationEntityList);
                    _view.updateOrganizationEntityList();
                }
            }

            @Override
            public void onFailure(Call<List<OrganizationEntity>> call, Throwable t) {

                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                    MyError.displayErrorNoConnexion(_view, null);
//                    _view.localRefreshRooms();
                }
            }
        });
    }

    public void deleteOrganization(final String organization_public_id) {
        _userEntity.refresh();

        Call call = _api.deleteOrganization(organization_public_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                _manager._organizationManager.delete(organization_public_id);
                if (_userEntity._organizationEntity != null && Objects.equals(organization_public_id, _userEntity._organizationEntity.get_public_id())) {
                    _manager._globalManager.deleteGlobal("organization_public_id_selected");
                }
                _view.selectOrganization();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
                }
            }
        });

    }

    public void postInvitationOrganization(HashMap<String, String> params, String organization_public_id) {
//        _userEntity.refresh();
//
//        Call<String> call = _api.postInvitationOrganization(organization_public_id, params);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                }
//            }
//        });
//        _view.closeDialog("invitation");
    }

    public void deleteMembersOrganization(HashMap<String, String> params, String organization_public_id) {
//        _view.closeDialog("members");
    }
}
