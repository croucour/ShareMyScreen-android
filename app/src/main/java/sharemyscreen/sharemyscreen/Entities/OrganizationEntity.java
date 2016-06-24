package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationEntity {
    /**
     * Attribut de l'API
     */
    @SerializedName("public_id")
    private String _public_id = null;
    @SerializedName("name")
    private String _name = null;
    @SerializedName("creator")
    private ProfileEntity _creator = null;
    @SerializedName("owner")
    private ProfileEntity _owner = null;
    @SerializedName("created_at")
    private String _created_at = null;
    @SerializedName("members")
    private List<ProfileEntity> _members = null;

    /**
     * Attribut locale
     */
    private transient long _id = 0;
    private String _creator_public_id = null;

    public String get_creator_public_id() {
        return _creator_public_id;
    }

    public void set_creator_public_id(String _creator_public_id) {
        this._creator_public_id = _creator_public_id;
    }

    public String get_owner_public_id() {
        return _owner_public_id;
    }

    public void set_owner_public_id(String _owner_public_id) {
        this._owner_public_id = _owner_public_id;
    }

    private String _owner_public_id = null;

    public OrganizationEntity(Cursor c) {
        this._id = c.getInt(0);
        this._public_id = c.getString(1);
        this._name = c.getString(2);
        this._creator_public_id = c.getString(3);
        this._owner_public_id = c.getString(4);
        this._created_at = c.getString(5);
    }

    public OrganizationEntity() {}

    public String get_public_id() {
        return _public_id;
    }

    public void set_public_id(String _public_id) {
        this._public_id = _public_id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public ProfileEntity get_creator() {
        return _creator;
    }

    public void set_creator(ProfileEntity _creator) {
        this._creator = _creator;
    }

    public ProfileEntity get_owner() {
        return _owner;
    }

    public void set_owner(ProfileEntity _owner) {
        this._owner = _owner;
    }

    public String get_created_at() {
        return _created_at;
    }

    public void set_created_at(String _created_at) {
        this._created_at = _created_at;
    }

    public List<ProfileEntity> get_members() {
        return _members;
    }

    public void set_members(List<ProfileEntity> _members) {
        this._members = _members;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
