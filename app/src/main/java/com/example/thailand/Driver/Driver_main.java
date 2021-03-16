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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thailand.R;
import com.example.thailand.User.SettingsActivity;
import com.example.thailand.User.User_main;
import com.example.thailand.User.User_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Driver_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    CardView card_view2;
    TextView id_driver;
    String url;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Deliver it");
        setSupportActionBar(toolbar);
        //fragment
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        id_driver=findViewById(R.id.id_driver);
        //intent
        url=getIntent().getStringExtra("key");
        documentReference=firebaseFirestore.collection("Driver").document(firebaseUser.getEmail().toString().toLowerCase());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        id_driver.setText(task.getResult().getString("driver_id"));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notes:
                Intent intent1=new Intent(getApplicationContext(), Driver_profile.class);
                intent1.putExtra("key",url);
                startActivity(intent1);
                break;
            case R.id.addNote:
                Toast.makeText(getApplicationContext(), "You are in Home..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sync:
                startActivity(new Intent(getApplicationContext(),Driver_order.class));
                break;
            case R.id.shareapp:
                startActivity(new Intent(getApplicationContext(),Order_List.class));
                break;
            case R.id.shareapp1:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.logout:
                androidx.appcompat.app.AlertDialog.Builder warning = new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Are you sure ?")
                        .setMessage("You are logged in with Temporary Account. Logging out will Delete All the notes.")
                        .setPositiveButton("Sync Data", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Toast.makeText(getApplicationContext(), "Data Sync Successfully", Toast.LENGTH_SHORT).show();

                                    }
                                },2000);


                            }
                        }).setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDO: delete all the notes created by the Anon user

                                // TODO: delete the anon user
                                //fAuth.signOut();
                                // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                firebaseAuth.signOut();
                                finishAffinity();


                            }
                        });

                warning.show();
                break;

        }


        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed()   {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you Exit ?")
                .setMessage("You are logged in with Temporary Account. Exiting  will Delete All the data.")
                .setPositiveButton("Sync Data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Data Sync Successfully", Toast.LENGTH_SHORT).show();

                            }
                        },2000);


                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ToDO: delete all the notes created by the Anon user

                        // TODO: delete the anon user
                        dialog.dismiss();
                        finishAffinity();

                    }
                });

        warning.show();
    }



}