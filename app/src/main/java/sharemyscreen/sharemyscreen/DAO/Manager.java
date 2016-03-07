package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

/**
 * Created by cleme_000 on 04/03/2016.
 */
public class Manager{
    final public ProfileManager _profileManager;
    final public RequestOfflineManager _requestOfflineManager;
    final public RoomByProfileManager _roomByProfileManager;
    final public RoomsManager _roomsManager;
    final public GlobalManager _globalManager;
    final public TokenManager _tokenManager;
    final public SettingsManager _settingsManager;
    private final Context _pContext;


    public Manager(Context pContext) {
        this._pContext = pContext;
        this._tokenManager = new TokenManager(pContext);
        this._profileManager = new ProfileManager(pContext);
        this._requestOfflineManager = new RequestOfflineManager(pContext);
        this._roomByProfileManager = new RoomByProfileManager(pContext);
        this._roomsManager = new RoomsManager(pContext);
        this._globalManager = new GlobalManager(pContext);
        this._settingsManager = new SettingsManager(pContext);
    }

    public Context get_pContext() {
        return _pContext;
    }
}
