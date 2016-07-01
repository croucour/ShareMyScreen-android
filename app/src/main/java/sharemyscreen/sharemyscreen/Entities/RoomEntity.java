package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomEntity {
    private long id;

    @Expose
    @SerializedName("public_id")
    private String _public_id;
    @SerializedName("name")
    private String _name;
    @SerializedName("created_at")
    private String _created_at;
    @SerializedName("owner")
    private String _owner;
    @SerializedName("members")
    private List<ProfileEntity> _members;
    @SerializedName("private")
    private boolean _private;

    public RoomEntity(long id, String name) {
        this.id = id;
        this._name = name;
    }

    public RoomEntity(Cursor c) {
        this.id = c.getLong(0);
        this._public_id = c.getString(1);
        this._name = c.getString(2);
        this._created_at = c.getString(3);
        this._owner = c.getString(4);
        this._private = c.getInt(5) == 1;
    }

    public RoomEntity(HashMap<String, String> params, List<ProfileEntity> members) {
        this._name = params.get("name");
        this._owner = params.get("owner");
        this._members = members;
    }

    public String get_public_id() {
        return _public_id;
    }

    public void set_public_id(String _public_id) {
        this._public_id = _public_id;
    }

    public String get_created_at() {
        return _created_at;
    }

    public void set_created_at(String _created_at) {
        this._created_at = _created_at;
    }

    public String get_owner() {
        return _owner;
    }

    public void set_owner(String _owner) {
        this._owner = _owner;
    }

    public List<ProfileEntity> get_members() {
        return _members;
    }

    public void set_members(List<ProfileEntity> _members) {
        this._members = _members;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public boolean is_private() {
        return _private;
    }

    public void set_private(boolean _private) {
        this._private = _private;
    }
}
