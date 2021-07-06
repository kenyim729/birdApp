package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JpRecord extends AppCompatActivity {

    SharedPreferences jp;
    TextView Player1,Player2,Player3,Player4,recording1,recording2,recording3,recording4,numberRecord;
    Button back,home,people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jp_record);

        jp = getApplication().getSharedPreferences("JpDetail", Context.MODE_PRIVATE);

        Player1 = findViewById(R.id.player1);
        Player2 = findViewById(R.id.player2);
        Player3 = findViewById(R.id.player3);
        Player4 = findViewById(R.id.player4);
        numberRecord = findViewById(R.id.recordNumber);
        recording1 = findViewById(R.id.record1);
        recording2 = findViewById(R.id.record2);
        recording3 = findViewById(R.id.record3);
        recording4 = findViewById(R.id.record4);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        Player1.setText(jp.getString("player1",""));
        Player2.setText(jp.getString("player2",""));
        Player3.setText(jp.getString("player3",""));
        Player4.setText(jp.getString("player4",""));

        recording1.setText(jp.getString("Player1Record",""));
        recording2.setText(jp.getString("Player2Record",""));
        recording3.setText(jp.getString("Player3Record",""));
        recording4.setText(jp.getString("Player4Record",""));
        numberRecord.setText(jp.getString("PrintRoundNumber",""));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpRecord.this, JpCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(JpRecord.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(JpRecord.this, MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .show();
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpRecord.this, setting.class);
                startActivity(i);
            }
        });
    }
}