package com.example.myapplication4;

import android.app.DownloadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

    public class SendMess {
        private OkHttpClient okhttpclient = new OkHttpClient();
        public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
//        public JSONObject get(String url) throws IOException, JSONException {
//            try {
//                Request request = new Request.Builder().url(url).build();
//                Response response = okhttpclient.newCall(request).execute();
//                JSONObject jsonObject = new JSONObject(response.body().string());
//                return jsonObject;
//            }catch (Exception e){
//                e.printStackTrace();
//                return null;
//            }
//        }
        public JSONObject post2(String url, String json) throws IOException {
            RequestBody requestBody = RequestBody.create(JSON,json);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            try {
                Response response = okhttpclient.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                return jsonObject;
                //return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        public JSONObject get(String url) throws IOException, JSONException {
            try {
                Request request = new Request.Builder().url(url).build();
                Response response = okhttpclient.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println(jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        public String post(String url, String json) throws IOException {
            RequestBody requestBody = RequestBody.create(JSON,json);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            try {
                Response response = okhttpclient.newCall(request).execute();
//                JSONObject jsonObject = new JSONObject(response.body().string());
                System.out.println(response.body().toString());
//                return jsonObject;
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

    }
