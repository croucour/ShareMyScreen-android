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
    @SerializedName("firsName")
    private String _firstName = null;
    @SerializedName("lastName")
    private String _lastName = null;
    @SerializedName("phone")
    private String _phone = null;
    @SerializedName("rooms")
    private List<String> _listTooms = null;

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
    private boolean _logged = false;
    private String _access_token = null;
    private String _refresh_token = null;
    private String _expireAccess_token = null;
    private String _expireRefresh_token = null;



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
        this._logged = c.getInt(12) == 1;
        this._access_token = c.getString(13);
        this._refresh_token = c.getString(14);
        this._expireAccess_token = c.getString(15);
        this._expireRefresh_token = c.getString(16);
    }

    public ProfileEntity(JSONObject resultJSON) {
        if (resultJSON != null) {
            try {
                this.__id = resultJSON.isNull("_id") ? null : resultJSON.getString("_id");
                this._createdAt = resultJSON.isNull("createdAt") ? null : resultJSON.getString("createdAt");
                this._updatedAt = resultJSON.isNull("updatedAt") ? null : resultJSON.getString("updatedAt");
                this._username = resultJSON.isNull("username") ? null : resultJSON.getString("username");
                this._email = resultJSON.isNull("email") ? null : resultJSON.getString("email");
                this._rooms = resultJSON.isNull("rooms") ? null : resultJSON.getString("rooms");
                this._firstName = resultJSON.isNull("firstName") ? null : resultJSON.getString("firstName");
                this._lastName = resultJSON.isNull("lastName") ? null : resultJSON.getString("lastName");
                this._phone = resultJSON.isNull("phone") ? null : resultJSON.getString("phone");
                this._role = resultJSON.isNull("role") ? null : resultJSON.getString("role");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ProfileEntity(String username) {
        this._username = username;
    }

    public ProfileEntity(String username, String password) {
        this._username = username;
        this._password = password;
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

    public String get_access_token() {
        return _access_token;
    }

    public void set_access_token(String _access_token) {
        this._access_token = _access_token;
    }

    public String get_refresh_token() {
        return _refresh_token;
    }

    public void set_refresh_token(String _refresh_token) {
        this._refresh_token = _refresh_token;
    }

    public String get_expireAccess_token() {
        return _expireAccess_token;
    }

    public void set_expireAccess_token(String _expireAccess_token) {
        this._expireAccess_token = _expireAccess_token;
    }

    public String get_expireRefresh_token() {
        return _expireRefresh_token;
    }

    public void set_expireRefresh_token(String _expireRefresh_token) {
        this._expireRefresh_token = _expireRefresh_token;
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
        toString += " _logged : " + this._logged;
        toString += " _access_token : " + this._access_token;
        toString += " _refresh_token : " + this._refresh_token;
        toString += " _expireAccess_token : " + this._expireAccess_token;
        toString += " _expireRefresh_token : " + this._expireRefresh_token;

        return toString;
    }

    public void set_logged(boolean logged) {
        this._logged = logged;
    }

    public boolean is_logged() {
        return _logged;
    }

    public void update(HashMap<String, String> mapProfile) {
        if (mapProfile != null && !mapProfile.isEmpty()) {
            String username = mapProfile.get("username");
            if (username != null) {
                this._username = username;
            }
            String firstName = mapProfile.get("firstName");
            if (firstName != null) {
                this._firstName = firstName;
            }
            String lastName = mapProfile.get("lastName");
            if (lastName != null) {
                this._lastName = lastName;
            }
            String phone = mapProfile.get("phone");
            if (phone != null) {
                this._phone = phone;
            }
            String email = mapProfile.get("email");
            if (email != null) {
                this._email = email;
            }
        }
    }

    public void update(JSONObject resultJSON) {
        if (resultJSON != null) {
            try {
                this.__id = resultJSON.isNull("_id") ? null : resultJSON.getString("_id");
                this._createdAt = resultJSON.isNull("createdAt") ? null : resultJSON.getString("createdAt");
                this._updatedAt = resultJSON.isNull("updatedAt") ? null : resultJSON.getString("updatedAt");
                this._username = resultJSON.isNull("username") ? null : resultJSON.getString("username");
                this._email = resultJSON.isNull("email") ? null : resultJSON.getString("email");
                this._rooms = resultJSON.isNull("rooms") ? null : resultJSON.getString("rooms");
                this._firstName = resultJSON.isNull("firstName") ? null : resultJSON.getString("firstName");
                this._lastName = resultJSON.isNull("lastName") ? null : resultJSON.getString("lastName");
                this._phone = resultJSON.isNull("phone") ? null : resultJSON.getString("phone");
                this._role = resultJSON.isNull("role") ? null : resultJSON.getString("role");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> get_listTooms() {
        return _listTooms;
    }

    public void set_listTooms(List<String> _listTooms) {
        this._listTooms = _listTooms;
    }

    public String get_tenant() {
        return _tenant;
    }

    public void set_tenant(String _tenant) {
        this._tenant = _tenant;
    }
}
