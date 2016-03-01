package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomEntity {
    private long id;
//    "_id": "56d47513ba979d11002b863c",
//            "createdAt": "2016-02-29T16:42:59.413Z",
//            "updatedAt": "2016-02-29T16:42:59.413Z",
//            "name": "ff",
//            "owner": "56c5d61c3311d8110085e29e",
//            "__v": 0,
//            "members":
    @Expose
    @SerializedName("_id")
    private String __id;
    @SerializedName("name")
    private String _name;
    @SerializedName("createdAt")
    private String _createdAt;
    @SerializedName("updatedAt")
    private String _updatedAt;
    @SerializedName("owner")
    private String _owner;
    @SerializedName("members")
    private List<ProfileEntity> _members;


    public RoomEntity(long id, String name) {
        this.id = id;
        this._name = name;
    }

    public RoomEntity(Cursor c) {
        this.id = c.getLong(0);
        this._name = c.getString(1);
        this._createdAt = c.getString(2);
        this._updatedAt = c.getString(3);
        this._owner = c.getString(4);
    }

    public String get__id() {
        return __id;
    }

    public void set__id(String __id) {
        this.__id = __id;
    }

    public String get_createdAt() {
        return _createdAt;
    }

    public void set_createdAt(String _createdAt) {
        this._createdAt = _createdAt;
    }

    public String get_updatedAt() {
        return _updatedAt;
    }

    public void set_updatedAt(String _updatedAt) {
        this._updatedAt = _updatedAt;
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

    //    public String get_id() {
//        return _id;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }

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
}
