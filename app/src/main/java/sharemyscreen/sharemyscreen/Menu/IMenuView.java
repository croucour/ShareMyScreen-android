package sharemyscreen.sharemyscreen.Menu;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IMenuView {
//    void startRoomActivity();

    void startOrganizationActivity();

//    void startProfileActivity();
//
//    void startSettingsActivity();
//
//    void startLogOfflineActivity();
//
//    void logout();
//
//    void startSignInActivity();

    void updateOrganizationEntityList();

    String getNameOrganization();

    void setErrorNameOrganization(int resId);

    void closeDialog(String target);

    void selectOrganization();
}
