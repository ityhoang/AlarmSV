package com.sict.alarmsv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.sict.alarmsv.R;
import com.sict.alarmsv.database.Getdata;
import com.sict.alarmsv.database.Result;
import com.sict.alarmsv.model.Alarm;
import com.sict.alarmsv.model.Session;
import com.sict.alarmsv.ultil.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {
    @BindView(R.id.username)
    public EditText username;
    @BindView(R.id.password)
    public EditText password;
    @BindView(R.id.login)
    public Button login;
    private Session session;
    private Getdata getDataAlram;
    public Result mResultCallback = null;
    public String checklogin = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        session = new Session(getApplicationContext());
        initVolleyCallback();
        getDataAlram = new Getdata(mResultCallback,this);
    }
    @OnClick(R.id.login)
    public void Onclick(View view){
        String usernames = username.getText().toString();
        String passwords = password.getText().toString();
        getDataAlram.login("login",Constants.Login,usernames,passwords);

    }

    public void initVolleyCallback(){
        mResultCallback = new Result() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
            }

            @Override
            public void notifySuccessString(String requestType, String response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
                checklogin = response;
                if(checklogin.equals("false")){
                    Toast.makeText(Login.this,"Sai tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        JSONArray jsonArray = jsonObj.getJSONArray("DanhSach");
                        JSONObject jsonObject = null;
                        String users = null;
                        String passwords = null;
                        for (int a = 0; a < jsonArray.length(); a++) {
                            jsonObject = jsonArray.getJSONObject(a);
                            users = (jsonObject.getString("user"));
                            passwords = (jsonObject.getString("password"));
                            session.createSession(users,passwords);
                            Intent i = new Intent(Login.this,AlarmMainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void LoadData(String requestType, JSONObject response) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + response);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d("ok", "Volley requester " + requestType);
                Log.d("ok", "Volley JSON post " + "That didn't work!");
            }
        };
    }

}
