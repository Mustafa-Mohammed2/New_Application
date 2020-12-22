package com.example.new_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordRestart extends AppCompatActivity {

    Button btn_restart;
    EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_restart);

        btn_restart =findViewById(R.id.btn_restart);
        et_email =findViewById(R.id.et_email);
        FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()==false)){
                    Toast.makeText(PasswordRestart.this, "Make Sure to Email", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(et_email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PasswordRestart.this, "Please Chek your Email", Toast.LENGTH_SHORT).show();
                                  Intent intent =new Intent(PasswordRestart.this, LogIn.class);
                                  startActivity(intent);
                            }else {
                                Toast.makeText(PasswordRestart.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

    }
}