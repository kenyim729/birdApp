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

public class TwRecording extends AppCompatActivity {

    SharedPreferences name,record1,record2,record3,record4,numberOfRecord;
    TextView Player1,Player2,Player3,Player4,recording1,recording2,recording3,recording4,numberRecord;
    Button back,home,people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tw_recording);

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


        name = getApplication().getSharedPreferences("TwPlayer_name", Context.MODE_PRIVATE);
        record1 = getApplication().getSharedPreferences("TwRecordPlayer1", Context.MODE_PRIVATE);
        record2 = getApplication().getSharedPreferences("TwRecordPlayer2", Context.MODE_PRIVATE);
        record3 = getApplication().getSharedPreferences("TwRecordPlayer3", Context.MODE_PRIVATE);
        record4 = getApplication().getSharedPreferences("TwRecordPlayer4", Context.MODE_PRIVATE);
        numberOfRecord = getApplication().getSharedPreferences("TwStrRecord", Context.MODE_PRIVATE);

        Player1.setText(name.getString("player1",""));
        Player2.setText(name.getString("player2",""));
        Player3.setText(name.getString("player3",""));
        Player4.setText(name.getString("player4",""));

        recording1.setText(record1.getString("recording1",""));
        recording2.setText(record2.getString("recording2",""));
        recording3.setText(record3.getString("recording3",""));
        recording4.setText(record4.getString("recording4",""));
        numberRecord.setText(numberOfRecord.getString("recording",""));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwRecording.this, TwCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(TwRecording.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwRecording.this, MainActivity.class);
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
                Intent i = new Intent(TwRecording.this, setting.class);
                startActivity(i);
            }
        });


    }
}