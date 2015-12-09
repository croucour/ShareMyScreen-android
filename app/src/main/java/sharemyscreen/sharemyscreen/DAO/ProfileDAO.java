package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import sharemyscreen.sharemyscreen.Entities.Profile;
import sharemyscreen.sharemyscreen.Entities.Room;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileDAO extends DAOBase {
    public static final String TABLE_NAME = "profile";
    public static final String KEY = "id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT," + EMAIL + " TEXT," + ROLE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ProfileDAO(Context pContext) {
        super(pContext);
    }

    public long add(Profile profile) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, profile.get_username());
        contentValues.put(EMAIL, profile.get_email());
        contentValues.put(ROLE, profile.get_role());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void delete(Profile profile) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(profile.get_id())});
    }

    public void delete(long id) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public void modify(Profile profile) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, profile.get_username());
        contentValues.put(EMAIL, profile.get_email());
        contentValues.put(ROLE, profile.get_role());
        _mDb.update(TABLE_NAME, contentValues, KEY + " = ?", new String[]{String.valueOf(profile.get_id())});
    }

    public Profile select(long id) {

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE KEY = ?" , new String[] {String.valueOf(id)});

        Profile profile = null;

        if (c.moveToFirst()) {
            profile = new Profile(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
        }

        c.close();
        return profile;
    }
}
