package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

/**
 * Created by cleme_000 on 04/03/2016.
 */
public class SettingsEntity {

    private long _id;
    private String _profile_public_id;
    private boolean _offline;
    private boolean _displayOffline;

    public SettingsEntity(Cursor c) {
        this._id = c.getLong(0);
        this._profile_public_id = c.getString(1);
        this._offline = c.getInt(2) == 1;
        this._displayOffline = c.getLong(3) == 1;
    }

    public SettingsEntity() {
        this._offline = true;
        this._displayOffline = true;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_profile_public_id() {
        return _profile_public_id;
    }

    public void set_profile_public_id(String _profile_public_id) {
        this._profile_public_id = _profile_public_id;
    }

    public boolean is_offline() {
        return _offline;
    }

    public void set_offline(boolean _offline) {
        this._offline = _offline;
    }

    public boolean is_displayOffline() {
        return _displayOffline;
    }

    public void set_displayOffline(boolean _displayOffline) {
        this._displayOffline = _displayOffline;
    }
}
