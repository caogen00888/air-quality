package com.caogen00888.airquality;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ParseAQData<T> {
    private List<T> mResults = new ArrayList<T>();
    public final List<T> parseData(Object o) throws JSONException {
        mResults.clear();
        if (o instanceof JSONArray) {
            ParseArray((JSONArray)o);
        } else if (o instanceof JSONObject) {
            mResults.add(Parse((JSONObject) o));
        }
        return mResults;
    }
    
    protected abstract T Parse(JSONObject jo) throws JSONException;
    
    private void ParseArray(JSONArray ja) throws JSONException {
        for(int i = 0; i < ja.length(); i++) {
            mResults.add(Parse(ja.getJSONObject(i)));
        }
    }
}
