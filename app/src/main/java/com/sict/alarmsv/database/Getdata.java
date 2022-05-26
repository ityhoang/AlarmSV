package com.sict.alarmsv.database;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.alarmsv.model.Alarm;
import com.sict.alarmsv.ultil.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Getdata {
    final public static String TAG = "ok";
    Result mResultCallback = null;
    Context mContext;

    public Getdata(Result resultCallback, Context context) {
        this.mResultCallback = resultCallback;
        this.mContext = context;
    }

    public void getDataVolley(final String requestType, String url, final String nameclass) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (mResultCallback != null)
                        mResultCallback.notifySuccess(requestType, jsonObj);
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("class", nameclass);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void update(final String requestType, String url, final Alarm alarm) {
        final String gio = alarm.getHour_x() + "";
        final String phut = alarm.getMinute_x() + "";
        final String monhoc = alarm.getMonhoc() + "";
        final String giaovien = alarm.getAlarm_Name() + "";
        final String phong = alarm.getRoom() + "";
        final String thu = alarm.getThu() + "";
        final String tiet = alarm.getTiethoc() + "";
        final String chedo = alarm.getOnOff() + "";
        final String id = alarm.getId() + "";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                LoadDatas("GETCALL", Constants.Data, nameclass);
                if (mResultCallback != null)
                    mResultCallback.notifySuccessString(requestType, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("hour", gio);
                hashMap.put("minute", phut);
                hashMap.put("monhoc", monhoc);
                hashMap.put("giaovien", giaovien);
                hashMap.put("phong", phong);
                hashMap.put("thu", thu);
                hashMap.put("tiet", tiet);
                hashMap.put("chedo", chedo);
                hashMap.put("id", id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void PostBai(final String requestType, String url, final String cookie, final String cmt) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (mResultCallback != null)
                        mResultCallback.LoadData(requestType, jsonObj);
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("cookie", cookie);
                hashMap.put("msg", cmt);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void LoadDatas(final String requestType, String url, final String nameclass) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (mResultCallback != null)
                        mResultCallback.LoadData(requestType, jsonObj);
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("class", nameclass);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void insert(final String requestType, String url, final Alarm alarm, final String nameclass) {
        final String gio = alarm.getHour_x() + "";
        final String phut = alarm.getMinute_x() + "";
        final String monhoc = alarm.getMonhoc() + "";
        final String giaovien = alarm.getAlarm_Name() + "";
        final String phong = alarm.getRoom() + "";
        final String thu = alarm.getThu() + "";
        final String tiet = alarm.getTiethoc() + "";
        final String img = alarm.getImg() + "";
        final String chedo = alarm.getOnOff() + "";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                LoadDatas("GETCALL", Constants.Data, nameclass);
                if (mResultCallback != null)
                    mResultCallback.notifySuccessString(requestType, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("hour", gio);
                hashMap.put("minute", phut);
                hashMap.put("monhoc", monhoc);
                hashMap.put("giaovien", giaovien);
                hashMap.put("phong", phong);
                hashMap.put("thu", thu);
                hashMap.put("tiet", tiet);
                hashMap.put("img", img);
                hashMap.put("chedo", chedo);
                hashMap.put("nameclass", nameclass);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void delete(final String requestType, String url, final int alarmId) {
        final String id = alarmId + "";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                LoadDatas("GETCALL", Constants.Data, nameclass);
                if (mResultCallback != null)
                    mResultCallback.notifySuccessString(requestType, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void login(final String requestType, String url, final String user, final String pass) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mResultCallback != null)
                    mResultCallback.notifySuccessString(requestType, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null)
                    mResultCallback.notifyError(requestType, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("user", user);
                hashMap.put("password", pass);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
