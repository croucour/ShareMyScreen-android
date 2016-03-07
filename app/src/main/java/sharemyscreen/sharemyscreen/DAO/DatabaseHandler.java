package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cleme_000 on 03/11/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(RoomsDAO.TABLE_CREATE);
        db.execSQL(RoomByProfileDAO.TABLE_CREATE);
        db.execSQL(ProfileDAO.TABLE_CREATE);
        db.execSQL(GlobalDAO.TABLE_CREATE);
        db.execSQL(RequestOfflineDAO.TABLE_CREATE);
        db.execSQL(TokenDAO.TABLE_CREATE);
        db.execSQL(SettingsDAO.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RoomsDAO.TABLE_DROP);
        db.execSQL(RoomByProfileDAO.TABLE_DROP);
        db.execSQL(ProfileDAO.TABLE_DROP);
        db.execSQL(GlobalDAO.TABLE_DROP);
        db.execSQL(RequestOfflineDAO.TABLE_DROP);
        db.execSQL(TokenDAO.TABLE_DROP);
        db.execSQL(SettingsDAO.TABLE_DROP);
        onCreate(db);
    }
}
