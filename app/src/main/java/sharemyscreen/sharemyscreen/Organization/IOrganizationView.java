package sharemyscreen.sharemyscreen.Organization;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou_c on 20/06/2016.
 */
interface IOrganizationView {
    String getName();

    void setErrorName(int resId);

    String  getSearch();

    void setErrorSearch(int resId);

    void setRefreshing(boolean b);

    void showDialog(String action, String organization_public_id);

    void setMembersOrganization(List<ProfileEntity> profileEntityList);

    void buttonDialogClicked(String action, String profile_public_id);

    void closeDialog(String target);

    void updateOrganizationEntityList();
}
