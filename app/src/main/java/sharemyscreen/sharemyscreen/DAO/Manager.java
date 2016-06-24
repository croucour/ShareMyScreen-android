package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

/**
 * Created by cleme_000 on 04/03/2016.
 */
public class Manager extends DAOBase{
    final public ProfileManager _profileManager;
    final public RequestOfflineManager _requestOfflineManager;
    final public RoomByProfileManager _roomByProfileManager;
    final public RoomsManager _roomsManager;
    final public GlobalManager _globalManager;
    final public TokenManager _tokenManager;
    final public SettingsManager _settingsManager;
    private final Context _pContext;
    public OrganizationManager _organizationManager;


    public Manager(Context pContext) {
        super(pContext);
        this._pContext = pContext;

        this._tokenManager = new TokenManager(this._mDb);
        this._profileManager = new ProfileManager(this._mDb);
        this._requestOfflineManager = new RequestOfflineManager(this._mDb);
        this._roomByProfileManager = new RoomByProfileManager(this._mDb);
        this._roomsManager = new RoomsManager(this._mDb);
        this._globalManager = new GlobalManager(this._mDb);
        this._settingsManager = new SettingsManager(this._mDb);
        this._organizationManager = new OrganizationManager(this._mDb, _profileManager);
    }

    public Context get_pContext() {
        return _pContext;
    }
}
