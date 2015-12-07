package com.caogen00888.airquality;

import org.json.JSONException;
import org.json.JSONObject;

import com.caogen00888.airquality.AQDataType.AQCityType;
import com.caogen00888.airquality.AQDataType.AQStationType;

public class ParseAQTools {
    public static class ParseCityType extends ParseAQData<AQCityType> {

        @Override
        protected AQCityType Parse(JSONObject jo) throws JSONException {
            AQCityType item = new AQCityType();
            item.city = jo.getString("city");
            item.stations = new ParseStationType().parseData(jo.get("stations"));
            return item;
        }
    }
    public static class ParseStationType extends ParseAQData<AQStationType> {

        @Override
        protected AQStationType Parse(JSONObject jo) throws JSONException {
            AQStationType item = new AQStationType();
            item.station_code = jo.getString("station_code");
            item.station_name = jo.getString("station_name");
            return item;
        }
    }
}
