package com.example.thailand.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thailand.R;

import java.util.List;

public class Inactive_Adapter extends RecyclerView.Adapter<Inactive_Adapter.myview> {
    public List<InActive_Model> data;

    public Inactive_Adapter(List<InActive_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_serial,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        holder.text2.setText(data.get(position).getId());
        holder.text4.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView text2,text4;
        public myview(@NonNull View itemView) {
            super(itemView);
            text4=itemView.findViewById(R.id.text4);
            text2=itemView.findViewById(R.id.text2);
        }
    }
}
