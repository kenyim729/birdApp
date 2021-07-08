package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TwCounting extends AppCompatActivity {

    SharedPreferences playerId,name,ShowMoney;
    Button playerOne,playerTwo,playerThree,playerFour,back,home,people,finish,exit;
    ImageButton GoCouting;
    int player1,player2,player3,player4;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tw_counting);

        playerId = getSharedPreferences("TwPlayer_Id", Context.MODE_PRIVATE);
        name = getApplicationContext().getSharedPreferences("TwPlayer_name", Context.MODE_PRIVATE);
        ShowMoney = getApplicationContext().getSharedPreferences("TwMoneyCounting", Context.MODE_PRIVATE);

        playerOne = findViewById(R.id.player1);
        playerTwo = findViewById(R.id.player2);
        playerThree = findViewById(R.id.player3);
        playerFour = findViewById(R.id.player4);
        GoCouting = findViewById(R.id.recordPage);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);
        finish = findViewById(R.id.finish);
        exit = findViewById(R.id.exit);

        player1 = ShowMoney.getInt("first" , 0);
        player2 = ShowMoney.getInt("second" , 0);
        player3 = ShowMoney.getInt("third" , 0);
        player4 = ShowMoney.getInt("fourth" , 0);

        String play1 = name.getString("player1","");
        String play2 = name.getString("player2","");
        String play3 = name.getString("player3","");
        String play4 = name.getString("player4","");

        //set name
        playerOne.setText(play1 + "\n\n" + player1);
        playerTwo.setText(play2 + "\n\n" + player2);
        playerThree.setText(play3 + "\n\n" + player3);
        playerFour.setText(play4 + "\n\n" + player4);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(TwCounting.this)
                        .setTitle("重新輸入玩家名字?")
                        .setMessage("返回後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwCounting.this, TwPlay.class);
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(TwCounting.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwCounting.this, RealSecondPage.class);
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
                Intent i = new Intent(TwCounting.this, setting.class);
                startActivity(i);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(TwCounting.this)
                        .setTitle("結束這一輪?")
                        .setMessage("本局遊戲將會結束")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwCounting.this, secondPage.class);
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

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AlertDialog dialog = new AlertDialog.Builder(TwCounting.this)
                        .setTitle("確認登出?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwCounting.this, LoginActivity.class);
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

        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",1);
                player_Id.apply();

                Intent i = new Intent(TwCounting.this,TwFinish.class);
                startActivity(i);

            }
        });
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",2);
                player_Id.apply();
                Intent i = new Intent(TwCounting.this,TwFinish.class);
                startActivity(i);

            }
        });
        playerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",3);
                player_Id.apply();
                Intent i = new Intent(TwCounting.this,TwFinish.class);
                startActivity(i);

            }
        });
        playerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",4);
                player_Id.apply();
                Intent i = new Intent(TwCounting.this,TwFinish.class);
                startActivity(i);

            }
        });

        GoCouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwCounting.this,TwRecording.class);
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("返回主頁?")
                .setMessage("返回主頁後所有紀錄將被取消")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(TwCounting.this, MainActivity.class);
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
}