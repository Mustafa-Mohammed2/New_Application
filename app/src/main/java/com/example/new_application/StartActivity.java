package com.example.new_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login ,signup ;
    FirebaseAuth auth ;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //      Auto LogIn
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null) {
            Toast.makeText(this, firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }




        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(StartActivity.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(StartActivity.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}