package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class UsaFinish extends AppCompatActivity {

    EditText point;
    Button player1,player2,player3,self,back,home,people;
    SharedPreferences name,winner,round,PrintRound,record,score;
    int Score,DoubleScore,mainerScore,win,Round,player1Score,player2Score,player3Score,player4Score;
    String RoundRecord,loser1,loser2,loser3,record1,record2,record3,record4,strScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usa_finish);

        point = findViewById(R.id.numberEaten);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        self = findViewById(R.id.self);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        name = getApplication().getSharedPreferences("UsaPlayer", Context.MODE_PRIVATE);
        winner = getApplication().getSharedPreferences("UsaWinner", Context.MODE_PRIVATE);
        round = getApplication().getSharedPreferences("UsaRound", Context.MODE_PRIVATE);
        record = getApplication().getSharedPreferences("UsaRecord", Context.MODE_PRIVATE);
        score = getApplication().getSharedPreferences("UsaScore", Context.MODE_PRIVATE);
        PrintRound = getApplication().getSharedPreferences("UsaPrintRound", Context.MODE_PRIVATE);

        //get player Score
        player1Score = score.getInt("PlayerScore1",0);
        player2Score = score.getInt("PlayerScore2",0);
        player3Score = score.getInt("PlayerScore3",0);
        player4Score = score.getInt("PlayerScore4",0);

        //get winner
        win = winner.getInt("winner", 0);

        //get round number and round number record
        Round = round.getInt("countingRound",0);
        RoundRecord = PrintRound.getString("PrintRoundNumber","");

        //get Button name
        if(win == 1){
            loser1 = name.getString("playerName2","");
            loser2 = name.getString("playerName3","");
            loser3 = name.getString("playerName4","");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        }

        else if(win == 2){
            loser1 = name.getString("playerName1","");
            loser2 = name.getString("playerName3","");
            loser3 = name.getString("playerName4","");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        }

        else if(win == 3){
            loser1 = name.getString("playerName1","");
            loser2 = name.getString("playerName2","");
            loser3 = name.getString("playerName4","");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        }

        else if(win == 4){
            loser1 = name.getString("playerName1","");
            loser2 = name.getString("playerName2","");
            loser3 = name.getString("playerName3","");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        }

        //get record
        record1 = record.getString("recordPlayer1","");
        record2 = record.getString("recordPlayer2","");
        record3 = record.getString("recordPlayer3","");
        record4 = record.getString("recordPlayer4","");

        //set edior
        SharedPreferences.Editor changingScore = score.edit();
        SharedPreferences.Editor RoundCounting = round.edit();
        SharedPreferences.Editor printRound = PrintRound.edit();
        SharedPreferences.Editor Record = record.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(UsaFinish.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(UsaFinish.this, MainActivity.class);
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
                Intent i = new Intent(UsaFinish.this, setting.class);
                startActivity(i);
            }
        });

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if(strScore.trim().equals("")){
                    Toast.makeText(UsaFinish.this, "請輸入分數",Toast.LENGTH_SHORT).show();
                }
                else{
                    Score = Integer.parseInt(strScore);

                    if(Score < 25){
                        Toast.makeText(UsaFinish.this, "分數必須大於25",Toast.LENGTH_SHORT).show();
                        Score = 0;
                    }
                    else{
                        DoubleScore = Score * 2;
                        mainerScore = Score * 4;
                    }
                }

                if(win == 1 && Score != 0){
                    player1Score += mainerScore;
                    player2Score -= DoubleScore;
                    player3Score -= Score;
                    player4Score -= Score;

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();

                }

                else if(win == 2 && Score != 0){
                    player1Score -= DoubleScore;
                    player2Score += mainerScore;
                    player3Score -= Score;
                    player4Score -= Score;

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 3 && Score != 0){
                    player1Score -= DoubleScore;
                    player2Score -= Score;
                    player3Score += mainerScore;
                    player4Score -= Score;

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 4 && Score != 0){
                    player1Score -= DoubleScore;
                    player2Score -= Score;
                    player3Score -= Score;
                    player4Score += mainerScore;

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                if(Score != 0){
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if(strScore.trim().equals("")){
                    Toast.makeText(UsaFinish.this, "請輸入分數",Toast.LENGTH_SHORT).show();
                }
                else{
                    Score = Integer.parseInt(strScore);

                    if(Score < 25){
                        Toast.makeText(UsaFinish.this, "分數必須大於25",Toast.LENGTH_SHORT).show();
                        Score = 0;
                    }
                    else{
                        DoubleScore = Score * 2;
                        mainerScore = Score * 4;
                    }
                }

                if(win == 1 && Score != 0){
                    player1Score += mainerScore;
                    player2Score -= Score;
                    player3Score -= DoubleScore;
                    player4Score -= Score;

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();

                }

                else if(win == 2 && Score != 0){
                    player1Score -= Score;
                    player2Score += mainerScore;
                    player3Score -= DoubleScore;
                    player4Score -= Score;

                    record1 += "-" + Score + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 3 && Score != 0){
                    player1Score -= Score;
                    player2Score -= DoubleScore;
                    player3Score += mainerScore;
                    player4Score -= Score;

                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 4 && Score != 0){
                    player1Score -= Score;
                    player2Score -= DoubleScore;
                    player3Score -= Score;
                    player4Score += mainerScore;

                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                if(Score != 0){
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if(strScore.trim().equals("")){
                    Toast.makeText(UsaFinish.this, "請輸入分數",Toast.LENGTH_SHORT).show();
                }
                else{
                    Score = Integer.parseInt(strScore);

                    if(Score < 25){
                        Toast.makeText(UsaFinish.this, "分數必須大於25",Toast.LENGTH_SHORT).show();
                        Score = 0;
                    }
                    else{
                        DoubleScore = Score * 2;
                        mainerScore = Score * 4;
                    }
                }

                if(win == 1 && Score != 0){
                    player1Score += mainerScore;
                    player2Score -= Score;
                    player3Score -= Score;
                    player4Score -= DoubleScore;

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();

                }

                else if(win == 2 && Score != 0){
                    player1Score -= Score;
                    player2Score += mainerScore;
                    player3Score -= Score;
                    player4Score -= DoubleScore;

                    record1 += "-" + Score + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 3 && Score != 0){
                    player1Score -= Score;
                    player2Score -= Score;
                    player3Score += mainerScore;
                    player4Score -= DoubleScore;

                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 4 && Score != 0){
                    player1Score -= Score;
                    player2Score -= Score;
                    player3Score -= DoubleScore;
                    player4Score += mainerScore;

                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                if(Score != 0){
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if(strScore.trim().equals("")){
                    Toast.makeText(UsaFinish.this, "請輸入分數",Toast.LENGTH_SHORT).show();
                }
                else{
                    Score = Integer.parseInt(strScore);

                    if(Score < 25){
                        Toast.makeText(UsaFinish.this, "分數必須大於25",Toast.LENGTH_SHORT).show();
                        Score = 0;
                    }
                    else{
                        DoubleScore = Score * 2;
                        mainerScore = Score * 6;
                    }
                }

                if(win == 1 && Score != 0){
                    player1Score += mainerScore;
                    player2Score -= DoubleScore;
                    player3Score -= DoubleScore;
                    player4Score -= DoubleScore;

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();

                }

                else if(win == 2 && Score != 0){
                    player1Score -= DoubleScore;
                    player2Score += mainerScore;
                    player3Score -= DoubleScore;
                    player4Score -= DoubleScore;

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 3 && Score != 0){
                    player1Score -= DoubleScore;
                    player2Score -= DoubleScore;
                    player3Score += mainerScore;
                    player4Score -= DoubleScore;

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                else if(win == 4 && Score != 0){
                    player1Score -= DoubleScore;
                    player2Score -= DoubleScore;
                    player3Score -= DoubleScore;
                    player4Score += mainerScore;

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1",record1);
                    Record.putString("recordPlayer2",record2);
                    Record.putString("recordPlayer3",record3);
                    Record.putString("recordPlayer4",record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1",player1Score);
                    changingScore.putInt("PlayerScore2",player2Score);
                    changingScore.putInt("PlayerScore3",player3Score);
                    changingScore.putInt("PlayerScore4",player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber",RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound",Round);
                    RoundCounting.commit();
                }

                if(Score != 0){
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });


















        
    }
}