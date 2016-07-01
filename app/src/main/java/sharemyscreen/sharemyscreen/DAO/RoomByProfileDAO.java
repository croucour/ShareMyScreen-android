package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by cleme_000 on 01/03/2016.
 */
public class RoomByProfileDAO{

    public static final String TABLE_NAME = "roomsByProfile";
    private final SQLiteDatabase _mDb;

    public static final String ROOM_PUBLIC_ID = "room_public_id";
    public static final String PROFILE_PUBLIC_ID = "profile_public__id";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + ROOM_PUBLIC_ID + " TEXT, "
            + PROFILE_PUBLIC_ID + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RoomByProfileDAO(SQLiteDatabase mDb) {
        this._mDb = mDb;
    }

    public long add(String profile_public_id, String room_public_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_PUBLIC_ID, profile_public_id);
        contentValues.put(ROOM_PUBLIC_ID, room_public_id);
        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void deleteByRoom_id(String room_public_id) {
        _mDb.delete(TABLE_NAME, ROOM_PUBLIC_ID + " = ?", new String[]{room_public_id});
    }
}
