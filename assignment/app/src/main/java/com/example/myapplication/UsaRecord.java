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

public class UsaRecord extends AppCompatActivity {
    SharedPreferences name,record,numberOfRecord;
    Button back,home,people;
    TextView Player1,Player2,Player3,Player4,recording1,recording2,recording3,recording4,numberRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usa_record);

        name = getApplication().getSharedPreferences("UsaPlayer", Context.MODE_PRIVATE);
        record = getApplication().getSharedPreferences("UsaRecord", Context.MODE_PRIVATE);
        numberOfRecord = getApplication().getSharedPreferences("UsaPrintRound", Context.MODE_PRIVATE);


        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);
        Player1 = findViewById(R.id.player1);
        Player2 = findViewById(R.id.player2);
        Player3 = findViewById(R.id.player3);
        Player4 = findViewById(R.id.player4);
        numberRecord = findViewById(R.id.recordNumber);
        recording1 = findViewById(R.id.record1);
        recording2 = findViewById(R.id.record2);
        recording3 = findViewById(R.id.record3);
        recording4 = findViewById(R.id.record4);


        Player1.setText(name.getString("playerName1",""));
        Player2.setText(name.getString("playerName2",""));
        Player3.setText(name.getString("playerName3",""));
        Player4.setText(name.getString("playerName4",""));

        recording1.setText(record.getString("recordPlayer1",""));
        recording2.setText(record.getString("recordPlayer2",""));
        recording3.setText(record.getString("recordPlayer3",""));
        recording4.setText(record.getString("recordPlayer4",""));
        numberRecord.setText(numberOfRecord.getString("PrintRoundNumber",""));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaRecord.this, UsaCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(UsaRecord.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(UsaRecord.this, MainActivity.class);
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
                Intent i = new Intent(UsaRecord.this, setting.class);
                startActivity(i);
            }
        });

    }
}