package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileManager {

    public ProfileDAO get_profileDAO() {
        return _profileDAO;
    }

    private ProfileDAO _profileDAO;

    public ProfileManager(Context pContext) {

        this._profileDAO = new ProfileDAO(pContext);
        this._profileDAO.open();
    }

    public void modifyProfil(ProfileEntity profile) {
        this._profileDAO.modify(profile);
    }

    public ProfileEntity modifyProfil(String username, String password) {

        ProfileEntity profile = this._profileDAO.selectByUsername(username);

        if (profile == null) {
            long id = this._profileDAO.add(new ProfileEntity(username, password));
            return this._profileDAO.selectById(id);
        }
        else {
            profile.set_password(password);
            this._profileDAO.modify(profile);
            return profile;
        }
    }

    public void modifyProfil(HashMap<String, String> mapProfile) {
        String username = mapProfile.get("username");
        if (username != null) {
            ProfileEntity profile = this._profileDAO.selectByUsername(username);
            if (profile != null) {
                profile.update(mapProfile);
                this._profileDAO.modify(profile);
            }
        }

    }

    public ProfileEntity selectByUsername(String username) {
        return _profileDAO.selectByUsername(username);
    }

    public ProfileEntity selectByUsernameAndPassword(String username, String password) {
        return _profileDAO.selectByUsernameAndPassword(username, password);
    }


    public ProfileEntity selectByRefreshToken(String refresh_token) {
        return _profileDAO.selectByRefreshToken(refresh_token);
    }

    public ProfileEntity selectById(long profile_id) {
        return this._profileDAO.selectById(profile_id);
    }
}
