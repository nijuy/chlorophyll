package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyPlantListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<MyPlantList> listData;
    private OnItemClick mCallback;

    private String packName, id;
    private int resID;

    ImageView plantImg;
    Button diaryBtn, detailBtn;
    View rootView;

    public MyPlantListAdapter(Context context, ArrayList<MyPlantList> listData, OnItemClick listener) {
        this.context = context;
        this.listData = listData;
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rootView = inflater.inflate(R.layout.myplant_item, parent, false);
        ViewHolder viewholder = new ViewHolder(rootView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        packName = context.getPackageName();
        resID = context.getResources().getIdentifier(listData.get(position).getImage(), "drawable", packName);
        id = listData.get(position).getId();
        //holder.plantSpecies.setText(String.valueOf(listData.get(position).getSpecies()));
        holder.plantSpecies.setText(listData.get(position).getSpecies());
        holder.plantNickname.setText(listData.get(position).getNickname());
        holder.plantImage.setImageResource(resID);

        String title = listData.get(position).getTitle();

        diaryBtn = rootView.findViewById(R.id.diaryBtn);
        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClick("diary", title);
            }
        });

        detailBtn = rootView.findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClick("detail", title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }
}
