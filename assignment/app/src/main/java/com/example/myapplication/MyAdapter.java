package com.example.myapplication;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.CommentRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<com.example.myapplication.MyAdapter.ViewHolder> {

    private List<String> mData;
    private List<String> userData;


    MyAdapter(List<String> data, List<String> uData) { mData = data; userData = uData; }

    //Create ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtItem;
        private TextView writer;

        ViewHolder(View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtItem);
            writer = itemView.findViewById(R.id.writer);

            //Event of clicking the view
            itemView.setOnClickListener( (view) -> {
                Toast.makeText(view.getContext(), txtItem.getText() + " by " + writer.getText(), Toast.LENGTH_SHORT).show();
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link list_item.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discuss_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set the contact of txtItem
        holder.txtItem.setText( mData.get(position) );
        holder.writer.setText( "by " + userData.get(position) );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
