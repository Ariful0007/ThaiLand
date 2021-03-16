package com.example.thailand.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thailand.R;
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
import java.util.UUID;

public class User_reg extends AppCompatActivity {
    TextInputEditText username_edit_text,address_edit_text,
            phone,email,password;
    Button signup;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    KProgressHUD progressHUD;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        username_edit_text=findViewById(R.id.username_edit_text);
        address_edit_text=findViewById(R.id.address_edit_text);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        progressHUD=KProgressHUD.create(User_reg.this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username,address,phoneNumber,emailAddress,password_username;
                username=username_edit_text.getText().toString().trim().toLowerCase();
                address=address_edit_text.getText().toString().trim().toLowerCase();
                phoneNumber=phone.getText().toString().trim().toLowerCase();
                emailAddress=email.getText().toString().trim().toLowerCase();
                password_username=password.getText().toString().trim().toLowerCase();
                final Random myRandom = new Random();
                final String randomkey=String.valueOf(myRandom.nextInt(10000));

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(address)|| TextUtils.isEmpty(phoneNumber)
                        || TextUtils.isEmpty(emailAddress)||TextUtils.isEmpty(password_username)) {
                    Toast.makeText(User_reg.this, "Some Fields are empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    progress_check();
                    documentReference=firebaseFirestore.collection("Users").document(username);
                    documentReference.get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            progressHUD.dismiss();

                                            Toast.makeText(User_reg.this, "Username already exists", Toast.LENGTH_LONG).show();
                                            username_edit_text.setError("Username already exists");
                                            return;
                                        }
                                        else {
                                            String username2=username+"@gmail.com";
                                            register_as_User(username2,password_username);
                                            register_user(username,address,phoneNumber,emailAddress,password_username,randomkey);

                                        }
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(User_reg.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    //register_user(username,address,phoneNumber,emailAddress,password_username,randomkey);
                }

            }
        });
    }

    private void register_as_User(String username2,
                                  String password_username) {
        firebaseAuth.createUserWithEmailAndPassword(username2,password_username)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_reg.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void register_user(final String username, String address, String phoneNumber,
                               String emailAddress, String password_username, String randomkey) {
        /*progress_check();;
        User_model user_model=new User_model(username,address,phoneNumber,emailAddress,password_username,randomkey);
        firebaseFirestore.collection("Users").document(username+"@gmail.com").set(user_model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressHUD.dismiss();
                            Toast.makeText(User_reg.this, "Account Create Successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent(getApplicationContext(),User_main.class);
                            startActivity(intent1);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(User_reg.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        progress_check();
        Model_2 model_2=new Model_2(username,address,phoneNumber,emailAddress,randomkey);
        firebaseFirestore.collection("Users").document(username+"@gmail.com")
                .set(model_2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressHUD.dismiss();
                            Toast.makeText(User_reg.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),User_login.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressHUD.dismiss();
                Toast.makeText(User_reg.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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