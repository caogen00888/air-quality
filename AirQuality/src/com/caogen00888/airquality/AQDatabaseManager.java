package com.caogen00888.airquality;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gmj on 2015/12/5.
 *
 字段  字段说明
 aqi     空气质量指数(AQI)，即air quality index，是定量描述空气质量状况的无纲量指数
 area    城市名称
 position_name   监测点名称
 station_code    监测点编码
 so2     二氧化硫1小时平均
 so2_24h     二氧化硫24小时滑动平均
 no2     二氧化氮1小时平均
 no2_24h     二氧化氮24小时滑动平均
 pm10    颗粒物（粒径小于等于10μm）1小时平均
 pm10_24h    颗粒物（粒径小于等于10μm）24小时滑动平均
 co  一氧化碳1小时平均
 co_24h  一氧化碳24小时滑动平均
 o3  臭氧1小时平均
 o3_24h  臭氧日最大1小时平均
 o3_8h   臭氧8小时滑动平均
 o3_8h_24h   臭氧日最大8小时滑动平均
 pm2_5   颗粒物（粒径小于等于2.5μm）1小时平均
 pm2_5_24h   颗粒物（粒径小于等于2.5μm）24小时滑动平均
 primary_pollutant   首要污染物
 quality     空气质量指数类别，有“优、良、轻度污染、中度污染、重度污染、严重污染”6类
 time_point  数据发布的时间
 */
public class AQDatabaseManager {
    public static final String TABLE_AIR_QUALITY = "air_quality";
    public static final long AIR_QUALITY_UPDATE_INTERNAL = 30 * 60 * 1000; //ms
    public static final String COLUMN_AQ_AQI = "aqi";
    public static final String COLUMN_AQ_AREA = "area";
    public static final String COLUMN_AQ_POSITION_NAME = "position_name";
    public static final String COLUMN_AQ_STATION_CODE = "station_code";
    public static final String COLUMN_AQ_SO2 = "so2";
    public static final String COLUMN_AQ_SO2_24H = "so2_24h";
    public static final String COLUMN_AQ_NO2 = "no2";
    public static final String COLUMN_AQ_NO2_24H = "no2_24h";
    public static final String COLUMN_AQ_PM10 = "pm10";
    public static final String COLUMN_AQ_PM10_24H = "pm10_24h";
    public static final String COLUMN_AQ_CO = "co";
    public static final String COLUMN_AQ_CO_24H = "co_24h";
    public static final String COLUMN_AQ_O3 = "o3";
    public static final String COLUMN_AQ_O3_24H = "o3_24h";
    public static final String COLUMN_AQ_O3_8H = "o3_8h";
    public static final String COLUMN_AQ_O3_8H_24H = "o3_8h_24h";
    public static final String COLUMN_AQ_PM2_5 = "pm2_5";
    public static final String COLUMN_AQ_PM2_5_24H = "pm2_5_24h";
    public static final String COLUMN_AQ_PRIMARY_POLLUTANT = "primary_pollutant";
    public static final String COLUMN_AQ_QUALITY = "quality";
    public static final String COLUMN_AQ_TIME_POINT = "time_point";
    public static final String COLUMN_AQ_STATION_ID = "station_id";
    
    public static final String TABLE_STATION = "stations";
    public static final String TABLE_CITY = "cities";
    public static final long CITY_UPDATE_INTERNAL = Long.MAX_VALUE; //ms
    public static final long STATION_UPDATE_INTERNAL = Long.MAX_VALUE; //ms
    public static final String COLUMN_CITY_NAME = "city";
    public static final String COLUMN_STATION_CODE = "station_code";
    public static final String COLUMN_STATION_NAME = "station_name";
    public static final String COLUMN_STATION_CITY_ID = "city_id";
    public static final String ID = "_id";
    public static final String TABLE_UPDATE = "update";
    public static final String COLUMN_UPDATE_TABLE = "table";
    public static final String COLUMN_UPDATE_SERVER_TIME = "server_time";
    public static final String COLUMN_UPDATE_LOCAL_TIME = "local_time";
    private Context mContext;
    private static AQDatabaseManager sInstance = null;
    private final SQLiteOpenHelper mDbHelper;
    private static final String DB_NAME = "air_quality.db";
    private static final int DB_VERSION = 1;

    private class AQDbHelper extends SQLiteOpenHelper {

        private AQDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_UPDATE + "("
            + COLUMN_UPDATE_TABLE + " VARCHAR NOT NULL, "
            + COLUMN_UPDATE_SERVER_TIME + " INTEGER NOT NULL, " +
                    COLUMN_UPDATE_LOCAL_TIME + " INTEGER NOT NULL" + ")");
            ContentValues cv = new ContentValues();
            cv.clear();
            cv.put(COLUMN_UPDATE_TABLE, TABLE_CITY);
            cv.put(COLUMN_UPDATE_SERVER_TIME, 0);
            cv.put(COLUMN_UPDATE_SERVER_TIME, 0);
            db.insert(TABLE_UPDATE, null,cv);

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CITY + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CITY_NAME + " VARCHAR UNIQUE NOT NULL" + ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATION + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_STATION_CITY_ID + " INTEGER NOT NULL,"
                    + COLUMN_STATION_NAME + " VARCHAR NOT NULL,"
                    + COLUMN_STATION_CODE + " VARCHAR NOT NULL" + ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_AIR_QUALITY + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_AQ_STATION_ID + " INTEGER NOT NULL,"
                    + COLUMN_AQ_SO2 + " INTEGER NOT NULL,"
                    + COLUMN_AQ_SO2_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_NO2 + " INTEGER NOT NULL,"
                    + COLUMN_AQ_NO2_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_PM10 + " INTEGER NOT NULL,"
                    + COLUMN_AQ_PM10_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_CO + " INTEGER NOT NULL,"
                    + COLUMN_AQ_CO_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_O3 + " INTEGER NOT NULL,"
                    + COLUMN_AQ_O3_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_O3_8H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_O3_8H_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_PM2_5 + " INTEGER NOT NULL,"
                    + COLUMN_AQ_PM2_5_24H + " INTEGER NOT NULL,"
                    + COLUMN_AQ_QUALITY + " INTEGER NOT NULL,"
                    + COLUMN_AQ_TIME_POINT + " INTEGER NOT NULL,"
                    + COLUMN_AQ_PRIMARY_POLLUTANT + " VARCHAR NOT NULL" + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private AQDatabaseManager(Context context) {
        mContext = context;
        mDbHelper = new AQDbHelper(mContext,DB_NAME,null,DB_VERSION);
    }

    public static AQDatabaseManager getInstance(Context context) {
        if (sInstance != null) {
            synchronized (AQDatabaseManager.class) {
                if (sInstance == null) {
                    sInstance = new AQDatabaseManager(context);
                }
            }
        }
        return sInstance;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            return db.delete(table,whereClause,whereArgs);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    public Cursor query(String table,String[] columns,String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            return db.query(table,columns,selection, selectionArgs, groupBy, having, orderBy);
        } finally {
            db.close();
        }
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            return db.insert(table,nullColumnHack,values);
        } finally {
            db.close();
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            return db.update(table,values,whereClause,whereArgs);
        } finally {
            db.close();
        }
    }

    public void parseAQJSON(String url, String json) {
        try {
            new ParseCity().parseJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static abstract class ParseAQJson {
        List<String> parseKeys = new ArrayList<String>();
        ContentValues cv = new ContentValues();
        public final void parseJson(String json) throws JSONException {
            Object o = new JSONTokener(json).nextValue();
            if (o instanceof JSONArray) {
                parseArray((JSONArray) o);
            } else if (o instanceof JSONObject) {
                parse((JSONObject) o);
            }
        }

        protected void parse(JSONObject jo) throws JSONException {
            cv.clear();
            for (String key : parseKeys) {
                cv.put(key, jo.getString(key));
            }
        }

        private void parseArray(JSONArray ja) throws JSONException {
            for(int i = 0; i < ja.length(); i++) {
                parse(ja.getJSONObject(i));
            }
        }
    }

    private class ParseCity extends ParseAQJson {
        public ParseCity() {
            parseKeys.add(COLUMN_CITY_NAME);
        }

        @Override
        protected void parse(JSONObject jo) throws JSONException {
            super.parse(jo);
            new ParseStation(insert(TABLE_CITY, null, cv)).parse(jo.getJSONObject("stations"));
        }
    }

    private class ParseStation extends ParseAQJson {
        private final long pId;
        public ParseStation(long cityId) {
            pId = cityId;
            Collections.addAll(parseKeys, new String[]{COLUMN_STATION_CODE,
                    COLUMN_STATION_NAME});
        }

        @Override
        protected void parse(JSONObject jo) throws JSONException {
            super.parse(jo);
            cv.put(COLUMN_STATION_CITY_ID,pId);
            insert(TABLE_STATION,null,cv);
        }
    }
    
    private class ParseAirQuality extends ParseAQJson {
        private final String[] keys = new String[] { COLUMN_AQ_AREA,
                COLUMN_AQ_POSITION_NAME, COLUMN_AQ_STATION_CODE,
                COLUMN_AQ_SO2, COLUMN_AQ_SO2_24H, COLUMN_AQ_NO2,
                COLUMN_AQ_NO2_24H, COLUMN_AQ_PM10, COLUMN_AQ_PM10_24H,
                COLUMN_AQ_CO, COLUMN_AQ_CO_24H, COLUMN_AQ_O3,
                COLUMN_AQ_O3_24H, COLUMN_AQ_O3_8H, COLUMN_AQ_O3_8H_24H,
                COLUMN_AQ_PM2_5, COLUMN_AQ_PM2_5_24H,
                COLUMN_AQ_PRIMARY_POLLUTANT, COLUMN_AQ_QUALITY,
                COLUMN_AQ_TIME_POINT };
        private final SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss");

        public ParseAirQuality() {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Collections.addAll(parseKeys, keys);
        }
        @Override
        protected void parse(JSONObject jo) throws JSONException {
            cv.clear();
            String area = null;
            String position_name = null;
            String station_code = null;
            try {
                for (String key : keys) {
                    if (COLUMN_AQ_POSITION_NAME.equals(key)) {
                        position_name = jo.getString(key);
                    } else if (COLUMN_AQ_STATION_CODE.equals(key)) {
                        station_code = jo.getString(key);
                    } else if (COLUMN_AQ_AREA.equals(key)) {
                        area = jo.getString(key);
                    } else if (COLUMN_AQ_PRIMARY_POLLUTANT.equals(key)) {
                        cv.put(key, jo.getString(key));
                    }else if (COLUMN_AQ_TIME_POINT.equals(key)) {
                        cv.put(key, sdf.parse(jo.getString(key)).getTime());
                    } else {
                        cv.put(key, jo.getInt(key));
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (area != null && position_name != null && station_code != null) {
                Cursor c = query(TABLE_CITY, new String[] { ID },
                        COLUMN_CITY_NAME + "=?", new String[] { area }, null,
                        null, null);
                long id = c.getLong(c.getColumnIndex(ID));
                c.close();
                c = query(TABLE_STATION, new String[] { ID }, COLUMN_STATION_CITY_ID
                        + "=? AND " + COLUMN_STATION_CODE + "=? AND "
                        + COLUMN_STATION_NAME + "=?", new String[] {
                        Long.valueOf(id).toString(), station_code,
                        position_name }, null, null, null);
                id = c.getLong(c.getColumnIndex(ID));
                c.close();
                cv.put(COLUMN_AQ_STATION_ID, id);
                insert(TABLE_AIR_QUALITY, null, cv);
            }
        }
    }
}
