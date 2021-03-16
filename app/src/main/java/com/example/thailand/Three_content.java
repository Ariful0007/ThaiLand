package com.example.thailand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thailand.Driver.Driver_login;
import com.example.thailand.Driver.Driver_main;
import com.example.thailand.User.User_login;
import com.example.thailand.User.User_main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Three_content extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_content);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Button login2=findViewById(R.id.login2);
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser!=null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String user1 = user.getEmail().toString().toLowerCase();
                    firebaseFirestore.collection("Users").document(user1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            startActivity(new Intent(getApplicationContext(), User_main.class));
                                            finish();
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), User_login.class));
                                            finish();
                                        }
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Three_content.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }
                else {
                    startActivity(new Intent(getApplicationContext(), User_login.class));
                    finish();
                }


            }
        });
        Button login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser!=null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String user1 = user.getEmail().toString().toLowerCase();
                    firebaseFirestore.collection("Driver").document(user1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            startActivity(new Intent(getApplicationContext(), Driver_main.class));
                                            finish();
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), Driver_login.class));
                                            finish();
                                        }
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Three_content.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }
                else {
                    startActivity(new Intent(getApplicationContext(), Driver_login.class));
                    finish();
                }



            }
        });
    }
}