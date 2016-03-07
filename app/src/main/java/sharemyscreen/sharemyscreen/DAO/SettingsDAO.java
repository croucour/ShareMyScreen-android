package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import sharemyscreen.sharemyscreen.Entities.SettingsEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;

/**
 * Created by cleme_000 on 04/03/2016.
 */
public class SettingsDAO extends DAOBase{
    public static final String TABLE_NAME = "settings";

    public SettingsDAO(Context pContext) {
        super(pContext);
    }

    /**
     * Attribut locale
     */
    public static final String KEY = "id";
    public static final String PROFILE_ID = "profile_id";
    public static final String OFFLINE = "offline";
    public static final String DISPLAY_OFFLINE = "display_offline";



    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PROFILE_ID + " TEXT,"
            + OFFLINE + " TEXT,"
            + DISPLAY_OFFLINE + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public long add(SettingsEntity settingsEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_ID, settingsEntity.get_profile_id());
        contentValues.put(OFFLINE, settingsEntity.is_offline());
        contentValues.put(DISPLAY_OFFLINE, settingsEntity.is_displayOffline());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void modify(SettingsEntity settingsEntity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_ID, settingsEntity.get_profile_id());
        contentValues.put(OFFLINE, settingsEntity.is_offline());
        contentValues.put(DISPLAY_OFFLINE, settingsEntity.is_displayOffline());

        _mDb.update(TABLE_NAME, contentValues, KEY + " = ?", new String[]{String.valueOf(settingsEntity.get_id())});
    }

    public SettingsEntity selectByProfileId(long profile_id) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + PROFILE_ID + " = ? " , new String[] {String.valueOf(profile_id)});

        SettingsEntity settingsEntity = null;

        if (c.moveToFirst()) {
            settingsEntity = new SettingsEntity(c);
        }

        c.close();
        return settingsEntity;
    }
}
