package sharemyscreen.sharemyscreen.Organization;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.MyError;
import sharemyscreen.sharemyscreen.MyService;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationService extends MyService{

    private IOrganizationService _api;

    public interface IOrganizationService {
        @Headers("Content-Type: application/json")
        @POST("organization")
        Call<OrganizationEntity> postCreateOrganization(@Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @POST("organization/{organization_public_id}")
        Call<String> postInvitationOrganization(@Path("organization_public_id") String organization_public_id, @Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @GET("organization")
        Call<List<OrganizationEntity>> getOrganizations();

        @Headers("Content-Type: application/json")
        @GET("users/search")
        Call<ProfileEntity> getSearchUsers(@Body Map<String, String> params);

    }

    private final IOrganizationView _view;

    public OrganizationService(IOrganizationView view, Manager manager, UserEntity userEntity) {
        super(manager, userEntity);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IOrganizationService.class, manager);
    }

    public void postCreateOrganization(HashMap<String, String> params) {
//        this._userEntity.refreshToken();
//
//
//        Call call = _api.postCreateOrganization(params);
//        call.enqueue(new Callback<OrganizationEntity>() {
//            @Override
//            public void onResponse(Call<OrganizationEntity> call, Response<OrganizationEntity> response) {
//                postCreateOrganizationOnResponse(response);
//            }
//
//            @Override
//            public void onFailure(Call<OrganizationEntity> call, Throwable t) {
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                }
//            }
//        });
        _view.showInvitation();

    }

    private void postCreateOrganizationOnResponse(Response<OrganizationEntity> response) {
//        OrganizationEntity organizationEntity = response.body();
//
//        if (organizationEntity != null) {
//            _manager._organizationManager.add(organizationEntity);
//
//            _view.showInvitation();
//        }
    }

    public void getSearchUsers(HashMap<String, String> params) {
//        this._userEntity.refreshToken();
//
//        Call call = _api.getSearchUsers(params);
//        call.enqueue(new Callback<ProfileEntity>() {
//            @Override
//            public void onResponse(Call<ProfileEntity> call, Response<ProfileEntity> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ProfileEntity> call, Throwable t) {
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                }
//            }
//        });

        ProfileEntity profileEntity = new ProfileEntity(0, "test2", "clement.roucour@gmail.com", "");
        profileEntity.set__id("008");
        profileEntity.set_firstName("Clément");
        profileEntity.set_lastName("Roucour");

        List<ProfileEntity> profileEntityList = new ArrayList<>();
        profileEntityList.add(profileEntity);
        profileEntityList.add(profileEntity);
        profileEntityList.add(profileEntity);
        profileEntityList.add(profileEntity);
        profileEntityList.add(profileEntity);
        profileEntityList.add(profileEntity);



        _view.setMembersOrganization(profileEntityList);
    }


    public void getOrganizations() {
//        _userEntity.refresh();
//
//        this._view.setRefreshing(false);
//
//        Call call = _api.getOrganizations();
//        call.enqueue(new Callback<List<OrganizationEntity>>() {
//            @Override
//            public void onResponse(Call<List<OrganizationEntity>> call, Response<List<OrganizationEntity>> response) {
//                List<OrganizationEntity> organizationEntityList = response.body();
//
//                if (organizationEntityList != null) {
//                    _manager._organizationManager.add(organizationEntityList);
//                    _userEntity.refreshOrganizationEntityList();
//                    _view.setOrganizationEntityList(organizationEntityList);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<OrganizationEntity>> call, Throwable t) {
//
//                if (t instanceof UnknownHostException && _userEntity._settingsEntity.is_displayOffline()){
//                    MyError.displayErrorNoConnexion(_view, null);
////                    _view.localRefreshRooms();
//                }
//            }
//        });
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
        _view.closeDialog("invitation");
    }
}
