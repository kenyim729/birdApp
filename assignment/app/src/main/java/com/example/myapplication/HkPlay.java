package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.model.CountRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class HkPlay extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText player1,player2,player3,player4;
    Button start,back,home,people,exit;
    SharedPreferences sp,money,count,numberOfRecord,Record1,Record2,Record3,Record4,recordCounting,gameNo;
    String i,play1,play2,play3,play4;
    int game,a;
    ArrayList<CountRecord> countRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_play);

        Spinner spinner = findViewById(R.id.money);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.money, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        gameNo = getApplication().getSharedPreferences("game",Context.MODE_PRIVATE);


        getData();


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
        exit = findViewById(R.id.exit);

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
                Intent i = new Intent(HkPlay.this,RealSecondPage.class);
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

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AlertDialog dialog = new AlertDialog.Builder(HkPlay.this)
                        .setTitle("確認登出?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(HkPlay.this, LoginActivity.class);
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

    private void getData() {
        //get user
        SharedPreferences userId = getApplicationContext().getSharedPreferences("user_Id", Context.MODE_PRIVATE);
        String username = userId.getString("username","");

        String postParams = "CreateUser=" + username;

        System.out.println (postParams);

        String urlString = "http://10.0.2.2:8080/lab_bird_php/getBird_json.php";

        MyAsynTask newTask = new MyAsynTask();
        newTask.execute(urlString, postParams);
    }

    private String getHttpURLConnection (String urlStr, String postParams) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        String returnStr = null;

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

            InputStream inputSteam = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputSteam == null) {
                // Nothing to do
                return null;
            }

            bufferedReader = new BufferedReader( new InputStreamReader(inputSteam) );

            String line;
            while ( (line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            returnStr = stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    private class MyAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String retStr = getHttpURLConnection(params[0], params[1]);
            return retStr;
        }

        @Override
        protected void onPostExecute(String retStr) {
            super.onPostExecute(retStr);

            System.out.println ("==================");
            System.out.println (retStr);

//            network_TextView.setText(retStr);
            countRecords = jsonToArrayList_records(retStr);

            SharedPreferences.Editor gameNumber = gameNo.edit();
            if( countRecords == null ){
                a = 0;
                gameNumber.putInt("game",a);
                gameNumber.commit();
            }

            if ( countRecords != null && countRecords.size() > 0 ) {
                for(int i = 0 ; i < countRecords.size(); i++){
                    if(countRecords.get(i).getGame() == a){
                        a = countRecords.get(i).getGame() + 1;
                    }
                }
                gameNumber.putInt("game",a);
                gameNumber.commit();

            }
        }
    }

    private ArrayList<CountRecord> jsonToArrayList_records(String jsonStr_records)  {

        ArrayList<CountRecord> countRecordArrayList = new ArrayList<CountRecord>();

        try {
//            System.out.println(jsonStr_records);
            JSONObject root = new JSONObject(  jsonStr_records );

            JSONArray jsonArray = root.getJSONArray("birdhistory");

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int game = jsonObject.getInt("game");
                int round = jsonObject.getInt("round");
                int player1 = jsonObject.getInt("player1");
                int player2 = jsonObject.getInt("player2");
                int player3 = jsonObject.getInt("player3");
                int player4 = jsonObject.getInt("player4");
                String createUser = jsonObject.getString("createUser");
                String createDate = jsonObject.getString("createDate");

                CountRecord countRecord = new CountRecord(game,round,player1,player2,player3,player4,createUser,createDate);

                countRecordArrayList.add(countRecord);
            }

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

        return countRecordArrayList;
    }

}