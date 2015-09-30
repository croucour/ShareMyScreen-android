package sharemyscreen.sharemyscreen;


import android.os.AsyncTask;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cleme_000 on 27/09/2015.
 */
public abstract class MyApi extends AsyncTask<String, String, String> {

    protected final short SERVER_API = 1;
    protected final short SERVER_STREAM = 2;

    protected short currentServer = SERVER_API;

    protected String dataParams = null;

    protected String currentResquest = null;
    protected String currentMethode = "GET";
    private boolean resquestExist = false;

    public final static String apiURL = "http://192.168.0.11:4000/api/v1";
    public final static String streamURL = "http://ip.jsontest.com/";


    private final String[][] API_REQUEST = {
            {"/users", "POST"},
            {"/users/login", "GET"},
            {"/users/logout", "GET"},
    };

    private final String[][] STREAM_REQUEST = {};

    protected JSONObject resultJSON;

    public String getCurrentResquest() {
        return currentResquest;
    }

    public void setdataParams(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }


        this.dataParams = result.toString();
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
                    urlString += this.dataParams;
                }
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod(this.currentMethode);

                urlConnection.setDoInput(true);


                if (this.currentMethode == "POST") {
                    urlConnection.setDoOutput(true);

                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
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
            }

        } catch (Exception e ) {
            System.out.println(e.getMessage());
//            return e.getMessage();
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
        this.resultJSON = new JSONObject(sb.toString());
        Log.i("info", this.resultJSON.toString());
    }

//    protected void onPostExecute(String result) {
//        try {
//            Log.i("info", this.resultJSON.getString("toot"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    protected abstract void onPostExecute(String str);
}

