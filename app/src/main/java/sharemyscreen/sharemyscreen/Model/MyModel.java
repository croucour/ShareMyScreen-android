package sharemyscreen.sharemyscreen.Model;

import android.content.Context;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by cleme_000 on 22/02/2016.
 */
public class MyModel {
    protected ProfileManager _profileManager;
    protected ProfileEntity _profileLogged = null;
    protected Context _pContext = null;
    protected MyApi _myApi;


    public MyModel(Context pContext) {
        this._pContext = pContext;
        this._profileManager = new ProfileManager(pContext);

        _profileLogged = _profileManager.get_profileDAO().selectProfileLogged();

    }

    public MyModel(RequestOfflineEntity requestOfflineEntity, Context pContext) {
        this._pContext = pContext;
        this._profileManager = new ProfileManager(pContext);
        if (requestOfflineEntity != null) {
            _profileLogged = _profileManager.selectById(requestOfflineEntity.get_profile_id());
        }
    }

    public void set_profileLogged(ProfileEntity _profileLogged) {
        this._profileLogged = _profileLogged;
    }
}
