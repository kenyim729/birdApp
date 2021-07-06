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

public class UsaPlay extends AppCompatActivity {

    EditText player1,player2,player3,player4;
    SharedPreferences player,record,round,PrintRound,score;
    Button start,back,home,people;
    String play1,play2,play3,play4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usa_play);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        start = findViewById(R.id.start);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        player = getSharedPreferences("UsaPlayer", Context.MODE_PRIVATE);
        record = getSharedPreferences("UsaRecord", Context.MODE_PRIVATE);
        round = getSharedPreferences("UsaRound", Context.MODE_PRIVATE);
        PrintRound = getSharedPreferences("UsaPrintRound", Context.MODE_PRIVATE);
        score = getSharedPreferences("UsaScore", Context.MODE_PRIVATE);


        SharedPreferences.Editor Record = record.edit();
        SharedPreferences.Editor Round = round.edit();
        SharedPreferences.Editor print = PrintRound.edit();
        SharedPreferences.Editor Score = score.edit();

        Round.putInt("countingRound",1);
        Round.commit();

        Record.putString("recordPlayer1","");
        Record.putString("recordPlayer2","");
        Record.putString("recordPlayer3","");
        Record.putString("recordPlayer4","");
        Record.commit();

        print.putString("PrintRoundNumber","");
        print.commit();

        Score.putInt("PlayerScore1",0);
        Score.putInt("PlayerScore2",0);
        Score.putInt("PlayerScore3",0);
        Score.putInt("PlayerScore4",0);
        Score.commit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaPlay.this,secondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaPlay.this,MainActivity.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaPlay.this,setting.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1.getText().toString().isEmpty() || player2.getText().toString().isEmpty() ||
                        player3.getText().toString().isEmpty() || player4.getText().toString().isEmpty()) {
                    Toast.makeText(UsaPlay.this, "請輸入所有玩家名字", Toast.LENGTH_SHORT).show();

                } else if (player1.getText().toString().equals(player2.getText().toString()) ||
                        player1.getText().toString().equals(player3.getText().toString()) ||
                        player1.getText().toString().equals(player4.getText().toString()) ||
                        player2.getText().toString().equals(player3.getText().toString()) ||
                        player2.getText().toString().equals(player4.getText().toString()) ||
                        player3.getText().toString().equals(player4.getText().toString())) {
                    Toast.makeText(UsaPlay.this, "不要輸入重覆名字", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor playerName = player.edit();

                    play1 = player1.getText().toString();
                    play2 = player2.getText().toString();
                    play3 = player3.getText().toString();
                    play4 = player4.getText().toString();


                    playerName.putString("playerName1", play1);
                    playerName.putString("playerName2", play2);
                    playerName.putString("playerName3", play3);
                    playerName.putString("playerName4", play4);
                    playerName.commit();


                    Intent i = new Intent(UsaPlay.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

    }
}