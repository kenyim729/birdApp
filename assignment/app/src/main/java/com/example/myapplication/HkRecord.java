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
import android.widget.TextView;
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

public class HkRecord extends AppCompatActivity {

    SharedPreferences name,record1,record2,record3,record4,numberOfRecord;
    Button back,home,people;
    TextView Player1,Player2,Player3,Player4,recording1,recording2,recording3,recording4,numberRecord;
    ArrayList<CountRecord> countRecords;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hk_record);

        name = getApplication().getSharedPreferences("player_name", Context.MODE_PRIVATE);
        record1 = getApplication().getSharedPreferences("RecordPlayer1", Context.MODE_PRIVATE);
        record2 = getApplication().getSharedPreferences("RecordPlayer2", Context.MODE_PRIVATE);
        record3 = getApplication().getSharedPreferences("RecordPlayer3", Context.MODE_PRIVATE);
        record4 = getApplication().getSharedPreferences("RecordPlayer4", Context.MODE_PRIVATE);
        numberOfRecord = getApplication().getSharedPreferences("strRecord", Context.MODE_PRIVATE);

        Player1 = findViewById(R.id.player1);
        Player2 = findViewById(R.id.player2);
        Player3 = findViewById(R.id.player3);
        Player4 = findViewById(R.id.player4);
        numberRecord = findViewById(R.id.recordNumber);
        recording1 = findViewById(R.id.record1);
        recording2 = findViewById(R.id.record2);
        recording3 = findViewById(R.id.record3);
        recording4 = findViewById(R.id.record4);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);


        Player1.setText(name.getString("player1",""));
        Player2.setText(name.getString("player2",""));
        Player3.setText(name.getString("player3",""));
        Player4.setText(name.getString("player4",""));

//        recording1.setText(record1.getString("recording1",""));
//        recording2.setText(record2.getString("recording2",""));
//        recording3.setText(record3.getString("recording3",""));
//        recording4.setText(record4.getString("recording4",""));
//        numberRecord.setText(numberOfRecord.getString("recording",""));

        getData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HkRecord.this, HkCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(HkRecord.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(HkRecord.this, MainActivity.class);
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
                Intent i = new Intent(HkRecord.this, setting.class);
                startActivity(i);
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

    class MyAsynTask extends AsyncTask<String, Void, String> {

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

            if ( countRecords != null && countRecords.size() > 0 ) {
                String r1 = "";
                String r2 = "";
                String r3 = "";
                String r4 = "";
                String num = "";

                for(int i = 0; i < countRecords.size() ; i++ ){

                    if(countRecords.get(i).getGame() > a)
                        a = countRecords.get(i).getGame();
                }

                for (int i = 0; i < countRecords.size() ; i++ ) {
                    if(countRecords.get(i).getGame() == a) {
                        r1 += countRecords.get(i).getPlayer1() + "\n\n";
                        r2 += countRecords.get(i).getPlayer2() + "\n\n";
                        r3 += countRecords.get(i).getPlayer3() + "\n\n";
                        r4 += countRecords.get(i).getPlayer4() + "\n\n";
                        num += countRecords.get(i).getRound() + "\n\n";
                    }
                }

                recording1.setText(r1);
                recording2.setText(r2);
                recording3.setText(r3);
                recording4.setText(r4);
                numberRecord.setText(num);

                Toast.makeText(getApplicationContext(), retStr, Toast.LENGTH_LONG).show();

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