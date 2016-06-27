package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationDAO {
    private final SQLiteDatabase _mDb;
    public static final String TABLE_NAME = "organization";

    /**
     * Attribut de l'API
     */
    public static final String PUBLIC_ID = "public_id";
    public static final String NAME = "name";
    public static final String CREATOR_PUBLIC_ID = "creator";
    public static final String OWNER_PUBLIC_ID = "owner";
    public static final String CREATED_AT = "created_at";

    /**
     * Attribut locale
     */
    public static final String KEY = "id";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PUBLIC_ID + " TEXT,"
            + NAME + " TEXT,"
            + CREATOR_PUBLIC_ID + " TEXT,"
            + OWNER_PUBLIC_ID + " TEXT,"
            + CREATED_AT + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public OrganizationDAO(SQLiteDatabase mDb) {
        _mDb = mDb;
    }

    public void modify(OrganizationEntity organizationEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUBLIC_ID, organizationEntity.get_public_id());
        contentValues.put(NAME, organizationEntity.get_name());
        contentValues.put(CREATOR_PUBLIC_ID, organizationEntity.get_creator().get_id());
        contentValues.put(OWNER_PUBLIC_ID, organizationEntity.get_owner().get_id());
        contentValues.put(CREATED_AT, organizationEntity.get_created_at());

        _mDb.update(TABLE_NAME, contentValues, PUBLIC_ID + " = ?", new String[]{String.valueOf(organizationEntity.get_public_id())});
    }

    public long add(OrganizationEntity organizationEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUBLIC_ID, organizationEntity.get_public_id());
        contentValues.put(NAME, organizationEntity.get_name());
        contentValues.put(CREATOR_PUBLIC_ID, organizationEntity.get_creator_public_id());
        contentValues.put(OWNER_PUBLIC_ID, organizationEntity.get_owner_public_id());
        contentValues.put(CREATED_AT, organizationEntity.get_created_at());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public OrganizationEntity selectByPublic_id(String public_id) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + PUBLIC_ID + " = ?" , new String[] {public_id});

        OrganizationEntity organizationEntity = null;

        if (c.moveToFirst()) {
            organizationEntity = new OrganizationEntity(c);
        }

        c.close();
        return organizationEntity;
    }
}
