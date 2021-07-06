package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.myHelper.MyDbAdapter;
import com.example.myapplication.myHelper.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private MyDbAdapter myDbAdapter;

    private EditText username=null;
    private EditText  password=null;
    private Button login;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_login);

        myDbAdapter = new MyDbAdapter( this );

        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        login = (Button)findViewById(R.id.button1);
        signUp = (Button)findViewById(R.id.button2);

        //debug testing
        username.setText("test");
        password.setText("haha");

//        myDbAdapter.deleteAllUsers();

        //setting who eat in next page
        SharedPreferences userId = getSharedPreferences("user_Id", Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> userList = myDbAdapter.getAllUsers(myDbAdapter.getAllUsers());
                System.out.println(userList);

                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                for ( User u : userList ) {
                    if( usernameText.equals(u.getUserName()) &&
                            passwordText.equals(u.getPassword())){
                        Toast.makeText(getApplicationContext(), "Redirecting...",
                                Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor user_Id = userId.edit();
                        user_Id.putString("username", usernameText);
                        user_Id.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity ( intent );
                        return;
                    }
                }

                Toast.makeText(getApplicationContext(), "Wrong Credentials",
                        Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> userNameList = myDbAdapter.getAllUsersName(myDbAdapter.getAllUsers());
                System.out.println(userNameList);

                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                if( !userNameList.contains(usernameText)){
                    Toast.makeText(getApplicationContext(), "Successfully sign up",
                            Toast.LENGTH_SHORT).show();
                    myDbAdapter.addUser(new User( usernameText, passwordText ));
                } else{
                    Toast.makeText(getApplicationContext(), "User already exists",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}