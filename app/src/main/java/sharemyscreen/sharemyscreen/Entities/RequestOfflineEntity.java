package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

/**
 * Created by cleme_000 on 21/02/2016.
 */
public class RequestOfflineEntity {
    private long _id = 0;
    private String _dataParams = null;
    private long _profile_id = 0;
    private String _methode = null;
    private String _request = null;
    private String _url = null;
    private int _responseCode = -1;
    private String _errorMsg = null;
    private boolean _treated = false;

    public RequestOfflineEntity(Cursor c) {
        this._id = c.getLong(0);
        this._dataParams = c.getString(1);
        this._profile_id= c.getInt(2);
        this._methode = c.getString(3);
        this._request = c.getString(4);
        this._url = c.getString(5);
        this._responseCode = c.getInt(6);
        this._errorMsg = c.getString(7);
        this._treated = c.getInt(8) == 1;
    }

    public RequestOfflineEntity() {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long id) {
        this._id = id;
    }

    public String get_dataParams() {
        return _dataParams;
    }

    public void set_dataParams(String _dataParams) {
        this._dataParams = _dataParams;
    }

    public long get_profile_id() {
        return _profile_id;
    }

    public void set_profile_id(long _profile_id) {
        this._profile_id = _profile_id;
    }

    public String get_methode() {
        return _methode;
    }

    public void set_methode(String _methode) {
        this._methode = _methode;
    }

    public String get_request() {
        return _request;
    }

    public void set_request(String _request) {
        this._request = _request;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String url) {
        this._url = url;
    }

    public int get_responseCode() {
        return _responseCode;
    }

    public void set_responseCode(int _responseCode) {
        this._responseCode = _responseCode;
    }

    public String get_errorMsg() {
        return _errorMsg;
    }

    public void set_errorMsg(String _errorMsg) {
        this._errorMsg = _errorMsg;
    }

    public boolean is_treated() {
        return _treated;
    }

    public void set_treated(boolean _treated) {
        this._treated = _treated;
    }

    @Override
    public String toString() {
        String  toString = "_id : " + String.valueOf(this.get_id());
        toString += " _dataParams : " + this._dataParams;
        toString += " _profile_id : " + this._profile_id;
        toString += " _methode : " + this._methode;
        toString += " _request : " + this._request;
        toString += " _url : " + this._url;
        toString += " _responseCode : " + this._responseCode;
        toString += " _errorMsg : " + this._errorMsg;
        toString += " _treated : " + this._treated;

        return toString;
    }
}
