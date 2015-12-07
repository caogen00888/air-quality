package com.caogen00888.airquality;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.caogen00888.airquality.ParseAQTools.ParseCityType;


public class MainActivity extends ActionBarActivity implements OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String mAirHttp = "http://www.pm25.in/api/querys/all_cities.json";
    private static final String mStationNamesHttp = "http://www.pm25.in/api/querys/station_names.json";
    private static final String TOKEN_VALUE = "5j1znBVAsnSf5xQyNQyq";
    private static final String TOKEN_KEY = "token";
    private TextView mMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.btn_refresh)).setOnClickListener(this);
        mMsg = (TextView)findViewById(R.id.tv_msg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (R.id.btn_refresh == v.getId()) {
            mMsg.setText(R.string.msg_refreshing);
            new AsyncTask<String, Void, String>() {

                @Override
                protected void onPostExecute(String result) {
                    Log.d(TAG,"Msg:" + result);
                    JSONTokener token = new JSONTokener(result);
                    Log.d(TAG, "======================================");
                    try {
                        Log.d(TAG, "" + new ParseCityType().parseData(token.nextValue()));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.d(TAG, "======================================");
                    mMsg.setText(result);
                    super.onPostExecute(result);
                }

                @Override
                protected String doInBackground(String... params) {
                    String url = params[0];
                    HttpConnectHelper helper = new HttpConnectHelper(MainActivity.this);
                    try {
                        return helper.getFakeData(url);
                    } catch (IOException e) {
                        Log.e(TAG, "Error occured during get data from url:" + url);
                    }
                    return null;
                }
                
            }.execute(mStationNamesHttp + "?" + TOKEN_KEY + "=" + TOKEN_VALUE);
        }
    }
}
