package com.example.new_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    Toolbar toolbar;
    EditText user_name  ,email  ,password ;
    Button sign;

    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar=findViewById(R.id.sign_tool);
        user_name=findViewById(R.id.sign_user);
        email=findViewById(R.id.sign_email);
        password=findViewById(R.id.sign_pass);
        sign=findViewById(R.id.btn_sign);
         toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.item_back){
                    Intent intent =new Intent(SignUp.this ,StartActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
///
                return false;
            }
        });
          database=FirebaseDatabase.getInstance();



         sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//    )  &&password.getText().toString().isEmpty()
       if ((Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()==false)
               ||password.getText().toString().isEmpty()||user_name.getText().toString().isEmpty()){
           Toast.makeText(SignUp.this, "check to email and password", Toast.LENGTH_SHORT).show();
                }else{
           String emai = email.getText().toString();
           String pass=password.getText().toString() ;
           String user=user_name.getText().toString() ;
//           Toast.makeText(SignUp.this, "email:"+emai +"password"+pass, Toast.LENGTH_SHORT).show();
                   signUP(user,emai,pass);


                }
            }

        });
    }
    public void signUP(String name ,String email, String password){
        mAuth = FirebaseAuth.getInstance();
         mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Sucsses", Toast.LENGTH_SHORT).show();

                            FirebaseUser user=mAuth.getCurrentUser();
                            String id= user.getUid();
                            String image= "default";

                            Toast.makeText(SignUp.this, "id:  "+id, Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUp.this, "email:  "+email, Toast.LENGTH_SHORT).show();

                            Converstion converstion=new Converstion(email ,id, image, name,password,"online");
                            reference=database.getReference("Con").child(id);

                            //
                            try {

                                reference.setValue(converstion);

                            } catch ( Exception e) {
                                e.printStackTrace();
                            }

                            try {
//                                Thread.sleep(200);
                                Intent intent =new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);
                                finish();
//InterruptedException e
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(SignUp.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

     }
}