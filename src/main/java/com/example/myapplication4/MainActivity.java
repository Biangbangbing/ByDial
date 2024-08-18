package com.example.myapplication4;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

import static android.content.ContentValues.TAG;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button mBtLogin;
    private Button mBtRegister;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtLogin = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        final String[] users_username = {username.getText().toString()};
        final String[] users_password = {password.getText().toString()};
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Drawable drawable = getDrawable(R.drawable.username);
        drawable.setBounds(0,0,80,80); //是长和宽不是右和下
        username.setCompoundDrawables(drawable,null,null,null);
        password.setCompoundDrawables(drawable,null,null,null);


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                users_username[0] = s.toString();
                Log.d("edittextChange", s.toString());
                System.out.println("usernameChange:" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                users_username[0] = s.toString();
                Log.d("edittextAfter", s.toString());
                System.out.println("usernameAfter:" + s.toString());
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                users_password[0] = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                users_password[0] = s.toString();

            }
        });

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, users_username[0], Toast.LENGTH_LONG).show();
                System.out.println("username:" + users_username[0]);
                System.out.println("password:" + users_password[0]);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SendMess sendMess = new SendMess();
                        String user_name = username.getText().toString().trim();
                        String pass_word = password.getText().toString().trim();
                        if(user_name.equals("") || pass_word.equals("")){
                            Looper.prepare();
                            Toast.makeText(MainActivity.this,"用户名/密码不能为空",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        boolean result = false;
                        JSONObject jsonObject = new JSONObject();
                        try {
//                            jsonObject.put("username",user_name);
//                            jsonObject.put("password",pass_word);
                            jsonObject.put("username",user_name);
                            jsonObject.put("password",pass_word);
                            //切换本地和远程链接
                            result = LocalLogin(jsonObject);
//                            result = RemoteLogin(jsonObject);


//                            JSONObject  res = sendMess.post("http://192.168.43.187:8081/login?",jsonObject.toString());

//keyong                      JSONObject  res = sendMess.get("http://47.98.101.147:8081/users/find?username="+user_name);


//                           JSONObject  res = sendMess.post("http://47.98.101.147:8081/users/add?",jsonObject.toString());
//                            System.out.println("登录后返回String："+res.toString());
////                            System.out.println("code:"+res.get("code"));
//                            System.out.println(res.get("code").getClass().toString());
//                            if (res.get("code").toString().equals("1"))
//                                result = true;
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            result = false;
                        }
                        if(result==false){
                            Looper.prepare();
                            System.out.println("登录不成功。");
                            Toast.makeText(MainActivity.this,"用户不存在/密码错误！",Toast.LENGTH_SHORT).show();
                            Looper.loop();

                        }else{
                            Looper.prepare();
                            Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            System.out.println("跳转之前");
                            Intent intent = new Intent(MainActivity.this,IndexActivity.class);
                            intent.putExtra("username",user_name);
                            startActivity(intent);
                            System.out.println("跳转之后");
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });
        mBtRegister = (Button) findViewById(R.id.register);
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("username:" + users_username[0]);
                System.out.println("password:" + users_password[0]);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SendMess sendMess = new SendMess();
                        String user_name = username.getText().toString().trim();
                        String pass_word = password.getText().toString().trim();
                        JSONObject jsonObject = new JSONObject();
                        try {//192.168.43.187
//                            jsonObject.put("username",user_name);
//                            jsonObject.put("password",pass_word);
                            jsonObject.put("username",user_name);
                            jsonObject.put("password",pass_word);
//                            System.out.println(jsonObject.toString());
                            LocalRegister(jsonObject);
//                            RemoteRegister(jsonObject);



//                            JSONObject res = sendMess.post("http://192.168.43.187:8081/register?",jsonObject.toString());
                            //String  res = sendMess.post("http://192.168.43.187:8080/register?",jsonObject.toString());

//                            JSONObject  res = sendMess.post("http://47.98.101.147:8081/users/add?username="+user_name+"&pass="+pass_word,"");
//                            JSONObject  res = sendMess.post("http://47.98.101.147:8081/users/add?",jsonObject.toString());
//                            System.out.println("登录后返回String："+res.toString());

                            //JSONObject jsonObject = sendMess.get("http://192.168.43.187:8080/login?username="+user_name+"&password="+pass_word);
//                            System.out.println("尝试put的json："+jsonObject);
//                            if (res.get("code").toString().equals("1")){
//                                Looper.prepare();
//                                Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//                                Looper.loop();
//                            }else{
//                                String str = (String) res.get("msg");
//                                Looper.prepare();
//                                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
//                                Looper.loop();
//                            }
//
//                            Looper.prepare();
//                            Toast.makeText(MainActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
//                            Looper.loop();

//                        }else{
//                            Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//                        }
//                            JSONObject  jsonresult = sendMess.post("http://47.98.101.147:8081/users/add?",jsonObject.toString());
                            //JSONObject jsonresult = sendMess.get("http://47.98.101.147:8081/users/add?username="+user_name+"&pass="+pass_word);

//                            JSONObject jsonresult = sendMess.get("http://47.98.101.147:8081/users/find?username="+user_name);
//                            System.out.println(jsonresult.get("username"));
//                            System.out.println(jsonresult.get("pass"));
//                            System.out.println(jsonresult.keys());

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




//                        URL url = null;
//                        try {
//                            url = new URL("http://47.98.101.147:8081/users/find?username=dyh");
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                            System.out.println("url不成功");
//                        }

//                        try {
//
//                            String str = null;
//                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                            httpURLConnection.setRequestMethod("GET");
//                            httpURLConnection.getInputStream();
//                            httpURLConnection.setReadTimeout(5000);
////                            httpURLConnection.setDoOutput(true);
////                            OutputStream os = httpURLConnection.getOutputStream();
////                            os.write(str.getBytes());
//
//                            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
//                                System.out.println("获取成功！");
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            System.out.println("visit不成功");
//                        }


//                        MysqlCon mysqlCon = new MysqlCon();
//                        String user_name = username.getText().toString().trim();
//                        String pass_word = password.getText().toString().trim();
                        //MysqlCon mysqlCon = new MysqlCon();
//                        boolean result = false;
//                        try {
//                            System.out.println("reg2"+user_name+pass_word);
//                            result = mysqlCon.register(user_name,pass_word);
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        }
//                        if(!result){
//                            Looper.prepare();
//                            Toast.makeText(MainActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//
//                        }else{
//                            Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//                        }
//                        Log.i(TAG,"login:"+result);
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode==RESULT_OK){
                String strback =data.getStringExtra("back");
                Toast.makeText(MainActivity.this,strback,Toast.LENGTH_SHORT);
            }
        }
    }

    private Boolean LocalLogin (JSONObject jsonObject) throws IOException, JSONException {
        SendMess sendMess = new SendMess();
        JSONObject  res = sendMess.post2("http://192.168.43.187:8081/login?",jsonObject.toString());//本地数据库要根据ip随时改ip
        System.out.println("登录后返回String："+res.toString());
//       System.out.println("code:"+res.get("code"));
        System.out.println(res.get("code").getClass().toString());
        if (res.get("code").toString().equals("1"))
            return true;
        return false;
    }

    private void LocalRegister (JSONObject jsonObject) throws IOException, JSONException {
        SendMess sendMess = new SendMess();
        JSONObject res = sendMess.post2("http://192.168.43.187:8081/register?",jsonObject.toString());
        if (res.get("code").toString().equals("1")){
            Looper.prepare();
            Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }else{
                String str = (String) res.get("msg");
                Looper.prepare();
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                Looper.loop();
        }

        Looper.prepare();
        Toast.makeText(MainActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    private Boolean RemoteLogin (JSONObject jsonObject) throws IOException, JSONException {
        SendMess sendMess = new SendMess();
        JSONObject  res = sendMess.get("http://47.98.101.147:8081/users/find?username="+jsonObject.get("username"));
        if(res.get("pass").equals(jsonObject.get("password"))){
            return true;
        }else
            return false;
//        System.out.println("登录后返回String："+res);
    }

    private void RemoteRegister (JSONObject jsonObject) throws IOException, JSONException {

        SendMess sendMess = new SendMess();
        String stringresult = sendMess.post("http://47.98.101.147:8081/users/add?username="+jsonObject.get("username")+"&pass="+jsonObject.get("password"),"");
        if (stringresult !=null){
            Looper.prepare();
            Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }else{
//            String str = (String) res.get("msg");
            Looper.prepare();
            Toast.makeText(MainActivity.this,"用户信息重复，请更改用户名",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

        Looper.prepare();
        Toast.makeText(MainActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
        Looper.loop();
        System.out.println(stringresult);
    }
}

