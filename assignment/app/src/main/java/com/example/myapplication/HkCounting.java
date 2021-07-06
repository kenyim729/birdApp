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

public class HkCounting extends AppCompatActivity {

    SharedPreferences playerId;
    Button playerOne,playerTwo,playerThree,playerFour,back,home,people;
    ImageButton record;
    int player1,player2,player3,player4;
    String count1,count2,count3,count4;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_counting);

        playerOne = findViewById(R.id.player1);
        playerTwo = findViewById(R.id.player2);
        playerThree = findViewById(R.id.player3);
        playerFour = findViewById(R.id.player4);
        record = findViewById(R.id.recordPage);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        //setting who eat in next page
        playerId = getSharedPreferences("player_Id", Context.MODE_PRIVATE);

        //get detail
        SharedPreferences sp = getApplicationContext().getSharedPreferences("player_name", Context.MODE_PRIVATE);
        SharedPreferences showMoney = getApplicationContext().getSharedPreferences("moneyCounting", Context.MODE_PRIVATE);

        //get money
        player1 = showMoney.getInt("first" , 0);
        player2 = showMoney.getInt("second" , 0);
        player3 = showMoney.getInt("third" , 0);
        player4 = showMoney.getInt("fourth" , 0);

        String strPlayer1 = Integer.toString(player1);
        String strPlayer2 = Integer.toString(player2);
        String strPlayer3 = Integer.toString(player3);
        String strPlayer4 = Integer.toString(player4);

        //get name
        String play1 = sp.getString("player1","");
        String play2 = sp.getString("player2","");
        String play3 = sp.getString("player3","");
        String play4 = sp.getString("player4","");

        //get user
        SharedPreferences userId = getApplicationContext().getSharedPreferences("user_Id", Context.MODE_PRIVATE);
        String username = userId.getString("username","");
        System.out.println("username: " + username);

        //set button value
        count1 = play1 + "\n\n" + strPlayer1;
        count2 = play2 + "\n\n" + strPlayer2;
        count3 = play3 + "\n\n" + strPlayer3;
        count4 = play4 + "\n\n" + strPlayer4;

        //set name
        playerOne.setText(count1);
        playerTwo.setText(count2);
        playerThree.setText(count3);
        playerFour.setText(count4);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(HkCounting.this)
                        .setTitle("重新輸入玩家名字?")
                        .setMessage("返回後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(HkCounting.this, HkPlay.class);
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
                AlertDialog dialog = new AlertDialog.Builder(HkCounting.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(HkCounting.this, MainActivity.class);
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
                Intent i = new Intent(HkCounting.this, setting.class);
                startActivity(i);
            }
        });


        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",1);
                player_Id.apply();

                Intent i = new Intent(HkCounting.this,HkEat.class);
                startActivity(i);

            }
        });
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",2);
                player_Id.apply();
                Intent i = new Intent(HkCounting.this,HkEat.class);
                startActivity(i);

            }
        });
        playerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",3);
                player_Id.apply();
                Intent i = new Intent(HkCounting.this,HkEat.class);
                startActivity(i);

            }
        });
        playerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor player_Id = playerId.edit();
                player_Id.putInt("player",4);
                player_Id.apply();
                Intent i = new Intent(HkCounting.this,HkEat.class);
                startActivity(i);

            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HkCounting.this,HkRecord.class);
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
                        Intent i = new Intent(HkCounting.this, MainActivity.class);
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