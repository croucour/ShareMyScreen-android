package sharemyscreen.sharemyscreen.DAO;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by cleme_000 on 01/03/2016.
 */
public class RoomByProfileManager extends RoomByProfileDAO {

    public RoomByProfileManager(SQLiteDatabase mDb) {
        super(mDb);
    }

    void add(List<ProfileEntity> profileEntityList, String room_public_id) {
        for (ProfileEntity profileEntity : profileEntityList) {
            super.add(profileEntity.get_public_id(), room_public_id);
        }
    }
}
