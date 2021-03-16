package com.example.thailand.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thailand.R;
import com.example.thailand.User.User_main;
import com.example.thailand.User.User_reg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Random;

public class Driver_reg extends AppCompatActivity {
TextInputEditText username_edit_text,password_edit_text,age,number,number1,
        number2,passwordq2;
Button signup;
FirebaseFirestore firebaseFirestore;
DocumentReference documentReference;
    KProgressHUD progressHUD;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        username_edit_text=findViewById(R.id.username_edit_text);
        password_edit_text=findViewById(R.id.password_edit_text);
        age=findViewById(R.id.age);
        number=findViewById(R.id.number);
        number1=findViewById(R.id.number1);
        number2=findViewById(R.id.number2);
        passwordq2=findViewById(R.id.passwordq2);
        signup=findViewById(R.id.signup);
        progressHUD=KProgressHUD.create(Driver_reg.this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username,phoneNumber,Vehicle_Weight,Vehicle_Length,Vehicle_Width
                        ,Email_Address,Password;
                username=username_edit_text.getText().toString().toLowerCase().trim();
                phoneNumber=password_edit_text.getText().toString().toLowerCase().trim();
                Vehicle_Weight=age.getText().toString().toLowerCase().trim();
                Vehicle_Length=number.getText().toString().toLowerCase().trim();
                Vehicle_Width=number1.getText().toString().toLowerCase().trim();
                Email_Address=number2.getText().toString().toLowerCase().trim();
                Password=passwordq2.getText().toString().toLowerCase().trim();
                final Random myRandom = new Random();
                final String randomkey=String.valueOf(myRandom.nextInt(10000));
                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(phoneNumber)||TextUtils.isEmpty(Vehicle_Length)||
                        TextUtils.isEmpty(Vehicle_Weight)||TextUtils.isEmpty(Vehicle_Width)||
                        TextUtils.isEmpty(Email_Address)||TextUtils.isEmpty(Password)) {
                    Toast.makeText(Driver_reg.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    progress_check();
                    documentReference=firebaseFirestore.collection("Driver").document(username);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    progressHUD.dismiss();
                                    Toast.makeText(Driver_reg.this, "Driver already Registered", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else {
                                    String username2=username+"@gmail.com";
                                    register_as_User(username2,Password);
                                    registe_driver(username,username2,phoneNumber,Vehicle_Weight,Vehicle_Length,Vehicle_Width
                                            ,Email_Address,Password,randomkey);
                                }
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            Toast.makeText(Driver_reg.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



    }

    private void register_as_User(String username2,
                                  String password) {
        firebaseAuth.createUserWithEmailAndPassword(username2,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void registe_driver(final String username,String username2, String phoneNumber,
                                String vehicle_weight, String vehicle_length,
                                String vehicle_width, String email_address,
                                String password, String randomkey) {
        Driver_Model driverModel=new Driver_Model(username,username2,phoneNumber,vehicle_weight,vehicle_length,vehicle_width
                ,email_address,password,randomkey);
        firebaseFirestore.collection("Driver").document(username2)
                .set(driverModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressHUD.dismiss();
                            Toast.makeText(Driver_reg.this, "Account Create Successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(getApplicationContext(), Driver_login.class);

                            startActivity(intent1);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(Driver_reg.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

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