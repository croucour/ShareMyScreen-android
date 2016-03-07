package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import sharemyscreen.sharemyscreen.Entities.RoomEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomsDAO extends DAOBase {
    public static final String TABLE_NAME = "rooms";
    public static final String KEY = "id";
    public static final String _ID = "__id";
    public static final String NAME = "name";
    public static final String CREATEDAT = "createdAt";
    public static final String UPDATEDAT = "updatedAt";
    public static final String OWNER = "owner";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _ID + " TEXT, "
            + NAME + " TEXT, "
            + CREATEDAT + " TEXT, "
            + UPDATEDAT + " TEXT, "
            + OWNER + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RoomsDAO(Context pContext) {
        super(pContext);
    }

    public long add(RoomEntity room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, room.get__id());
        contentValues.put(NAME, room.get_name());
        contentValues.put(CREATEDAT, room.get_createdAt());
        contentValues.put(UPDATEDAT, room.get_updatedAt());
        contentValues.put(OWNER, room.get_owner());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void delete(RoomEntity room) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(room.getId())});
    }

    public void delete(long id) {
        _mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public void modify(RoomEntity room) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, room.get__id());
        contentValues.put(NAME, room.get_name());
        contentValues.put(CREATEDAT, room.get_createdAt());
        contentValues.put(UPDATEDAT, room.get_updatedAt());
        contentValues.put(OWNER, room.get_owner());

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

    public List<RoomEntity> selectAll(String orderBy) {

        List<RoomEntity> list_room = new ArrayList<RoomEntity>();

        if (orderBy != null) {
            orderBy = " ORDER BY " +orderBy;
        }

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + (orderBy == null ? "" : orderBy), null);

        while (c.moveToNext()) {
            long id = c.getLong(0);
            String name = c.getString(1);

            RoomEntity room = new RoomEntity(id, name);
            list_room.add(room);
        }

        c.close();
        return list_room.isEmpty() ? null : list_room;
    }

    public List<RoomEntity> selectAllByProfile_id(String profile__id) {
        List<RoomEntity> roomEntityList = new ArrayList<>();

        Cursor c = _mDb.rawQuery("select "+ TABLE_NAME+".* from " + TABLE_NAME
                + " INNER JOIN " + RoomByProfileDAO.TABLE_NAME + " on " + TABLE_NAME+"."+_ID+"="+RoomByProfileDAO.TABLE_NAME+"."+RoomByProfileDAO.ROOM_ID
                + " WHERE "+RoomByProfileDAO.PROFILE_ID+ " = ? ORDER BY "+UPDATEDAT+" DESC", new String[] {profile__id});

        while (c.moveToNext()) {
            roomEntityList.add(new RoomEntity(c));
        }

        c.close();
        return roomEntityList.isEmpty() ? null : roomEntityList;
    }

    public void delete(String room__id) {
        _mDb.delete(TABLE_NAME, _ID + " = ?", new String[]{room__id});
    }

    public RoomEntity selectBy_id(String _id) {

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE "+_ID+" = ?" , new String[] {String.valueOf(_id)});

        RoomEntity roomEntity = null;

        if (c.moveToFirst()) {
            roomEntity = new RoomEntity(c);
        }

        c.close();
        return roomEntity;
    }
}
