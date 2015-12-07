package com.caogen00888.airquality;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;

public class ConnectionManager {
    private static final String APP_KEY = "5j1znBVAsnSf5xQyNQyq";
    private Context mContext;
    private ConnectivityManager mConnectivityManager;
    private LocationManager mLocationManager;
    public ConnectionManager(Context context) {
        mContext = context;
        mConnectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        mLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
    }
}
