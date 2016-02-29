package sharemyscreen.sharemyscreen;

import android.content.Context;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class MyService {
    protected final Context _pContext;
    protected ProfileEntity _profileLogged;
    protected final ProfileManager _profileManager;

    public MyService(Context pContext) {
        this._pContext = pContext;
        _profileManager = new ProfileManager(pContext);
        _profileLogged = _profileManager.selectProfileLogged();
    }

    public MyService(RequestOfflineEntity requestOfflineEntity, Context pContext) {
        this._pContext = pContext;
        this._profileManager = new ProfileManager(pContext);

        if (requestOfflineEntity != null) {
            _profileLogged = _profileManager.selectById(requestOfflineEntity.get_profile_id());
        }
        else {
            _profileLogged = null;
        }
    }

    public void set_profileLogged(ProfileEntity profileLogged) {
        this._profileLogged = profileLogged;
    }

    public ProfileEntity get_profileLogged() {
        return _profileLogged;
    }

    public ProfileManager get_profileManager() {
        return _profileManager;
    }

    public void updateProfileLogged() {
        _profileLogged = _profileManager.selectProfileLogged();
    }
}
