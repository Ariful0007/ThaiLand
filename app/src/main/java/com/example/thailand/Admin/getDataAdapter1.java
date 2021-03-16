package com.example.thailand.Admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thailand.Driver.Driver_Model;
import com.example.thailand.R;
import com.example.thailand.User.User_model;

import java.util.List;

public class getDataAdapter1 extends RecyclerView.Adapter<getDataAdapter1.myview> {
    public List<Driver_Model> data;

    public getDataAdapter1(List<Driver_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_database,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        holder.name.setText(data.get(position).getUsername());
        holder.id.setText(data.get(position).getDriver_id());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=data.get(position).getUsername2();
                Intent intent1=new Intent(v.getContext(),Driver_Details.class);
                intent1.putExtra("key",username);
                v.getContext().startActivity(intent1);

            }
        });
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
