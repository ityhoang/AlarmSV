package com.sict.alarmsv.database;

import com.android.volley.VolleyError;
import org.json.JSONObject;

public interface Result {
    public void notifySuccess(String requestType, JSONObject response);
    public void notifySuccessString(String requestType, String response);
    public void LoadData(String requestType, JSONObject response);
    public void notifyError(String requestType, VolleyError error);
}