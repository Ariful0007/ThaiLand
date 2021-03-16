package com.example.thailand.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thailand.R;
import com.example.thailand.User.Add_Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

public class Order_Details extends AppCompatActivity {
    TextView Password_Log,Password_Log1,Password_Log3,Password_Log4;
    EditText Password_Log6; 
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    String url,url1,url2,url3,url4;
    KProgressHUD progressHUD;
    Button tmio_1;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);
        firebaseFirestore=FirebaseFirestore.getInstance();
        //url
        url=getIntent().getStringExtra("key");
        url1=getIntent().getStringExtra("key1");
        url2=getIntent().getStringExtra("key2");
        url3=getIntent().getStringExtra("key3");
        url4=getIntent().getStringExtra("key4");
        Password_Log=findViewById(R.id.Password_Log);
        Password_Log1=findViewById(R.id.Password_Log1);
        Password_Log3=findViewById(R.id.Password_Log3);
        Password_Log4=findViewById(R.id.Password_Log4);
        Password_Log6=findViewById(R.id.Password_Log6);
        progressHUD=KProgressHUD.create(Order_Details.this);
        Password_Log.setText(url1);
        Password_Log1.setText(url2);
        Password_Log3.setText(url4);
        Password_Log4.setText(url3);
        tmio_1=findViewById(R.id.tmio_1);
        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driver=Password_Log6.getText().toString().toLowerCase();
                if (TextUtils.isEmpty(driver)) {
                    Toast.makeText(Order_Details.this, "Enter driver name", Toast.LENGTH_SHORT).show();
                    Password_Log6.setError("Enter driver name");
                    return;

                }
                else {
                    progress_check();
                    String driver_formate=driver+"@gmail.com";
                    give_order1(driver_formate, url,url1,url2,url3,url4);
                    give_order(driver_formate, url,url1,url2,url3,url4);


                }
            }
        });

    }

    private void give_order1(String driver_formate,
                             String url, String url1, String url2, String url3, String url4) {
        Notification notification=new Notification(driver_formate,url,url1,url2,url3,url4);
        firebaseFirestore.collection("Supply").document(driver_formate)
                .set(notification)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(Order_Details.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void give_order(final String driver_formate, String url,
                            String url1, String url2, String url3, String url4) {
        Notification notification=new Notification(driver_formate,url,url1,url2,url3,url4);
        firebaseFirestore.collection("Notification_order").document(driver_formate).collection("All_order")
                .document(url3).set(notification)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressHUD.dismiss();
                            search_value(driver_formate);
                            Toast.makeText(Order_Details.this, "Order going to driver", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Admin_list.class));
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(Order_Details.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void search_value(String driver_formate) {
        firebaseFirestore.collection("Driver").document(driver_formate)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        phone=task.getResult().getString("phoneNumber");
                        textSend_user();

                    }
                    else {
                        Toast.makeText(Order_Details.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                    }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Order_Details.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void textSend_user() {
        int permission= ContextCompat.checkSelfPermission(Order_Details.this, Manifest.permission.SEND_SMS);
        if (permission== PackageManager.PERMISSION_GRANTED) {
            sending();
            sending2();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }

    private void sending2() {
        String phone_number1233=Password_Log4.getText().toString();
        String sm333s="Your Order Conform!!!"+"\nDriver is going.\n Driver PhoneNumber : "+Password_Log4.getText().toString();
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phone_number1233,null,sm333s,null,null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }

    private void sending() {
        String phone_number1233=phone;
        String sm333s="New Order Arrive!!!"+"\nFrom : "+Password_Log.getText().toString().trim()+
                "\nTo : "+Password_Log1.getText().toString()+"\nWeight : "+Password_Log3.getText().toString()+"\nPhone Number : "+Password_Log4.getText().toString();
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phone_number1233,null,sm333s,null,null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 0:
                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    sending();;
                }
                else {
                    Toast.makeText(this, "Don't  Have permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }


}