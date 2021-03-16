package com.example.thailand.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thailand.R;
import com.example.thailand.User.User_login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

public class Driver_Details extends AppCompatActivity {
    TextView Password_Log,Password_Log1,Password_Log3,Password_Log4,Password_Log6;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    String url;
    KProgressHUD progressHUD;
    Button tmio_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__details);
        firebaseFirestore=FirebaseFirestore.getInstance();
        //url
        url=getIntent().getStringExtra("key");
        Password_Log=findViewById(R.id.Password_Log);
        Password_Log1=findViewById(R.id.Password_Log1);
        Password_Log3=findViewById(R.id.Password_Log3);
        Password_Log4=findViewById(R.id.Password_Log4);
        Password_Log6=findViewById(R.id.Password_Log6);
        progressHUD=KProgressHUD.create(Driver_Details.this);
        tmio_1=findViewById(R.id.tmio_1);
        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_check();
                documentReference=firebaseFirestore.collection("Driver").document(url);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                Password_Log.setText(task.getResult().getString("driver_id"));
                                Password_Log1.setText(task.getResult().getString("username"));
                                Password_Log3.setText(task.getResult().getString("phoneNumber"));
                                Password_Log4.setText(task.getResult().getString("vehicle_Weight"));
                                Password_Log6.setText(task.getResult().getString("vehicle_Length"));
                                String driver_id,username,phonenumber,vehicle_Weight,vehicle_Length;
                                driver_id=task.getResult().getString("driver_id");
                                username=task.getResult().getString("username");
                                phonenumber=task.getResult().getString("phoneNumber");
                                vehicle_Weight=task.getResult().getString("vehicle_Weight");
                                vehicle_Length=task.getResult().getString("vehicle_Length");
                                active_the_Driver(driver_id,username,phonenumber,vehicle_Weight,vehicle_Length);

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressHUD.dismiss();
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        documentReference=firebaseFirestore.collection("Driver").document(url);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Password_Log.setText(task.getResult().getString("driver_id"));
                        Password_Log1.setText(task.getResult().getString("username"));
                        Password_Log3.setText(task.getResult().getString("phoneNumber"));
                        Password_Log4.setText(task.getResult().getString("vehicle_Weight"));
                        Password_Log6.setText(task.getResult().getString("vehicle_Length"));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void active_the_Driver(String driver_id,
                                   String username, String phonenumber,
                                   String vehicle_weight, String vehicle_length) {
        Active_Model active_model=new Active_Model(driver_id,username,phonenumber,vehicle_weight,vehicle_length);
        firebaseFirestore.collection("Active_list").document(username.toLowerCase())
                .set(active_model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressHUD.dismiss();
                            Toast.makeText(Driver_Details.this, "Driver Added As Active", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Active_list.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(Driver_Details.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}