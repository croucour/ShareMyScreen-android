package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
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
    public static final String PUBLIC_ID = "_id";
    public static final String CREATEDAT = "createdat";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";


    /**
     * Attribut locale
     */
    public static final String KEY = "id";
    public static final String PASSWORD = "password";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PUBLIC_ID + " TEXT,"
            + CREATEDAT + " TEXT,"
            + EMAIL + " TEXT,"
            + FIRSTNAME + " TEXT,"
            + LASTNAME + " TEXT,"
            + PASSWORD + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ProfileDAO(SQLiteDatabase mDb) {
        this._mDb = mDb;
    }

    public ProfileEntity selectByEmail(String email) {

        ProfileEntity profile = null;

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE email = ?" , new String[] {email});

        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
    }

    public long add(ProfileEntity profile) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUBLIC_ID, profile.get_public_id());
        contentValues.put(CREATEDAT, profile.get_createdAt());
        contentValues.put(EMAIL, profile.get_email());
        contentValues.put(FIRSTNAME, profile.get_firstName());
        contentValues.put(LASTNAME, profile.get_lastName());
        contentValues.put(PASSWORD, profile.get_password());

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
        contentValues.put(PUBLIC_ID, profile.get_public_id());
        contentValues.put(CREATEDAT, profile.get_createdAt());
        contentValues.put(EMAIL, profile.get_email());
        contentValues.put(FIRSTNAME, profile.get_firstName());
        contentValues.put(LASTNAME, profile.get_lastName());

        if (profile.get_password() != null) {
            contentValues.put(PASSWORD, profile.get_password());
        }

        _mDb.update(TABLE_NAME, contentValues, PUBLIC_ID + " = ?", new String[]{String.valueOf(profile.get_public_id())});
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

    public ProfileEntity selectByPublic_id(String public_id) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + PUBLIC_ID + " = ?" , new String[] {public_id});

        ProfileEntity profile = null;

        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
    }
}
