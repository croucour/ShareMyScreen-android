package sharemyscreen.sharemyscreen.Entities;

import android.database.Cursor;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import sharemyscreen.sharemyscreen.ServiceGeneratorApi;

/**
 * Created by cleme_000 on 21/02/2016.
 */
public class RequestOfflineEntity {
    private long _id = 0;
    private String _dataParams = null;
    private long _token_id = 0;
    private String _methode = null;
    private String _request = null;
    private String _url = null;
    private int _responseCode = -1;
    private String _errorMsg = null;
    private boolean _treated = false;

    public RequestOfflineEntity(Cursor c) {
        this._id = c.getLong(0);
        this._dataParams = c.getString(1);
        this._token_id = c.getInt(2);
        this._methode = c.getString(3);
        this._request = c.getString(4);
        this._url = c.getString(5);
        this._responseCode = c.getInt(6);
        this._errorMsg = c.getString(7);
        this._treated = c.getInt(8) == 1;
    }

    public RequestOfflineEntity() {

    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public RequestOfflineEntity(Request request, TokenEntity tokenEntity) throws IOException {
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            this._dataParams = buffer.readString(charset);
            this._token_id = tokenEntity.get_id();
            this._request = String.valueOf(request.url()).replace(ServiceGeneratorApi.API_BASE_URL+"/v1/", "");
            this._methode = request.method();
            this._url = String.valueOf(request.url()).replace(this._request, "");
        }
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

    public long get_token_id() {
        return _token_id;
    }

    public void set_token_id(long _token_id) {
        this._token_id = _token_id;
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
        toString += " _token_id : " + this._token_id;
        toString += " _methode : " + this._methode;
        toString += " _request : " + this._request;
        toString += " _url : " + this._url;
        toString += " _responseCode : " + this._responseCode;
        toString += " _errorMsg : " + this._errorMsg;
        toString += " _treated : " + this._treated;

        return toString;
    }
}
