package com.example.thailand.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thailand.Admin.getDataAdapter;
import com.example.thailand.R;

import java.util.List;

public class Old_Adapter extends RecyclerView.Adapter<Old_Adapter.myview> {
    public List<Order_model> data;

    public Old_Adapter(List<Order_model> data) {
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
        holder.text6.setText(data.get(position).getPhone());
        holder.text8.setText(data.get(position).getWeight());

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
