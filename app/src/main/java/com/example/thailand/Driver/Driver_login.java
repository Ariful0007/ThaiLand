package com.example.thailand.Driver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thailand.Admin.Admin_list;
import com.example.thailand.R;
import com.example.thailand.User.User_login;
import com.example.thailand.User.User_main;
import com.example.thailand.User.User_reg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

public class Driver_login extends AppCompatActivity {
    TextView text4;
    Button signup;
    TextInputEditText username_edit_text,address_edit_text,
            phone,email,password;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    KProgressHUD progressHUD;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        text4=findViewById(R.id.text4);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Driver_reg.class));

            }
        });
        username_edit_text=findViewById(R.id.username_edit_text);
        password=findViewById(R.id.password_edit_text);
        progressHUD=KProgressHUD.create(Driver_login.this);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),User_main.class));
                final String username,password1;
                username=username_edit_text.getText().toString().toLowerCase();
                password1=password.getText().toString().toLowerCase();
                if(username.equals("admin")) {
                    startActivity(new Intent(getApplicationContext(), Admin_list.class));
                }
               else  if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password1)) {
                    Toast.makeText(Driver_login.this, "Username or password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    progress_check();
                    String username1=username+"@gmail.com";
                    firebaseAuth.signInWithEmailAndPassword(username1,password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(),Driver_main.class));
                                        Toast.makeText(Driver_login.this, "You are login", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

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