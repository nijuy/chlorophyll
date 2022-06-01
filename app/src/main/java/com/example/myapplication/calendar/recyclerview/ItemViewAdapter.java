// item_todo와 ItemToDo를 사용하기 위한 어댑터
package com.example.myapplication.calendar.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewAdapter.ViewHolder> {
    private ArrayList<ItemToDo> mData = null;

    public ItemViewAdapter(ArrayList<ItemToDo> data) {
        mData = data;
    }

    // onCreateViewHolder : 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리터
    @NonNull
    @Override
    public ItemViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_todo, parent, false);
        ItemViewAdapter.ViewHolder vh = new ItemViewAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder : position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ItemViewAdapter.ViewHolder holder, int position) {
        ItemToDo item = mData.get(position);

        holder.plantName.setText(item.getPlant());
        holder.todoWhat.setText(item.getWhat());
    }

    // getItemCount() : 전체 데이터의 개수를 리턴
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView plantName;
        TextView todoWhat;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            plantName = itemView.findViewById(R.id.item_todo_plant);
            todoWhat = itemView.findViewById(R.id.item_todo_what);
        }
    }
}
