package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import sharemyscreen.sharemyscreen.Entities.Room;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomsDAO extends DAOBase {
    public static final String TABLE_NAME = "rooms";
    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RoomsDAO(Context pContext) {
        super(pContext);
    }

    public long add(Room room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, room.get_name());
        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void delete(Room room) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(room.getId())});
    }

    public void delete(long id) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public void modify(Room room) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY, room.getId());
        contentValues.put(NAME, room.get_name());
        _mDb.update(TABLE_NAME, contentValues, KEY + " = ?", new String[]{String.valueOf(room.getId())});
    }

    public String select(long id) {

        String value = null;

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE KEY = ?" , new String[] {String.valueOf(id)});

        if (c.moveToFirst()) {
            value = c.getString(1);
        }

        c.close();
        return value;
    }

    public List<Room> selectAll(String orderBy) {

        List<Room> list_room = new ArrayList<Room>();

        if (orderBy != null) {
            orderBy = " ORDER BY " +orderBy;
        }

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + (orderBy == null ? "" : orderBy), null);

        while (c.moveToNext()) {
            long id = c.getLong(0);
            String name = c.getString(1);

            Room room = new Room(id, name);
            list_room.add(room);
        }

        c.close();
        return list_room.isEmpty() ? null : list_room;
    }
}
