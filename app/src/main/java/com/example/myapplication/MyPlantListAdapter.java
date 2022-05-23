package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyPlantListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<MyPlantList> listData;

    public MyPlantListAdapter(Context context, ArrayList<MyPlantList> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.myplant_item, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.test1_textView.setText(String.valueOf(listData.get(position).getTest1()));
        holder.test2_textView.setText(listData.get(position).getTest2());
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }
}
