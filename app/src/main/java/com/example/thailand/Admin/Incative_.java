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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

public class Incative_ extends AppCompatActivity {
    TextView Password_Log,Password_Log1,Password_Log3,Password_Log4;
    EditText Password_Log6;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    String url,url1,url2,url3,url4;
    KProgressHUD progressHUD;
    Button tmio_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incative_);
        firebaseFirestore=FirebaseFirestore.getInstance();
        //url
        progressHUD=KProgressHUD.create(Incative_.this);
        url=getIntent().getStringExtra("key");
        url1=getIntent().getStringExtra("key1");
        url2=getIntent().getStringExtra("key2");
        url3=getIntent().getStringExtra("key3");
        Password_Log=findViewById(R.id.Password_Log);
        Password_Log1=findViewById(R.id.Password_Log1);
        Password_Log.setText(url);
        Password_Log1.setText(url1);
        tmio_1=findViewById(R.id.tmio_1);
        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_check();
                inactive(url,url1,url2);
                delete_parent(url1);


            }
        });
    }

    private void inactive(String url, String url1, String url2) {
        InActive_Model inActive_model=new InActive_Model(url,url1);
        firebaseFirestore.collection("INActive_List").document(url)
                .set(inActive_model)
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
                Toast.makeText(Incative_.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void delete_parent(String url1) {
        firebaseFirestore.collection("Active_list").whereEqualTo("username",url1.toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        WriteBatch writeBatch=FirebaseFirestore.getInstance().batch();
                        List<DocumentSnapshot> snapshotList=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot:snapshotList) {
                            writeBatch.delete(documentSnapshot.getReference());
                        }
                        writeBatch.commit()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressHUD.dismiss();
                                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),Active_list.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressHUD.dismiss();
                                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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