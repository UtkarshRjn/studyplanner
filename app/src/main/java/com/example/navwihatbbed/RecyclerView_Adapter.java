package com.example.navwihatbbed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {

    List<EventModel> my_list;
    Context context;

    public RecyclerView_Adapter(Context ct, List<EventModel> event_list){
        context = ct;
        my_list = event_list;
    };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(my_list.get(position).getTitle());
        holder.date.setText(my_list.get(position).getDate());
        holder.description.setText(my_list.get(position).getDescription());
        holder.time.setText(my_list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return my_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description,date,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView6);
            description = itemView.findViewById(R.id.textView);
            date = itemView.findViewById(R.id.textView8);
            time = itemView.findViewById(R.id.textView7);
        }
    }
}
