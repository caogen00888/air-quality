package com.caogen00888.airquality;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;

public class AirQualityService extends Service {
    private static final String TAG = "AirQualityService";
    private HandlerThread mHttpThread = null;
    private HttpHandler mHttpHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpThread = new HandlerThread("HttpThread");
        mHttpHandler = new HttpHandler(mHttpThread.getLooper());
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    private class HttpHandler extends Handler{
        public HttpHandler(Looper looper) {
            super(looper);
        }
    }
}
