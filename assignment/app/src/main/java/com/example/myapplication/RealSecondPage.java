package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RealSecondPage extends AppCompatActivity {

    Button button1,button2,method,discuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_second_page);

        button1 = findViewById(R.id.mj);
        button2 = findViewById(R.id.setting);
        method = findViewById(R.id.method);
        discuss = findViewById(R.id.discuss);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent i = new Intent(RealSecondPage.this,secondPage.class);
                startActivity(i);
            }
        });

        method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealSecondPage.this,Introduce.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealSecondPage.this,setting.class);
                startActivity(i);
            }
        });

        discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RealSecondPage.this,Discuss.class);
                startActivity(i);
            }
        });
    }
}