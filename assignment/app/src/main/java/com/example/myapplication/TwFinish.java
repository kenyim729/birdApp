package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RadialGradient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TwFinish extends AppCompatActivity {

    RadioGroup RG;
    RadioButton button,button2,button3,button4;
    Button eatPlayer1,eatPlayer2,eatPlayer3,self,back,home,people,exit;
    SharedPreferences name,id,base,MoneyOfPoint,counting;
    int GetId,mainer,Totalpoint,baseMoney,point,player1Point,player2Point,player3Point,player4Point,Twcounting,BaseAddPoint;
    int GameNo;
    String player1,player2,player3,player4,changeMoney,DataRecord1,DataRecord2,DataRecord3,DataRecord4,NumberOfRecord;
    EditText Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tw_finish);

        RG = findViewById(R.id.group);

        button2 = findViewById(R.id.up);
        button3 = findViewById(R.id.opposite);
        button4 = findViewById(R.id.down);
        self = findViewById(R.id.self);
        eatPlayer1 = findViewById(R.id.player1);
        eatPlayer2 = findViewById(R.id.player2);
        eatPlayer3 = findViewById(R.id.player3);
        Total = findViewById(R.id.numberEaten);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);
        exit = findViewById(R.id.exit);



        name = getApplication().getSharedPreferences("TwPlayer_name", Context.MODE_PRIVATE);
        id = getApplication().getSharedPreferences("TwPlayer_Id" , Context.MODE_PRIVATE);
        base = getApplication().getSharedPreferences("BaseMoney",Context.MODE_PRIVATE);
        MoneyOfPoint = getApplication().getSharedPreferences("TwMoneySelect", Context.MODE_PRIVATE);
        counting = getApplication().getSharedPreferences("TwMoneyCounting" , Context.MODE_PRIVATE);
        SharedPreferences numberOfRecord = getApplicationContext().getSharedPreferences("TwStrRecord", Context.MODE_PRIVATE);
        SharedPreferences numberOfMarking = getApplicationContext().getSharedPreferences("TwIntRecord", Context.MODE_PRIVATE);
        SharedPreferences record1 = getApplicationContext().getSharedPreferences("TwRecordPlayer1", Context.MODE_PRIVATE);
        SharedPreferences record2 = getApplicationContext().getSharedPreferences("TwRecordPlayer2", Context.MODE_PRIVATE);
        SharedPreferences record3 = getApplicationContext().getSharedPreferences("TwRecordPlayer3", Context.MODE_PRIVATE);
        SharedPreferences record4 = getApplicationContext().getSharedPreferences("TwRecordPlayer4", Context.MODE_PRIVATE);
        SharedPreferences game = getApplicationContext().getSharedPreferences("game", Context.MODE_PRIVATE);

        //set game number
        GameNo = game.getInt("game",0);

        //get user
        SharedPreferences userId = getApplicationContext().getSharedPreferences("user_Id", Context.MODE_PRIVATE);
        String username = userId.getString("username","");
        System.out.println("username: " + username);

        //get player record detail
        DataRecord1 = record1.getString("recording1","");
        DataRecord2 = record2.getString("recording2","");
        DataRecord3 = record3.getString("recording3","");
        DataRecord4 = record4.getString("recording4","");

        //get ??????record and ???????????????
        NumberOfRecord = numberOfRecord.getString("recording","");
        Twcounting = numberOfMarking.getInt("counting",0);


        //??????
        player1Point = counting.getInt("first",0);
        player2Point = counting.getInt("second",0);
        player3Point = counting.getInt("third",0);
        player4Point = counting.getInt("fourth",0);


        //?????????
        GetId = id.getInt("player",0);

        //??????
        player1 = name.getString("player1","");
        player2 = name.getString("player2","");
        player3 = name.getString("player3","");
        player4 = name.getString("player4","");

        //????????????
        point = MoneyOfPoint.getInt("money",0);

        //????????????
        baseMoney = base.getInt("base",0);

        //set editor
        SharedPreferences.Editor FirstRecord = record1.edit();
        SharedPreferences.Editor SecondRecord = record2.edit();
        SharedPreferences.Editor ThirdRecord = record3.edit();
        SharedPreferences.Editor FourthRecord = record4.edit();
        SharedPreferences.Editor RecordNumber = numberOfRecord.edit();
        SharedPreferences.Editor CountingNumber = numberOfMarking.edit();

        if(GetId == 1){
            button2.setText(player2);
            button3.setText(player3);
            button4.setText(player4);
            eatPlayer1.setText(player2);
            eatPlayer2.setText(player3);
            eatPlayer3.setText(player4);
        }

        else if(GetId == 2){
            button2.setText(player1);
            button3.setText(player3);
            button4.setText(player4);
            eatPlayer1.setText(player1);
            eatPlayer2.setText(player3);
            eatPlayer3.setText(player4);
        }

        else if (GetId == 3){
            button2.setText(player1);
            button3.setText(player2);
            button4.setText(player4);
            eatPlayer1.setText(player1);
            eatPlayer2.setText(player2);
            eatPlayer3.setText(player4);
        }

        else if (GetId == 4){
            button2.setText(player1);
            button3.setText(player2);
            button4.setText(player3);
            eatPlayer1.setText(player1);
            eatPlayer2.setText(player2);
            eatPlayer3.setText(player3);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwFinish.this, TwCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(TwFinish.this)
                        .setTitle("?????????????")
                        .setMessage("???????????????????????????????????????")
                        .setPositiveButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwFinish.this, RealSecondPage.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("???", new DialogInterface.OnClickListener() {
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
                Intent i = new Intent(TwFinish.this, setting.class);
                startActivity(i);
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AlertDialog dialog = new AlertDialog.Builder(TwFinish.this)
                        .setTitle("?????????????")
                        .setPositiveButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(TwFinish.this, LoginActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .show();

            }
        });

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button = findViewById(checkedId);
                Toast.makeText(TwFinish.this, button.getText() + "?????????" , Toast.LENGTH_SHORT).show();

                if (button.getText().toString().equals("??????")) {
                    mainer = 1;
                } else if (button.getText().toString().equals(button2.getText().toString())) {
                    mainer = 2;
                } else if (button.getText().toString().equals(button3.getText().toString())) {
                    mainer = 3;
                } else if (button.getText().toString().equals(button4.getText().toString())) {
                    mainer = 4;
                } else {
                    mainer = 0;
                }
            }
        });

        eatPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor calculate = counting.edit();

                changeMoney = Total.getText().toString();

                if(changeMoney.trim().equals("")){
                    Toast.makeText(TwFinish.this, "???????????????" , Toast.LENGTH_SHORT).show();
                    Totalpoint = -1;
                }
                else{
                    Totalpoint = Integer.parseInt(changeMoney);
                }

                BaseAddPoint = baseMoney + point * Totalpoint;


                if(GetId == 1 && Totalpoint != -1){
                    player1Point = player1Point + baseMoney + point * Totalpoint;
                    player2Point = player2Point - baseMoney - point * Totalpoint;

                    calculate.putInt("first",player1Point);
                    calculate.putInt("second", player2Point);

                    postData(GameNo, Twcounting, BaseAddPoint,BaseAddPoint * -1,0,0, username);

                    DataRecord1 += "+" + BaseAddPoint +"\n\n";
                    DataRecord2 += "-" + BaseAddPoint+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);



                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 2 && Totalpoint != -1){
                    player2Point = player2Point + baseMoney + point * Totalpoint;
                    player1Point = player1Point - baseMoney - point * Totalpoint;

                    postData(GameNo, Twcounting, BaseAddPoint * -1,BaseAddPoint,0,0, username);

                    calculate.putInt("first",player1Point);
                    calculate.putInt("second", player2Point);

                    DataRecord1 += "-" + BaseAddPoint +"\n\n";
                    DataRecord2 += "+" + BaseAddPoint+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);

                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 3 && Totalpoint != -1){
                    player3Point = player3Point + baseMoney + point * Totalpoint;
                    player1Point = player1Point - baseMoney - point * Totalpoint;

                    calculate.putInt("first",player1Point);
                    calculate.putInt("third", player3Point);

                    postData(GameNo, Twcounting, BaseAddPoint * -1,0,BaseAddPoint,0, username);

                    DataRecord1 += "-" + BaseAddPoint +"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "+" + BaseAddPoint+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 4 && Totalpoint != -1){
                    player4Point = player4Point + baseMoney + point * Totalpoint;
                    player1Point = player1Point - baseMoney - point * Totalpoint;

                    calculate.putInt("first",player1Point);
                    calculate.putInt("fourth", player4Point);

                    postData(GameNo, Twcounting, BaseAddPoint * -1,0,0,BaseAddPoint, username);

                    DataRecord1 += "-" + BaseAddPoint +"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "+" + BaseAddPoint+"\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);

                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(Totalpoint != -1){
                    Intent i = new Intent(TwFinish.this,TwCounting.class);
                    startActivity(i);
                }
            }
        });

        eatPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor calculate = counting.edit();

                changeMoney = Total.getText().toString();

                if(changeMoney.trim().equals("")){
                    Toast.makeText(TwFinish.this, "???????????????" , Toast.LENGTH_SHORT).show();
                    Totalpoint = -1;
                }
                else{
                    Totalpoint = Integer.parseInt(changeMoney);
                }

                BaseAddPoint = baseMoney + point * Totalpoint;

                if(GetId == 1 && Totalpoint != -1){
                    player1Point = player1Point + baseMoney + point * Totalpoint;
                    player3Point = player3Point - baseMoney - point * Totalpoint;

                    calculate.putInt("first",player1Point);
                    calculate.putInt("third", player3Point);

                    postData(GameNo, Twcounting, BaseAddPoint ,0,BaseAddPoint * -1,0, username);

                    DataRecord1 += "+" + BaseAddPoint +"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "-" + BaseAddPoint+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 2 && Totalpoint != -1){
                    player2Point = player2Point + baseMoney + point * Totalpoint;
                    player3Point = player3Point - baseMoney - point * Totalpoint;

                    calculate.putInt("third",player3Point);
                    calculate.putInt("second", player2Point);

                    postData(GameNo, Twcounting, 0 ,BaseAddPoint,BaseAddPoint  * -1,0, username);


                    DataRecord1 += "0\n\n";
                    DataRecord2 += "+" + BaseAddPoint +"\n\n";
                    DataRecord3 += "-" + BaseAddPoint+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 3 && Totalpoint != -1){
                    player3Point = player3Point + baseMoney + point * Totalpoint;
                    player2Point = player2Point - baseMoney - point * Totalpoint;

                    calculate.putInt("third",player3Point);
                    calculate.putInt("second", player2Point);

                    postData(GameNo, Twcounting, 0 ,BaseAddPoint * -1,BaseAddPoint ,0, username);


                    DataRecord1 += "0\n\n";
                    DataRecord2 += "-" + BaseAddPoint +"\n\n";
                    DataRecord3 += "+" + BaseAddPoint+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 4 && Totalpoint != -1){
                    player4Point = player4Point + baseMoney + point * Totalpoint;
                    player2Point = player2Point - baseMoney - point * Totalpoint;

                    calculate.putInt("fourth",player4Point);
                    calculate.putInt("second", player2Point);

                    postData(GameNo, Twcounting, 0 ,0,BaseAddPoint * -1,BaseAddPoint, username);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "-" + BaseAddPoint +"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "+" + BaseAddPoint+"\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(Totalpoint != -1){
                    Intent i = new Intent(TwFinish.this,TwCounting.class);
                    startActivity(i);
                }
            }
        });

        eatPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor calculate = counting.edit();

                changeMoney = Total.getText().toString();

                if(changeMoney.trim().equals("")){
                    Toast.makeText(TwFinish.this, "???????????????" , Toast.LENGTH_SHORT).show();
                    Totalpoint = -1;
                }
                else{
                    Totalpoint = Integer.parseInt(changeMoney);
                }

                BaseAddPoint = baseMoney + point * Totalpoint;

                if(GetId == 1 && Totalpoint != -1){
                    player1Point = player1Point + baseMoney + point * Totalpoint;
                    player4Point = player4Point - baseMoney - point * Totalpoint;

                    postData(GameNo, Twcounting, BaseAddPoint ,0, 0,BaseAddPoint * -1, username);


                    calculate.putInt("first",player1Point);
                    calculate.putInt("fourth", player4Point);

                    DataRecord1 += "+" + BaseAddPoint+"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "-" + BaseAddPoint+"\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 2 && Totalpoint != -1){
                    player2Point = player2Point + baseMoney + point * Totalpoint;
                    player4Point = player4Point - baseMoney - point * Totalpoint;

                    calculate.putInt("fourth",player4Point);
                    calculate.putInt("second", player2Point);

                    postData(GameNo, Twcounting, 0 ,BaseAddPoint, 0,BaseAddPoint * -1, username);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "+" + BaseAddPoint+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "-" + BaseAddPoint+"\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 3 && Totalpoint != -1){
                    player3Point = player3Point + baseMoney + point * Totalpoint;
                    player4Point = player4Point - baseMoney - point * Totalpoint;

                    calculate.putInt("third",player3Point);
                    calculate.putInt("fourth", player4Point);

                    postData(GameNo, Twcounting, 0 ,0, BaseAddPoint,BaseAddPoint * -1, username);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "+" + BaseAddPoint+"\n\n";
                    DataRecord4 += "-" + BaseAddPoint+"\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(GetId == 4 && Totalpoint != -1){
                    player4Point = player4Point + baseMoney + point * Totalpoint;
                    player3Point = player3Point - baseMoney - point * Totalpoint;

                    calculate.putInt("fourth",player4Point);
                    calculate.putInt("third", player3Point);

                    postData(GameNo, Twcounting, 0 ,0, BaseAddPoint * -1, BaseAddPoint, username);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "-" + BaseAddPoint+"\n\n";
                    DataRecord4 += "+" + BaseAddPoint+"\n\n";
                    NumberOfRecord += Twcounting + ".\n\n";
                    Twcounting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",Twcounting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    calculate.commit();
                }

                if(Totalpoint != -1){
                    Intent i = new Intent(TwFinish.this,TwCounting.class);
                    startActivity(i);
                }
            }
        });

        self.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                SharedPreferences.Editor calculate = counting.edit();

                changeMoney = Total.getText().toString();

                if (changeMoney.trim().equals("")) {
                    Toast.makeText(TwFinish.this, "???????????????", Toast.LENGTH_SHORT).show();
                    Totalpoint = -1;
                } else {
                    Totalpoint = Integer.parseInt(changeMoney);
                }

                double self_cost = (baseMoney + point * Totalpoint) * 1.5;
                int SeperateCost = (int) self_cost / 3;
                int AddingPoint = (int)self_cost + point;
                int WayAddingPoint = SeperateCost + point;

                if(mainer == 0){
                    Toast.makeText(TwFinish.this, "???????????????" , Toast.LENGTH_SHORT).show();
                 }
                else if (GetId == 1) {
                    if (mainer == 1) {
                        player1Point += (int) self_cost;
                        player2Point -= SeperateCost;
                        player3Point -= SeperateCost;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, self_cost ,SeperateCost * -1, SeperateCost * -1, SeperateCost * -1, username);

                        DataRecord1 += "+" + (int)self_cost +"\n\n";
                        DataRecord2 += "-" + SeperateCost+"\n\n";
                        DataRecord3 += "-" + SeperateCost+"\n\n";
                        DataRecord4 += "-" + SeperateCost+"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();

                    }

                    if (mainer == 2) {
                        player1Point += (int) self_cost + point;
                        player2Point -= SeperateCost + point;
                        player3Point -= SeperateCost;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, self_cost + point ,(SeperateCost + point) * -1, SeperateCost * -1, SeperateCost * -1, username);

                        DataRecord1 += "+" + AddingPoint +"\n\n";
                        DataRecord2 += "-" + WayAddingPoint +"\n\n";
                        DataRecord3 += "-" + SeperateCost+"\n\n";
                        DataRecord4 += "-" + SeperateCost+"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }

                    if (mainer == 3) {
                        player1Point += (int) self_cost + point;
                        player2Point -= SeperateCost;
                        player3Point -= SeperateCost + point;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, self_cost + point ,SeperateCost  * -1, (SeperateCost + point) * -1, SeperateCost * -1, username);


                        DataRecord1 += "+" + AddingPoint +"\n\n";
                        DataRecord2 += "-" + SeperateCost+"\n\n";
                        DataRecord3 += "-" + WayAddingPoint +"\n\n";
                        DataRecord4 += "-" + SeperateCost+"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                    if (mainer == 4) {
                        player1Point += (int) self_cost + point;
                        player2Point -= SeperateCost;
                        player3Point -= SeperateCost;
                        player4Point -= SeperateCost + point;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, self_cost + point ,SeperateCost  * -1, SeperateCost * -1, (SeperateCost + point) * -1, username);

                        DataRecord1 += "+" + AddingPoint +"\n\n";
                        DataRecord2 += "-" + SeperateCost+"\n\n";
                        DataRecord3 += "-" + SeperateCost+"\n\n";
                        DataRecord4 += "-" + WayAddingPoint +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                }

                if (GetId == 2) {
                    if (mainer == 1) {
                        player1Point -= SeperateCost;
                        player2Point += (int) self_cost;
                        player3Point -= SeperateCost;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,self_cost, SeperateCost * -1, SeperateCost  * -1, username);

                        DataRecord1 += "-" + (int)self_cost +"\n\n";
                        DataRecord2 += "+" + SeperateCost+"\n\n";
                        DataRecord3 += "-" + SeperateCost+"\n\n";
                        DataRecord4 += "-" + SeperateCost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();

                    }

                    if (mainer == 2) {
                        player1Point -= SeperateCost + point;
                        player2Point += (int) self_cost + point;
                        player3Point -= SeperateCost;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, (SeperateCost + point) * -1 ,self_cost + point, SeperateCost * -1, SeperateCost  * -1, username);

                        DataRecord1 += "-" + WayAddingPoint +"\n\n";
                        DataRecord2 += "+" + AddingPoint+"\n\n";
                        DataRecord3 += "-" + SeperateCost+"\n\n";
                        DataRecord4 += "-" + SeperateCost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }

                    if (mainer == 3) {
                        player1Point -= SeperateCost;
                        player2Point += (int) self_cost + point;
                        player3Point -= SeperateCost + point;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,self_cost + point, (SeperateCost + point) * -1, SeperateCost  * -1, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "+" + AddingPoint+"\n\n";
                        DataRecord3 += "-" + WayAddingPoint +"\n\n";
                        DataRecord4 += "-" + SeperateCost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                    if (mainer == 4) {
                        player1Point -= SeperateCost;
                        player2Point += (int) self_cost + point;
                        player3Point -= SeperateCost;
                        player4Point -= SeperateCost + point;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,self_cost + point, SeperateCost * -1, (SeperateCost + point)  * -1, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "+" + AddingPoint+"\n\n";
                        DataRecord3 += "-" + SeperateCost +"\n\n";
                        DataRecord4 += "-" + WayAddingPoint +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                }

                if (GetId == 3) {
                    if (mainer == 1) {
                        player1Point -= SeperateCost;
                        player2Point -= SeperateCost;
                        player3Point += (int) self_cost;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,SeperateCost * -1, self_cost, SeperateCost  * -1, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "-" + SeperateCost +"\n\n";
                        DataRecord3 += "+" + (int)self_cost +"\n\n";
                        DataRecord4 += "-" + SeperateCost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();

                    }

                    if (mainer == 2) {
                        player1Point -= SeperateCost + point;
                        player2Point -= SeperateCost;
                        player3Point += (int) self_cost + point;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, (SeperateCost + point) * -1 ,SeperateCost * -1, self_cost + point, SeperateCost * -1, username);

                        DataRecord1 += "-" + WayAddingPoint +"\n\n";
                        DataRecord2 += "-" + SeperateCost +"\n\n";
                        DataRecord3 += "+" + AddingPoint +"\n\n";
                        DataRecord4 += "-" + SeperateCost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }

                    if (mainer == 3) {
                        player1Point -= SeperateCost;
                        player2Point -= SeperateCost + point;
                        player3Point += (int) self_cost + point;
                        player4Point -= SeperateCost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,(SeperateCost + point) * -1, self_cost + point, SeperateCost * -1, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "-" + WayAddingPoint +"\n\n";
                        DataRecord3 += "+" + AddingPoint +"\n\n";
                        DataRecord4 += "-" + SeperateCost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                    if (mainer == 4) {
                        player1Point -= SeperateCost;
                        player2Point -= SeperateCost;
                        player3Point += (int) self_cost + point;
                        player4Point -= SeperateCost + point;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,SeperateCost * -1, self_cost + point, (SeperateCost + point) * -1, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "-" + SeperateCost +"\n\n";
                        DataRecord3 += "+" + AddingPoint +"\n\n";
                        DataRecord4 += "-" + WayAddingPoint +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                }

                if (GetId == 4) {
                    if (mainer == 1) {
                        player1Point -= SeperateCost;
                        player2Point -= SeperateCost;
                        player3Point -= SeperateCost;
                        player4Point += (int) self_cost;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,SeperateCost * -1,SeperateCost * -1, self_cost, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "-" + SeperateCost +"\n\n";
                        DataRecord3 += "-" + SeperateCost +"\n\n";
                        DataRecord4 += "+" + (int)self_cost +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();

                    }

                    if (mainer == 2) {
                        player1Point -= SeperateCost + point;
                        player2Point -= SeperateCost;
                        player3Point -= SeperateCost;
                        player4Point += (int) self_cost + point;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, (SeperateCost + point) * -1 ,SeperateCost * -1,SeperateCost * -1, self_cost + point, username);


                        DataRecord1 += "-" + WayAddingPoint +"\n\n";
                        DataRecord2 += "-" + SeperateCost +"\n\n";
                        DataRecord3 += "-" + SeperateCost +"\n\n";
                        DataRecord4 += "+" + AddingPoint +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }

                    if (mainer == 3) {
                        player1Point -= SeperateCost;
                        player2Point -= SeperateCost + point;
                        player3Point -= SeperateCost;
                        player4Point += (int) self_cost + point;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,(SeperateCost + point) * -1,SeperateCost * -1, self_cost + point, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "-" + WayAddingPoint +"\n\n";
                        DataRecord3 += "-" + SeperateCost +"\n\n";
                        DataRecord4 += "+" + AddingPoint +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                    if (mainer == 4) {
                        player1Point -= SeperateCost;
                        player2Point -= SeperateCost;
                        player3Point -= SeperateCost - point;
                        player4Point += (int) self_cost + point;

                        calculate.putInt("first", player1Point);
                        calculate.putInt("second", player2Point);
                        calculate.putInt("fourth", player4Point);
                        calculate.putInt("third", player3Point);

                        postData(GameNo, Twcounting, SeperateCost * -1 ,SeperateCost * -1,(SeperateCost + point) * -1, self_cost + point, username);

                        DataRecord1 += "-" + SeperateCost +"\n\n";
                        DataRecord2 += "-" + SeperateCost +"\n\n";
                        DataRecord3 += "-" + WayAddingPoint +"\n\n";
                        DataRecord4 += "+" + AddingPoint +"\n\n";
                        NumberOfRecord += Twcounting + ".\n\n";
                        Twcounting += 1;

                        FirstRecord.putString("recording1",DataRecord1);
                        SecondRecord.putString("recording2",DataRecord2);
                        ThirdRecord.putString("recording3",DataRecord3);
                        FourthRecord.putString("recording4",DataRecord4);
                        RecordNumber.putString("recording",NumberOfRecord);
                        CountingNumber.putInt("counting",Twcounting);


                        FirstRecord.commit();
                        SecondRecord.commit();
                        ThirdRecord.commit();
                        FourthRecord.commit();
                        RecordNumber.commit();
                        CountingNumber.commit();

                        calculate.commit();
                    }
                }

                if (Totalpoint != -1 && mainer !=0) {
                    Intent i = new Intent(TwFinish.this, TwCounting.class);
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