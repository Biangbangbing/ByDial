package com.example.myapplication4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IndexActivity extends AppCompatActivity {
    private TextView textView ;
    private Button backButton ;

    public String username;

//    public static void setUsername(String username) {
//        this.username = username;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        textView = findViewById(R.id.welcome);
        backButton = findViewById(R.id.backBtn);
        String username = getIntent().getStringExtra("username");
        textView.setText("亲爱的"+username+"欢迎来到 by的世界");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("back","欢迎回到登录界面");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

}