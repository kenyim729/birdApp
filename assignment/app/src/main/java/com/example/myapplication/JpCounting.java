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
import android.widget.ImageButton;
import android.widget.Toast;

public class JpCounting extends AppCompatActivity {

    SharedPreferences jp;
    Button playerOne,playerTwo,playerThree,playerFour,stand1,stand2,stand3,stand4,back,home,people;
    ImageButton record;
    int player1,player2,player3,player4,AlreadyStand1,AlreadyStand2,AlreadyStand3,AlreadyStand4,editStand ;
    String count1,count2,count3,count4,strPlayer1,strPlayer2,strPlayer3,strPlayer4,play1,play2,play3,play4;
    String record1,record2,record3,record4,PrintRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jp_counting);

        playerOne = findViewById(R.id.player1);
        playerTwo = findViewById(R.id.player2);
        playerThree = findViewById(R.id.player3);
        playerFour = findViewById(R.id.player4);
        record = findViewById(R.id.recordPage);
        stand1 = findViewById(R.id.stand1);
        stand2 = findViewById(R.id.stand2);
        stand3 = findViewById(R.id.stand3);
        stand4 = findViewById(R.id.stand4);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        jp = getApplication().getSharedPreferences("JpDetail", Context.MODE_PRIVATE);

        //get stand and edit Stand
        AlreadyStand1 = jp.getInt("stand1",0);
        AlreadyStand2 = jp.getInt("stand2",0);
        AlreadyStand3 = jp.getInt("stand3",0);
        AlreadyStand4 = jp.getInt("stand4",0);
        editStand = jp.getInt("StandEdit",0);


        //get money
        player1 = jp.getInt("score1" , 0);
        player2 = jp.getInt("score2" , 0);
        player3 = jp.getInt("score3" , 0);
        player4 = jp.getInt("score4" , 0);

        strPlayer1 = Integer.toString(player1);
        strPlayer2 = Integer.toString(player2);
        strPlayer3 = Integer.toString(player3);
        strPlayer4 = Integer.toString(player4);

        //get name
        play1 = jp.getString("player1","");
        play2 = jp.getString("player2","");
        play3 = jp.getString("player3","");
        play4 = jp.getString("player4","");

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

        //get record str
        record1 = jp.getString("Player1Record","");
        record2 = jp.getString("Player2Record","");
        record3 = jp.getString("Player3Record","");
        record4 = jp.getString("Player4Record","");
        PrintRecord = jp.getString("PrintRoundNumber","");

        SharedPreferences.Editor JpEdit = jp.edit();

        if(editStand == 0) {
            PrintRecord += "立直.\n\n";
            JpEdit.putString("PrintRoundNumber", PrintRecord);
            JpEdit.putInt("StandEdit",1);

            JpEdit.commit();
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(JpCounting.this)
                        .setTitle("重新輸入玩家名字?")
                        .setMessage("返回後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(JpCounting.this, JpPlay.class);
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
                AlertDialog dialog = new AlertDialog.Builder(JpCounting.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(JpCounting.this, MainActivity.class);
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
                Intent i = new Intent(JpCounting.this, setting.class);
                startActivity(i);
            }
        });

        stand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlreadyStand1 == 1){
                    Toast.makeText(JpCounting.this, "已經立直",Toast.LENGTH_SHORT).show();
                }


                if(AlreadyStand1 == 0 && player1 > 1000){
                    player1 -= 1000;
                    strPlayer1 = Integer.toString(player1);
                    count1 = play1 + "\n\n" + strPlayer1;
                    playerOne.setText(count1);
                    record1 += "-1000\n\n";

                    JpEdit.putString("Player1Record",record1);
                    JpEdit.putInt("score1",player1);
                    JpEdit.putInt("stand1",1);
                    AlreadyStand1 = 1;

                    JpEdit.commit();

                    Toast.makeText(JpCounting.this, "玩家 "+play1+" 立直成功",Toast.LENGTH_SHORT).show();

                }

                if(player1 < 1000){
                    Toast.makeText(JpCounting.this, "分數不足以立直",Toast.LENGTH_SHORT).show();
                }
            }
        });

        stand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlreadyStand2 == 1){
                    Toast.makeText(JpCounting.this, "已經立直",Toast.LENGTH_SHORT).show();
                }

                if(AlreadyStand2 == 0 && player2 > 1000){
                    player2 -= 1000;
                    strPlayer2 = Integer.toString(player2);
                    count2 = play2 + "\n\n" + strPlayer2;
                    playerTwo.setText(count2);
                    record2 += "-1000\n\n";

                    JpEdit.putString("Player2Record",record2);
                    JpEdit.putInt("score2",player2);
                    JpEdit.putInt("stand2",1);
                    AlreadyStand2 = 1;

                    JpEdit.commit();

                    Toast.makeText(JpCounting.this, "玩家 "+play2+" 立直成功",Toast.LENGTH_SHORT).show();
                }

                if(player2 < 1000){
                    Toast.makeText(JpCounting.this, "分數不足以立直",Toast.LENGTH_SHORT).show();
                }
            }
        });

        stand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlreadyStand3 == 1){
                    Toast.makeText(JpCounting.this, "已經立直",Toast.LENGTH_SHORT).show();
                }

                if(AlreadyStand3 == 0 && player3 > 1000){
                    player3 -= 1000;
                    strPlayer3 = Integer.toString(player3);
                    count3 = play3 + "\n\n" + strPlayer3;
                    playerThree.setText(count3);
                    record3 += "-1000\n\n";

                    JpEdit.putString("Player3Record",record3);
                    JpEdit.putInt("score3",player3);
                    JpEdit.putInt("stand3",1);
                    AlreadyStand3 = 1;

                    JpEdit.commit();

                    Toast.makeText(JpCounting.this, "玩家 "+play3+" 立直成功",Toast.LENGTH_SHORT).show();
                }

                if(player3 < 1000){
                    Toast.makeText(JpCounting.this, "分數不足以立直",Toast.LENGTH_SHORT).show();
                }
            }
        });

        stand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlreadyStand4 == 1){
                    Toast.makeText(JpCounting.this, "已經立直",Toast.LENGTH_SHORT).show();
                }

                if(AlreadyStand4 == 0 && player4 > 1000){
                    player4 -= 1000;
                    strPlayer4 = Integer.toString(player4);
                    count4 = play4 + "\n\n" + strPlayer4;
                    playerFour.setText(count4);
                    record4 += "-1000\n\n";

                    JpEdit.putString("Player4Record",record4);
                    JpEdit.putInt("score4",player4);
                    JpEdit.putInt("stand4",1);
                    AlreadyStand4 = 1;

                    JpEdit.commit();

                    Toast.makeText(JpCounting.this, "玩家 "+play4+" 立直成功",Toast.LENGTH_SHORT).show();
                }

                if(player4 < 1000){
                    Toast.makeText(JpCounting.this, "分數不足以立直",Toast.LENGTH_SHORT).show();
                }
            }
        });

        playerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JpEdit.putInt("winner",1);
                JpEdit.apply();

                Intent i = new Intent(JpCounting.this,JpFinish.class);
                startActivity(i);

            }
        });
        playerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JpEdit.putInt("winner",2);
                JpEdit.apply();
                Intent i = new Intent(JpCounting.this,JpFinish.class);
                startActivity(i);

            }
        });
        playerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JpEdit.putInt("winner",3);
                JpEdit.apply();
                Intent i = new Intent(JpCounting.this,JpFinish.class);
                startActivity(i);

            }
        });
        playerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JpEdit.putInt("winner",4);
                JpEdit.apply();
                Intent i = new Intent(JpCounting.this,JpFinish.class);
                startActivity(i);

            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpCounting.this,JpRecord.class);
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
                        Intent i = new Intent(JpCounting.this, MainActivity.class);
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