package sharemyscreen.sharemyscreen.Organization;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.IView;
import sharemyscreen.sharemyscreen.Menu.IMenuView;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationPresenter {
    private final IView _view;
    private final OrganizationService _organizationService;
    private final Manager _manager;

    public OrganizationPresenter(IView view, Manager manager, UserEntity userEntity) {
        this._manager = manager;
        this._view = view;
        this._organizationService = new OrganizationService(view, manager, userEntity);
    }

    public void onCreateClicked() {
        boolean error = false;
        String name = _view.getNameOrganization();

        if (name.isEmpty()) {
            _view.setErrorNameOrganization(R.string.createOrganization_nameEmpty);
            error = true;
        }

        if (!error) {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);

            _organizationService.postOrganization(params);
        }
    }

    public void onInvitationClicked(String organization_public_id, String profile_public_id) {

//        if (organization_public_id != null && profile_public_id != null) {
//            HashMap<String, String> params = new HashMap<>();
//            params.put("user_id", profile_public_id);
//
//            _organizationService.postInvitationOrganization(params, organization_public_id);
//        }
    }

    public void onKickMemberClicked(String organization_public_id, String profile_public_id) {
//        if (organization_public_id != null && profile_public_id != null) {
//            HashMap<String, String> params = new HashMap<>();
//            params.put("user_id", profile_public_id);
//
//            _organizationService.deleteMembersOrganization(params, organization_public_id);
//        }
    }

    public void getOrganizations() {
        this._organizationService.getOrganizations();
    }

    public void onSwipedForRefreshOrganizations() {
        this._organizationService.getOrganizations();
    }

    public void deleteOrganization(String organization_public_id) {
        _organizationService.deleteOrganization(organization_public_id);
    }
}
