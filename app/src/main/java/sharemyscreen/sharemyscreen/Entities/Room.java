package sharemyscreen.sharemyscreen.Entities;

/**
 * Created by roucou-c on 09/12/15.
 */
public class Room {
    private long _id;
    private String _name;

    public Room(long id, String name) {
        this._id = id;
        this._name = name;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
