package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class HkPlay extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText player1,player2,player3,player4;
    Button start,back,home,people;
    SharedPreferences sp,money,count,numberOfRecord,Record1,Record2,Record3,Record4,recordCounting,gameNo;
    String i,play1,play2,play3,play4;
    int game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_play);

        Spinner spinner = findViewById(R.id.money);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.money, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);

        //debug testing
        player1.setText("a");
        player2.setText("b");
        player3.setText("c");
        player4.setText("d");

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        sp = getSharedPreferences("player_name", Context.MODE_PRIVATE);
        money = getSharedPreferences("moneySelect", Context.MODE_PRIVATE);
        count = getSharedPreferences("moneyCounting", Context.MODE_PRIVATE);
        numberOfRecord = getSharedPreferences("strRecord", Context.MODE_PRIVATE);
        recordCounting = getSharedPreferences("intRecord", Context.MODE_PRIVATE);
        Record1 = getSharedPreferences("RecordPlayer1", Context.MODE_PRIVATE);
        Record2 = getSharedPreferences("RecordPlayer2", Context.MODE_PRIVATE);
        Record3 = getSharedPreferences("RecordPlayer3", Context.MODE_PRIVATE);
        Record4 = getSharedPreferences("RecordPlayer4", Context.MODE_PRIVATE);

        SharedPreferences.Editor moneyCount = count.edit();
        SharedPreferences.Editor Recording = numberOfRecord.edit();
        SharedPreferences.Editor intRecording = recordCounting.edit();
        SharedPreferences.Editor Recording1 = Record1.edit();
        SharedPreferences.Editor Recording2 = Record2.edit();
        SharedPreferences.Editor Recording3 = Record3.edit();
        SharedPreferences.Editor Recording4 = Record4.edit();

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
                Intent i = new Intent(HkPlay.this,secondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HkPlay.this,MainActivity.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HkPlay.this,setting.class);
                startActivity(i);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1.getText().toString().isEmpty() || player2.getText().toString().isEmpty() ||
                        player3.getText().toString().isEmpty() || player4.getText().toString().isEmpty()) {
                    Toast.makeText(HkPlay.this, "請輸入所有玩家名字", Toast.LENGTH_SHORT).show();

                }
                else if(player1.getText().toString().equals(player2.getText().toString()) ||
                        player1.getText().toString().equals(player3.getText().toString()) ||
                        player1.getText().toString().equals(player4.getText().toString()) ||
                        player2.getText().toString().equals(player3.getText().toString()) ||
                        player2.getText().toString().equals(player4.getText().toString()) ||
                        player3.getText().toString().equals(player4.getText().toString()) ){
                    Toast.makeText(HkPlay.this, "不要輸入重覆名字", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor moneySelect = money.edit();

                    if(i.equals("32")){
                        moneySelect.putInt("money",32);
                        moneySelect.apply();
                    }

                    else if (i.equals("64")){
                        moneySelect.putInt("money",64);
                        moneySelect.apply();
                    }

                    else if (i.equals("128")){
                        moneySelect.putInt("money",128);
                        moneySelect.apply();
                    }
                    else if (i.equals("256")){
                        moneySelect.putInt("money",256);
                        moneySelect.apply();
                    }

                    play1 = player1.getText().toString();
                    play2 = player2.getText().toString();
                    play3 = player3.getText().toString();
                    play4 = player4.getText().toString();

                    SharedPreferences.Editor editor = sp.edit();


                    editor.putString("player1", play1);
                    editor.putString("player2", play2);
                    editor.putString("player3", play3);
                    editor.putString("player4", play4);
                    editor.commit();


                    Intent i = new Intent(HkPlay.this, HkCounting.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        i = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "已選擇8番為" + i ,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}