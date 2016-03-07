package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by cleme_000 on 01/03/2016.
 */
public class RoomByProfileDAO extends DAOBase {

    public static final String TABLE_NAME = "roomsByProfile";
    public static final String KEY = "id";
    public static final String ROOM_ID = "room_id";
    public static final String PROFILE_ID = "profile_id";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ROOM_ID + " TEXT, "
            + PROFILE_ID + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RoomByProfileDAO(Context pContext) {
        super(pContext);
    }

    public long add(String profile__id, String room__id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_ID, profile__id);
        contentValues.put(ROOM_ID, room__id);
        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    void add(List<ProfileEntity> profileEntityList, String room__id) {
        for (ProfileEntity profileEntity : profileEntityList) {
            this.add(profileEntity.get__id(), room__id);
        }
    }

    public void deleteByRoom_id(String room__id) {
        _mDb.delete(TABLE_NAME, ROOM_ID + " = ?", new String[]{room__id});
    }
}
