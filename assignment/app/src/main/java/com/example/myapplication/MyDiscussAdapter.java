package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyDiscussAdapter extends RecyclerView.Adapter<MyDiscussAdapter.ViewHolder> {

    private List<String> mData;
    private List<String> userData;


    MyDiscussAdapter(List<String> data, List<String> uData) { mData = data; userData = uData; }

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
