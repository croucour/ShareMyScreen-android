package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileEntity {

    /**
     * Attribut de l'API
     */
    @SerializedName("_id")
    private String __id = null;
    @SerializedName("createdAt")
    private String _createdAt = null;
    @SerializedName("updatedAt")
    private String _updatedAt = null;
    @SerializedName("username")
    private String _username = null;
    @SerializedName("email")
    private String _email = null;
    @SerializedName("firstName")
    private String _firstName = null;
    @SerializedName("lastName")
    private String _lastName = null;
    @SerializedName("phone")
    private String _phone = null;
    @SerializedName("rooms")
    private List<String> _listRooms = null;

    private String _rooms = null;
    @SerializedName("tenant")
    private String _tenant = null;
    @SerializedName("role")
    private String _role = null;

    /**
     * Attribut locale
     */
    private transient long _id = 0;
    private String _password = null;



    public ProfileEntity(long id, String username, String email, String role) {
        this._id = id;
        this._username = username;
        this._email = email;
        this._role = role;
    }

    public ProfileEntity(Cursor c) {
        this._id = c.getInt(0);
        this.__id = c.getString(1);
        this._createdAt = c.getString(2);
        this._updatedAt = c.getString(3);
        this._username = c.getString(4);
        this._email = c.getString(5);
        this._firstName = c.getString(6);
        this._lastName= c.getString(7);
        this._phone = c.getString(8);
        this._rooms = c.getString(9);
        this._password = c.getString(10);
        this._role = c.getString(11);
    }


    public ProfileEntity(String username) {
        this._username = username;
    }

    public ProfileEntity(String username, String password) {
        this._username = username;
        this._password = password;
    }

    public ProfileEntity(HashMap<String, String> params) {
        this._firstName = params.get("firstName");
        this._lastName = params.get("lastName");
        this._username = params.get("username");
        this._password = params.get("password");
        this._email = params.get("email");
        this._phone = params.get("phone");
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long id) {
        this._id = id;
    }

    public String get__id() {
        return __id;
    }

    public void set__id(String _id) {
        this.__id = _id;
    }

    public String get_createdAt() {
        return _createdAt;
    }

    public void set_createdAt(String createdAt) {
        this._createdAt = createdAt;
    }

    public String get_updatedAt() {
        return _updatedAt;
    }

    public void set_updatedAt(String updatedAt) {
        this._updatedAt = updatedAt;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String username) {
        this._username = username;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String email) {
        this._email = email;
    }

    public String get_role() {
        return _role;
    }

    public void set_role(String role) {
        this._role = role;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String lastName) {
        this._lastName = lastName;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String firstName) {
        this._firstName = firstName;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String phone) {
        this._phone = phone;
    }

    public String get_rooms() {
        return _rooms;
    }

    public void set_rooms(String rooms) {
        this._rooms = rooms;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String password) {
        this._password = password;
    }

    @Override
    public String toString() {
        String  toString = "id : " + String.valueOf(this.get_id());
        toString += " _id : " + this.get__id();
        toString += " _createdAt : " + this.get_createdAt();
        toString += " _updatedAt : " + this.get_updatedAt();
        toString += " _username : " + this._username;
        toString += " _email : " + this._email;
        toString += " _firstName : " + this._firstName;
        toString += " _lastName : " + this._lastName;
        toString += " _phone : " + this._phone;
        toString += " _rooms : " + this._rooms;
        toString += " _role : " + this._role;
        toString += " _password : " + this._password;

        return toString;
    }

//    public void update(HashMap<String, String> mapProfile) {
//        if (mapProfile != null && !mapProfile.isEmpty()) {
//            String username = mapProfile.get("username");
//            if (username != null) {
//                this._username = username;
//            }
//            String firstName = mapProfile.get("firstName");
//            if (firstName != null) {
//                this._firstName = firstName;
//            }
//            String lastName = mapProfile.get("lastName");
//            if (lastName != null) {
//                this._lastName = lastName;
//            }
//            String phone = mapProfile.get("phone");
//            if (phone != null) {
//                this._phone = phone;
//            }
//            String email = mapProfile.get("email");
//            if (email != null) {
//                this._email = email;
//            }
//        }
//    }

    public List<String> get_listRooms() {
        return _listRooms;
    }

    public void set_listRooms(List<String> _listRooms) {
        this._listRooms = _listRooms;
    }

    public String get_tenant() {
        return _tenant;
    }

    public void set_tenant(String _tenant) {
        this._tenant = _tenant;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof ProfileEntity)) {
//            return false;
//        }
//
//        ProfileEntity profileEntity = (ProfileEntity) o;
//
//        return _id == profileEntity._id && __id.equals(profileEntity.__id) &&
//                _createdAt.equals(profileEntity._createdAt) &&
//                _updatedAt.equals(profileEntity._updatedAt) &&
//                _username.equals(profileEntity._username) && _email.equals(profileEntity._email) &&
//                _firstName.equals(profileEntity._firstName) && _lastName.equals(profileEntity._lastName) &&
//                _phone.equals(profileEntity._phone) && _rooms.equals(profileEntity._rooms) &&
//                _password.equals(profileEntity._password) && _role.equals(profileEntity._role);
//    }
}
