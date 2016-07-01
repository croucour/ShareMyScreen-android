package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sharemyscreen.sharemyscreen.Entities.RoomEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomsDAO{
    public static final String TABLE_NAME = "rooms";
    private final SQLiteDatabase _mDb;

    public static final String KEY = "id";
    public static final String PUBLIC_ID = "public_id";
    public static final String NAME = "name";
    public static final String CREATED_AT = "created_at";
    public static final String OWNER = "owner";
    public static final String PRIVATE = "private";
    private static final String ORGANIZATION_PUBLIC_ID = "organization_public_id";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PUBLIC_ID + " TEXT, "
            + NAME + " TEXT, "
            + CREATED_AT + " TEXT, "
            + OWNER + " TEXT, "
            + PRIVATE + " TEXT, "
            + ORGANIZATION_PUBLIC_ID + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RoomsDAO(SQLiteDatabase mDb) {
        this._mDb = mDb;
    }

    public long add(RoomEntity room, String organization_public_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUBLIC_ID, room.get_public_id());
        contentValues.put(NAME, room.get_name());
        contentValues.put(CREATED_AT, room.get_created_at());
        contentValues.put(OWNER, room.get_owner());
        contentValues.put(PRIVATE, room.is_private());
        contentValues.put(ORGANIZATION_PUBLIC_ID, organization_public_id);

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
        contentValues.put(PUBLIC_ID, room.get_public_id());
        contentValues.put(NAME, room.get_name());
        contentValues.put(CREATED_AT, room.get_created_at());
        contentValues.put(OWNER, room.get_owner());
        contentValues.put(PRIVATE, room.is_private());
//        contentValues.put(ORGANIZATION_PUBLIC_ID, organization_public_id);

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
                + " INNER JOIN " + RoomByProfileDAO.TABLE_NAME + " on " + TABLE_NAME+"."+ PUBLIC_ID +"="+RoomByProfileDAO.TABLE_NAME+"."+RoomByProfileDAO.ROOM_PUBLIC_ID
                + " WHERE "+RoomByProfileDAO.PROFILE_PUBLIC_ID + " = ?", new String[] {profile__id});

        while (c.moveToNext()) {
            roomEntityList.add(new RoomEntity(c));
        }

        c.close();
        return roomEntityList.isEmpty() ? null : roomEntityList;
    }

    public void delete(String room__id) {
        _mDb.delete(TABLE_NAME, PUBLIC_ID + " = ?", new String[]{room__id});
    }

    public RoomEntity selectByPublic_id(String _id) {

        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE "+ PUBLIC_ID +" = ?" , new String[] {String.valueOf(_id)});

        RoomEntity roomEntity = null;

        if (c.moveToFirst()) {
            roomEntity = new RoomEntity(c);
        }

        c.close();
        return roomEntity;
    }

    public List<RoomEntity> selectAllByProfile_idAndOrganization_id(String profile_public_id, String organization_public_id) {
        List<RoomEntity> roomEntityList = new ArrayList<>();

        Cursor c = _mDb.rawQuery("select "+ TABLE_NAME +".* from " + TABLE_NAME
                /*+ " INNER JOIN " + RoomByProfileDAO.TABLE_NAME + " on " + TABLE_NAME + "." + PUBLIC_ID + "="+RoomByProfileDAO.TABLE_NAME+"."+RoomByProfileDAO.ROOM_PUBLIC_ID*/
                /*+ " WHERE "+RoomByProfileDAO.PROFILE_PUBLIC_ID + " = ? AND "+ */ + " WHERE "+ TABLE_NAME + "." + ORGANIZATION_PUBLIC_ID + " = ?", new String[] {/*profile_public_id, */organization_public_id});

        while (c.moveToNext()) {
            roomEntityList.add(new RoomEntity(c));
        }

        c.close();
        return roomEntityList.isEmpty() ? null : roomEntityList;

    }
}
