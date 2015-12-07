package sharemyscreen.sharemyscreen;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

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
import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.Model.SignInModel;

/**
 * Created by cleme_000 on 27/09/2015.
 */
public abstract class MyApi extends AsyncTask<String, String, String> {

    protected final short SERVER_API = 1;
    protected final short SERVER_STREAM = 2;

    protected short currentServer = SERVER_API;

    protected String dataParams = null;
    protected String usernamePasswordEncoded = null;

    protected String currentResquest = null;
    protected String currentMethode = "GET";

    protected Context contextApplication = null;

    private boolean resquestExist = false;

    protected String access_token = null;

    private String apiURL = "http://192.168.192.47:4000/v1";
    public final static String streamURL = "http://ip.jsontest.com/";


    protected SettingsManager settingsManager;


    private final String[][] API_REQUEST = {
            {"/users", "POST"},
            {"/oauth2/token/", "POST"},
            {"/user/logout", "GET"},
    };

    private final String[][] STREAM_REQUEST = {};

    protected JSONObject resultJSON;

    public MyApi(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public String getCurrentResquest() {
        return currentResquest;
    }

    public void setdataParams(HashMap<String, String> params) {

        if (params.size() != 0) {
            JSONObject jsonObject = new JSONObject(params);
            this.dataParams = jsonObject.toString();
        }


//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            try {
//                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//                result.append("=");
//                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        this.dataParams = result.toString();
    }

    public void setCurrentResquest(String currentResquest) {

        this.resquestExist = findIfRequestAccepted(currentResquest);

        if (this.resquestExist) {
            this.currentResquest = currentResquest;
        }
    }

    protected boolean findIfRequestAccepted(String Request)
    {
        boolean isFind = false;

        if (this.currentServer == SERVER_API)
        {
            for(String[] subarray : API_REQUEST) {
                for (String aSubarray : subarray) {
                    if (aSubarray.equals(Request)) {
                        this.currentMethode = subarray[1];
                        isFind = true;
                        break;
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

        if (ip != null && port != null)
        {
            apiURL = "http://"+ip+":"+port+"/v1";
        }

        Log.i("onPreExecute url", apiURL);

        this.access_token = this.settingsManager.select("access_token");
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = apiURL;
        InputStream in;

        try {

            if (this.resquestExist)
            {
                urlString += this.currentResquest;
                if (this.dataParams != null && this.currentMethode == "GET")
                {
                    urlString += "?" + this.dataParams;
                }
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if (this.usernamePasswordEncoded != null && this.access_token == null) {
                    urlConnection.setRequestProperty("Authorization", "Basic " + this.usernamePasswordEncoded);
                }
                else if (this.access_token != null) {
                    urlConnection.setRequestProperty("Authorization", "Bearer " + this.access_token);
                }

                Log.i("info", String.valueOf(urlConnection.getRequestProperties()));

                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod(this.currentMethode);

                urlConnection.setDoInput(true);

                if (this.currentMethode == "POST") {
                    urlConnection.setDoOutput(true);

                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    writer.write(this.dataParams);
                    writer.flush();
                    writer.close();
                    os.close();
                }
                else if (this.currentMethode == "GET")
                {
                    urlConnection.setDoOutput(false);
                }

                System.out.println(urlConnection.getResponseCode());

                in = new BufferedInputStream(urlConnection.getInputStream());

                this.parseJSON((BufferedInputStream) in);

                urlConnection.disconnect();
            }

        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
        return "";
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
            this.resultJSON = new JSONObject(sb.toString());
        }

    }

    public void encodeUsernamePassword64(String username, String password)
    {
        try {
            this.usernamePasswordEncoded = Base64.encodeToString((username + ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onPostExecute(String str);

}