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
import android.widget.Button;
import android.widget.EditText;
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

public class UsaPlay extends AppCompatActivity {

    EditText player1,player2,player3,player4;
    SharedPreferences player,record,round,PrintRound,score,gameNo;
    Button start,back,home,people,exit;
    String play1,play2,play3,play4;
    ArrayList<CountRecord> countRecords;
    int a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usa_play);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        start = findViewById(R.id.start);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);
        exit = findViewById(R.id.exit);

        gameNo = getApplication().getSharedPreferences("game",Context.MODE_PRIVATE);
        player = getSharedPreferences("UsaPlayer", Context.MODE_PRIVATE);
        record = getSharedPreferences("UsaRecord", Context.MODE_PRIVATE);
        round = getSharedPreferences("UsaRound", Context.MODE_PRIVATE);
        PrintRound = getSharedPreferences("UsaPrintRound", Context.MODE_PRIVATE);
        score = getSharedPreferences("UsaScore", Context.MODE_PRIVATE);


        SharedPreferences.Editor Record = record.edit();
        SharedPreferences.Editor Round = round.edit();
        SharedPreferences.Editor print = PrintRound.edit();
        SharedPreferences.Editor Score = score.edit();

        Round.putInt("countingRound",1);
        Round.commit();

        Record.putString("recordPlayer1","");
        Record.putString("recordPlayer2","");
        Record.putString("recordPlayer3","");
        Record.putString("recordPlayer4","");
        Record.commit();

        print.putString("PrintRoundNumber","");
        print.commit();

        Score.putInt("PlayerScore1",0);
        Score.putInt("PlayerScore2",0);
        Score.putInt("PlayerScore3",0);
        Score.putInt("PlayerScore4",0);
        Score.commit();

        getData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaPlay.this,secondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaPlay.this,RealSecondPage.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsaPlay.this,setting.class);
                startActivity(i);
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AlertDialog dialog = new AlertDialog.Builder(UsaPlay.this)
                        .setTitle("確認登出?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(UsaPlay.this, LoginActivity.class);
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
                    Toast.makeText(UsaPlay.this, "請輸入所有玩家名字", Toast.LENGTH_SHORT).show();

                } else if (player1.getText().toString().equals(player2.getText().toString()) ||
                        player1.getText().toString().equals(player3.getText().toString()) ||
                        player1.getText().toString().equals(player4.getText().toString()) ||
                        player2.getText().toString().equals(player3.getText().toString()) ||
                        player2.getText().toString().equals(player4.getText().toString()) ||
                        player3.getText().toString().equals(player4.getText().toString())) {
                    Toast.makeText(UsaPlay.this, "不要輸入重覆名字", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor playerName = player.edit();

                    play1 = player1.getText().toString();
                    play2 = player2.getText().toString();
                    play3 = player3.getText().toString();
                    play4 = player4.getText().toString();


                    playerName.putString("playerName1", play1);
                    playerName.putString("playerName2", play2);
                    playerName.putString("playerName3", play3);
                    playerName.putString("playerName4", play4);
                    playerName.commit();


                    Intent i = new Intent(UsaPlay.this, UsaCounting.class);
                    startActivity(i);
                }
            }
        });

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