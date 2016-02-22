package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileDAO extends DAOBase {
    public static final String TABLE_NAME = "profile";


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
    public static final String LOGGED = "logged";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String EXPIREACCESS_TOKEN = "expireAccess_token";
    public static final String EXPIREREFRESH_TOKEN = "expireRefresh_token";


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
            + ROLE + " TEXT,"
            + LOGGED + " BOOLEAN,"
            + ACCESS_TOKEN + " TEXT,"
            + REFRESH_TOKEN + " TEXT,"
            + EXPIREACCESS_TOKEN + " TEXT,"
            + EXPIREREFRESH_TOKEN + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ProfileDAO(Context pContext) {
        super(pContext);
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
        contentValues.put(LOGGED, profile.is_logged());
        contentValues.put(ACCESS_TOKEN, profile.get_access_token());
        contentValues.put(REFRESH_TOKEN, profile.get_refresh_token());
        contentValues.put(EXPIREACCESS_TOKEN, profile.get_expireAccess_token());
        contentValues.put(EXPIREREFRESH_TOKEN, profile.get_expireRefresh_token());

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
        contentValues.put(LOGGED, profile.is_logged());
        contentValues.put(ACCESS_TOKEN, profile.get_access_token());
        contentValues.put(REFRESH_TOKEN, profile.get_refresh_token());
        contentValues.put(EXPIREACCESS_TOKEN, profile.get_expireAccess_token());
        contentValues.put(EXPIREREFRESH_TOKEN, profile.get_expireRefresh_token());

        if (profile.get_password() != null) {
            contentValues.put(PASSWORD, profile.get_password());
        }

        _mDb.update(TABLE_NAME, contentValues, USERNAME + " = ?", new String[]{String.valueOf(profile.get_username())});
    }

    public void setProfileIsLogged(ProfileEntity profile) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE logged = ?" , new String[] {"1"});

        ProfileEntity tmpProfile;
        while (c.moveToNext()) {
            tmpProfile = new ProfileEntity(c);
            tmpProfile.set_logged(false);
            this.modify(tmpProfile);
        }

        profile.set_logged(true);
        this.modify(profile);

        c.close();
    }

    public ProfileEntity selectProfileLogged() {

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE logged = ?" , new String[] {"1"});

        ProfileEntity profile = null;

        Log.d("nb profile logged true", String.valueOf(c.getCount()));
        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
    }

    public ProfileEntity selectByUsernameAndPassword(String username, String password) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + USERNAME + " = ? AND " + PASSWORD + " = ?" , new String[] {username, password});

        ProfileEntity profile = null;

        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
    }

    public ProfileEntity selectByRefreshToken(String refresh_token) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + REFRESH_TOKEN + " = ?" , new String[] {refresh_token});

        ProfileEntity profile = null;

        if (c.moveToFirst()) {
            profile = new ProfileEntity(c);
        }

        c.close();
        return profile;
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

//    public List<ProfileEntity> selectAllProfile() {
//        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME , null);
//
//        List<ProfileEntity> listProfile = null;
//
//        ProfileEntity tmpProfile;
//        while (c.moveToNext()) {
//            tmpProfile = new ProfileEntity(c);
////            listProfile.add(new ProfileEntity(c));
//
//            Log.d("tous les profile", tmpProfile.toString());
//        }
//
//        c.close();
//        return listProfile;
//    }
}
