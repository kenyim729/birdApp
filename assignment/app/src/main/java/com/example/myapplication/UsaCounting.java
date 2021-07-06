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

public class UsaCounting extends AppCompatActivity {

    Button playerOne,playerTwo,playerThree,playerFour,back,home,people;
    ImageButton record;
    SharedPreferences winner,name,score;
    int score1,score2,score3,score4;
    String name1,name2,name3,name4;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usa_counting);

        playerOne = findViewById(R.id.player1);
        playerTwo = findViewById(R.id.player2);
        playerThree = findViewById(R.id.player3);
        playerFour = findViewById(R.id.player4);
        record = findViewById(R.id.recordPage);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);


        name = getApplication().getSharedPreferences("UsaPlayer",Context.MODE_PRIVATE);
        score = getApplication().getSharedPreferences("UsaScore",Context.MODE_PRIVATE);
        winner = getSharedPreferences("UsaWinner", Context.MODE_PRIVATE);

        //get score
        score1 = score.getInt("PlayerScore1",0);
        score2 = score.getInt("PlayerScore2",0);
        score3 = score.getInt("PlayerScore3",0);
        score4 = score.getInt("PlayerScore4",0);


        //get name
        name1 = name.getString("playerName1","");
        name2 = name.getString("playerName2","");
        name3 = name.getString("playerName3","");
        name4 = name.getString("playerName4","");

        playerOne.setText(name1 + "\n\n" + score1);
        playerTwo.setText(name2 + "\n\n" + score2);
        playerThree.setText(name3 + "\n\n" + score3);
        playerFour.setText(name4 + "\n\n" + score4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(UsaCounting.this)
                        .setTitle("重新輸入玩家名字?")
                        .setMessage("返回後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(UsaCounting.this, UsaPlay.class);
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
                AlertDialog dialog = new AlertDialog.Builder(UsaCounting.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(UsaCounting.this, MainActivity.class);
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
                Intent i = new Intent(UsaCounting.this, setting.class);
                startActivity(i);
            }
        });

        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor Details = winner.edit();

                Details.putInt("winner",1);
                Details.commit();

                Intent i = new Intent(UsaCounting.this,UsaFinish.class);
                startActivity(i);
            }
        });

        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor Details = winner.edit();

                Details.putInt("winner",2);
                Details.commit();

                Intent i = new Intent(UsaCounting.this,UsaFinish.class);
                startActivity(i);
            }
        });

        playerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor Details = winner.edit();

                Details.putInt("winner",3);
                Details.commit();

                Intent i = new Intent(UsaCounting.this,UsaFinish.class);
                startActivity(i);
            }
        });

        playerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor Details = winner.edit();

                Details.putInt("winner",4);
                Details.commit();

                Intent i = new Intent(UsaCounting.this,UsaFinish.class);
                startActivity(i);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaCounting.this,UsaRecord.class);
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
                        Intent i = new Intent(UsaCounting.this, MainActivity.class);
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