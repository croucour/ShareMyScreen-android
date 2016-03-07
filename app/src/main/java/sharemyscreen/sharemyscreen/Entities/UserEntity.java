package sharemyscreen.sharemyscreen.Entities;

import java.util.List;

import sharemyscreen.sharemyscreen.DAO.Manager;

/**
 * Created by cleme_000 on 04/03/2016.
 */
public class UserEntity {
    public ProfileEntity _profileEntity = null;
    public TokenEntity _tokenEntity = null;
    public SettingsEntity _settingsEntity = null;
    public List<RoomEntity> _roomEntityList = null;
    private Manager _manager;

    public UserEntity(Manager _manager) {
        this._manager = _manager;
        this.refresh();
    }

    public void refresh(){
        if (refreshToken()) {
            if (_tokenEntity != null) {
                long profile_id = _tokenEntity.get_profile_id();
                if (profile_id != 0) {
                    _profileEntity = _manager._profileManager.selectById(profile_id);
                    _settingsEntity = _manager._settingsManager.selectByProfileId(profile_id);
                    _roomEntityList = _manager._roomsManager.selectAllByProfile_id(_profileEntity.get__id());
                }
            }
        }
    }

    public void addProfile(ProfileEntity profileEntity) {
        long profile_id = _profileEntity == null ? _manager._profileManager.add(profileEntity) : _profileEntity.get_id();
        this._tokenEntity.set_profile_id(profile_id);


        this.update_tokenEntity();

        this.refresh();

        this.initAddProfile();

    }

    private void initAddProfile() {
        if (_settingsEntity == null) {
            SettingsEntity settingsEntity = new SettingsEntity();
            settingsEntity.set_profile_id(_profileEntity.get_id());
            _manager._settingsManager.add(settingsEntity);
            _settingsEntity = settingsEntity;
        }
    }

    public void set_tokenEntity(TokenEntity tokenEntity) {
        this._tokenEntity = tokenEntity;
        this.update_tokenEntity();
    }

    public void update_tokenEntity() {
        this._manager._tokenManager.modify(_tokenEntity);
    }

    public void set_profileEntity(ProfileEntity profileEntity) {
        this._profileEntity = profileEntity;
        this.update_profileEntity();
    }

    public void update_profileEntity() {
        this._manager._profileManager.modifyProfil(_profileEntity);
    }

    public void refresh(String username) {

        ProfileEntity profileEntity = _manager._profileManager.selectByUsername(username);

        if (profileEntity != null) {
            _profileEntity = profileEntity;
            TokenEntity tokenEntity = _manager._tokenManager.selectByProfileId(profileEntity.get_id());
            if (tokenEntity != null) {
                this._tokenEntity = tokenEntity;
                _manager._globalManager.addGlobal("current_token_id", String.valueOf(tokenEntity.get_id()));
            }
        }
    }

    public boolean refreshToken() {
        String current_token_id = _manager._globalManager.select("current_token_id");

        if (current_token_id != null) {
            _tokenEntity = _manager._tokenManager.selectById(current_token_id);
            if (_tokenEntity != null) {
                return true;
            }
        }
        return false;
    }

    public void logout() {
        if (_tokenEntity != null) {
            _tokenEntity.set_access_token(null);
            _tokenEntity.set_expire_access_token(null);
            _tokenEntity.set_refresh_token(null);
            this.update_tokenEntity();
        }

        _manager._globalManager.deleteGlobal("current_token_id");
    }

    public void addTokenEntity(TokenEntity tokenEntity) {
        if (tokenEntity != null) {
            long newToken_id = _manager._tokenManager.add(tokenEntity);
            _manager._globalManager.addGlobal("current_token_id", String.valueOf(newToken_id));
            tokenEntity.set_id(newToken_id);
            this._tokenEntity = tokenEntity;
        }
    }

    public void update_settingsEntity() {
        this._manager._settingsManager.modify(_settingsEntity);
    }

    public void refreshRoomEntityList() {
        if (_profileEntity != null) {
            _roomEntityList = _manager._roomsManager.selectAllByProfile_id(_profileEntity.get__id());
        }
    }
}
