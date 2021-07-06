package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JpPlay extends AppCompatActivity {

    SharedPreferences Jp;
    EditText player1, player2, player3, player4;
    String name1, name2, name3, name4;
    Button start,back,home,people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jp_play);

        Jp = getSharedPreferences("JpDetail", Context.MODE_PRIVATE);
        start = findViewById(R.id.start);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        SharedPreferences.Editor JpEdit = Jp.edit();

        JpEdit.putInt("score1", 25000);
        JpEdit.putInt("score2", 25000);
        JpEdit.putInt("score3", 25000);
        JpEdit.putInt("score4", 25000);

        JpEdit.putInt("RoundNumber", 1);
        JpEdit.putString("PrintRoundNumber", "");

        JpEdit.putString("Player1Record", "");
        JpEdit.putString("Player2Record", "");
        JpEdit.putString("Player3Record", "");
        JpEdit.putString("Player4Record", "");

        JpEdit.putInt("winner",1);

        JpEdit.putInt("stand1",0);
        JpEdit.putInt("stand2",0);
        JpEdit.putInt("stand3",0);
        JpEdit.putInt("stand4",0);

        JpEdit.putInt("StandEdit",0);


        JpEdit.commit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpPlay.this,secondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpPlay.this,MainActivity.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpPlay.this,setting.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1.getText().toString().isEmpty() || player2.getText().toString().isEmpty() ||
                        player3.getText().toString().isEmpty() || player4.getText().toString().isEmpty()) {
                    Toast.makeText(JpPlay.this, "請輸入所有玩家名字", Toast.LENGTH_SHORT).show();

                } else if (player1.getText().toString().equals(player2.getText().toString()) ||
                        player1.getText().toString().equals(player3.getText().toString()) ||
                        player1.getText().toString().equals(player4.getText().toString()) ||
                        player2.getText().toString().equals(player3.getText().toString()) ||
                        player2.getText().toString().equals(player4.getText().toString()) ||
                        player3.getText().toString().equals(player4.getText().toString())) {
                    Toast.makeText(JpPlay.this, "不要輸入重覆名字", Toast.LENGTH_SHORT).show();
                } else {
                    name1 = player1.getText().toString();
                    name2 = player2.getText().toString();
                    name3 = player3.getText().toString();
                    name4 = player4.getText().toString();


                    JpEdit.putString("player1", name1);
                    JpEdit.putString("player2", name2);
                    JpEdit.putString("player3", name3);
                    JpEdit.putString("player4", name4);
                    JpEdit.commit();


                    Intent i = new Intent(JpPlay.this, JpCounting.class);
                    startActivity(i);
                }
            }
        });
    }
}