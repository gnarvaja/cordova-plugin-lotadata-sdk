package com.lotadata.moments.plugin.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

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

    public static Object wrap(Object o) {
        if (o == null) {
            return "null";
        }

        if (o.getClass().isArray()) {
            JSONArray innerArray = new JSONArray();
            final int length = Array.getLength(o);
            for (int i = 0; i < length; ++i) {
                innerArray.put(wrap(Array.get(o, i)));
            }
            return innerArray;
        }

        if (o instanceof JSONArray || o instanceof JSONObject) {
            return o;
        }
        try {
            if (o instanceof Collection) {
                return new JSONArray((Collection) o);
            }
            if (o instanceof Map) {
                return new JSONObject((Map) o);
            }
            if (o instanceof Boolean ||
                    o instanceof Byte ||
                    o instanceof Character ||
                    o instanceof Double ||
                    o instanceof Float ||
                    o instanceof Integer ||
                    o instanceof Long ||
                    o instanceof Short ||
                    o instanceof String) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
        } catch (Exception ignored) {
        }
        return "null";
    }
}
