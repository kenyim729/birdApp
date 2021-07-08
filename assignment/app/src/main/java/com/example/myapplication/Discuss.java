package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.CommentRecord;

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

public class Discuss extends AppCompatActivity {

    private EditText add_comment;
    private RecyclerView recycler_view;
    private MyDiscussAdapter adapter;
    Button back,home,people;

    private ArrayList<String> commentDataList = new ArrayList<>();
    private ArrayList<String> userDataList = new ArrayList<>();

    ArrayList<CommentRecord> commentRecordArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_discuss);

        //================================================================

        //get user
        SharedPreferences userId = getApplicationContext().getSharedPreferences("user_Id", Context.MODE_PRIVATE);
        String username = userId.getString("username","");

        //================================================================

        String urlString = "http://10.0.2.2:8080/lab_comment_php/getComment_json.php";

        MyGetAsynTask newTask = new MyGetAsynTask();
        newTask.execute(urlString);

        //================================================================

        recycler_view = findViewById(R.id.recycler_view);
        //set RecycleView as a list
        recycler_view.setLayoutManager( new LinearLayoutManager(this));

        adapter = new MyDiscussAdapter(commentDataList, userDataList);
        recycler_view.setAdapter(adapter);

        //================================================================

        add_comment = (EditText) findViewById(R.id.add_comment);

        // Setting EditorActionListener for the EditText
        add_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ) {

                    String postParams =
                            "Comment="    + add_comment.getText().toString() + "&" +
                            "CreateUser="   + username;

                    System.out.println (postParams);

                    String urlString = "http://10.0.2.2:8080/lab_comment_php/postComment.php";

                    MyPostAsynTask newTask = new MyPostAsynTask();
                    newTask.execute(urlString, postParams);

                    commentDataList.add( add_comment.getText().toString() );
                    userDataList.add( username );
                    add_comment.setText("");
                }
                return false;
            }
        });

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Discuss.this,RealSecondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Discuss.this,MainActivity.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Discuss.this,setting.class);
                startActivity(i);
            }
        });

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

    private class MyPostAsynTask extends AsyncTask<String, Void, String> {

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

            Toast.makeText(getApplicationContext(), retStr, Toast.LENGTH_LONG).show();
        }

    }


    private ArrayList<CommentRecord> jsonToArrayList_comment(String jsonStr_comment)  {

        ArrayList<CommentRecord> commentArrayList = new ArrayList<CommentRecord>();

        try {
            JSONObject root = new JSONObject(  jsonStr_comment );

            JSONArray jsonArray = root.getJSONArray("discussrecord");

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String comment = jsonObject.getString("comment");
                String createUser = jsonObject.getString("createUser");

                CommentRecord cm = new CommentRecord(comment, createUser);

                commentArrayList.add(cm);
            }

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

        return commentArrayList;
    }

    private String getHttpURLConnection (String urlStr) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        String returnStr = null;

        try {
            URL url = new URL(urlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

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

    private class MyGetAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... parems) {
            String retStr = getHttpURLConnection(parems[0]);
            return retStr;
        }

        @Override
        protected void onPostExecute(String retStr) {
            super.onPostExecute(retStr);

            System.out.println ("==================");
            System.out.println (retStr);

            commentRecordArrayList = jsonToArrayList_comment(retStr);

            if ( commentRecordArrayList != null && commentRecordArrayList.size() > 0 ) {
                for ( CommentRecord cm : commentRecordArrayList ) {
                    commentDataList.add( cm.getComment() );
                    userDataList.add( cm.getCreateUser() );
                }
            }
        }
    }
}
