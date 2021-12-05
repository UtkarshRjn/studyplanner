package com.example.navwihatbbed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {

    List<EventModel> my_list;
    Context context;
    mySQLiteDBHandler dbHandler;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(my_list.get(position).getTitle());
        holder.date.setText(my_list.get(position).getDate());
        holder.description.setText(my_list.get(position).getDescription());
        holder.time.setText(my_list.get(position).getTime());

        dbHandler = new mySQLiteDBHandler(context);

        final int currentPosition = position;
        final EventModel eventModel = my_list.get(position);

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItem(eventModel);
                EventModel eventModel = (EventModel) my_list.get(currentPosition);
                Toast.makeText(context,"Delete called for " + eventModel.toString(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void removeItem(EventModel eventModel) {

        int currPosition = my_list.indexOf(eventModel);
        my_list.remove(currPosition);
        notifyItemRemoved(currPosition);
        dbHandler.deleteOne(eventModel);

    }

    @Override
    public int getItemCount() {
        return my_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description,date,time;
        Button delete_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView6);
            description = itemView.findViewById(R.id.textView);
            date = itemView.findViewById(R.id.textView8);
            time = itemView.findViewById(R.id.textView7);
            delete_button = itemView.findViewById(R.id.delete_button);

        }
    }
}
