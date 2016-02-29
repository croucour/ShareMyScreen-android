package sharemyscreen.sharemyscreen;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;

/**
 * Created by cleme_000 on 27/09/2015.
 */
public abstract class MyApi extends AsyncTask<String, String, String>{

    protected final short SERVER_API = 1;
    protected final short SERVER_STREAM = 2;
    private ProfileManager _profileManager = null;
    private RequestOfflineManager _requestOfflineManager = null;
    private ProfileEntity _profileEntity = null;

    protected short currentServer = SERVER_API;

    protected String dataParams = null;
    protected String _keySecretEncoded = null;

    protected String currentResquest = null;
    protected String currentMethode = "GET";

    protected Context _pContext = null;

    private boolean resquestExist = false;

    protected String _access_token = null;

    private String apiURL = null;
    public final static String streamURL = "http://ip.jsontest.com/";

    /**
     * Heroku
     */
    public final static String KEY = "sqE1rRxhjPbmwgWc";
    public final static String SECRET = "TvfCZag4DRfqLsa8anETSxRNWstscQQK";

//    /**
//     * Local
//     */
//    public final static String KEY = "CJtYXgR8GlFWZfTr";
//    public final static String SECRET = "YNUnOblELFjjJmUIvyeVzmPIlY3VlH3W";

    protected SettingsManager settingsManager;


    private final String[][] API_REQUEST = {
            {"/users", "POST", "Bearer"},
            {"/oauth2/token/", "POST", "Basic"},
            {"/logout", "GET", "Bearer"},
            {"/profile", "GET", "Bearer"},
            {"/profile", "PUT", "Bearer"},
            {"/rooms", "GET", "Bearer"},
            {"/rooms", "POST", "Bearer"}
    };

    private final String[][] STREAM_REQUEST = {};

    protected JSONObject resultJSON;
    private int _responseCode;
    private boolean _internetConnection = false;
    private String _refresh_token = null;
    private String _authorization = null;
    private IMyApiListener _mListener;
//
    public MyApi(ProfileEntity profileEntity, Context pContext) {
        this.settingsManager = new SettingsManager(pContext);
        this._requestOfflineManager = new RequestOfflineManager(pContext);
        this._pContext = pContext;
        _profileEntity = profileEntity;
        this._profileManager = new ProfileManager(pContext);
        this.encodeKeySecret64();
        this._access_token = _profileEntity.get_access_token();
        this._refresh_token = _profileEntity.get_refresh_token();
    }

    public MyApi(Context pContext) {
        this.settingsManager = new SettingsManager(pContext);
        this._requestOfflineManager = new RequestOfflineManager(pContext);
        this._pContext = pContext;
        this.encodeKeySecret64();
    }

    public String getCurrentResquest() {
        return currentResquest;
    }

    public JSONObject getResultJSON() {
        return resultJSON;
    }

    public void setDataParams(HashMap<String, String> params) {
        if (params.size() != 0) {
            JSONObject jsonObject = new JSONObject(params);
            this.dataParams = jsonObject.toString();
        }
    }

    public void setDataParams(String dataParams) {
        this.dataParams = dataParams;
    }

    public void setCurrentRequest(String currentRequest, String requestCommand) {

        this.resquestExist = findIfRequestAccepted(currentRequest, requestCommand);


        if (this.resquestExist) {
            this.currentResquest = currentRequest;
        }
    }

    protected boolean findIfRequestAccepted(String Request, String requestCommand)
    {
        boolean isFind = false;

        if (this.currentServer == SERVER_API)
        {
            for(String[] subarray : API_REQUEST) {
                for (String aSubarray : subarray) {
                    if (aSubarray.equals(Request)) {
                        if (Objects.equals(requestCommand, subarray[1])) {
                            this.currentMethode = subarray[1];
                            if (Objects.equals(subarray[2], "Basic")) {
                                this._authorization = subarray[2] + " " + this._keySecretEncoded;
                            }
                            else {
                                this._authorization = subarray[2] + " " + this._access_token;
                            }
                            isFind = true;
                            break;
                        }
                    }
                }
            }
        }
        else if (this.currentServer == SERVER_STREAM)
        {
            for(String[] subarray : STREAM_REQUEST) {
                for (String aSubarray : subarray) {
                    if (aSubarray.equals(Request)) {
                        this.currentMethode = subarray[1];
                        isFind = true;
                        break;
                    }
                }
            }
        }
        return isFind;
    }

    public short getCurrentServer() {
        return currentServer;
    }

    public void setCurrentServer(short currentServer) {
        this.currentServer = currentServer;
    }

    protected void onPreExecute()
    {
        String ip = this.settingsManager.select("ip");
        String port = this.settingsManager.select("port");

        if (ip != null && port != null) {
            apiURL = "http://" + ip + ":" + port + "/v1";
        }
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("apiURL", apiURL);
        Log.d("request", currentResquest);
        Log.d("methode", currentMethode);
        if (haveInternetConnection(this._pContext)) {
            _internetConnection = true;
            String urlString = apiURL;
            InputStream in;

            try {
                if (this.resquestExist) {
                    urlString += this.currentResquest;
                    if (this.dataParams != null && Objects.equals(this.currentMethode, "GET")) {
                        urlString += "?" + this.dataParams;
                    }
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Authorization", this._authorization);

                    Log.i("info", String.valueOf(urlConnection.getRequestProperties()));
                    if (this.dataParams != null) {
                        Log.i("dataParams", this.dataParams);
                    }
                    urlConnection.setReadTimeout(15000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestMethod(this.currentMethode);

                    urlConnection.setDoInput(true);

                    if (this.currentMethode == "POST" || this.currentMethode == "PUT") {
                        urlConnection.setDoOutput(true);

                        OutputStream os = urlConnection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                        writer.write(this.dataParams);
                        writer.flush();
                        writer.close();
                        os.close();
                    } else if (this.currentMethode == "GET") {
                        urlConnection.setDoOutput(false);
                    }

                    this._responseCode = urlConnection.getResponseCode();

                    if (!this.isErrorRequest()) {
                        in = new BufferedInputStream(urlConnection.getInputStream());
                    } else {
                        in = new BufferedInputStream(urlConnection.getErrorStream());
                    }

                    this.parseJSON((BufferedInputStream) in);

                    if (!this.isErrorRequest()) {
                        updateExpireToken();
                    }

                    urlConnection.disconnect();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            this._internetConnection = false;
            if (_refresh_token != null) { // si refresh_token existe on peut se connecter en mode offline
                if (!Objects.equals(this.currentMethode, "GET") && !Objects.equals(this.currentResquest, "/oauth2/token/")) {
                    RequestOfflineEntity requestOfflineEntity = new RequestOfflineEntity();
                    requestOfflineEntity.set_dataParams(this.dataParams);
                    requestOfflineEntity.set_profile_id(_profileEntity.get_id());
                    requestOfflineEntity.set_methode(this.currentMethode);
                    requestOfflineEntity.set_request(this.currentResquest);
                    requestOfflineEntity.set_url(apiURL);
                    this._requestOfflineManager.add(requestOfflineEntity);
                }
            }
        }
        return "";
    }

    private void updateExpireToken() {
        if (_profileEntity == null && !this.resultJSON.isNull("expires_in")) { // user non authen
            try {
                long expires_in = this.resultJSON.getInt("expires_in");
                this.settingsManager.addSettings("expires_in", String.valueOf(expires_in));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (_profileEntity != null && !this.isErrorRequest()) {
            _profileEntity.set_expireAccess_token(this.getNewExpireToken());
            _profileManager.modifyProfil(_profileEntity);
        }
    }

    public String getNewExpireToken() {
        long expires_in = Long.parseLong(this.settingsManager.select("expires_in"));
        Date date = new Date();
        long expireToken = date.getTime() + (expires_in * 1000);

        return String.valueOf(expireToken);
    }

    private void parseJSON(BufferedInputStream in) throws IOException, JSONException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        if (sb.toString().isEmpty()) {
            this.resultJSON = null;
        }
        else {
            if (this.apiURL.matches("room")) {
                String tmp = " { \"rooms\":" + sb.toString() + "}";
                this.resultJSON = new JSONObject(tmp);

                Log.i("result param API", sb.toString());
            }
            else {
                this.resultJSON = new JSONObject(sb.toString());

            }

//            JSONArray json = new JSONArray(sb);
//            this.resultJSON = new JSONObject(tmp);
//            Log.i("result param API", sb.toString());
        }
    }

    public void encodeKeySecret64()
    {
        try {
            this._keySecretEncoded = Base64.encodeToString((KEY+ ":" + SECRET).getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onPostExecute(String str);

    public boolean isErrorRequest() {
        Log.i("_responseCode", String.valueOf(this._responseCode));

        if (this._responseCode == 200 || this._responseCode == 201 || this._responseCode == 204 || this._responseCode == 206) {
            return false;
        }
        return true;
    }

    public int get_responseCode() {
        return _responseCode;
    }

    public boolean is_internetConnection() {
        return _internetConnection;
    }

    public static boolean haveInternetConnection(Context pContext){
        NetworkInfo network = ((ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return !(network == null || !network.isConnected());
    }
//
    public MyApi setListener(IMyApiListener listener) {
        this._mListener = listener;
//        this._mListener.onComplete();
        return this;
    }
//
//    @Override
//    protected void onCancelled() {
//        super.onCancelled();
//        if (_mListener != null) {
////            this._mListener.onComplete();
//        }
//    }
//
    public interface IMyApiListener {
        public void onComplete();
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        Log.i("Debug", "onPreExecute");
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        Log.i("Debug", "doInBackground");
//        return "operation finished";
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        Log.i("Debug", "onPostExecute");
//        if (this._mListener != null) {
////            this._mListener.onComplete();
//        }
//    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.i("Debug", "onCancelled");
    }
}

