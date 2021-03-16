package com.example.thailand.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thailand.R;
import com.example.thailand.User.User_model;

import java.util.List;

public class getDataAdapter extends RecyclerView.Adapter<getDataAdapter.myview> {
    public List<User_model> data;

    public getDataAdapter(List<User_model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_database,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        holder.name.setText(data.get(position).getUsername());
        holder.id.setText(data.get(position).getCustomer_id());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView name,id;

        public myview(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text1);
            id=itemView.findViewById(R.id.text2);
        }
    }
}
