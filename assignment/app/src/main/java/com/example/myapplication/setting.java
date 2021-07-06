package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.net.URL;

public class setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



    }

    public void payme(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://payme.hsbc/zeronight"));
        startActivity(i);
    }
}