package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("ALL")
public class HkEat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button button1,button2,button3,self,back,home,people;
    int GameNo;
    int a;
    int Eaten;
    int point;
    int total_cost;
    int oneCost;
    int twoCost;
    int threeCost;
    int fourCost;
    int separate_cost;
    int counting;
    String NumberOfRecord;
    double self_total;
    String player1,player2,player3,player4,DataRecord1,DataRecord2,DataRecord3,DataRecord4,Marking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_eat);

        Spinner spinner = findViewById(R.id.numberEaten);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numberOfEaten, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button1 = findViewById(R.id.player1);
        button2 = findViewById(R.id.player2);
        button3 = findViewById(R.id.player3);
        self = findViewById(R.id.self);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("player_Id", Context.MODE_PRIVATE);
        SharedPreferences name = getApplicationContext().getSharedPreferences("player_name", Context.MODE_PRIVATE);
        SharedPreferences money = getApplicationContext().getSharedPreferences("moneyCounting", Context.MODE_PRIVATE);
        SharedPreferences numberOfEaten = getApplicationContext().getSharedPreferences("moneySelect", Context.MODE_PRIVATE);
        SharedPreferences numberOfRecord = getApplicationContext().getSharedPreferences("strRecord", Context.MODE_PRIVATE);
        SharedPreferences numberOfMarking = getApplicationContext().getSharedPreferences("intRecord", Context.MODE_PRIVATE);
        SharedPreferences record1 = getApplicationContext().getSharedPreferences("RecordPlayer1", Context.MODE_PRIVATE);
        SharedPreferences record2 = getApplicationContext().getSharedPreferences("RecordPlayer2", Context.MODE_PRIVATE);
        SharedPreferences record3 = getApplicationContext().getSharedPreferences("RecordPlayer3", Context.MODE_PRIVATE);
        SharedPreferences record4 = getApplicationContext().getSharedPreferences("RecordPlayer4", Context.MODE_PRIVATE);
        SharedPreferences game = getApplicationContext().getSharedPreferences("game", Context.MODE_PRIVATE);

        //set game number
        GameNo = game.getInt("game",0);

        //get player record detail
        DataRecord1 = record1.getString("recording1","");
        DataRecord2 = record2.getString("recording2","");
        DataRecord3 = record3.getString("recording3","");
        DataRecord4 = record4.getString("recording4","");

        //get 總項record and 下項係第幾
        NumberOfRecord = numberOfRecord.getString("recording","");
        counting = numberOfMarking.getInt("counting",0);

        //get who eat
        a = sp.getInt("player",1);

        //get番數
        point = numberOfEaten.getInt("money", 1);

        //get each money
        oneCost = money.getInt("first" , 0);
        twoCost = money.getInt("second" , 0);
        threeCost = money.getInt("third" , 0);
        fourCost = money.getInt("fourth" , 0);

        //get name
        player1 = name.getString("player1" , "");
        player2 = name.getString("player2" , "");
        player3 = name.getString("player3" , "");
        player4 = name.getString("player4" , "");

        //get user
        SharedPreferences userId = getApplicationContext().getSharedPreferences("user_Id", Context.MODE_PRIVATE);
        String username = userId.getString("username","");
        System.out.println("username: " + username);

        //set editor
        SharedPreferences.Editor FirstRecord = record1.edit();
        SharedPreferences.Editor SecondRecord = record2.edit();
        SharedPreferences.Editor ThirdRecord = record3.edit();
        SharedPreferences.Editor FourthRecord = record4.edit();
        SharedPreferences.Editor RecordNumber = numberOfRecord.edit();
        SharedPreferences.Editor CountingNumber = numberOfMarking.edit();



        if(a == 1){
            button1.setText("" + GameNo);
            button2.setText("" + GameNo);
            button3.setText("" + GameNo);
        }

        else if (a == 2){
            button1.setText(player1);
            button2.setText(player3);
            button3.setText(player4);
        }

        else if(a == 3){
            button1.setText(player1);
            button2.setText(player2);
            button3.setText(player4);
        }

        else if (a == 4) {
            button1.setText(player1);
            button2.setText(player2);
            button3.setText(player3);

        }

        if(point == 32){
            total_cost = 4;
        }

        else if(point == 64){
            total_cost = 8;
        }

        else if(point == 128){
            total_cost = 16;
        }

        else if(point == 256){
            total_cost = 32;
        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HkEat.this, HkCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(HkEat.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(HkEat.this, MainActivity.class);
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
                Intent i = new Intent(HkEat.this, setting.class);
                startActivity(i);
            }
        });

        //button1 onclick
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Eaten){
                    case 0:
                        break;

                    case 4:
                        total_cost = total_cost * 2;
                        break;

                    case 5:
                        total_cost = total_cost * 3;
                        break;

                    case 6:
                        total_cost = total_cost * 4;
                        break;

                    case 7:
                        total_cost = total_cost * 6;
                        break;

                    case 8:
                        total_cost = total_cost * 8;
                        break;

                    case 9:
                        total_cost = total_cost * 12;
                        break;

                    case 10:
                        total_cost = total_cost * 16;
                        break;

                    case 11:
                        total_cost = total_cost * 24;
                        break;

                    case 12:
                        total_cost = total_cost * 32;
                        break;

                    case 13:
                        total_cost = total_cost * 48;
                        break;
                }

                SharedPreferences.Editor CalculateCost = money.edit();
                if(Eaten == 0){
                    Toast.makeText(HkEat.this, "請選擇番數", Toast.LENGTH_SHORT).show();
                }
                else{
                    Marking = String.valueOf(total_cost);
                }



                if(Eaten != 0 && a == 1){
                    oneCost += total_cost;
                    twoCost -= total_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("second",twoCost);

                    DataRecord1 += "+" + Marking+"\n\n";
                    DataRecord2 += "-" + Marking+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,total_cost,total_cost*-1,0,0, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();

                }

                else if(Eaten != 0 && a == 2){
                    twoCost += total_cost;
                    oneCost -= total_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("second",twoCost);

                    DataRecord1 += "-" + Marking+"\n\n";
                    DataRecord2 += "+" + Marking+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,total_cost*-1,total_cost,0,0, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 3){
                    threeCost += total_cost;
                    oneCost -= total_cost;


                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("third",threeCost);

                    DataRecord1 += "-" + Marking+"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "+" + Marking+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,total_cost*-1,0,total_cost,0, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 4){
                    fourCost += total_cost;
                    oneCost -= total_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "-" + Marking+"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "+" + Marking+"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,total_cost*-1,0,0,total_cost, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                if(Eaten != 0) {
                    Intent i = new Intent(HkEat.this, HkCounting.class);
                    startActivity(i);
                }
            }
        });


        //button2 onclick
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Eaten){
                    case 0:
                        break;

                    case 4:
                        total_cost = total_cost * 2;
                        break;

                    case 5:
                        total_cost = total_cost * 3;
                        break;

                    case 6:
                        total_cost = total_cost * 4;
                        break;

                    case 7:
                        total_cost = total_cost * 6;
                        break;

                    case 8:
                        total_cost = total_cost * 8;
                        break;

                    case 9:
                        total_cost = total_cost * 12;
                        break;

                    case 10:
                        total_cost = total_cost * 16;
                        break;

                    case 11:
                        total_cost = total_cost * 24;
                        break;

                    case 12:
                        total_cost = total_cost * 32;
                        break;

                    case 13:
                        total_cost = total_cost * 48;
                        break;
                }

                SharedPreferences.Editor CalculateCost = money.edit();
                if(Eaten == 0){
                    Toast.makeText(HkEat.this, "請選擇番數", Toast.LENGTH_SHORT).show();
                }
                else{
                    Marking = String.valueOf(total_cost);
                }

                if(Eaten != 0 && a == 1){
                    oneCost += total_cost;
                    threeCost-= total_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("third",threeCost);

                    DataRecord1 += "+" + Marking+"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "-" + Marking+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,total_cost,0,total_cost*-1,0, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 2){
                    twoCost += total_cost;
                    threeCost -= total_cost;

                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("third",threeCost);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "+" + Marking+"\n\n";
                    DataRecord3 += "-" + Marking+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,0,total_cost,total_cost*-1,0, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 3){
                    threeCost += total_cost;
                    twoCost -= total_cost;

                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("third",threeCost);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "-" + Marking+"\n\n";
                    DataRecord3 += "+" + Marking+"\n\n";
                    DataRecord4 += "0\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,0,total_cost*-1,total_cost,0, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 4){
                    fourCost += total_cost;
                    twoCost -= total_cost;

                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "-" + Marking+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "+" + Marking+"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,0,total_cost*-1,0,total_cost, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }


                if(Eaten != 0) {
                    Intent i = new Intent(HkEat.this, HkCounting.class);
                    startActivity(i);
                }
            }
        });


        //button3 on click
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Eaten){
                    case 0:
                        break;

                    case 4:
                        total_cost = total_cost * 2;
                        break;

                    case 5:
                        total_cost = total_cost * 3;
                        break;

                    case 6:
                        total_cost = total_cost * 4;
                        break;

                    case 7:
                        total_cost = total_cost * 6;
                        break;

                    case 8:
                        total_cost = total_cost * 8;
                        break;

                    case 9:
                        total_cost = total_cost * 12;
                        break;

                    case 10:
                        total_cost = total_cost * 16;
                        break;

                    case 11:
                        total_cost = total_cost * 24;
                        break;

                    case 12:
                        total_cost = total_cost * 32;
                        break;

                    case 13:
                        total_cost = total_cost * 48;
                        break;
                }

                SharedPreferences.Editor CalculateCost = money.edit();
                if(Eaten == 0){
                    Toast.makeText(HkEat.this, "請選擇番數", Toast.LENGTH_SHORT).show();
                }
                else{
                    Marking = String.valueOf(total_cost);
                }

                if(Eaten != 0 && a == 1){
                    oneCost += total_cost;
                    fourCost -= total_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "+" + Marking+"\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "-" + Marking+"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,total_cost,0,0,total_cost*-1, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 2){
                    twoCost += total_cost;
                    fourCost -= total_cost;

                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "+" + Marking+"\n\n";
                    DataRecord3 += "0\n\n";
                    DataRecord4 += "-" + Marking+"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,0,total_cost,0,total_cost*-1, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 3){
                    threeCost += total_cost;
                    fourCost -= total_cost;

                    CalculateCost.putInt("third",threeCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "+" + Marking+"\n\n";
                    DataRecord4 += "-" + Marking+"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,0,0,total_cost,total_cost*-1, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 4){
                    fourCost += total_cost;
                    threeCost -= total_cost;

                    CalculateCost.putInt("third",threeCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "0\n\n";
                    DataRecord2 += "0\n\n";
                    DataRecord3 += "-" + Marking+"\n\n";
                    DataRecord4 += "+" + Marking+"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,0,0,total_cost*-1,total_cost, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                if(Eaten != 0) {
                    Intent i = new Intent(HkEat.this, HkCounting.class);
                    startActivity(i);
                }
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Eaten){
                    case 0:
                        break;

                    case 3:
                        self_total = total_cost * 1.5;
                        break;

                    case 4:
                        self_total = total_cost * 2 * 1.5;
                        break;

                    case 5:
                        self_total = total_cost * 3 * 1.5;
                        break;

                    case 6:
                        self_total = total_cost * 4 * 1.5;
                        break;

                    case 7:
                        self_total = total_cost * 6 * 1.5;
                        break;

                    case 8:
                        self_total = total_cost * 8 * 1.5;
                        break;

                    case 9:
                        self_total = total_cost * 12 * 1.5;
                        break;

                    case 10:
                        self_total = total_cost * 16 * 1.5;
                        break;

                    case 11:
                        self_total = total_cost * 24 * 1.5;
                        break;

                    case 12:
                        self_total = total_cost * 32 * 1.5;
                        break;

                    case 13:
                        self_total = total_cost * 48 * 1.5;
                        break;
                }

                separate_cost = (int) self_total / 3;

                SharedPreferences.Editor CalculateCost = money.edit();
                if(Eaten == 0){
                    Toast.makeText(HkEat.this, "請選擇番數", Toast.LENGTH_SHORT).show();
                }

                if(Eaten != 0 && a == 1){
                    oneCost += self_total;
                    twoCost -= separate_cost;
                    threeCost -= separate_cost;
                    fourCost -= separate_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("third",threeCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "+" + (int)self_total +"\n\n";
                    DataRecord2 += "-" + separate_cost +"\n\n";
                    DataRecord3 += "-" + separate_cost +"\n\n";
                    DataRecord4 += "-" + separate_cost +"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,self_total,separate_cost*-1,separate_cost*-1,separate_cost*-1, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 2){
                    twoCost += self_total;
                    oneCost -= separate_cost;
                    threeCost -= separate_cost;
                    fourCost -= separate_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("third",threeCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "-" + separate_cost +"\n\n";
                    DataRecord2 += "+" + (int)self_total +"\n\n";
                    DataRecord3 += "-" + separate_cost +"\n\n";
                    DataRecord4 += "-" + separate_cost +"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,separate_cost*-1,self_total,separate_cost*-1,separate_cost*-1, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 3){
                    threeCost += self_total;
                    twoCost -= separate_cost;
                    oneCost -= separate_cost;
                    fourCost -= separate_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("third",threeCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "-" + separate_cost +"\n\n";
                    DataRecord2 += "-" + separate_cost +"\n\n";
                    DataRecord3 += "+" + (int)self_total +"\n\n";
                    DataRecord4 += "-" + separate_cost +"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,separate_cost*-1,separate_cost*-1,self_total,separate_cost*-1, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }

                else if(Eaten != 0 && a == 4){
                    fourCost += self_total;
                    twoCost -= separate_cost;
                    threeCost -= separate_cost;
                    oneCost -= separate_cost;

                    CalculateCost.putInt("first",oneCost);
                    CalculateCost.putInt("second",twoCost);
                    CalculateCost.putInt("third",threeCost);
                    CalculateCost.putInt("fourth",fourCost);

                    DataRecord1 += "-" + separate_cost +"\n\n";
                    DataRecord2 += "-" + separate_cost +"\n\n";
                    DataRecord3 += "-" + separate_cost +"\n\n";
                    DataRecord4 += "+" + (int)self_total +"\n\n";
                    NumberOfRecord += counting + ".\n\n";

                    postData(GameNo, counting,separate_cost*-1,separate_cost*-1,separate_cost*-1,self_total, username);

                    counting += 1;

                    FirstRecord.putString("recording1",DataRecord1);
                    SecondRecord.putString("recording2",DataRecord2);
                    ThirdRecord.putString("recording3",DataRecord3);
                    FourthRecord.putString("recording4",DataRecord4);
                    RecordNumber.putString("recording",NumberOfRecord);
                    CountingNumber.putInt("counting",counting);


                    FirstRecord.commit();
                    SecondRecord.commit();
                    ThirdRecord.commit();
                    FourthRecord.commit();
                    RecordNumber.commit();
                    CountingNumber.commit();

                    CalculateCost.commit();
                }


                if(Eaten != 0) {
                    Intent i = new Intent(HkEat.this, HkCounting.class);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String i = parent.getItemAtPosition(position).toString();

        if(i.equals("--請選擇--")){
            Eaten = 0;
            Toast.makeText(HkEat.this, "請選擇番數", Toast.LENGTH_SHORT).show();
        }
        else if (i.equals("3")){
            Eaten = 3;
        }

        else if (i.equals("4")){
            Eaten = 4;
        }
        else if (i.equals("5")){
            Eaten = 5;
        }
        else if (i.equals("6")){
            Eaten = 6;
        }
        else if (i.equals("7")){
            Eaten = 7;
        }
        else if (i.equals("8")){
            Eaten = 8;
        }
        else if (i.equals("9")){
            Eaten = 9;
        }
        else if (i.equals("10")){
            Eaten = 10;
        }
        else if (i.equals("11")){
            Eaten = 11;
        }
        else if (i.equals("12")){
            Eaten = 12;
        }
        else if (i.equals("13")){
            Eaten = 13;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


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