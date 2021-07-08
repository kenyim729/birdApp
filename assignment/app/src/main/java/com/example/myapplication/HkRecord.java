package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HkRecord extends AppCompatActivity {

    SharedPreferences name,record1,record2,record3,record4,numberOfRecord,gameNo;
    Button back,home,people,exit;
    TextView Player1,Player2,Player3,Player4,recording1,recording2,recording3,recording4,numberRecord;
    TextView total1,total2,total3,total4;
    ArrayList<CountRecord> countRecords;
    int a = 0;
    int one,two,three,four;

    private RecyclerView recycler_view_hkRecord;
    private MyHkRecordAdapter adapter;
    private Map<Integer, List<CountRecord>> hkRecordMap = new HashMap<>();
    private Context c;

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
        gameNo = getApplication().getSharedPreferences("game",Context.MODE_PRIVATE);
        SharedPreferences showMoney = getApplicationContext().getSharedPreferences("moneyCounting", Context.MODE_PRIVATE);


        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);
        exit = findViewById(R.id.exit);

        c = this;
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
                                Intent i = new Intent(HkRecord.this, RealSecondPage.class);
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

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AlertDialog dialog = new AlertDialog.Builder(HkRecord.this)
                        .setTitle("確認登出?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(HkRecord.this, LoginActivity.class);
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String retStr) {
            super.onPostExecute(retStr);

            System.out.println ("==================");
            System.out.println (retStr);

//            network_TextView.setText(retStr);
            countRecords = jsonToArrayList_records(retStr);
            System.out.println( "gameNo is : " + gameNo.getInt("game",0) );
            System.out.println(countRecords.size());

            if ( countRecords != null && countRecords.size() > 0 ) {

                for (int i = 0; i < countRecords.size() ; i++ ) {
                    CountRecord current = countRecords.get(i);
                    if ( !hkRecordMap.containsKey( countRecords.get(i).getGame()) ) {
                        List<CountRecord> list = new ArrayList<>();
                        list.add(current);
                        hkRecordMap.put(countRecords.get(i).getGame(), list);
                    } else {
                        hkRecordMap.get(current.getGame()).add(current);
                    }
                }

                System.out.println( "hkRecordMap size: " + hkRecordMap.size() );

                for ( Map.Entry<Integer, List<CountRecord>> entry : hkRecordMap.entrySet() ) {
                    Integer test111 = entry.getKey();
                    List<CountRecord> test222 = entry.getValue();
                    System.out.println(test111 + ": \n" + test222) ;
                }
                //================================================================

                recycler_view_hkRecord = findViewById(R.id.recycler_view_hkRecord);
                //set RecycleView as a list
                recycler_view_hkRecord.setLayoutManager( new LinearLayoutManager(c));

                adapter = new MyHkRecordAdapter(hkRecordMap);
                recycler_view_hkRecord.setAdapter(adapter);

                //================================================================
            }

            if ( countRecords != null && countRecords.size() > 0 ) {
                String r1 = "";
                String r2 = "";
                String r3 = "";
                String r4 = "";
                String num = "";


                for (int i = 0; i < countRecords.size() ; i++ ) {
                    if(countRecords.get(i).getGame() == gameNo.getInt("game",0)) {
                        r1 += countRecords.get(i).getPlayer1() + "\n\n";
                        r2 += countRecords.get(i).getPlayer2() + "\n\n";
                        r3 += countRecords.get(i).getPlayer3() + "\n\n";
                        r4 += countRecords.get(i).getPlayer4() + "\n\n";
                        num += countRecords.get(i).getRound() + "\n\n";
                    }
                }
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