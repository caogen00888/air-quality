package com.caogen00888.airquality;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.content.Context;

public class HttpConnectHelper {
    private static final String TAG = "HttpConnectHelper";
    private HttpURLConnection mConnection;
    private final Context mContext;

    public HttpConnectHelper(Context context) {
        mContext = context;
    }

    private void connect(String url) throws IOException {
        Log.d(TAG, "make connect to " + url);
        mConnection = (HttpURLConnection) new URL(url).openConnection();
        mConnection.setConnectTimeout(20 * 1000);
        mConnection.setReadTimeout(20 * 1000);
        mConnection.connect();
        if (mConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            mConnection.disconnect();
            throw new IOException("Error in connect " + url + ",response code:"
                    + mConnection.getResponseCode());
        }
    }

    private void disconnect() {
        Log.d(TAG, "the current connection is disconnected");
        if (null != mConnection) {
            mConnection.disconnect();
            mConnection = null;
        }
    }

    public String getDataFromUrl(String url) throws IOException {
        connect(url);
        InputStream input = null;
        BufferedReader reader = null;
        try {
            input = mConnection.getInputStream();
            if (null == input) {
                Log.e(TAG, "Error in getInputStream from connection");
                return null;
            }
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(input));
            String data = "";
            while ((data = reader.readLine()) != null) {
                buffer.append(data + "\n");
            }
            return buffer.toString();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }

                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error occur during close. " + e);
            }
            disconnect();
        }
    }

    public Document getDocFromUrl(String url) throws IOException {
        String content = getDataFromUrl(url);
        if (null == content) {
            Log.e(TAG, "fail to get document form " + url);
            return null;
        }
        InputStream is = new ByteArrayInputStream(content.getBytes());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(is);
        } catch (Exception e) {
            Log.e(TAG, "Error occur during parse server data!" + e);
        }
        return doc;
    }

    public String getFakeData(String url) throws IOException{
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = mContext.getResources().getAssets()
                    .open("all_station_names.dat");
            br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String data = "";
            while ((data = br.readLine()) != null) {
                sb.append(data + "\n");
            }
            return sb.toString();
        } finally {
            try {
                if (br != null) {
                    br.close();
                    br = null;
                }
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                Log.d(TAG, "Error during close resource! e:" + e);
            }
        }
    }
}
