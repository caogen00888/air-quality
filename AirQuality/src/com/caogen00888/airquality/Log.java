package com.caogen00888.airquality;

public class Log {
    private static final String TAG = "MJDAQ";
    public static void d(String tag, String msg) {
        android.util.Log.d(TAG, "[" + tag + "] " + msg);
    }
    public static void e(String tag, String msg) {
        android.util.Log.e(TAG, "[" + tag + "] " + msg);
    }
    public static void v(String tag, String msg) {
        android.util.Log.v(TAG, "[" + tag + "] " + msg);
    }
    public static void i(String tag, String msg) {
        android.util.Log.i(TAG, "[" + tag + "] " + msg);
    }
    public static void w(String tag, String msg) {
        android.util.Log.w(TAG, "[" + tag + "] " + msg);
    }
    public static void wtf(String tag, String msg) {
        android.util.Log.wtf(TAG, "[" + tag + "] " + msg);
    }
}
