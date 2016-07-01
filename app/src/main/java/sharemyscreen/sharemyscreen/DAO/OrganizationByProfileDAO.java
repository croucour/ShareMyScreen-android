package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationByProfileDAO {
    private final SQLiteDatabase _mDb;
    public static final String TABLE_NAME = "organization_profile";


    /**
     * Attribut locale
     */
    public static final String ORGANIZATION_PUBLIC_ID = "organization_public_id";
    public static final String USER_PUBLIC_ID = "user_public_id";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + ORGANIZATION_PUBLIC_ID + " TEXT,"
            + USER_PUBLIC_ID + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public OrganizationByProfileDAO(SQLiteDatabase mDb) {
        _mDb = mDb;
    }

    public long add(OrganizationEntity organizationEntity, ProfileEntity profileEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORGANIZATION_PUBLIC_ID, organizationEntity.get_public_id());
        contentValues.put(USER_PUBLIC_ID, profileEntity.get_public_id());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void deleteByOrganization(String public_id) {
        _mDb.delete(TABLE_NAME, ORGANIZATION_PUBLIC_ID + " = ?", new String[]{public_id});
    }

    public List<ProfileEntity> selectProfilesByOrganization(String public_id) {
        List<ProfileEntity> list_profile = new ArrayList<ProfileEntity>();

        Cursor c = _mDb.rawQuery("select "+ ProfileDAO.TABLE_NAME + ".* from " + TABLE_NAME
                + " INNER JOIN " + ProfileDAO.TABLE_NAME + " on " + TABLE_NAME+"."+ USER_PUBLIC_ID + "=" +ProfileDAO.TABLE_NAME+"."+ProfileDAO.PUBLIC_ID +
                " WHERE " + ORGANIZATION_PUBLIC_ID + " = ?", new String[] {public_id});

        while (c.moveToNext()) {
            list_profile.add(new ProfileEntity(c));
        }

        c.close();
        return list_profile.isEmpty() ? null : list_profile;
    }

    public List<OrganizationEntity> selectAllByProfile_id(String profile_public_id) {
        List<OrganizationEntity> organizationEntityList = new ArrayList<>();

        Cursor c = _mDb.rawQuery("select "+ OrganizationDAO.TABLE_NAME + ".* from " + TABLE_NAME
                + " INNER JOIN " + OrganizationDAO.TABLE_NAME + " on " + TABLE_NAME + "." + ORGANIZATION_PUBLIC_ID + "=" +OrganizationDAO.TABLE_NAME+"."+OrganizationDAO.PUBLIC_ID +
                " WHERE " + USER_PUBLIC_ID + " = ?", new String[] {profile_public_id});

        while (c.moveToNext()) {
            organizationEntityList.add(new OrganizationEntity(c));
        }

        c.close();
        return organizationEntityList.isEmpty() ? null : organizationEntityList;
    }
}
