package sharemyscreen.sharemyscreen.Entities;

/**
 * Created by roucou-c on 09/12/15.
 */
public class Profile {
    private long _id;
    private String _username;
    private String _email;
    private String _role;

    public Profile(long id, String username, String email, String role) {
        this._id = id;
        this._username = username;
        this._email = email;
        this._role = role;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_role() {
        return _role;
    }

    public void set_role(String _role) {
        this._role = _role;
    }
}
