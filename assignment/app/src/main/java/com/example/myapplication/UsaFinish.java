package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.myapplication.model.CountRecord;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UsaFinish extends AppCompatActivity {

    EditText point;
    Button player1,player2,player3,self,back,home,people,exit;
    SharedPreferences name,winner,round,PrintRound,record,score;
    int Score,DoubleScore,mainerScore,win,Round,player1Score,player2Score,player3Score,player4Score;
    String RoundRecord,loser1,loser2,loser3,record1,record2,record3,record4,strScore;
    int GameNo;

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
        exit = findViewById(R.id.exit);

        name = getApplication().getSharedPreferences("UsaPlayer", Context.MODE_PRIVATE);
        winner = getApplication().getSharedPreferences("UsaWinner", Context.MODE_PRIVATE);
        round = getApplication().getSharedPreferences("UsaRound", Context.MODE_PRIVATE);
        record = getApplication().getSharedPreferences("UsaRecord", Context.MODE_PRIVATE);
        score = getApplication().getSharedPreferences("UsaScore", Context.MODE_PRIVATE);
        PrintRound = getApplication().getSharedPreferences("UsaPrintRound", Context.MODE_PRIVATE);
        SharedPreferences game = getApplicationContext().getSharedPreferences("game", Context.MODE_PRIVATE);

        //set game number
        GameNo = game.getInt("game",0);

        //get user name
        SharedPreferences userId = getApplicationContext().getSharedPreferences("user_Id", Context.MODE_PRIVATE);
        String username = userId.getString("username", "");
        System.out.println("username: " + username);

        //get player Score
        player1Score = score.getInt("PlayerScore1", 0);
        player2Score = score.getInt("PlayerScore2", 0);
        player3Score = score.getInt("PlayerScore3", 0);
        player4Score = score.getInt("PlayerScore4", 0);

        //get winner
        win = winner.getInt("winner", 0);

        //get round number and round number record
        Round = round.getInt("countingRound", 0);
        RoundRecord = PrintRound.getString("PrintRoundNumber", "");

        //get Button name
        if (win == 1) {
            loser1 = name.getString("playerName2", "");
            loser2 = name.getString("playerName3", "");
            loser3 = name.getString("playerName4", "");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        } else if (win == 2) {
            loser1 = name.getString("playerName1", "");
            loser2 = name.getString("playerName3", "");
            loser3 = name.getString("playerName4", "");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        } else if (win == 3) {
            loser1 = name.getString("playerName1", "");
            loser2 = name.getString("playerName2", "");
            loser3 = name.getString("playerName4", "");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        } else if (win == 4) {
            loser1 = name.getString("playerName1", "");
            loser2 = name.getString("playerName2", "");
            loser3 = name.getString("playerName3", "");

            player1.setText(loser1);
            player2.setText(loser2);
            player3.setText(loser3);
        }

        //get record
        record1 = record.getString("recordPlayer1", "");
        record2 = record.getString("recordPlayer2", "");
        record3 = record.getString("recordPlayer3", "");
        record4 = record.getString("recordPlayer4", "");

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
                                Intent i = new Intent(UsaFinish.this, RealSecondPage.class);
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

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AlertDialog dialog = new AlertDialog.Builder(UsaFinish.this)
                        .setTitle("確認登出?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(UsaFinish.this, LoginActivity.class);
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

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if (strScore.trim().equals("")) {
                    Toast.makeText(UsaFinish.this, "請輸入分數", Toast.LENGTH_SHORT).show();
                } else {
                    Score = Integer.parseInt(strScore);

                    if (Score < 25) {
                        Toast.makeText(UsaFinish.this, "分數必須大於25", Toast.LENGTH_SHORT).show();
                        Score = 0;
                    } else {
                        DoubleScore = Score * 2;
                        mainerScore = Score * 4;
                    }
                }

                if (win == 1 && Score != 0) {
                    player1Score += mainerScore;
                    player2Score -= DoubleScore;
                    player3Score -= Score;
                    player4Score -= Score;

                    postData(GameNo, Round, mainerScore ,DoubleScore * -1, Score * -1,Score * -1, username);

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();

                }

                else if (win == 2 && Score != 0) {
                    player1Score -= DoubleScore;
                    player2Score += mainerScore;
                    player3Score -= Score;
                    player4Score -= Score;

                    postData(GameNo, Round, DoubleScore ,mainerScore , Score * -1,Score * -1, username);

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 3 && Score != 0) {
                    player1Score -= DoubleScore;
                    player2Score -= Score;
                    player3Score += mainerScore;
                    player4Score -= Score;

                    postData(GameNo, Round, DoubleScore ,Score * -1 , mainerScore,Score * -1, username);

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 4 && Score != 0) {
                    player1Score -= DoubleScore;
                    player2Score -= Score;
                    player3Score -= Score;
                    player4Score += mainerScore;

                    postData(GameNo, Round, DoubleScore ,Score * -1 , Score * -1,mainerScore, username);

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                if (Score != 0) {
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if (strScore.trim().equals("")) {
                    Toast.makeText(UsaFinish.this, "請輸入分數", Toast.LENGTH_SHORT).show();
                } else {
                    Score = Integer.parseInt(strScore);

                    if (Score < 25) {
                        Toast.makeText(UsaFinish.this, "分數必須大於25", Toast.LENGTH_SHORT).show();
                        Score = 0;
                    } else {
                        DoubleScore = Score * 2;
                        mainerScore = Score * 4;
                    }
                }

                if (win == 1 && Score != 0) {
                    player1Score += mainerScore;
                    player2Score -= Score;
                    player3Score -= DoubleScore;
                    player4Score -= Score;

                    postData(GameNo, Round, mainerScore ,Score * -1 , DoubleScore,Score * -1, username);

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();

                }

                else if (win == 2 && Score != 0) {
                    player1Score -= Score;
                    player2Score += mainerScore;
                    player3Score -= DoubleScore;
                    player4Score -= Score;

                    postData(GameNo, Round, Score * -1 ,mainerScore , DoubleScore,Score * -1, username);


                    record1 += "-" + Score + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 3 && Score != 0) {
                    player1Score -= Score;
                    player2Score -= DoubleScore;
                    player3Score += mainerScore;
                    player4Score -= Score;

                    postData(GameNo, Round, Score * -1 ,DoubleScore , mainerScore,Score * -1, username);

                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + Score + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 4 && Score != 0) {
                    player1Score -= Score;
                    player2Score -= DoubleScore;
                    player3Score -= Score;
                    player4Score += mainerScore;

                    postData(GameNo, Round, Score * -1 ,DoubleScore , Score * -1,mainerScore, username);


                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                if (Score != 0) {
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if (strScore.trim().equals("")) {
                    Toast.makeText(UsaFinish.this, "請輸入分數", Toast.LENGTH_SHORT).show();
                } else {
                    Score = Integer.parseInt(strScore);

                    if (Score < 25) {
                        Toast.makeText(UsaFinish.this, "分數必須大於25", Toast.LENGTH_SHORT).show();
                        Score = 0;
                    } else {
                        DoubleScore = Score * 2;
                        mainerScore = Score * 4;
                    }
                }

                if (win == 1 && Score != 0) {
                    player1Score += mainerScore;
                    player2Score -= Score;
                    player3Score -= Score;
                    player4Score -= DoubleScore;

                    postData(GameNo, Round, mainerScore ,Score * -1 , Score * -1,DoubleScore, username);

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();

                } else if (win == 2 && Score != 0) {
                    player1Score -= Score;
                    player2Score += mainerScore;
                    player3Score -= Score;
                    player4Score -= DoubleScore;

                    postData(GameNo, Round, Score * -1 ,mainerScore , Score * -1,DoubleScore, username);

                    record1 += "-" + Score + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + Score + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 3 && Score != 0) {
                    player1Score -= Score;
                    player2Score -= Score;
                    player3Score += mainerScore;
                    player4Score -= DoubleScore;

                    postData(GameNo, Round, Score * -1 ,Score * -1 , mainerScore ,DoubleScore, username);

                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 4 && Score != 0) {
                    player1Score -= Score;
                    player2Score -= Score;
                    player3Score -= DoubleScore;
                    player4Score += mainerScore;

                    postData(GameNo, Round, Score * -1 ,Score * -1 , DoubleScore ,mainerScore, username);


                    record1 += "-" + Score + "\n\n";
                    record2 += "-" + Score + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                if (Score != 0) {
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strScore = point.getText().toString();

                if (strScore.trim().equals("")) {
                    Toast.makeText(UsaFinish.this, "請輸入分數", Toast.LENGTH_SHORT).show();
                } else {
                    Score = Integer.parseInt(strScore);

                    if (Score < 25) {
                        Toast.makeText(UsaFinish.this, "分數必須大於25", Toast.LENGTH_SHORT).show();
                        Score = 0;
                    } else {
                        DoubleScore = Score * 2;
                        mainerScore = Score * 6;
                    }
                }

                if (win == 1 && Score != 0) {
                    player1Score += mainerScore;
                    player2Score -= DoubleScore;
                    player3Score -= DoubleScore;
                    player4Score -= DoubleScore;

                    postData(GameNo, Round, mainerScore ,DoubleScore * -1, DoubleScore * -1,DoubleScore * -1, username);

                    record1 += "+" + mainerScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();

                }

                else if (win == 2 && Score != 0) {
                    player1Score -= DoubleScore;
                    player2Score += mainerScore;
                    player3Score -= DoubleScore;
                    player4Score -= DoubleScore;

                    postData(GameNo, Round, DoubleScore * -1,mainerScore , DoubleScore * -1,DoubleScore * -1, username);

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "+" + mainerScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 3 && Score != 0) {
                    player1Score -= DoubleScore;
                    player2Score -= DoubleScore;
                    player3Score += mainerScore;
                    player4Score -= DoubleScore;

                    postData(GameNo, Round, DoubleScore * -1,DoubleScore * -1, mainerScore ,DoubleScore * -1, username);

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "+" + mainerScore + "\n\n";
                    record4 += "-" + DoubleScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                else if (win == 4 && Score != 0) {
                    player1Score -= DoubleScore;
                    player2Score -= DoubleScore;
                    player3Score -= DoubleScore;
                    player4Score += mainerScore;

                    postData(GameNo, Round, DoubleScore * -1,DoubleScore * -1, DoubleScore * -1,mainerScore, username);

                    record1 += "-" + DoubleScore + "\n\n";
                    record2 += "-" + DoubleScore + "\n\n";
                    record3 += "-" + DoubleScore + "\n\n";
                    record4 += "+" + mainerScore + "\n\n";
                    RoundRecord += Round + ".\n\n";
                    Round += 1;

                    Record.putString("recordPlayer1", record1);
                    Record.putString("recordPlayer2", record2);
                    Record.putString("recordPlayer3", record3);
                    Record.putString("recordPlayer4", record4);
                    Record.commit();

                    changingScore.putInt("PlayerScore1", player1Score);
                    changingScore.putInt("PlayerScore2", player2Score);
                    changingScore.putInt("PlayerScore3", player3Score);
                    changingScore.putInt("PlayerScore4", player4Score);
                    changingScore.commit();

                    printRound.putString("PrintRoundNumber", RoundRecord);
                    printRound.commit();

                    RoundCounting.putInt("countingRound", Round);
                    RoundCounting.commit();
                }

                if (Score != 0) {
                    Intent i = new Intent(UsaFinish.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });
    }
        private void postData (int game, int round, double player1, double player2, double player3, double player4, String username) {


            String postParams = "Game=" + game + " & "
                    + "Round=" + round + " & "
                    + "Player1=" + player1 + " & "
                    + "Player2=" + player2 + " & "
                    + "Player3=" + player3 + " & "
                    + "Player4=" + player4 + " & "
                    + "CreateUser=" + username
                    ;

            System.out.println (postParams);

            String urlString = "http://10.0.2.2:8080/lab_bird_php/postBird.php";
            MyAsynTask newTask = new MyAsynTask();
            newTask.execute(urlString, postParams);
        }

        private String postHttpURLConnection (String urlStr, String postParams) {

            String response = "";

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(urlStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                OutputStream outputStream = urlConnection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(postParams);

                dataOutputStream.flush();
                dataOutputStream.close();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                } else {
                    response = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }


        private class MyAsynTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String retStr = postHttpURLConnection(params[0], params[1]);
                return retStr;
            }

            @Override
            protected void onPostExecute(String retStr) {
                super.onPostExecute(retStr);

                System.out.println ("===============================");
                System.out.println(retStr);

//            status_TextView.setText(retStr);
                Toast.makeText(getApplicationContext(), retStr, Toast.LENGTH_LONG).show();
            }

        } // MyAsynTask
}