package com.example.thailand.User;

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
import android.widget.Toast;

import com.example.thailand.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Random;

public class Add_Order extends AppCompatActivity {
    TextInputEditText username_edit_text,address_edit_text,
            phone,email,password;
    Button signup;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    KProgressHUD progressHUD;
    String url;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__order);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressHUD=KProgressHUD.create(Add_Order.this);

        username_edit_text=findViewById(R.id.username_edit_text);
        address_edit_text=findViewById(R.id.address_edit_text);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        signup=findViewById(R.id.signup);
        //intent
        //url=getIntent().getStringExtra("key");
        //Toast.makeText(this, ""+url, Toast.LENGTH_SHORT).show();
        url=firebaseUser.getEmail().toString().toLowerCase();
       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String username,address,phoneNumber,emailAddress,password_username;
               username=username_edit_text.getText().toString().trim().toLowerCase();
               address=address_edit_text.getText().toString().trim().toLowerCase();
               phoneNumber=phone.getText().toString().trim().toLowerCase();
               emailAddress=email.getText().toString().trim().toLowerCase();
               final Random myRandom = new Random();
               final String randomkey=String.valueOf(myRandom.nextInt(10000));
               if (TextUtils.isEmpty(username) || TextUtils.isEmpty(address)|| TextUtils.isEmpty(phoneNumber)
                       || TextUtils.isEmpty(emailAddress)) {
                   Toast.makeText(getApplicationContext(), "Some Fields are empty.", Toast.LENGTH_LONG).show();
                   return;
               }
               else {
                   progress_check();
                   documentReference=firebaseFirestore.collection("Order").document(url)
                           .collection("new_order").document(randomkey);
                   documentReference.get()
                           .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                   if (task.isSuccessful()) {
                                       if (task.getResult().exists()) {
                                           progressHUD.dismiss();
                                           Toast.makeText(Add_Order.this, "This Data exists", Toast.LENGTH_SHORT).show();
                                           return;
                                       }
                                       else {

                                           register_user1(username,address,phoneNumber,emailAddress,randomkey);
                                           register_user(username,address,phoneNumber,emailAddress,randomkey);

                                       }
                                   }
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           progressHUD.dismiss();
                           Toast.makeText(Add_Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                       }
                   });
               }

           }
       });

    }

    private void register_user1(String username, String address, String phoneNumber,
                                String emailAddress, String randomkey) {
        Order_model order_model=new Order_model(username,address,phoneNumber,emailAddress,randomkey);
        firebaseFirestore.collection("All_Order").document(randomkey).set(order_model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            textSend_user();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void register_user(String username, String address,
                               String phoneNumber, String emailAddress, String randomkey) {
        Order_model order_model=new Order_model(username,address,phoneNumber,emailAddress,randomkey);
        documentReference.set(order_model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressHUD.dismiss();
                    Toast.makeText(Add_Order.this, "Order Successfully Done", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),User_main.class));

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(Add_Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        int permission= ContextCompat.checkSelfPermission(Add_Order.this, Manifest.permission.SEND_SMS);
        if (permission== PackageManager.PERMISSION_GRANTED) {
            sending();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }
    private void sending() {

        ///+66
        String phone_number1233="+660623124174";
        String sm333s="New Order Arrive!!!"+"\nFrom : "+username_edit_text.getText().toString().trim()+
                "\nTo : "+address_edit_text.getText().toString()+"\nWeight : "+phone.getText().toString()+"\nPhone Number : "+email.getText().toString();
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