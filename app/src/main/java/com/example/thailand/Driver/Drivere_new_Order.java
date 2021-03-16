package com.example.thailand.Driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thailand.Admin.New_Adapter;
import com.example.thailand.R;
import com.example.thailand.User.Order_model;

import java.util.List;

public class Drivere_new_Order extends RecyclerView.Adapter<Drivere_new_Order.myview> {
    List<Order_model> data;

    public Drivere_new_Order(List<Order_model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_design,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        holder.text2.setText(data.get(position).getFrom());
        holder.text4.setText(data.get(position).getTo());
        holder.text6.setText(data.get(position).getWeight());
        holder.text8.setText(data.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView text2,text4,text6,text8;
        public myview(@NonNull View itemView) {
            super(itemView);
            text2=itemView.findViewById(R.id.text2);
            text4=itemView.findViewById(R.id.text4);
            text6=itemView.findViewById(R.id.text6);
            text8=itemView.findViewById(R.id.text8);
        }
    }
}
