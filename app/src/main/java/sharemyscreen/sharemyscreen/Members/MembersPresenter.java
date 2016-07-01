package sharemyscreen.sharemyscreen.Members;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class MembersPresenter {

    private final IMembersView _view;
    private final MembersService _membersService;
    private final Manager _manager;

    public MembersPresenter(IMembersView _view, Manager manager, UserEntity userEntity) {
        this._manager = manager;
        this._view = _view;
        this._membersService = new MembersService(_view, manager, userEntity);
    }

    public void searchUser() {
        String search = _view.getSearch();

        if (search.isEmpty()) {
            _view.setErrorSearch(R.string.invitOrganization_searchEmpty);
            _view.setMembersOrganization(null);
        }
        else {
            _membersService.getSearchUsers(search);
        }
    }

    public void onInvitationClicked(String organization_public_id, String profile_public_id) {
        if (organization_public_id != null && profile_public_id != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", profile_public_id);

            _membersService.postInvitationOrganization(params, organization_public_id);
        }

    }

    public void onKickMemberClicked(String organization_public_id, String profile_public_id) {
        if (organization_public_id != null && profile_public_id != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", profile_public_id);

            _membersService.deleteMembersOrganization(params, organization_public_id);
        }
    }

    public void getMembersOrginzation(String organization_public_id) {
        _membersService.getMembersOrganization(organization_public_id);
    }
}
