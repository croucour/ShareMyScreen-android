package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileDAO{
    public static final String TABLE_NAME = "profile";
    private final SQLiteDatabase _mDb;

    /**
     * Attribut de l'API
     */
    public static final String _ID = "_id";
    public static final String CREATEDAT = "createdat";
    public static final String UPDATEDAT = "updatedAt";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String PHONE = "phone";
    public static final String ROOMS = "rooms";
    public static final String ROLE = "role";


    /**
     * Attribut locale
     */
    public static final String KEY = "id";
    public static final String PASSWORD = "password";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _ID + " TEXT,"
            + CREATEDAT + " TEXT,"
            + UPDATEDAT + " TEXT,"
            + USERNAME + " TEXT,"
            + EMAIL + " TEXT,"
            + FIRSTNAME + " TEXT,"
            + LASTNAME + " TEXT,"
            + PHONE + " TEXT,"
            + ROOMS + " TEXT,"
            + PASSWORD + " TEXT,"
            + ROLE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ProfileDAO(SQLiteDatabase mDb) {
        this._mDb = mDb;
    }

    public ProfileEntity selectByUsername(String username) {

        ProfileEntity profile = null;

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE username = ?" , new String[] {username});

        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
    }

    public long add(ProfileEntity profile) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, profile.get__id());
        contentValues.put(CREATEDAT, profile.get_createdAt());
        contentValues.put(UPDATEDAT, profile.get_updatedAt());
        contentValues.put(USERNAME, profile.get_username());
        contentValues.put(EMAIL, profile.get_email());
        contentValues.put(FIRSTNAME, profile.get_firstName());
        contentValues.put(LASTNAME, profile.get_lastName());
        contentValues.put(PHONE, profile.get_phone());
        contentValues.put(ROOMS, profile.get_rooms());
        contentValues.put(PASSWORD, profile.get_password());
        contentValues.put(ROLE, profile.get_role());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void delete(ProfileEntity profile) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(profile.get_id())});
    }

    public void delete(long id) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public void modify(ProfileEntity profile) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, profile.get__id());
        contentValues.put(CREATEDAT, profile.get_createdAt());
        contentValues.put(UPDATEDAT, profile.get_updatedAt());
        contentValues.put(USERNAME, profile.get_username());
        contentValues.put(EMAIL, profile.get_email());
        contentValues.put(FIRSTNAME, profile.get_firstName());
        contentValues.put(LASTNAME, profile.get_lastName());
        contentValues.put(PHONE, profile.get_phone());
        contentValues.put(ROOMS, profile.get_rooms());
        contentValues.put(ROLE, profile.get_role());

        if (profile.get_password() != null) {
            contentValues.put(PASSWORD, profile.get_password());
        }

        _mDb.update(TABLE_NAME, contentValues, USERNAME + " = ?", new String[]{String.valueOf(profile.get_username())});
    }


    public ProfileEntity selectById(long id) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + KEY + " = ?" , new String[] {String.valueOf(id)});

        ProfileEntity profile = null;

        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
    }
}
