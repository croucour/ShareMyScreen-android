package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.Room;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomsManager extends RoomsDAO{

    public RoomsManager(Context pContext) {
        super(pContext);
    }

    public void addRoom(Room room) {
        super.add(room);
    }

    public void addRoom(String name) {
        Room room = new Room(0, name);
        super.add(room);
    }

    @Override
    public List<Room> selectAll(String orderBy) {
        return super.selectAll(orderBy);
    }
}
