package com.example.new_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {

    Toolbar toolbar;
    EditText email  ,password ;
    Button btn_log;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView forgetPass;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

         toolbar=findViewById(R.id.log_tool);
         email=findViewById(R.id.log_email);
         password=findViewById(R.id.log_pass);
         btn_log=findViewById(R.id.btn_log);
         forgetPass=findViewById(R.id.tv_forget);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.item_back){
                    Intent intent =new Intent(LogIn.this ,StartActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),PasswordRestart.class);
                startActivity(intent);
            }
        });

        // Write a message to the database
         database = FirebaseDatabase.getInstance();
         reference = database.getReference("Con");



        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth=FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LogIn.this, "Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LogIn.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else {
                                    Toast.makeText(LogIn.this, "Check to email and  password ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}