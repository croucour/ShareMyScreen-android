package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

import sharemyscreen.sharemyscreen.Entities.Profile;
import sharemyscreen.sharemyscreen.Model.ProfileModel;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileManager {

    private ProfileDAO _profileDAO;

    public ProfileManager(Context pContext) {

        this._profileDAO = new ProfileDAO(pContext);
        this._profileDAO.open();
    }

    public void addProfil(String username, String email, String role) {

        Profile profile = new Profile(0, username, email, role);
        this._profileDAO.add(profile);
    }
}
