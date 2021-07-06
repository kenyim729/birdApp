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

public class TwPlay extends AppCompatActivity {

    EditText player1,player2,player3,player4,base,money;
    Button start,back,home,people;
    int Base,Money;
    SharedPreferences TwPlayer,TwBase,TwMoney,TwCount,TwNumberOfRecord,TwRecord1,TwRecord2,TwRecord3,TwRecord4,TwRecordCounting;
    String strBase,strMoney,play1,play2,play3,play4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tw_play);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        base = findViewById(R.id.baseCost);
        money = findViewById(R.id.money);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        TwPlayer = getSharedPreferences("TwPlayer_name", Context.MODE_PRIVATE);
        TwBase = getSharedPreferences("BaseMoney", Context.MODE_PRIVATE);
        TwMoney = getSharedPreferences("TwMoneySelect", Context.MODE_PRIVATE);
        TwCount = getSharedPreferences("TwMoneyCounting", Context.MODE_PRIVATE);
        TwNumberOfRecord = getSharedPreferences("TwStrRecord", Context.MODE_PRIVATE);
        TwRecordCounting = getSharedPreferences("TwIntRecord", Context.MODE_PRIVATE);
        TwRecord1 = getSharedPreferences("TwRecordPlayer1", Context.MODE_PRIVATE);
        TwRecord2 = getSharedPreferences("TwRecordPlayer2", Context.MODE_PRIVATE);
        TwRecord3 = getSharedPreferences("TwRecordPlayer3", Context.MODE_PRIVATE);
        TwRecord4 = getSharedPreferences("TwRecordPlayer4", Context.MODE_PRIVATE);

        SharedPreferences.Editor moneyCount = TwCount.edit();
        SharedPreferences.Editor Recording = TwNumberOfRecord.edit();
        SharedPreferences.Editor intRecording = TwRecordCounting.edit();
        SharedPreferences.Editor Recording1 = TwRecord1.edit();
        SharedPreferences.Editor Recording2 = TwRecord2.edit();
        SharedPreferences.Editor Recording3 = TwRecord3.edit();
        SharedPreferences.Editor Recording4 = TwRecord4.edit();

        intRecording.putInt("counting",1);

        intRecording.commit();

        Recording1.putString("recording1","");
        Recording2.putString("recording2","");
        Recording3.putString("recording3","");
        Recording4.putString("recording4","");

        Recording1.commit();
        Recording2.commit();
        Recording3.commit();
        Recording4.commit();

        Recording.putString("recording","");
        Recording.commit();

        moneyCount.putInt("first",0);
        moneyCount.putInt("second",0);
        moneyCount.putInt("third",0);
        moneyCount.putInt("fourth",0);

        moneyCount.commit();

        start = findViewById(R.id.start);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwPlay.this,secondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwPlay.this,MainActivity.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwPlay.this,setting.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1.getText().toString().isEmpty() || player2.getText().toString().isEmpty() ||
                        player3.getText().toString().isEmpty() || player4.getText().toString().isEmpty()) {
                    Toast.makeText(TwPlay.this, "請輸入所有玩家名字", Toast.LENGTH_SHORT).show();

                }

                else if (player1.getText().toString().equals(player2.getText().toString()) ||
                        player1.getText().toString().equals(player3.getText().toString()) ||
                        player1.getText().toString().equals(player4.getText().toString()) ||
                        player2.getText().toString().equals(player3.getText().toString()) ||
                        player2.getText().toString().equals(player4.getText().toString()) ||
                        player3.getText().toString().equals(player4.getText().toString())) {
                    Toast.makeText(TwPlay.this, "不要輸入重覆名字", Toast.LENGTH_SHORT).show();
                }

                else if(base.getText().toString().equals("") || money.getText().toString().equals("")){
                    Toast.makeText(TwPlay.this, "請輸入底數或台數", Toast.LENGTH_SHORT).show();
                }

                else {
                    strBase = base.getText().toString();
                    Base = Integer.parseInt(strBase);

                    strMoney = money.getText().toString();
                    Money = Integer.parseInt(strMoney);

                    SharedPreferences.Editor TwBaseSelect = TwBase.edit();
                    TwBaseSelect.putInt("base", Base);
                    TwBaseSelect.commit();


                    SharedPreferences.Editor TwMoneySelect = TwMoney.edit();
                    TwMoneySelect.putInt("money", Money);
                    TwMoneySelect.commit();

                    play1 = player1.getText().toString();
                    play2 = player2.getText().toString();
                    play3 = player3.getText().toString();
                    play4 = player4.getText().toString();

                    SharedPreferences.Editor editor = TwPlayer.edit();

                    editor.putString("player1", play1);
                    editor.putString("player2", play2);
                    editor.putString("player3", play3);
                    editor.putString("player4", play4);
                    editor.commit();

                    Intent i = new Intent(TwPlay.this,TwCounting.class);
                    startActivity(i);
                }
            }
        });





        }
}