package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileEntity {

    /**
     * Attribut de l'API
     */
    @SerializedName("public_id")
    private String _public_id = null;
    @SerializedName("created_at")
    private String _createdAt = null;
    @SerializedName("email")
    private String _email = null;
    @SerializedName("first_name")
    private String _firstName = null;
    @SerializedName("last_name")
    private String _lastName = null;
    @SerializedName("organization")
    private List<OrganizationEntity> _organizationEntities = null;



    /**
     * Attribut locale
     */
    private transient long _id = 0;
    private String _password = null;


    public ProfileEntity(long id, String username, String email, String role) {
        this._id = id;
        this._email = email;
    }

    public ProfileEntity(Cursor c) {
        this._id = c.getInt(0);
        this._public_id = c.getString(1);
        this._createdAt = c.getString(2);
        this._email = c.getString(3);
        this._firstName = c.getString(4);
        this._lastName = c.getString(5);
        this._password = c.getString(6);
    }


    public ProfileEntity(String username, String password) {
        this._password = password;
    }

    public ProfileEntity(HashMap<String, String> params) {
        this._firstName = params.get("firstName");
        this._lastName = params.get("lastName");
        this._password = params.get("password");
        this._email = params.get("email");
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long id) {
        this._id = id;
    }

    public String get_public_id() {
        return _public_id;
    }

    public void set_public_id(String _id) {
        this._public_id = _id;
    }

    public String get_createdAt() {
        return _createdAt;
    }

    public void set_createdAt(String createdAt) {
        this._createdAt = createdAt;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String email) {
        this._email = email;
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

    public String get_password() {
        return _password;
    }

    public void set_password(String password) {
        this._password = password;
    }

    @Override
    public String toString() {
        String toString = "id : " + String.valueOf(this.get_id());
        toString += " _id : " + this.get_public_id();
        toString += " _createdAt : " + this.get_createdAt();
        toString += " _email : " + this._email;
        toString += " _firstName : " + this._firstName;
        toString += " _lastName : " + this._lastName;
        toString += " _password : " + this._password;

        return toString;
    }
}
