/*
package com.example.myapplication.recommend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Plant;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<Plant> listData;

    public RecyclerAdapter(Context context, ArrayList<Plant> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_item, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.profile_imageView.setImageResource(listData.get(position).getProfile_image());
        //Glide.with(context).load(listData.get(position).getProfile_image()).into(holder.plant_image);
        holder.nickname_textView.setText(listData.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }
}
*/