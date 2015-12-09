package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.Room;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomsManager {

    private RoomsDAO roomsDAO;

    public RoomsManager(Context pContext) {

        this.roomsDAO = new RoomsDAO(pContext);
        this.roomsDAO.open();
    }

    public void addRoom(Room room) {
        this.roomsDAO.add(room);
    }

    public void addRoom(String name) {
        Room room = new Room(0, name);
        this.addRoom(room);
    }

    public List<Room> selectAll(String orderBy) {
        return this.roomsDAO.selectAll(orderBy);
    }
}
