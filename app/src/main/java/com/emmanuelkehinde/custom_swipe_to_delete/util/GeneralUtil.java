package com.emmanuelkehinde.custom_swipe_to_delete.util;

import org.json.JSONArray;
import org.json.JSONException;

public class GeneralUtil {

    public static JSONArray getJsonArray(String sb) {
        try {
            return new JSONArray(sb);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
