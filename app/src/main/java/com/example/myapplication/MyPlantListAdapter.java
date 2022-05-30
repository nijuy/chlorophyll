package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyPlantListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<MyPlantList> listData;
    private OnItemClick mCallback;

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
        //Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rootView = inflater.inflate(R.layout.myplant_item, parent, false);
        ViewHolder viewholder = new ViewHolder(rootView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.test1_textView.setText(String.valueOf(listData.get(position).getTest1()));
        holder.test2_textView.setText(listData.get(position).getTest2());

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
