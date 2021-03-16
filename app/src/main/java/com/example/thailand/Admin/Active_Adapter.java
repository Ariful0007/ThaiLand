package com.example.thailand.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thailand.R;
import com.example.thailand.User.User_model;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Active_Adapter extends RecyclerView.Adapter<Active_Adapter.myview> {
    public List<Active_Model> data;

    public Active_Adapter(List<Active_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_serial,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        holder.text2.setText(data.get(position).getDriver_id());
        holder.text4.setText(data.get(position).getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder warning = new AlertDialog.Builder(v.getContext())
                        .setTitle("Are you Sure ?")
                        .setMessage("You are want to inactive this driver?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {

                                Intent intent1=new Intent(v.getContext(),Incative_.class);
                                intent1.putExtra("key",data.get(position).getDriver_id());
                                intent1.putExtra("key1",data.get(position).getUsername());

                                v.getContext().startActivity(intent1);


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDO: delete all the notes created by the Anon user

                                // TODO: delete the anon user

                                dialog.dismiss();


                            }
                        });

                warning.show();


            }
        });


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
