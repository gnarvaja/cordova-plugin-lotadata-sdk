package com.lotadata.moments.plugin.utils;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonParser {
    public static String getJsParameterAsString(JSONArray data, int index) {
        try {
            return data.getString(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Double getJsParameterAsDouble(JSONArray data, int index) {
        try {
            return data.getDouble(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
