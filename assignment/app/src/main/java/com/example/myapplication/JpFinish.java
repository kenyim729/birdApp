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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class JpFinish extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RadioGroup RG;
    RadioButton button,button2,button3,button4;
    Button nextPlayer1,nextPlayer2,nextPlayer3,self,back,home,people ;
    SharedPreferences jp;
    int Eaten,score1,score2,score3,score4,roundNumber,winner,stand,mainer,TotalFu,StartCal = 1;
    int MainerStandPoint,MainerUnstandPoint,StandPoint,UnstandPoint,stand1,stand2,stand3,stand4;
    int MainerSelf,Self,MainerEatenBySelf,EatenBySelf,StandMainerSelf,StandSelf;
    String name1,name2,name3,name4,PrintRecord,record1,record2,record3,record4,Check;
    TextView fu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jp_finish);

        Spinner spinner = findViewById(R.id.numberEaten);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.JpPoint, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        jp = getApplication().getSharedPreferences("JpDetail", Context.MODE_PRIVATE);

        RG = findViewById(R.id.group);
        button2 = findViewById(R.id.up);
        button3 = findViewById(R.id.opposite);
        button4 = findViewById(R.id.down);
        nextPlayer1 = findViewById(R.id.player1);
        nextPlayer2 = findViewById(R.id.player2);
        nextPlayer3 = findViewById(R.id.player3);
        self = findViewById(R.id.self);
        fu = findViewById(R.id.numberOfFu);

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        //get stand or not
        stand1 = jp.getInt("stand1",0);
        stand2 = jp.getInt("stand2",0);
        stand3 = jp.getInt("stand3",0);
        stand4 = jp.getInt("stand4",0);

        //get Name
        name1 = jp.getString("player1","");
        name2 = jp.getString("player2","");
        name3 = jp.getString("player3","");
        name4 = jp.getString("player4","");

        //get score
        score1 = jp.getInt("score1",0);
        score2 = jp.getInt("score2",0);
        score3 = jp.getInt("score3",0);
        score4 = jp.getInt("score4",0);

        //get Round Number and Record Number String
        roundNumber = jp.getInt("RoundNumber",1);
        PrintRecord = jp.getString("PrintRoundNumber","");

        //get player string Record
        record1 = jp.getString("Player1Record","");
        record2 = jp.getString("Player2Record","");
        record3 = jp.getString("Player3Record","");
        record4 = jp.getString("Player4Record","");

        //get winner
        winner = jp.getInt("winner",0);

        //get stand and set button text
        if(winner == 1){
            stand = jp.getInt("stand1",0);
            nextPlayer1.setText(name2);
            nextPlayer2.setText(name3);
            nextPlayer3.setText(name4);

            button2.setText(name2);
            button3.setText(name3);
            button4.setText(name4);

        }
        else if(winner == 2){
            stand = jp.getInt("stand2",0);
            nextPlayer1.setText(name1);
            nextPlayer2.setText(name3);
            nextPlayer3.setText(name4);

            button2.setText(name1);
            button3.setText(name3);
            button4.setText(name4);
        }
        else if(winner == 3){
            stand = jp.getInt("stand3",0);
            nextPlayer1.setText(name1);
            nextPlayer2.setText(name2);
            nextPlayer3.setText(name4);

            button2.setText(name1);
            button3.setText(name2);
            button4.setText(name4);
        }
        else if(winner == 4){
            stand = jp.getInt("stand4",0);
            nextPlayer1.setText(name1);
            nextPlayer2.setText(name2);
            nextPlayer3.setText(name3);

            button2.setText(name1);
            button3.setText(name2);
            button4.setText(name3);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JpFinish.this, JpCounting.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(JpFinish.this)
                        .setTitle("返回主頁?")
                        .setMessage("返回主頁後所有紀錄將被取消")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(JpFinish.this, MainActivity.class);
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
                Intent i = new Intent(JpFinish.this, setting.class);
                startActivity(i);
            }
        });

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button = findViewById(checkedId);
                Toast.makeText(JpFinish.this, button.getText() + "是莊家" , Toast.LENGTH_SHORT).show();

                if (button.getText().toString().equals("自己")) {
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

        SharedPreferences.Editor JpEdit = jp.edit();

        nextPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Check = fu.getText().toString();

                //check input
                if (Eaten == 0) {
                    Toast.makeText(JpFinish.this, "請輸入番數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else if (Check.trim().equals("")) {
                    Toast.makeText(JpFinish.this, "請輸入符數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else {
                    TotalFu = Integer.parseInt(fu.getText().toString());
                    if(TotalFu < 20 && (Eaten < 5)){
                        Toast.makeText(JpFinish.this, Eaten + "番符數最少要有20", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }
                    else if (TotalFu < 30 && Eaten == 1) {
                        Toast.makeText(JpFinish.this, "1番最少符數為30", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else if ((TotalFu < 25 && Eaten == 2) || (TotalFu < 25 && Eaten == 3) || (TotalFu < 25 && Eaten == 4)) {
                        Toast.makeText(JpFinish.this, Eaten + "番榮胡最少符數為25", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else if (mainer == 0) {
                        Toast.makeText(JpFinish.this, "請選擇莊家", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else {
                        StartCal = 1;
                    }
                }

                if (StartCal == 1) {
                    //adding 0 to stand record
                    if (stand1 == 0) {
                        record1 += "0\n\n";
                    }
                    if (stand2 == 0) {
                        record2 += "0\n\n";
                    }
                    if (stand3 == 0) {
                        record3 += "0\n\n";
                    }
                    if (stand4 == 0) {
                        record4 += "0\n\n";
                    }
                }

                if (winner == 1 && StartCal == 1) {
                    //1番30符
                    if (Eaten == 1 && TotalFu == 30) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 1500;
                                record1 += "+1500\n\n";
                                score2 -= 1500;
                                record2 += "-1500\n\n";
                            } else if (mainer != 1) {
                                score1 += 1000;
                                record1 += "+1000\n\n";
                                score2 -= 1000;
                                record2 += "-1000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 2500;
                                record1 += "+2500\n\n";
                                score2 -= 1500;
                                record2 += "-1500\n\n";
                            } else if (mainer != 1) {
                                score1 += 2000;
                                record1 += "+2000\n\n";
                                score2 -= 1000;
                                record2 += "-1000\n\n";
                            }
                        }

                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 2000;
                                record1 += "+2000\n\n";
                                score2 -= 2000;
                                record2 += "-2000\n\n";
                            } else if (mainer != 1) {
                                score1 += 1300;
                                record1 += "+1300\n\n";
                                score2 -= 1300;
                                record2 += "-1300\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 3000;
                                record1 += "+3000\n\n";
                                score2 -= 2000;
                                record2 += "-2000\n\n";
                            } else if (mainer != 1) {
                                score1 += 2300;
                                record1 += "+2300\n\n";
                                score2 -= 1300;
                                record2 += "-1300\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 2400;
                                record1 += "+2400\n\n";
                                score2 -= 2400;
                                record2 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score1 += 1600;
                                record1 += "+1600\n\n";
                                score2 -= 1600;
                                record2 += "-1600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 3400;
                                record1 += "+3400\n\n";
                                score2 -= 2400;
                                record2 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score1 += 2600;
                                record1 += "+2600\n\n";
                                score2 -= 1600;
                                record2 += "-1600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 2900;
                                record1 += "+2900\n\n";
                                score2 -= 2900;
                                record2 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score1 += 2000;
                                record1 += "+2000\n\n";
                                score2 -= 2000;
                                record2 += "-2000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 2900;
                                record2 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score1 += 3000;
                                record1 += "+3000\n\n";
                                score2 -= 2000;
                                record2 += "-2000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 3400;
                                record1 += "+3400\n\n";
                                score2 -= 3400;
                                record2 += "-3400\n\n";
                            } else if (mainer != 1) {
                                score1 += 2300;
                                record1 += "+2300\n\n";
                                score2 -= 2300;
                                record2 += "-2300\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 4400;
                                record1 += "+4400\n\n";
                                score2 -= 3400;
                                record2 += "-3400\n\n";
                            } else if (mainer != 1) {
                                score1 += 3300;
                                record1 += "+3300\n\n";
                                score2 -= 2300;
                                record2 += "-2300\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score1 += 2600;
                                record1 += "+2600\n\n";
                                score2 -= 2600;
                                record2 += "-2600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 4900;
                                record1 += "+4900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score1 += 3600;
                                record1 += "+3600\n\n";
                                score2 -= 2600;
                                record2 += "-2600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 4400;
                                record1 += "+4400\n\n";
                                score2 -= 4400;
                                record2 += "-4400\n\n";
                            } else if (mainer != 1) {
                                score1 += 2900;
                                record1 += "+2900\n\n";
                                score2 -= 2900;
                                record2 += "-2900\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 5400;
                                record1 += "+5400\n\n";
                                score2 -= 4400;
                                record2 += "-4400\n\n";
                            } else if (mainer != 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 2900;
                                record2 += "-2900\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 90 && TotalFu <= 100)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 4800;
                                record1 += "+4800\n\n";
                                score2 -= 4800;
                                record2 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score1 += 3200;
                                record1 += "+3200\n\n";
                                score2 -= 3200;
                                record2 += "-3200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 5800;
                                record1 += "+5800\n\n";
                                score2 -= 4800;
                                record2 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score1 += 4200;
                                record1 += "+4200\n\n";
                                score2 -= 3200;
                                record2 += "-3200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;

                    } else if (Eaten == 1 && TotalFu > 100) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 5300;
                                record1 += "+5300\n\n";
                                score2 -= 5300;
                                record2 += "-5300\n\n";
                            } else if (mainer != 1) {
                                score1 += 3600;
                                record1 += "+3600\n\n";
                                score2 -= 3600;
                                record2 += "-3600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 6300;
                                record1 += "+6300\n\n";
                                score2 -= 5300;
                                record2 += "-5300\n\n";
                            } else if (mainer != 1) {
                                score1 += 4600;
                                record1 += "+4600\n\n";
                                score2 -= 3600;
                                record2 += "-3600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && TotalFu == 25) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 2400;
                                record1 += "+2400\n\n";
                                score2 -= 2400;
                                record2 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score1 += 1600;
                                record1 += "+1600\n\n";
                                score2 -= 1600;
                                record2 += "-1600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 3400;
                                record1 += "+3400\n\n";
                                score2 -= 2400;
                                record2 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score1 += 2600;
                                record1 += "+2600\n\n";
                                score2 -= 1600;
                                record2 += "-1600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 2900;
                                record1 += "+2900\n\n";
                                score2 -= 2900;
                                record2 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score1 += 2000;
                                record1 += "+2000\n\n";
                                score2 -= 2000;
                                record2 += "-2000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 2900;
                                record2 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score1 += 3000;
                                record1 += "+3000\n\n";
                                score2 -= 2000;
                                record2 += "-2000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score1 += 2600;
                                record1 += "+2600\n\n";
                                score2 -= 2600;
                                record2 += "-2600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 4900;
                                record1 += "+4900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score1 += 3600;
                                record1 += "+3600\n\n";
                                score2 -= 2600;
                                record2 += "-2600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 4800;
                                record1 += "+4800\n\n";
                                score2 -= 4800;
                                record2 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score1 += 3200;
                                record1 += "+3200\n\n";
                                score2 -= 3200;
                                record2 += "-3200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 5800;
                                record1 += "+5800\n\n";
                                score2 -= 4800;
                                record2 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score1 += 4200;
                                record1 += "+4200\n\n";
                                score2 -= 3200;
                                record2 += "-3200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 5800;
                                record1 += "+5800\n\n";
                                score2 -= 5800;
                                record2 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 6800;
                                record1 += "+6800\n\n";
                                score2 -= 5800;
                                record2 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score1 += 4900;
                                record1 += "+4900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 6800;
                                record1 += "+6800\n\n";
                                score2 -= 6800;
                                record2 += "-6800\n\n";
                            } else if (mainer != 1) {
                                score1 += 4500;
                                record1 += "+4500\n\n";
                                score2 -= 4500;
                                record2 += "-4500\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 7800;
                                record1 += "+7800\n\n";
                                score2 -= 6800;
                                record2 += "-6800\n\n";
                            } else if (mainer != 1) {
                                score1 += 5500;
                                record1 += "+5500\n\n";
                                score2 -= 4500;
                                record2 += "-4500\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 7700;
                                record1 += "+7700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score1 += 5200;
                                record1 += "+5200\n\n";
                                score2 -= 5200;
                                record2 += "-5200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 8700;
                                record1 += "+8700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score1 += 6200;
                                record1 += "+6200\n\n";
                                score2 -= 5200;
                                record2 += "-5200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 8700;
                                record1 += "+8700\n\n";
                                score2 -= 8700;
                                record2 += "-8700\n\n";
                            } else if (mainer != 1) {
                                score1 += 5800;
                                record1 += "+5800\n\n";
                                score2 -= 5800;
                                record2 += "-5800\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 9700;
                                record1 += "+9700\n\n";
                                score2 -= 8700;
                                record2 += "-8700\n\n";
                            } else if (mainer != 1) {
                                score1 += 6800;
                                record1 += "+6800\n\n";
                                score2 -= 5800;
                                record2 += "-5800\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 9600;
                                record1 += "+9600\n\n";
                                score2 -= 9600;
                                record2 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score1 += 6400;
                                record1 += "+6400\n\n";
                                score2 -= 6400;
                                record2 += "-6400\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 10600;
                                record1 += "+10600\n\n";
                                score2 -= 9600;
                                record2 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score1 += 7400;
                                record1 += "+7400\n\n";
                                score2 -= 6400;
                                record2 += "-6400\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && TotalFu > 100) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 10600;
                                record1 += "+10600\n\n";
                                score2 -= 10600;
                                record2 += "-10600\n\n";
                            } else if (mainer != 1) {
                                score1 += 7100;
                                record1 += "+7100\n\n";
                                score2 -= 7100;
                                record2 += "-7100\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 11600;
                                record1 += "+11600\n\n";
                                score2 -= 10600;
                                record2 += "-10600\n\n";
                            } else if (mainer != 1) {
                                score1 += 8100;
                                record1 += "+8100\n\n";
                                score2 -= 7100;
                                record2 += "-7100\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && TotalFu == 25) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 4800;
                                record1 += "+4800\n\n";
                                score2 -= 4800;
                                record2 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score1 += 3200;
                                record1 += "+3200\n\n";
                                score2 -= 3200;
                                record2 += "-3200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 5800;
                                record1 += "+5800\n\n";
                                score2 -= 4800;
                                record2 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score1 += 4200;
                                record1 += "+4200\n\n";
                                score2 -= 3200;
                                record2 += "-3200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 5800;
                                record1 += "+5800\n\n";
                                score2 -= 5800;
                                record2 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score1 += 3900;
                                record1 += "+3900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 6800;
                                record1 += "+6800\n\n";
                                score2 -= 5800;
                                record2 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score1 += 4900;
                                record1 += "+4900\n\n";
                                score2 -= 3900;
                                record2 += "-3900\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 7700;
                                record1 += "+7700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score1 += 5200;
                                record1 += "+5200\n\n";
                                score2 -= 5200;
                                record2 += "-5200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 8700;
                                record1 += "+8700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score1 += 6200;
                                record1 += "+6200\n\n";
                                score2 -= 5200;
                                record2 += "-5200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 9600;
                                record1 += "+9600\n\n";
                                score2 -= 9600;
                                record2 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score1 += 6400;
                                record1 += "+6400\n\n";
                                score2 -= 6400;
                                record2 += "-6400\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 10600;
                                record1 += "+10600\n\n";
                                score2 -= 9600;
                                record2 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score1 += 7400;
                                record1 += "+7400\n\n";
                                score2 -= 6400;
                                record2 += "-6400\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 11600;
                                record1 += "+11600\n\n";
                                score2 -= 11600;
                                record2 += "-11600\n\n";
                            } else if (mainer != 1) {
                                score1 += 7700;
                                record1 += "+7700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 12600;
                                record1 += "+12600\n\n";
                                score2 -= 11600;
                                record2 += "-11600\n\n";
                            } else if (mainer != 1) {
                                score1 += 8700;
                                record1 += "+8700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 12000;
                                record1 += "+12000\n\n";
                                score2 -= 12000;
                                record2 += "-12000\n\n";
                            } else if (mainer != 1) {
                                score1 += 8000;
                                record1 += "+8000\n\n";
                                score2 -= 8000;
                                record2 += "-8000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 13000;
                                record1 += "+13000\n\n";
                                score2 -= 12000;
                                record2 += "-12000\n\n";
                            } else if (mainer != 1) {
                                score1 += 9000;
                                record1 += "+9000\n\n";
                                score2 -= 8000;
                                record2 += "-8000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 4 && TotalFu == 25) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 9600;
                                record1 += "+9600\n\n";
                                score2 -= 9600;
                                record2 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score1 += 6400;
                                record1 += "+6400\n\n";
                                score2 -= 6400;
                                record2 += "-6400\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 10600;
                                record1 += "+10600\n\n";
                                score2 -= 9600;
                                record2 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score1 += 7400;
                                record1 += "+7400\n\n";
                                score2 -= 6400;
                                record2 += "-6400\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 11600;
                                record1 += "+11600\n\n";
                                score2 -= 11600;
                                record2 += "-11600\n\n";
                            } else if (mainer != 1) {
                                score1 += 7700;
                                record1 += "+7700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 12600;
                                record1 += "+12600\n\n";
                                score2 -= 11600;
                                record2 += "-11600\n\n";
                            } else if (mainer != 1) {
                                score1 += 8700;
                                record1 += "+8700\n\n";
                                score2 -= 7700;
                                record2 += "-7700\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 6 || Eaten == 7) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 18000;
                                record1 += "+18000\n\n";
                                score2 -= 18000;
                                record2 += "-18000\n\n";
                            } else if (mainer != 1) {
                                score1 += 12000;
                                record1 += "+12000\n\n";
                                score2 -= 12000;
                                record2 += "-12000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 19000;
                                record1 += "+19000\n\n";
                                score2 -= 18000;
                                record2 += "-18000\n\n";
                            } else if (mainer != 1) {
                                score1 += 13000;
                                record1 += "+13000\n\n";
                                score2 -= 12000;
                                record2 += "-12000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 24000;
                                record1 += "+24000\n\n";
                                score2 -= 24000;
                                record2 += "-24000\n\n";
                            } else if (mainer != 1) {
                                score1 += 16000;
                                record1 += "+16000\n\n";
                                score2 -= 16000;
                                record2 += "-16000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 25000;
                                record1 += "+25000\n\n";
                                score2 -= 24000;
                                record2 += "-24000\n\n";
                            } else if (mainer != 1) {
                                score1 += 17000;
                                record1 += "+17000\n\n";
                                score2 -= 16000;
                                record2 += "-16000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 12 || Eaten == 11) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 36000;
                                record1 += "+36000\n\n";
                                score2 -= 36000;
                                record2 += "-36000\n\n";
                            } else if (mainer != 1) {
                                score1 += 24000;
                                record1 += "+24000\n\n";
                                score2 -= 24000;
                                record2 += "-24000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 37000;
                                record1 += "+37000\n\n";
                                score2 -= 36000;
                                record2 += "-36000\n\n";
                            } else if (mainer != 1) {
                                score1 += 25000;
                                record1 += "+25000\n\n";
                                score2 -= 24000;
                                record2 += "-24000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 13) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 48000;
                                record1 += "+48000\n\n";
                                score2 -= 48000;
                                record2 += "-48000\n\n";
                            } else if (mainer != 1) {
                                score1 += 32000;
                                record1 += "+32000\n\n";
                                score2 -= 32000;
                                record2 += "-32000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 49000;
                                record1 += "+49000\n\n";
                                score2 -= 48000;
                                record2 += "-48000\n\n";
                            } else if (mainer != 1) {
                                score1 += 33000;
                                record1 += "+33000\n\n";
                                score2 -= 32000;
                                record2 += "-32000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 26) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 96000;
                                record1 += "+96000\n\n";
                                score2 -= 96000;
                                record2 += "-96000\n\n";
                            } else if (mainer != 1) {
                                score1 += 64000;
                                record1 += "+64000\n\n";
                                score2 -= 64000;
                                record2 += "-64000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 97000;
                                record1 += "+97000\n\n";
                                score2 -= 96000;
                                record2 += "-96000\n\n";
                            } else if (mainer != 1) {
                                score1 += 65000;
                                record1 += "+65000\n\n";
                                score2 -= 64000;
                                record2 += "-64000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 39) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 144000;
                                record1 += "+144000\n\n";
                                score2 -= 144000;
                                record2 += "-144000\n\n";
                            } else if (mainer != 1) {
                                score1 += 96000;
                                record1 += "+96000\n\n";
                                score2 -= 96000;
                                record2 += "-96000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 145000;
                                record1 += "+145000\n\n";
                                score2 -= 145000;
                                record2 += "-145000\n\n";
                            } else if (mainer != 1) {
                                score1 += 97000;
                                record1 += "+97000\n\n";
                                score2 -= 96000;
                                record2 += "-96000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 52) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 192000;
                                record1 += "+192000\n\n";
                                score2 -= 192000;
                                record2 += "-192000\n\n";
                            } else if (mainer != 1) {
                                score1 += 128000;
                                record1 += "+128000\n\n";
                                score2 -= 128000;
                                record2 += "-128000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 193000;
                                record1 += "+193000\n\n";
                                score2 -= 192000;
                                record2 += "-192000\n\n";
                            } else if (mainer != 1) {
                                score1 += 129000;
                                record1 += "+129000\n\n";
                                score2 -= 128000;
                                record2 += "-128000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 65) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 240000;
                                record1 += "+240000\n\n";
                                score2 -= 240000;
                                record2 += "-240000\n\n";
                            } else if (mainer != 1) {
                                score1 += 160000;
                                record1 += "+160000\n\n";
                                score2 -= 160000;
                                record2 += "-160000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 241000;
                                record1 += "+241000\n\n";
                                score2 -= 240000;
                                record2 += "-240000\n\n";
                            } else if (mainer != 1) {
                                score1 += 161000;
                                record1 += "+161000\n\n";
                                score2 -= 160000;
                                record2 += "-160000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 78) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 288000;
                                record1 += "+288000\n\n";
                                score2 -= 288000;
                                record2 += "-288000\n\n";
                            } else if (mainer != 1) {
                                score1 += 192000;
                                record1 += "+192000\n\n";
                                score2 -= 192000;
                                record2 += "-192000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 289000;
                                record1 += "+289000\n\n";
                                score2 -= 288000;
                                record2 += "-288000\n\n";
                            } else if (mainer != 1) {
                                score1 += 193000;
                                record1 += "+193000\n\n";
                                score2 -= 192000;
                                record2 += "-192000\n\n";
                            }
                        }

                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 91) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score1 += 336000;
                                record1 += "+336000\n\n";
                                score2 -= 336000;
                                record2 += "-336000\n\n";
                            } else if (mainer != 1) {
                                score1 += 224000;
                                record1 += "+224000\n\n";
                                score2 -= 224000;
                                record2 += "-224000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score1 += 337000;
                                record1 += "+337000\n\n";
                                score2 -= 336000;
                                record2 += "-336000\n\n";
                            } else if (mainer != 1) {
                                score1 += 225000;
                                record1 += "+225000\n\n";
                                score2 -= 224000;
                                record2 += "-224000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }
                }

                if (winner == 2 && StartCal == 1) {
                    //1番30符
                    if (Eaten == 1 && TotalFu == 30) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 1500;
                                record2 += "+1500\n\n";
                                score1 -= 1500;
                                record1 += "-1500\n\n";
                            } else if (mainer != 1) {
                                score2 += 1000;
                                record2 += "+1000\n\n";
                                score1 -= 1000;
                                record1 += "-1000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 2500;
                                record2 += "+2500\n\n";
                                score1 -= 1500;
                                record1 += "-1500\n\n";
                            } else if (mainer != 1) {
                                score2 += 2000;
                                record2 += "+2000\n\n";
                                score1 -= 1000;
                                record1 += "-1000\n\n";
                            }
                        }

                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 2000;
                                record2 += "+2000\n\n";
                                score1 -= 2000;
                                record1 += "-2000\n\n";
                            } else if (mainer != 1) {
                                score2 += 1300;
                                record2 += "+1300\n\n";
                                score1 -= 1300;
                                record1 += "-1300\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 3000;
                                record2 += "+3000\n\n";
                                score1 -= 2000;
                                record1 += "-2000\n\n";
                            } else if (mainer != 1) {
                                score2 += 2300;
                                record2 += "+2300\n\n";
                                score1 -= 1300;
                                record1 += "-1300\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 2400;
                                record2 += "+2400\n\n";
                                score1 -= 2400;
                                record1 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score2 += 1600;
                                record2 += "+1600\n\n";
                                score1 -= 1600;
                                record1 += "-1600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 3400;
                                record2 += "+3400\n\n";
                                score1 -= 2400;
                                record1 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score2 += 2600;
                                record2 += "+2600\n\n";
                                score1 -= 1600;
                                record1 += "-1600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 2900;
                                record2 += "+2900\n\n";
                                score1 -= 2900;
                                record1 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score2 += 2000;
                                record2 += "+2000\n\n";
                                score1 -= 2000;
                                record1 += "-2000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 2900;
                                record1 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score2 += 3000;
                                record2 += "+3000\n\n";
                                score1 -= 2000;
                                record1 += "-2000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 3400;
                                record2 += "+3400\n\n";
                                score1 -= 3400;
                                record1 += "-3400\n\n";
                            } else if (mainer != 1) {
                                score2 += 2300;
                                record2 += "+2300\n\n";
                                score1 -= 2300;
                                record1 += "-2300\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 4400;
                                record2 += "+4400\n\n";
                                score1 -= 3400;
                                record1 += "-3400\n\n";
                            } else if (mainer != 1) {
                                score2 += 3300;
                                record2 += "+3300\n\n";
                                score1 -= 2300;
                                record1 += "-2300\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score2 += 2600;
                                record2 += "+2600\n\n";
                                score1 -= 2600;
                                record1 += "-2600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 4900;
                                record2 += "+4900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score2 += 3600;
                                record2 += "+3600\n\n";
                                score1 -= 2600;
                                record1 += "-2600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 4400;
                                record2 += "+4400\n\n";
                                score1 -= 4400;
                                record1 += "-4400\n\n";
                            } else if (mainer != 1) {
                                score2 += 2900;
                                record2 += "+2900\n\n";
                                score1 -= 2900;
                                record1 += "-2900\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 5400;
                                record2 += "+5400\n\n";
                                score1 -= 4400;
                                record1 += "-4400\n\n";
                            } else if (mainer != 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 2900;
                                record1 += "-2900\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 1 && (TotalFu > 90 && TotalFu <= 100)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 4800;
                                record2 += "+4800\n\n";
                                score1 -= 4800;
                                record1 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score2 += 3200;
                                record2 += "+3200\n\n";
                                score1 -= 3200;
                                record1 += "-3200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 5800;
                                record2 += "+5800\n\n";
                                score1 -= 4800;
                                record1 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score2 += 4200;
                                record2 += "+4200\n\n";
                                score1 -= 3200;
                                record1 += "-3200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;

                    } else if (Eaten == 1 && TotalFu > 100) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 5300;
                                record2 += "+5300\n\n";
                                score1 -= 5300;
                                record1 += "-5300\n\n";
                            } else if (mainer != 1) {
                                score2 += 3600;
                                record2 += "+3600\n\n";
                                score1 -= 3600;
                                record1 += "-3600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 6300;
                                record2 += "+6300\n\n";
                                score1 -= 5300;
                                record1 += "-5300\n\n";
                            } else if (mainer != 1) {
                                score2 += 4600;
                                record2 += "+4600\n\n";
                                score1 -= 3600;
                                record1 += "-3600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && TotalFu == 25) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 2400;
                                record2 += "+2400\n\n";
                                score1 -= 2400;
                                record1 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score2 += 1600;
                                record2 += "+1600\n\n";
                                score1 -= 1600;
                                record1 += "-1600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 3400;
                                record2 += "+3400\n\n";
                                score1 -= 2400;
                                record1 += "-2400\n\n";
                            } else if (mainer != 1) {
                                score2 += 2600;
                                record2 += "+2600\n\n";
                                score1 -= 1600;
                                record1 += "-1600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 2900;
                                record2 += "+2900\n\n";
                                score1 -= 2900;
                                record1 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score2 += 2000;
                                record2 += "+2000\n\n";
                                score1 -= 2000;
                                record1 += "-2000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 2900;
                                record1 += "-2900\n\n";
                            } else if (mainer != 1) {
                                score2 += 3000;
                                record2 += "+3000\n\n";
                                score1 -= 2000;
                                record1 += "-2000\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score2 += 2600;
                                record2 += "+2600\n\n";
                                score1 -= 2600;
                                record1 += "-2600\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 4900;
                                record2 += "+4900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            } else if (mainer != 1) {
                                score2 += 3600;
                                record2 += "+3600\n\n";
                                score1 -= 2600;
                                record1 += "-2600\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 4800;
                                record2 += "+4800\n\n";
                                score1 -= 4800;
                                record1 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score2 += 3200;
                                record2 += "+3200\n\n";
                                score1 -= 3200;
                                record1 += "-3200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 5800;
                                record2 += "+5800\n\n";
                                score1 -= 4800;
                                record1 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score2 += 4200;
                                record2 += "+4200\n\n";
                                score1 -= 3200;
                                record1 += "-3200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 5800;
                                record2 += "+5800\n\n";
                                score1 -= 5800;
                                record1 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 6800;
                                record2 += "+6800\n\n";
                                score1 -= 5800;
                                record1 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score2 += 4900;
                                record2 += "+4900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 6800;
                                record2 += "+6800\n\n";
                                score1 -= 6800;
                                record1 += "-6800\n\n";
                            } else if (mainer != 1) {
                                score2 += 4500;
                                record2 += "+4500\n\n";
                                score1 -= 4500;
                                record1 += "-4500\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 7800;
                                record2 += "7800\n\n";
                                score1 -= 6800;
                                record1 += "-6800\n\n";
                            } else if (mainer != 1) {
                                score2 += 5500;
                                record2 += "+5500\n\n";
                                score1 -= 4500;
                                record1 += "-4500\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 7700;
                                record2 += "+7700\n\n";
                                score1 -= 7700;
                                record1 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score2 += 5200;
                                record2 += "+5200\n\n";
                                score1 -= 5200;
                                record1 += "-5200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 8700;
                                record2 += "8700\n\n";
                                score1 -= 7700;
                                record1 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score2 += 6200;
                                record2 += "+6200\n\n";
                                score1 -= 5200;
                                record1 += "-5200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 8700;
                                record2 += "+8700\n\n";
                                score1 -= 8700;
                                record1 += "-8700\n\n";
                            } else if (mainer != 1) {
                                score2 += 5800;
                                record2 += "+5800\n\n";
                                score1 -= 5800;
                                record1 += "-5800\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 9700;
                                record2 += "9700\n\n";
                                score1 -= 8700;
                                record1 += "-8700\n\n";
                            } else if (mainer != 1) {
                                score2 += 6800;
                                record2 += "+6800\n\n";
                                score1 -= 5800;
                                record1 += "-5800\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 9600;
                                record2 += "+9600\n\n";
                                score1 -= 9600;
                                record1 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score2 += 6400;
                                record2 += "+6400\n\n";
                                score1 -= 6400;
                                record1 += "-6400\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 10600;
                                record2 += "10600\n\n";
                                score1 -= 9600;
                                record1 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score2 += 7400;
                                record2 += "+7400\n\n";
                                score1 -= 6400;
                                record1 += "-6400\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 2 && TotalFu > 100) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 10600;
                                record2 += "+10600\n\n";
                                score1 -= 10600;
                                record1 += "-10600\n\n";
                            } else if (mainer != 1) {
                                score2 += 7100;
                                record2 += "+7100\n\n";
                                score1 -= 7100;
                                record1 += "-7100\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 11600;
                                record2 += "11600\n\n";
                                score1 -= 10600;
                                record1 += "-10600\n\n";
                            } else if (mainer != 1) {
                                score2 += 8100;
                                record2 += "+8100\n\n";
                                score1 -= 7100;
                                record1 += "-7100\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && TotalFu == 25) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 4800;
                                record2 += "+4800\n\n";
                                score1 -= 4800;
                                record1 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score2 += 3200;
                                record2 += "+3200\n\n";
                                score1 -= 3200;
                                record1 += "-3200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 5800;
                                record2 += "5800\n\n";
                                score1 -= 4800;
                                record1 += "-4800\n\n";
                            } else if (mainer != 1) {
                                score2 += 4200;
                                record2 += "+4200\n\n";
                                score1 -= 3200;
                                record1 += "-3200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 5800;
                                record2 += "+5800\n\n";
                                score1 -= 5800;
                                record1 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score2 += 3900;
                                record2 += "+3900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 6800;
                                record2 += "6800\n\n";
                                score1 -= 5800;
                                record1 += "-5800\n\n";
                            } else if (mainer != 1) {
                                score2 += 4900;
                                record2 += "+4900\n\n";
                                score1 -= 3900;
                                record1 += "-3900\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 7700;
                                record2 += "+7700\n\n";
                                score1 -= 7700;
                                record1 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score2 += 5200;
                                record2 += "+5200\n\n";
                                score1 -= 5200;
                                record1 += "-5200\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 8700;
                                record2 += "8700\n\n";
                                score1 -= 7700;
                                record1 += "-7700\n\n";
                            } else if (mainer != 1) {
                                score2 += 6200;
                                record2 += "+6200\n\n";
                                score1 -= 5200;
                                record1 += "-5200\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 9600;
                                record2 += "+9600\n\n";
                                score1 -= 9600;
                                record1 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score2 += 6400;
                                record2 += "+6400\n\n";
                                score1 -= 6400;
                                record1 += "-6400\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 10600;
                                record2 += "10600\n\n";
                                score1 -= 9600;
                                record1 += "-9600\n\n";
                            } else if (mainer != 1) {
                                score2 += 7400;
                                record2 += "+7400\n\n";
                                score1 -= 6400;
                                record1 += "-6400\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 11600;
                                record2 += "+11600\n\n";
                                score1 -= 11600;
                                record1 += "-11600\n\n";
                            } else if (mainer != 1) {
                                score2 += 7700;
                                record2 += "+7700\n\n";
                                score1 -= 7700;
                                record1 += "-7700\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 12600;
                                record2 += "12600\n\n";
                                score1 -= 11600;
                                record1 += "-11600\n\n";
                            } else if (mainer != 1) {
                                score2 += 8700;
                                record2 += "+8700\n\n";
                                score1 -= 7700;
                                record1 += "-7700\n\n";
                            }
                        }
                        record3 += "0\n\n";
                        record4 += "0\n\n";
                        PrintRecord += roundNumber + ".\n\n";
                        roundNumber += 1;
                    } else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        if (stand == 0) {
                            if (mainer == 1) {
                                score2 += 12000;
                                record2 += "+12000\n\n";
                                score1 -= 12000;
                                record1 += "-12000\n\n";
                            } else if (mainer != 1) {
                                score2 += 8000;
                                record2 += "+8000\n\n";
                                score1 -= 8000;
                                record1 += "-8000\n\n";
                            }
                        } else if (stand == 1) {
                            if (mainer == 1) {
                                score2 += 13000;
                                record2 += "13000\n\n";
                                score1 -= 12000;
                                record1 += "-12000\n\n";
                            } else if (mainer != 1) {
                                score2 += 9000;
                                record2 += "+9000\n\n";
                                score1 -= 8000;
                                record1 += "-8000\n\n";
                            }
                        }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (Eaten == 4 && TotalFu == 25) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 9600;
                                    record2 += "+9600\n\n";
                                    score1 -= 9600;
                                    record1 += "-9600\n\n";
                                } else if (mainer != 1) {
                                    score2 += 6400;
                                    record2 += "+6400\n\n";
                                    score1 -= 6400;
                                    record1 += "-6400\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 10600;
                                    record2 += "10600\n\n";
                                    score1 -= 9600;
                                    record1 += "-9600\n\n";
                                } else if (mainer != 1) {
                                    score2 += 7400;
                                    record2 += "+7400\n\n";
                                    score1 -= 6400;
                                    record1 += "-6400\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 11600;
                                    record2 += "+11600\n\n";
                                    score1 -= 11600;
                                    record1 += "-11600\n\n";
                                } else if (mainer != 1) {
                                    score2 += 7700;
                                    record2 += "+7700\n\n";
                                    score1 -= 7700;
                                    record1 += "-7700\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 12600;
                                    record2 += "12600\n\n";
                                    score1 -= 11600;
                                    record1 += "-11600\n\n";
                                } else if (mainer != 1) {
                                    score2 += 8700;
                                    record2 += "+8700\n\n";
                                    score1 -= 7700;
                                    record1 += "-7700\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 6 || Eaten == 7) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 18000;
                                    record2 += "+18000\n\n";
                                    score1 -= 18000;
                                    record1 += "-18000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 12000;
                                    record2 += "+12000\n\n";
                                    score1 -= 12000;
                                    record1 += "-12000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 19000;
                                    record2 += "19000\n\n";
                                    score1 -= 18000;
                                    record1 += "-18000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 13000;
                                    record2 += "+13000\n\n";
                                    score1 -= 12000;
                                    record1 += "-12000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 24000;
                                    record2 += "+24000\n\n";
                                    score1 -= 24000;
                                    record1 += "-24000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 16000;
                                    record2 += "+16000\n\n";
                                    score1 -= 16000;
                                    record1 += "-16000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 25000;
                                    record2 += "25000\n\n";
                                    score1 -= 24000;
                                    record1 += "-24000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 17000;
                                    record2 += "+17000\n\n";
                                    score1 -= 16000;
                                    record1 += "-16000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 12 || Eaten == 11) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 36000;
                                    record2 += "+36000\n\n";
                                    score1 -= 36000;
                                    record1 += "-36000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 24000;
                                    record2 += "+24000\n\n";
                                    score1 -= 24000;
                                    record1 += "-24000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 37000;
                                    record2 += "37000\n\n";
                                    score1 -= 36000;
                                    record1 += "-36000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 25000;
                                    record2 += "+25000\n\n";
                                    score1 -= 24000;
                                    record1 += "-24000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 13) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 48000;
                                    record2 += "+48000\n\n";
                                    score1 -= 48000;
                                    record1 += "-48000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 32000;
                                    record2 += "+32000\n\n";
                                    score1 -= 32000;
                                    record1 += "-32000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 49000;
                                    record2 += "49000\n\n";
                                    score1 -= 48000;
                                    record1 += "-48000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 33000;
                                    record2 += "+33000\n\n";
                                    score1 -= 32000;
                                    record1 += "-32000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 26) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 96000;
                                    record2 += "+96000\n\n";
                                    score1 -= 96000;
                                    record1 += "-96000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 64000;
                                    record2 += "+64000\n\n";
                                    score1 -= 64000;
                                    record1 += "-64000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 97000;
                                    record2 += "97000\n\n";
                                    score1 -= 96000;
                                    record1 += "-96000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 65000;
                                    record2 += "+65000\n\n";
                                    score1 -= 64000;
                                    record1 += "-64000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 39) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 144000;
                                    record2 += "+144000\n\n";
                                    score1 -= 144000;
                                    record1 += "-144000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 96000;
                                    record2 += "+96000\n\n";
                                    score1 -= 96000;
                                    record1 += "-96000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 145000;
                                    record2 += "145000\n\n";
                                    score1 -= 145000;
                                    record1 += "-145000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 97000;
                                    record2 += "+97000\n\n";
                                    score1 -= 96000;
                                    record1 += "-96000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 52) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 192000;
                                    record2 += "+192000\n\n";
                                    score1 -= 192000;
                                    record1 += "-192000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 128000;
                                    record2 += "+128000\n\n";
                                    score1 -= 128000;
                                    record1 += "-128000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 193000;
                                    record2 += "193000\n\n";
                                    score1 -= 192000;
                                    record1 += "-192000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 129000;
                                    record2 += "+129000\n\n";
                                    score1 -= 128000;
                                    record1 += "-128000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 65) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 240000;
                                    record2 += "+240000\n\n";
                                    score1 -= 240000;
                                    record1 += "-240000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 160000;
                                    record2 += "+160000\n\n";
                                    score1 -= 160000;
                                    record1 += "-160000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 241000;
                                    record2 += "241000\n\n";
                                    score1 -= 240000;
                                    record1 += "-240000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 161000;
                                    record2 += "+161000\n\n";
                                    score1 -= 160000;
                                    record1 += "-160000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 78) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 288000;
                                    record2 += "+288000\n\n";
                                    score1 -= 288000;
                                    record1 += "-288000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 192000;
                                    record2 += "+192000\n\n";
                                    score1 -= 192000;
                                    record1 += "-192000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 289000;
                                    record2 += "289000\n\n";
                                    score1 -= 288000;
                                    record1 += "-288000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 193000;
                                    record2 += "+193000\n\n";
                                    score1 -= 192000;
                                    record1 += "-192000\n\n";
                                }
                            }

                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        } else if (Eaten == 91) {
                            if (stand == 0) {
                                if (mainer == 1) {
                                    score2 += 336000;
                                    record2 += "+336000\n\n";
                                    score1 -= 336000;
                                    record1 += "-336000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 224000;
                                    record2 += "+224000\n\n";
                                    score1 -= 224000;
                                    record1 += "-224000\n\n";
                                }
                            } else if (stand == 1) {
                                if (mainer == 1) {
                                    score2 += 337000;
                                    record2 += "337000\n\n";
                                    score1 -= 336000;
                                    record1 += "-336000\n\n";
                                } else if (mainer != 1) {
                                    score2 += 225000;
                                    record2 += "+225000\n\n";
                                    score1 -= 224000;
                                    record1 += "-224000\n\n";
                                }
                            }
                            record3 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }

                if (winner == 3 && StartCal == 1) {
                    //1番30符
                    if (Eaten == 1 && TotalFu == 30) {
                        MainerStandPoint = 2500;
                        MainerUnstandPoint = 1500;
                        StandPoint = 2000;
                        UnstandPoint = 1000;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 3000;
                        MainerUnstandPoint = 2000;
                        StandPoint = 2300;
                        UnstandPoint = 1300;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 4400;
                        MainerUnstandPoint = 3400;
                        StandPoint = 3300;
                        UnstandPoint = 2300;
                    }

                    else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && TotalFu > 100) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && TotalFu == 25) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 7800;
                        MainerUnstandPoint = 6800;
                        StandPoint = 5500;
                        UnstandPoint = 4500;
                    }

                    else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 9700;
                        MainerUnstandPoint = 8700;
                        StandPoint = 6800;
                        UnstandPoint = 5800;
                    }

                    else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 2 && TotalFu > 100) {
                        MainerStandPoint = 11600;
                        MainerUnstandPoint = 10600;
                        StandPoint = 8100;
                        UnstandPoint = 7100;
                    }

                    else if (Eaten == 3 && TotalFu == 25) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        MainerStandPoint = 13000;
                        MainerUnstandPoint = 12000;
                        StandPoint = 9000;
                        UnstandPoint = 8000;
                    }

                    else if (Eaten == 4 && TotalFu == 25) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 6 || Eaten == 7) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                        MainerStandPoint = 25000;
                        MainerUnstandPoint = 24000;
                        StandPoint = 19000;
                        UnstandPoint = 18000;
                    }

                    else if (Eaten == 12 || Eaten == 11) {
                        MainerStandPoint = 37000;
                        MainerUnstandPoint = 36000;
                        StandPoint = 25000;
                        UnstandPoint = 24000;
                    }

                    else if (Eaten == 13) {
                        MainerStandPoint = 49000;
                        MainerUnstandPoint = 48000;
                        StandPoint = 33000;
                        UnstandPoint = 32000;
                    }

                    else if (Eaten == 26) {
                        MainerStandPoint = 97000;
                        MainerUnstandPoint = 96000;
                        StandPoint = 65000;
                        UnstandPoint = 64000;
                    }

                    else if (Eaten == 39) {
                        MainerStandPoint = 145000;
                        MainerUnstandPoint = 144000;
                        StandPoint = 97000;
                        UnstandPoint = 96000;
                    }

                    else if (Eaten == 52) {
                        MainerStandPoint = 193000;
                        MainerUnstandPoint = 192000;
                        StandPoint = 129000;
                        UnstandPoint = 128000;
                    }

                    else if (Eaten == 65) {
                        MainerStandPoint = 241000;
                        MainerUnstandPoint = 240000;
                        StandPoint = 161000;
                        UnstandPoint = 160000;
                    }

                    else if (Eaten == 78) {
                        MainerStandPoint = 289000;
                        MainerUnstandPoint = 288000;
                        StandPoint = 193000;
                        UnstandPoint = 192000;
                    }

                    else if (Eaten == 91) {
                        MainerStandPoint = 3370000;
                        MainerUnstandPoint = 336000;
                        StandPoint = 225000;
                        UnstandPoint = 224000;
                    }

                    if (stand == 0 && StartCal == 1) {
                        if (mainer == 1) {
                            score3 += MainerUnstandPoint;
                            record3 += "+" + MainerUnstandPoint + "\n\n";
                            score1 -= MainerUnstandPoint;
                            record1 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score3 += UnstandPoint;
                            record3 += "+" + UnstandPoint + "\n\n";
                            score1 -= UnstandPoint;
                            record1 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1 && StartCal == 1) {
                        if (mainer == 1) {
                            score3 += MainerStandPoint;
                            record3 += "+" + MainerStandPoint + "\n\n";
                            score1 -= MainerUnstandPoint;
                            record1 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score3 += StandPoint;
                            record3 += "+" + StandPoint + "\n\n";
                            score1 -= UnstandPoint;
                            record1 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                if (winner == 4 && StartCal == 1) {
                    //1番30符
                    if (Eaten == 1 && TotalFu == 30) {
                        MainerStandPoint = 2500;
                        MainerUnstandPoint = 1500;
                        StandPoint = 2000;
                        UnstandPoint = 1000;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 3000;
                        MainerUnstandPoint = 2000;
                        StandPoint = 2300;
                        UnstandPoint = 1300;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 4400;
                        MainerUnstandPoint = 3400;
                        StandPoint = 3300;
                        UnstandPoint = 2300;
                    }

                    else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && TotalFu > 100) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && TotalFu == 25) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 7800;
                        MainerUnstandPoint = 6800;
                        StandPoint = 5500;
                        UnstandPoint = 4500;
                    }

                    else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 9700;
                        MainerUnstandPoint = 8700;
                        StandPoint = 6800;
                        UnstandPoint = 5800;
                    }

                    else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 2 && TotalFu > 100) {
                        MainerStandPoint = 11600;
                        MainerUnstandPoint = 10600;
                        StandPoint = 8100;
                        UnstandPoint = 7100;
                    }

                    else if (Eaten == 3 && TotalFu == 25) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        MainerStandPoint = 13000;
                        MainerUnstandPoint = 12000;
                        StandPoint = 9000;
                        UnstandPoint = 8000;
                    }

                    else if (Eaten == 4 && TotalFu == 25) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 6 || Eaten == 7) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                        MainerStandPoint = 25000;
                        MainerUnstandPoint = 24000;
                        StandPoint = 19000;
                        UnstandPoint = 18000;
                    }

                    else if (Eaten == 12 || Eaten == 11) {
                        MainerStandPoint = 37000;
                        MainerUnstandPoint = 36000;
                        StandPoint = 25000;
                        UnstandPoint = 24000;
                    }

                    else if (Eaten == 13) {
                        MainerStandPoint = 49000;
                        MainerUnstandPoint = 48000;
                        StandPoint = 33000;
                        UnstandPoint = 32000;
                    }

                    else if (Eaten == 26) {
                        MainerStandPoint = 97000;
                        MainerUnstandPoint = 96000;
                        StandPoint = 65000;
                        UnstandPoint = 64000;
                    }

                    else if (Eaten == 39) {
                        MainerStandPoint = 145000;
                        MainerUnstandPoint = 144000;
                        StandPoint = 97000;
                        UnstandPoint = 96000;
                    }

                    else if (Eaten == 52) {
                        MainerStandPoint = 193000;
                        MainerUnstandPoint = 192000;
                        StandPoint = 129000;
                        UnstandPoint = 128000;
                    }

                    else if (Eaten == 65) {
                        MainerStandPoint = 241000;
                        MainerUnstandPoint = 240000;
                        StandPoint = 161000;
                        UnstandPoint = 160000;
                    }

                    else if (Eaten == 78) {
                        MainerStandPoint = 289000;
                        MainerUnstandPoint = 288000;
                        StandPoint = 193000;
                        UnstandPoint = 192000;
                    }

                    else if (Eaten == 91) {
                        MainerStandPoint = 3370000;
                        MainerUnstandPoint = 336000;
                        StandPoint = 225000;
                        UnstandPoint = 224000;
                    }

                    if (stand == 0 && StartCal == 1) {
                        if (mainer == 1) {
                            score4 += MainerUnstandPoint;
                            record4 += "+" + MainerUnstandPoint + "\n\n";
                            score1 -= MainerUnstandPoint;
                            record1 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score4 += UnstandPoint;
                            record4 += "+" + UnstandPoint + "\n\n";
                            score1 -= UnstandPoint;
                            record1 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1 && StartCal == 1) {
                        if (mainer == 1) {
                            score4 += MainerStandPoint;
                            record4 += "+" + MainerStandPoint + "\n\n";
                            score1 -= MainerUnstandPoint;
                            record1 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score4 += StandPoint;
                            record4 += "+" + StandPoint + "\n\n";
                            score1 -= UnstandPoint;
                            record1 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }

                }



                if (StartCal == 1) {

                    JpEdit.putInt("score1", score1);
                    JpEdit.putInt("score2", score2);
                    JpEdit.putInt("score3", score3);
                    JpEdit.putInt("score4", score4);
                    JpEdit.putString("Player1Record", record1);
                    JpEdit.putString("Player2Record", record2);
                    JpEdit.putString("Player3Record", record3);
                    JpEdit.putString("Player4Record", record4);
                    JpEdit.putString("PrintRoundNumber",PrintRecord);
                    JpEdit.putInt("RoundNumber", roundNumber);
                    JpEdit.putInt("stand1",0);
                    JpEdit.putInt("stand2",0);
                    JpEdit.putInt("stand3",0);
                    JpEdit.putInt("stand4",0);
                    JpEdit.putInt("StandEdit",0);
                    JpEdit.commit();

                    Intent i = new Intent(JpFinish.this, JpCounting.class);
                    startActivity(i);
                }
            }
        });

        nextPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check = fu.getText().toString();

                //check input
                if (Eaten == 0) {
                    Toast.makeText(JpFinish.this, "請輸入番數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else if (Check.trim().equals("")) {
                    Toast.makeText(JpFinish.this, "請輸入符數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else {
                    TotalFu = Integer.parseInt(fu.getText().toString());
                    if(TotalFu < 20 && (Eaten < 5)){
                        Toast.makeText(JpFinish.this, Eaten + "番符數最少要有20", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }
                    else if (TotalFu < 30 && Eaten == 1) {
                        Toast.makeText(JpFinish.this, "1番最少符數為30", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else if ((TotalFu < 25 && Eaten == 2) || (TotalFu < 25 && Eaten == 3) || (TotalFu < 25 && Eaten == 4)) {
                        Toast.makeText(JpFinish.this, Eaten + "番榮胡最少符數為25", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else if (mainer == 0) {
                        Toast.makeText(JpFinish.this, "請選擇莊家", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else {
                        StartCal = 1;
                    }
                }

                if(StartCal == 1){
                    if (Eaten == 1 && TotalFu == 30) {
                        MainerStandPoint = 2500;
                        MainerUnstandPoint = 1500;
                        StandPoint = 2000;
                        UnstandPoint = 1000;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 3000;
                        MainerUnstandPoint = 2000;
                        StandPoint = 2300;
                        UnstandPoint = 1300;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 4400;
                        MainerUnstandPoint = 3400;
                        StandPoint = 3300;
                        UnstandPoint = 2300;
                    }

                    else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && TotalFu > 100) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && TotalFu == 25) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 7800;
                        MainerUnstandPoint = 6800;
                        StandPoint = 5500;
                        UnstandPoint = 4500;
                    }

                    else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 9700;
                        MainerUnstandPoint = 8700;
                        StandPoint = 6800;
                        UnstandPoint = 5800;
                    }

                    else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 2 && TotalFu > 100) {
                        MainerStandPoint = 11600;
                        MainerUnstandPoint = 10600;
                        StandPoint = 8100;
                        UnstandPoint = 7100;
                    }

                    else if (Eaten == 3 && TotalFu == 25) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        MainerStandPoint = 13000;
                        MainerUnstandPoint = 12000;
                        StandPoint = 9000;
                        UnstandPoint = 8000;
                    }

                    else if (Eaten == 4 && TotalFu == 25) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 6 || Eaten == 7) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                        MainerStandPoint = 25000;
                        MainerUnstandPoint = 24000;
                        StandPoint = 19000;
                        UnstandPoint = 18000;
                    }

                    else if (Eaten == 12 || Eaten == 11) {
                        MainerStandPoint = 37000;
                        MainerUnstandPoint = 36000;
                        StandPoint = 25000;
                        UnstandPoint = 24000;
                    }

                    else if (Eaten == 13) {
                        MainerStandPoint = 49000;
                        MainerUnstandPoint = 48000;
                        StandPoint = 33000;
                        UnstandPoint = 32000;
                    }

                    else if (Eaten == 26) {
                        MainerStandPoint = 97000;
                        MainerUnstandPoint = 96000;
                        StandPoint = 65000;
                        UnstandPoint = 64000;
                    }

                    else if (Eaten == 39) {
                        MainerStandPoint = 145000;
                        MainerUnstandPoint = 144000;
                        StandPoint = 97000;
                        UnstandPoint = 96000;
                    }

                    else if (Eaten == 52) {
                        MainerStandPoint = 193000;
                        MainerUnstandPoint = 192000;
                        StandPoint = 129000;
                        UnstandPoint = 128000;
                    }

                    else if (Eaten == 65) {
                        MainerStandPoint = 241000;
                        MainerUnstandPoint = 240000;
                        StandPoint = 161000;
                        UnstandPoint = 160000;
                    }

                    else if (Eaten == 78) {
                        MainerStandPoint = 289000;
                        MainerUnstandPoint = 288000;
                        StandPoint = 193000;
                        UnstandPoint = 192000;
                    }

                    else if (Eaten == 91) {
                        MainerStandPoint = 3370000;
                        MainerUnstandPoint = 336000;
                        StandPoint = 225000;
                        UnstandPoint = 224000;
                    }

                    //adding 0 to stand record
                    if(stand1 == 0){
                        record1 += "0\n\n";
                    }
                    if(stand2 == 0){
                        record2 += "0\n\n";
                    }
                    if(stand3 == 0){
                        record3 += "0\n\n";
                    }
                    if(stand4 == 0){
                        record4 += "0\n\n";
                    }
                }

                if(winner == 1 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score1 += MainerUnstandPoint;
                            record1 += "+" + MainerUnstandPoint + "\n\n";
                            score3 -= MainerUnstandPoint;
                            record3 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score1 += UnstandPoint;
                            record1 += "+" + UnstandPoint + "\n\n";
                            score3 -= UnstandPoint;
                            record3 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score1 += MainerStandPoint;
                            record1 += "+" + MainerStandPoint + "\n\n";
                            score3 -= MainerUnstandPoint;
                            record3 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score1 += StandPoint;
                            record1 += "+" + StandPoint + "\n\n";
                            score3 -= UnstandPoint;
                            record3 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                else if(winner == 2 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score2 += MainerUnstandPoint;
                            record2 += "+" + MainerUnstandPoint + "\n\n";
                            score3 -= MainerUnstandPoint;
                            record3 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score2 += UnstandPoint;
                            record2 += "+" + UnstandPoint + "\n\n";
                            score3 -= UnstandPoint;
                            record3 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score2 += MainerStandPoint;
                            record2 += "+" + MainerStandPoint + "\n\n";
                            score3 -= MainerUnstandPoint;
                            record3 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score2 += StandPoint;
                            record2 += "+" + StandPoint + "\n\n";
                            score3 -= UnstandPoint;
                            record3 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                else if(winner == 3 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score3 += MainerUnstandPoint;
                            record3 += "+" + MainerUnstandPoint + "\n\n";
                            score2 -= MainerUnstandPoint;
                            record2 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score3 += UnstandPoint;
                            record3 += "+" + UnstandPoint + "\n\n";
                            score2 -= UnstandPoint;
                            record2 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score3 += MainerStandPoint;
                            record3 += "+" + MainerStandPoint + "\n\n";
                            score2 -= MainerUnstandPoint;
                            record2 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score3 += StandPoint;
                            record3 += "+" + StandPoint + "\n\n";
                            score2 -= UnstandPoint;
                            record2 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record4 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                else if(winner == 4 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score4 += MainerUnstandPoint;
                            record4 += "+" + MainerUnstandPoint + "\n\n";
                            score2 -= MainerUnstandPoint;
                            record2 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score4 += UnstandPoint;
                            record4 += "+" + UnstandPoint + "\n\n";
                            score2 -= UnstandPoint;
                            record2 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score4 += MainerStandPoint;
                            record4 += "+" + MainerStandPoint + "\n\n";
                            score2 -= MainerUnstandPoint;
                            record2 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score4 += StandPoint;
                            record4 += "+" + StandPoint + "\n\n";
                            score2 -= UnstandPoint;
                            record2 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }


                if (StartCal == 1) {

                    JpEdit.putInt("score1", score1);
                    JpEdit.putInt("score2", score2);
                    JpEdit.putInt("score3", score3);
                    JpEdit.putInt("score4", score4);
                    JpEdit.putString("Player1Record", record1);
                    JpEdit.putString("Player2Record", record2);
                    JpEdit.putString("Player3Record", record3);
                    JpEdit.putString("Player4Record", record4);
                    JpEdit.putString("PrintRoundNumber",PrintRecord);
                    JpEdit.putInt("stand1",0);
                    JpEdit.putInt("stand2",0);
                    JpEdit.putInt("stand3",0);
                    JpEdit.putInt("stand4",0);
                    JpEdit.putInt("StandEdit",0);
                    JpEdit.putInt("RoundNumber", roundNumber);
                    JpEdit.commit();

                    Intent i = new Intent(JpFinish.this, JpCounting.class);
                    startActivity(i);
                }
            }
        });

        nextPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check = fu.getText().toString();

                //check input
                if (Eaten == 0) {
                    Toast.makeText(JpFinish.this, "請輸入番數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else if (Check.trim().equals("")) {
                    Toast.makeText(JpFinish.this, "請輸入符數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else {
                    TotalFu = Integer.parseInt(fu.getText().toString());
                    if(TotalFu < 20 && (Eaten < 5)){
                        Toast.makeText(JpFinish.this, Eaten + "番符數最少要有20", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }
                    else
                    if (TotalFu < 30 && Eaten == 1) {
                        Toast.makeText(JpFinish.this, "1番最少符數為30", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else if ((TotalFu < 25 && Eaten == 2) || (TotalFu < 25 && Eaten == 3) || (TotalFu < 25 && Eaten == 4)) {
                        Toast.makeText(JpFinish.this, Eaten + "番榮胡最少符數為25", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else if (mainer == 0) {
                        Toast.makeText(JpFinish.this, "請選擇莊家", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    } else {
                        StartCal = 1;
                    }
                }

                if(StartCal == 1){
                    if (Eaten == 1 && TotalFu == 30) {
                        MainerStandPoint = 2500;
                        MainerUnstandPoint = 1500;
                        StandPoint = 2000;
                        UnstandPoint = 1000;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 3000;
                        MainerUnstandPoint = 2000;
                        StandPoint = 2300;
                        UnstandPoint = 1300;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 4400;
                        MainerUnstandPoint = 3400;
                        StandPoint = 3300;
                        UnstandPoint = 2300;
                    }

                    else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 5400;
                        MainerUnstandPoint = 4400;
                        StandPoint = 3900;
                        UnstandPoint = 2900;
                    }

                    else if (Eaten == 1 && TotalFu > 100) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && TotalFu == 25) {
                        MainerStandPoint = 3400;
                        MainerUnstandPoint = 2400;
                        StandPoint = 2600;
                        UnstandPoint = 1600;
                    }

                    else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 3900;
                        MainerUnstandPoint = 2900;
                        StandPoint = 3000;
                        UnstandPoint = 2000;
                    }

                    else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 4900;
                        MainerUnstandPoint = 3900;
                        StandPoint = 3600;
                        UnstandPoint = 2600;
                    }

                    else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerStandPoint = 7800;
                        MainerUnstandPoint = 6800;
                        StandPoint = 5500;
                        UnstandPoint = 4500;
                    }

                    else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerStandPoint = 9700;
                        MainerUnstandPoint = 8700;
                        StandPoint = 6800;
                        UnstandPoint = 5800;
                    }

                    else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 2 && TotalFu > 100) {
                        MainerStandPoint = 11600;
                        MainerUnstandPoint = 10600;
                        StandPoint = 8100;
                        UnstandPoint = 7100;
                    }

                    else if (Eaten == 3 && TotalFu == 25) {
                        MainerStandPoint = 5800;
                        MainerUnstandPoint = 4800;
                        StandPoint = 4200;
                        UnstandPoint = 3200;
                    }

                    else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 6800;
                        MainerUnstandPoint = 5800;
                        StandPoint = 4900;
                        UnstandPoint = 3900;
                    }

                    else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerStandPoint = 8700;
                        MainerUnstandPoint = 7700;
                        StandPoint = 6200;
                        UnstandPoint = 5200;
                    }

                    else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        MainerStandPoint = 13000;
                        MainerUnstandPoint = 12000;
                        StandPoint = 9000;
                        UnstandPoint = 8000;
                    }

                    else if (Eaten == 4 && TotalFu == 25) {
                        MainerStandPoint = 10600;
                        MainerUnstandPoint = 9600;
                        StandPoint = 7400;
                        UnstandPoint = 6400;
                    }

                    else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 6 || Eaten == 7) {
                        MainerStandPoint = 12600;
                        MainerUnstandPoint = 11600;
                        StandPoint = 8700;
                        UnstandPoint = 7700;
                    }

                    else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                        MainerStandPoint = 25000;
                        MainerUnstandPoint = 24000;
                        StandPoint = 19000;
                        UnstandPoint = 18000;
                    }

                    else if (Eaten == 12 || Eaten == 11) {
                        MainerStandPoint = 37000;
                        MainerUnstandPoint = 36000;
                        StandPoint = 25000;
                        UnstandPoint = 24000;
                    }

                    else if (Eaten == 13) {
                        MainerStandPoint = 49000;
                        MainerUnstandPoint = 48000;
                        StandPoint = 33000;
                        UnstandPoint = 32000;
                    }

                    else if (Eaten == 26) {
                        MainerStandPoint = 97000;
                        MainerUnstandPoint = 96000;
                        StandPoint = 65000;
                        UnstandPoint = 64000;
                    }

                    else if (Eaten == 39) {
                        MainerStandPoint = 145000;
                        MainerUnstandPoint = 144000;
                        StandPoint = 97000;
                        UnstandPoint = 96000;
                    }

                    else if (Eaten == 52) {
                        MainerStandPoint = 193000;
                        MainerUnstandPoint = 192000;
                        StandPoint = 129000;
                        UnstandPoint = 128000;
                    }

                    else if (Eaten == 65) {
                        MainerStandPoint = 241000;
                        MainerUnstandPoint = 240000;
                        StandPoint = 161000;
                        UnstandPoint = 160000;
                    }

                    else if (Eaten == 78) {
                        MainerStandPoint = 289000;
                        MainerUnstandPoint = 288000;
                        StandPoint = 193000;
                        UnstandPoint = 192000;
                    }

                    else if (Eaten == 91) {
                        MainerStandPoint = 3370000;
                        MainerUnstandPoint = 336000;
                        StandPoint = 225000;
                        UnstandPoint = 224000;
                    }

                    //adding 0 to stand record
                    if(stand1 == 0){
                        record1 += "0\n\n";
                    }
                    if(stand2 == 0){
                        record2 += "0\n\n";
                    }
                    if(stand3 == 0){
                        record3 += "0\n\n";
                    }
                    if(stand4 == 0){
                        record4 += "0\n\n";
                    }
                }

                if(winner == 1 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score1 += MainerUnstandPoint;
                            record1 += "+" + MainerUnstandPoint + "\n\n";
                            score4 -= MainerUnstandPoint;
                            record4 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score1 += UnstandPoint;
                            record1 += "+" + UnstandPoint + "\n\n";
                            score4 -= UnstandPoint;
                            record4 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score1 += MainerStandPoint;
                            record1 += "+" + MainerStandPoint + "\n\n";
                            score4 -= MainerUnstandPoint;
                            record4 += "-" + MainerUnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score1 += StandPoint;
                            record1 += "+" + StandPoint + "\n\n";
                            score4 -= UnstandPoint;
                            record4 += "-" + UnstandPoint + "\n\n";

                            record2 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                else if(winner == 2 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score2 += MainerUnstandPoint;
                            record2 += "+" + MainerUnstandPoint + "\n\n";
                            score4 -= MainerUnstandPoint;
                            record4 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score2 += UnstandPoint;
                            record2 += "+" + UnstandPoint + "\n\n";
                            score4 -= UnstandPoint;
                            record4 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score2 += MainerStandPoint;
                            record2 += "+" + MainerStandPoint + "\n\n";
                            score4 -= MainerUnstandPoint;
                            record4 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score2 += StandPoint;
                            record2 += "+" + StandPoint + "\n\n";
                            score4 -= UnstandPoint;
                            record4 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record3 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                else if(winner == 3 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score3 += MainerUnstandPoint;
                            record3 += "+" + MainerUnstandPoint + "\n\n";
                            score4 -= MainerUnstandPoint;
                            record4 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score3 += UnstandPoint;
                            record3 += "+" + UnstandPoint + "\n\n";
                            score4 -= UnstandPoint;
                            record4 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score3 += MainerStandPoint;
                            record3 += "+" + MainerStandPoint + "\n\n";
                            score4 -= MainerUnstandPoint;
                            record4 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score3 += StandPoint;
                            record3 += "+" + StandPoint + "\n\n";
                            score4 -= UnstandPoint;
                            record4 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                else if(winner == 4 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score4 += MainerUnstandPoint;
                            record4 += "+" + MainerUnstandPoint + "\n\n";
                            score3 -= MainerUnstandPoint;
                            record3 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer != 1) {
                            score4 += UnstandPoint;
                            record4 += "+" + UnstandPoint + "\n\n";
                            score3 -= UnstandPoint;
                            record3 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score4 += MainerStandPoint;
                            record4 += "+" + MainerStandPoint + "\n\n";
                            score3 -= MainerUnstandPoint;
                            record3 += "-" + MainerUnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer != 1) {
                            score4 += StandPoint;
                            record4 += "+" + StandPoint + "\n\n";
                            score3 -= UnstandPoint;
                            record3 += "-" + UnstandPoint + "\n\n";

                            record1 += "0\n\n";
                            record2 += "0\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }


                if (StartCal == 1) {

                    JpEdit.putInt("score1", score1);
                    JpEdit.putInt("score2", score2);
                    JpEdit.putInt("score3", score3);
                    JpEdit.putInt("score4", score4);
                    JpEdit.putString("Player1Record", record1);
                    JpEdit.putString("Player2Record", record2);
                    JpEdit.putString("Player3Record", record3);
                    JpEdit.putString("Player4Record", record4);
                    JpEdit.putString("PrintRoundNumber",PrintRecord);
                    JpEdit.putInt("RoundNumber", roundNumber);
                    JpEdit.putInt("stand1",0);
                    JpEdit.putInt("stand2",0);
                    JpEdit.putInt("stand3",0);
                    JpEdit.putInt("stand4",0);
                    JpEdit.putInt("StandEdit",0);
                    JpEdit.commit();

                    Intent i = new Intent(JpFinish.this, JpCounting.class);
                    startActivity(i);
                }
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check = fu.getText().toString();

                //check input
                if (Eaten == 0) {
                    Toast.makeText(JpFinish.this, "請輸入番數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else if (Check.trim().equals("")) {
                    Toast.makeText(JpFinish.this, "請輸入符數", Toast.LENGTH_SHORT).show();
                    StartCal = 0;
                }

                else {
                    TotalFu = Integer.parseInt(fu.getText().toString());
                    if(TotalFu < 20 && (Eaten < 5)){
                        Toast.makeText(JpFinish.this, Eaten + "番符數最少要有20", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }
                    else if (TotalFu < 30 && Eaten == 1) {
                        Toast.makeText(JpFinish.this, "1番最少符數為30", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }
                    else if ((TotalFu > 20 && TotalFu <= 25) && Eaten == 2) {
                        Toast.makeText(JpFinish.this, Eaten + "番自摸不會出現25符", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }

                    else if (mainer == 0) {
                        Toast.makeText(JpFinish.this, "請選擇莊家", Toast.LENGTH_SHORT).show();
                        StartCal = 0;
                    }
                    else {
                        StartCal = 1;
                    }
                }

                if(StartCal == 1){
                    if (Eaten == 1 && TotalFu == 30) {
                        MainerSelf = 1500;
                        Self = 1100;
                        MainerEatenBySelf = 500;
                        EatenBySelf = 300;
                        StandMainerSelf = 2500;
                        StandSelf = 2100;
                    }

                    //1番40符
                    else if (Eaten == 1 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerSelf = 2100;
                        Self = 1500;
                        MainerEatenBySelf = 700;
                        EatenBySelf = 400;
                        StandMainerSelf = 3100;
                        StandSelf = 2500;
                    }

                    //1番50符
                    else if (Eaten == 1 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerSelf = 2400;
                        Self = 1600;
                        MainerEatenBySelf = 800;
                        EatenBySelf = 400;
                        StandMainerSelf = 3400;
                        StandSelf = 2600;
                    }

                    //1番60符
                    else if (Eaten == 1 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerSelf = 3000;
                        Self = 2000;
                        MainerEatenBySelf = 1000;
                        EatenBySelf = 500;
                        StandMainerSelf = 4000;
                        StandSelf = 3000;
                    }

                    else if (Eaten == 1 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerSelf = 3600;
                        Self = 2400;
                        MainerEatenBySelf = 1200;
                        EatenBySelf = 600;
                        StandMainerSelf = 4600;
                        StandSelf = 3400;
                    }

                    else if (Eaten == 1 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerSelf = 3900;
                        Self = 2700;
                        MainerEatenBySelf = 1300;
                        EatenBySelf = 700;
                        StandMainerSelf = 4900;
                        StandSelf = 3700;
                    }

                    else if (Eaten == 1 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerSelf = 4500;
                        Self = 3100;
                        MainerEatenBySelf = 1500;
                        EatenBySelf = 800;
                        StandMainerSelf = 5500;
                        StandSelf = 4100;
                    }

                    else if (Eaten == 1 && TotalFu > 90 ) {
                        MainerSelf = 4800;
                        Self = 3200;
                        MainerEatenBySelf = 1600;
                        EatenBySelf = 800;
                        StandMainerSelf = 5800;
                        StandSelf = 4200;
                    }

                    else if (Eaten == 2 && TotalFu == 20) {
                        MainerSelf = 2100;
                        Self = 1500;
                        MainerEatenBySelf = 700;
                        EatenBySelf = 400;
                        StandMainerSelf = 3100;
                        StandSelf = 2500;
                    }

                    else if (Eaten == 2 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerSelf = 3000;
                        Self = 2000;
                        MainerEatenBySelf = 1000;
                        EatenBySelf = 500;
                        StandMainerSelf = 4000;
                        StandSelf = 3000;
                    }

                    else if (Eaten == 2 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerSelf = 3900;
                        Self = 2700;
                        MainerEatenBySelf = 1300;
                        EatenBySelf = 700;
                        StandMainerSelf = 4900;
                        StandSelf = 3700;
                    }

                    else if (Eaten == 2 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerSelf = 4800;
                        Self = 3200;
                        MainerEatenBySelf = 1600;
                        EatenBySelf = 800;
                        StandMainerSelf = 5800;
                        StandSelf = 4200;
                    }

                    else if (Eaten == 2 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerSelf = 6000;
                        Self = 4000;
                        MainerEatenBySelf = 2000;
                        EatenBySelf = 1000;
                        StandMainerSelf = 7000;
                        StandSelf = 5000;
                    }

                    else if (Eaten == 2 && (TotalFu > 60 && TotalFu <= 70)) {
                        MainerSelf = 6900;
                        Self = 4700;
                        MainerEatenBySelf = 2300;
                        EatenBySelf = 1200;
                        StandMainerSelf = 7900;
                        StandSelf = 5700;
                    }

                    else if (Eaten == 2 && (TotalFu > 70 && TotalFu <= 80)) {
                        MainerSelf = 7800;
                        Self = 5200;
                        MainerEatenBySelf = 2600;
                        EatenBySelf = 1300;
                        StandMainerSelf = 8800;
                        StandSelf = 6200;
                    }

                    else if (Eaten == 2 && (TotalFu > 80 && TotalFu <= 90)) {
                        MainerSelf = 8700;
                        Self = 5900;
                        MainerEatenBySelf = 2900;
                        EatenBySelf = 1500;
                        StandMainerSelf = 9700;
                        StandSelf = 6900;
                    }

                    else if (Eaten == 2 && (TotalFu > 90 && TotalFu <= 100)) {
                        MainerSelf = 9600;
                        Self = 6400;
                        MainerEatenBySelf = 3200;
                        EatenBySelf = 1600;
                        StandMainerSelf = 10600;
                        StandSelf = 7400;
                    }

                    else if (Eaten == 2 && TotalFu > 100) {
                        MainerSelf = 10800;
                        Self = 7200;
                        MainerEatenBySelf = 3600;
                        EatenBySelf = 1800;
                        StandMainerSelf = 11800;
                        StandSelf = 8200;
                    }

                    else if (Eaten == 3 && TotalFu == 20) {
                        MainerSelf = 3900;
                        Self = 2700;
                        MainerEatenBySelf = 1300;
                        EatenBySelf = 700;
                        StandMainerSelf = 4900;
                        StandSelf = 3700;
                    }

                    else if (Eaten == 3 && (TotalFu > 20 && TotalFu <=25)) {
                        MainerSelf = 4800;
                        Self = 3200;
                        MainerEatenBySelf = 1600;
                        EatenBySelf = 800;
                        StandMainerSelf = 5800;
                        StandSelf = 4200;
                    }

                    else if (Eaten == 3 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerSelf = 6000;
                        Self = 4000;
                        MainerEatenBySelf = 2000;
                        EatenBySelf = 1000;
                        StandMainerSelf = 7000;
                        StandSelf = 5000;
                    }

                    else if (Eaten == 3 && (TotalFu > 30 && TotalFu <= 40)) {
                        MainerSelf = 7800;
                        Self = 5200;
                        MainerEatenBySelf = 2600;
                        EatenBySelf = 1300;
                        StandMainerSelf = 8800;
                        StandSelf = 6200;
                    }

                    else if (Eaten == 3 && (TotalFu > 40 && TotalFu <= 50)) {
                        MainerSelf = 9600;
                        Self = 6400;
                        MainerEatenBySelf = 3200;
                        EatenBySelf = 1600;
                        StandMainerSelf = 10600;
                        StandSelf = 7400;
                    }

                    else if (Eaten == 3 && (TotalFu > 50 && TotalFu <= 60)) {
                        MainerSelf = 11700;
                        Self = 7900;
                        MainerEatenBySelf = 3900;
                        EatenBySelf = 2000;
                        StandMainerSelf = 12700;
                        StandSelf = 8900;
                    }

                    else if ((Eaten == 3 && TotalFu > 60) || (Eaten == 4 && TotalFu > 30) || (Eaten == 5)) {
                        MainerSelf = 12000;
                        Self = 8000;
                        MainerEatenBySelf = 4000;
                        EatenBySelf = 2000;
                        StandMainerSelf = 13000;
                        StandSelf = 9000;
                    }

                    else if (Eaten == 4 && TotalFu == 20) {
                        MainerSelf = 7800;
                        Self = 5200;
                        MainerEatenBySelf = 2600;
                        EatenBySelf = 1300;
                        StandMainerSelf = 8800;
                        StandSelf = 6200;
                    }

                    else if (Eaten == 4 && (TotalFu > 20 && TotalFu <= 25)) {
                        MainerSelf = 9600;
                        Self = 6400;
                        MainerEatenBySelf = 3200;
                        EatenBySelf = 1600;
                        StandMainerSelf = 10600;
                        StandSelf = 7400;
                    }

                    else if (Eaten == 4 && (TotalFu > 25 && TotalFu <= 30)) {
                        MainerSelf = 11700;
                        Self = 7900;
                        MainerEatenBySelf = 3900;
                        EatenBySelf = 2000;
                        StandMainerSelf = 12700;
                        StandSelf = 8900;
                    }

                    else if (Eaten == 6 || Eaten == 7) {
                        MainerSelf = 18000;
                        Self = 12000;
                        MainerEatenBySelf = 6000;
                        EatenBySelf = 3000;
                        StandMainerSelf = 19000;
                        StandSelf = 13000;
                    }

                    else if (Eaten == 8 || Eaten == 9 || Eaten == 10) {
                        MainerSelf = 24000;
                        Self = 16000;
                        MainerEatenBySelf = 8000;
                        EatenBySelf = 4000;
                        StandMainerSelf = 25000;
                        StandSelf = 17000;
                    }

                    else if (Eaten == 12 || Eaten == 11) {
                        MainerSelf = 36000;
                        Self = 24000;
                        MainerEatenBySelf = 12000;
                        EatenBySelf = 6000;
                        StandMainerSelf = 37000;
                        StandSelf = 25000;
                    }

                    else if (Eaten == 13) {
                        MainerSelf = 48000;
                        Self = 36000;
                        MainerEatenBySelf = 16000;
                        EatenBySelf = 8000;
                        StandMainerSelf = 49000;
                        StandSelf = 37000;
                    }

                    else if (Eaten == 26) {
                        MainerSelf = 96000;
                        Self = 64000;
                        MainerEatenBySelf = 32000;
                        EatenBySelf = 16000;
                        StandMainerSelf = 97000;
                        StandSelf = 73000;
                    }

                    else if (Eaten == 39) {
                        MainerSelf = 144000;
                        Self = 96000;
                        MainerEatenBySelf = 48000;
                        EatenBySelf = 24000;
                        StandMainerSelf = 145000;
                        StandSelf = 97000;
                    }

                    else if (Eaten == 52) {
                        MainerSelf = 192000;
                        Self = 128000;
                        MainerEatenBySelf = 64000;
                        EatenBySelf = 32000;
                        StandMainerSelf = 193000;
                        StandSelf = 129000;
                    }

                    else if (Eaten == 65) {
                        MainerSelf = 240000;
                        Self = 160000;
                        MainerEatenBySelf = 80000;
                        EatenBySelf = 40000;
                        StandMainerSelf = 241000;
                        StandSelf = 161000;
                    }

                    else if (Eaten == 78) {
                        MainerSelf = 288000;
                        Self = 192000;
                        MainerEatenBySelf = 96000;
                        EatenBySelf = 48000;
                        StandMainerSelf = 289000;
                        StandSelf = 193000;
                    }

                    else if (Eaten == 91) {
                        MainerSelf = 336000;
                        Self = 224000;
                        MainerEatenBySelf = 112000;
                        EatenBySelf = 56000;
                        StandMainerSelf = 337000;
                        StandSelf = 225000;
                    }

                    //adding 0 to stand record
                    if(stand1 == 0){
                        record1 += "0\n\n";
                    }
                    if(stand2 == 0){
                        record2 += "0\n\n";
                    }
                    if(stand3 == 0){
                        record3 += "0\n\n";
                    }
                    if(stand4 == 0){
                        record4 += "0\n\n";
                    }
                }

                if(winner == 1 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score1 += MainerSelf;
                            score2 -= MainerEatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "+" + MainerSelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 += Self;
                            score2 -= MainerEatenBySelf;
                            score3 -= EatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "+" + Self + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 += Self;
                            score2 -= EatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "+" + Self + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 += Self;
                            score2 -= EatenBySelf;
                            score3 -= EatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "+" + Self + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score1 += StandMainerSelf;
                            score2 -= MainerEatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "+" + StandMainerSelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 += StandSelf;
                            score2 -= MainerEatenBySelf;
                            score3 -= EatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "+" + StandSelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 += StandSelf;
                            score2 -= EatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "+" + StandSelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 += StandSelf;
                            score2 -= EatenBySelf;
                            score3 -= EatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "+" + StandSelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                if(winner == 2 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score1 -= MainerEatenBySelf;
                            score2 += MainerSelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "+" + MainerSelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 -= MainerEatenBySelf;
                            score2 += Self;
                            score3 -= EatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "+" + Self + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 -= EatenBySelf;
                            score2 += Self;
                            score3 -= MainerEatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "+" + Self + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 -= EatenBySelf;
                            score2 += Self;
                            score3 -= EatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "+" + Self + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score1 -= MainerEatenBySelf;
                            score2 += StandMainerSelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "+" + StandMainerSelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 -= MainerEatenBySelf;
                            score2 += StandSelf;
                            score3 -= EatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "+" + StandSelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 -= EatenBySelf;
                            score2 += StandSelf;
                            score3 -= MainerEatenBySelf;
                            score4 -= EatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "+" + StandSelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 -= EatenBySelf;
                            score2 += StandSelf;
                            score3 -= EatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "+" + StandSelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                if(winner == 3 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score1 -= MainerEatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 += MainerSelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "+" + MainerSelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 -= MainerEatenBySelf;
                            score2 -= EatenBySelf;
                            score3 += Self;
                            score4 -= EatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "+" + Self + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 -= EatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 += Self;
                            score4 -= EatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "+" + Self + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 -= EatenBySelf;
                            score2 -= EatenBySelf;
                            score3 += Self;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "+" + Self + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score1 -= MainerEatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 += StandMainerSelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "+" + StandMainerSelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 -= EatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 += StandMainerSelf;
                            score4 -= EatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "+" + StandMainerSelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 -= EatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 += StandMainerSelf;
                            score4 -= EatenBySelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "+" + StandMainerSelf + "\n\n";
                            record4 += "-" + EatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 -= StandSelf;
                            score2 -= EatenBySelf;
                            score3 += EatenBySelf;
                            score4 -= MainerEatenBySelf;

                            record1 += "-" + StandSelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "+" + EatenBySelf + "\n\n";
                            record4 += "-" + MainerEatenBySelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }

                if(winner == 4 && StartCal ==1){
                    if (stand == 0) {
                        if (mainer == 1) {
                            score1 -= MainerEatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 += MainerSelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "+" + MainerSelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 -= MainerEatenBySelf;
                            score2 -= EatenBySelf;
                            score3 -= EatenBySelf;
                            score4 += Self;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "+" + Self + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 -= EatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 -= EatenBySelf;
                            score4 += Self;

                            record1 += "+" + EatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "-" + Self + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 -= EatenBySelf;
                            score2 -= EatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 += Self;

                            record1 += "+" + EatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "-" + Self + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                    else if (stand == 1) {
                        if (mainer == 1) {
                            score1 -= MainerEatenBySelf;
                            score2 -= MainerEatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 += StandMainerSelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + MainerEatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "+" + StandMainerSelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }

                        else if (mainer == 2) {
                            score1 -= MainerEatenBySelf;
                            score2 -= EatenBySelf;
                            score3 -= EatenBySelf;
                            score4 += StandSelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "+" + StandSelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 3) {
                            score1 -= EatenBySelf;
                            score2 -= EatenBySelf;
                            score3 -= MainerEatenBySelf;
                            score4 += StandSelf;

                            record1 += "-" + EatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + MainerEatenBySelf + "\n\n";
                            record4 += "+" + StandSelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                        else if (mainer == 4) {
                            score1 -= MainerEatenBySelf;
                            score2 -= EatenBySelf;
                            score3 -= EatenBySelf;
                            score4 += StandSelf;

                            record1 += "-" + MainerEatenBySelf + "\n\n";
                            record2 += "-" + EatenBySelf + "\n\n";
                            record3 += "-" + EatenBySelf + "\n\n";
                            record4 += "+" + StandSelf + "\n\n";
                            PrintRecord += roundNumber + ".\n\n";
                            roundNumber += 1;
                        }
                    }
                }



                if (StartCal == 1) {


                    JpEdit.putInt("score1", score1);
                    JpEdit.putInt("score2", score2);
                    JpEdit.putInt("score3", score3);
                    JpEdit.putInt("score4", score4);
                    JpEdit.putString("Player1Record", record1);
                    JpEdit.putString("Player2Record", record2);
                    JpEdit.putString("Player3Record", record3);
                    JpEdit.putString("Player4Record", record4);
                    JpEdit.putString("PrintRoundNumber",PrintRecord);
                    JpEdit.putInt("RoundNumber", roundNumber);
                    JpEdit.putInt("stand1",0);
                    JpEdit.putInt("stand2",0);
                    JpEdit.putInt("stand3",0);
                    JpEdit.putInt("stand4",0);
                    JpEdit.putInt("StandEdit",0);
                    JpEdit.commit();

                    Intent i = new Intent(JpFinish.this, JpCounting.class);
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
            Toast.makeText(JpFinish.this, "請選擇番數", Toast.LENGTH_SHORT).show();
        }
        else if (i.equals("1")){
            Eaten = 1;
        }
        else if (i.equals("2")){
            Eaten = 2;
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
        else if (i.equals("雙倍役滿")){
            Eaten = 26;
        }
        else if (i.equals("三倍役滿")){
            Eaten = 39;
        }
        else if (i.equals("四倍役滿")){
            Eaten = 52;
        }
        else if (i.equals("五倍役滿")){
            Eaten = 65;
        }
        else if (i.equals("六倍役滿")){
            Eaten = 78;
        }
        else if (i.equals("七倍役滿")){
            Eaten = 91;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}