package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.CountRecord;

import java.util.List;
import java.util.Map;

public class MyHkRecordAdapter extends RecyclerView.Adapter<MyHkRecordAdapter.ViewHolder> {

    private Map<Integer, List<CountRecord>> hkRecordMap;

    MyHkRecordAdapter(Map<Integer, List<CountRecord>> tempMap) {
        hkRecordMap = tempMap;
    }

    //Create ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView player1Name, player2Name, player3Name, player4Name;
        private TextView hkRecordNumber, hkRecord1, hkRecord2, hkRecord3, hkRecord4;
        private TextView hkTotal1, hkTotal2, hkTotal3, hkTotal4;
        private TextView gameDate;

        ViewHolder(View itemView) {
            super(itemView);

            player1Name = itemView.findViewById(R.id.player1Name);
            player2Name = itemView.findViewById(R.id.player2Name);
            player3Name = itemView.findViewById(R.id.player3Name);
            player4Name = itemView.findViewById(R.id.player4Name);

            hkRecordNumber = itemView.findViewById(R.id.hkRecordNumber);
            hkRecord1 = itemView.findViewById(R.id.hkRecord1);
            hkRecord2 = itemView.findViewById(R.id.hkRecord2);
            hkRecord3 = itemView.findViewById(R.id.hkRecord3);
            hkRecord4 = itemView.findViewById(R.id.hkRecord4);

            hkTotal1 = itemView.findViewById(R.id.hkTotal1);
            hkTotal2 = itemView.findViewById(R.id.hkTotal2);
            hkTotal3 = itemView.findViewById(R.id.hkTotal3);
            hkTotal4 = itemView.findViewById(R.id.hkTotal4);

            gameDate = itemView.findViewById(R.id.gameDate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link list_item.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hk_record_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        System.out.println( position + ": hkRecordMap record: " + hkRecordMap.get(position) );

        holder.player1Name.setText( "a" );
        holder.player2Name.setText( "b" );
        holder.player3Name.setText( "c" );
        holder.player4Name.setText( "d" );

        List<CountRecord> tempList = hkRecordMap.get(position);

        String r1 = "";
        String r2 = "";
        String r3 = "";
        String r4 = "";
        String num = "";
        int t1 = 0;
        int t2 = 0;
        int t3 = 0;
        int t4 = 0;

        for (int i = 0; i < tempList.size() ; i++ ) {
            r1 += tempList.get(i).getPlayer1() + "\n\n";
            r2 += tempList.get(i).getPlayer2() + "\n\n";
            r3 += tempList.get(i).getPlayer3() + "\n\n";
            r4 += tempList.get(i).getPlayer4() + "\n\n";

            num += tempList.get(i).getRound() + "\n\n";

            t1 += tempList.get(i).getPlayer1();
            t2 += tempList.get(i).getPlayer2();
            t3 += tempList.get(i).getPlayer3();
            t4 += tempList.get(i).getPlayer4();
        }

        holder.hkRecord1.setText(r1);
        holder.hkRecord2.setText(r2);
        holder.hkRecord3.setText(r3);
        holder.hkRecord4.setText(r4);

        holder.hkTotal1.setText(Integer.toString(t1));
        holder.hkTotal2.setText(Integer.toString(t2));
        holder.hkTotal3.setText(Integer.toString(t3));
        holder.hkTotal4.setText(Integer.toString(t4));

        holder.hkRecordNumber.setText(num);
        holder.gameDate.setText("Game: " + (position + 1) + "(" + tempList.get(tempList.size()-1).getCreateTime().toString() + ")");
    }

    @Override
    public int getItemCount() {
        return hkRecordMap.size();
    }

}
