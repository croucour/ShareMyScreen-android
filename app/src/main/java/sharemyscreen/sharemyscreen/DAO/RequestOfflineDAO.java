package sharemyscreen.sharemyscreen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;

/**
 * Created by cleme_000 on 21/02/2016.
 */
public class RequestOfflineDAO extends DAOBase{
    public static final String TABLE_NAME = "requestOffline";


    /**
     * Attribut de l'API
     */
    public static final String DATAPARAMS = "dataParams";
    public static final String TOKEN_ID = "token_id";
    public static final String METHODE = "methode";
    public static final String REQUEST = "request";
    public static final String URL = "url";
    public static final String RESPONSECODE = "urresponseCode";
    public static final String ERRORMSG = "errorMsg";
    public static final String TREATED = "treated";

    /**
     * Attribut locale
     */
    public static final String KEY = "id";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATAPARAMS + " TEXT,"
            + TOKEN_ID + " INT,"
            + METHODE + " TEXT,"
            + REQUEST + " TEXT,"
            + URL + " TEXT,"
            + RESPONSECODE + " INT,"
            + ERRORMSG + " TEXT,"
            + TREATED + " BOOLEAN);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public RequestOfflineDAO(Context pContext) {
        super(pContext);
    }

    public long add(RequestOfflineEntity requestOfflineEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATAPARAMS, requestOfflineEntity.get_dataParams());
        contentValues.put(TOKEN_ID, requestOfflineEntity.get_token_id());
        contentValues.put(METHODE, requestOfflineEntity.get_methode());
        contentValues.put(REQUEST, requestOfflineEntity.get_request());
        contentValues.put(URL, requestOfflineEntity.get_url());
        contentValues.put(RESPONSECODE, requestOfflineEntity.get_responseCode());
        contentValues.put(ERRORMSG, requestOfflineEntity.get_errorMsg());
        contentValues.put(TREATED, requestOfflineEntity.is_treated());

        return _mDb.insert(TABLE_NAME, null, contentValues);
    }

    public void modify(RequestOfflineEntity requestOfflineEntity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DATAPARAMS, requestOfflineEntity.get_dataParams());
        contentValues.put(TOKEN_ID, requestOfflineEntity.get_token_id());
        contentValues.put(METHODE, requestOfflineEntity.get_methode());
        contentValues.put(REQUEST, requestOfflineEntity.get_request());
        contentValues.put(URL, requestOfflineEntity.get_url());
        contentValues.put(RESPONSECODE, requestOfflineEntity.get_responseCode());
        contentValues.put(ERRORMSG, requestOfflineEntity.get_errorMsg());
        contentValues.put(TREATED, requestOfflineEntity.is_treated());

        _mDb.update(TABLE_NAME, contentValues, KEY + " = ?", new String[]{String.valueOf(requestOfflineEntity.get_id())});
    }

    public RequestOfflineEntity selectUntreated() {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE treated = ? ORDER BY " + KEY , new String[] {"0"});

        RequestOfflineEntity requestOfflineEntity = null;

        if (c.moveToFirst()) {
            requestOfflineEntity = new RequestOfflineEntity(c);
        }

        c.close();
        return requestOfflineEntity;
    }

    public RequestOfflineEntity selectById(long id) {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " WHERE " + KEY + " = ? " , new String[] {String.valueOf(id)});

        RequestOfflineEntity requestOfflineEntity = null;

        if (c.moveToFirst()) {
            requestOfflineEntity = new RequestOfflineEntity(c);
        }

        c.close();
        return requestOfflineEntity;
    }

    public void setRequestOfflineTreated(long id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TREATED, "1");

        _mDb.update(TABLE_NAME, contentValues, KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<RequestOfflineEntity> selectAll() {
        Cursor c = _mDb.rawQuery("select * from " + TABLE_NAME + " ORDER BY " + KEY  , null);

        ArrayList<RequestOfflineEntity> requestOfflineEntities = new ArrayList<>();

        RequestOfflineEntity tmpRequestOfflineEntity;
        while (c.moveToNext()) {
            tmpRequestOfflineEntity = new RequestOfflineEntity(c);
            requestOfflineEntities.add(tmpRequestOfflineEntity);
        }

        c.close();
        return requestOfflineEntities;
    }
}
