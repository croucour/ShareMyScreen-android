package sharemyscreen.sharemyscreen;

import android.content.Context;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.DAO.TokenManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class MyService {
    protected final Context _pContext;
    protected SettingsManager _settingsManager;
    protected TokenEntity _tokenEntity = null;
    protected ProfileEntity _profileLogged;
    protected final ProfileManager _profileManager;
    protected final TokenManager _tokenManager;

    public MyService(Context pContext) {
        this._pContext = pContext;
        _profileManager = new ProfileManager(pContext);
        this._tokenManager = new TokenManager(pContext);

        _settingsManager = new SettingsManager(_pContext);
        String token_id = _settingsManager.select("current_token_id");
        if (token_id != null) {
            this.setCurrentTokenEntity(Long.parseLong(token_id));
        }
    }

//    public MyService(RequestOfflineEntity requestOfflineEntity, Context pContext) {
//        this._pContext = pContext;
//        this._profileManager = new ProfileManager(pContext);
//        this._tokenManager = new TokenManager(pContext);
//
//        this.setCurrentTokenEntity(requestOfflineEntity.get_token_id());
//    }

    private void setCurrentTokenEntity(long token_id) {
        if (token_id != 0) {
            _tokenEntity = _tokenManager.selectById(token_id);
            _profileLogged = _profileManager.selectById(_tokenEntity.get_profile_id());
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
        String token_id = _settingsManager.select("current_token_id");
        if (token_id != null) {
            this.setCurrentTokenEntity(Long.parseLong(token_id));
        }
        if (token_id != null) {
            setCurrentTokenEntity(Long.parseLong(token_id));
        }
    }
}
