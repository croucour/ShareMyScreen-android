package sharemyscreen.sharemyscreen.DAO;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.RoomEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomsManager extends RoomsDAO{

    private final RoomByProfileManager _roomByProfileManager;

    public RoomsManager(SQLiteDatabase mDb) {
        super(mDb);
        _roomByProfileManager = new RoomByProfileManager(mDb);
    }

    public void addRoom(String name, String organization_public_id) {
        RoomEntity room = new RoomEntity(0, name);
        super.add(room, organization_public_id);
    }

    @Override
    public List<RoomEntity> selectAll(String orderBy) {
        return super.selectAll(orderBy);
    }

    public long add(RoomEntity room, String organization_public_id) {
        if (this.selectByPublic_id(room.get_public_id()) == null) {
            if (room.get_members() != null) {
                _roomByProfileManager.add(room.get_members(), room.get_public_id());
            }
            return super.add(room, organization_public_id);
        }
        return 0;
    }

    public void add(List<RoomEntity> roomEntityList, String organization_public_id) {
        if (roomEntityList != null) {
            for (RoomEntity roomEntity : roomEntityList) {
                long room_id = this.add(roomEntity, organization_public_id);
            }
        }
    }

    public void delete(String room__id) {
        if (room__id != null) {
            this._roomByProfileManager.deleteByRoom_id(room__id);
            super.delete(room__id);
        }
    }

    public List<RoomEntity> selectAllByProfile_idAndOrganization_id(String profile_public_id, String organization_public_id) {
        return super.selectAllByProfile_idAndOrganization_id(profile_public_id, organization_public_id);
    }
}
