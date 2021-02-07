package com.example.todoreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {
    private ArrayList<TODO> datalist;
    private onRecyclerListener monRecylcerListerer;
    private String x =  "DUE";
    public RecyclerViewAdapter(ArrayList<TODO> datalist, onRecyclerListener onRecyclerListener) {
        this.datalist = datalist;
        this.monRecylcerListerer = onRecyclerListener;
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_list_item_2, viewGroup,false);
        return new myViewHolder(view, monRecylcerListerer);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.todo_title.setText(datalist.get(position).getTitle());
        holder.todo.setText(datalist.get(position).getTodo());
        holder.duedate.setText(datalist.get(position).getDate());
        holder.date2.setText(x);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        holder.date.setText(currentDateTimeString);


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView todo_title, todo, date, date2, duedate;
        onRecyclerListener onRecyclerListener;

        public myViewHolder(@NonNull View itemView, onRecyclerListener onRecyclerListener ){
            super(itemView);
            todo_title = itemView.findViewById(R.id.textView);
            todo = itemView.findViewById(R.id.textView2);
            date = itemView.findViewById(R.id.dateAndTime);
            date2 = itemView.findViewById(R.id.textView6);
            duedate = itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(this);
            this.onRecyclerListener = onRecyclerListener;
        }

        @Override
        public void onClick(View v) {
            onRecyclerListener.onRecyclerClick(getAdapterPosition());

        }
    }
    public interface onRecyclerListener{
        void onRecyclerClick(int position);

    }
}
