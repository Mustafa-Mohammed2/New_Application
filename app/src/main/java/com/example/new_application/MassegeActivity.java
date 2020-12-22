package com.example.new_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MassegeActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView profile_image;
    TextView user_name;
    EditText send_massege;
    ImageButton btn_send;

    DatabaseReference reference1;
    FirebaseUser user ;

  private    RecyclerView recyclerView ;
  private   ArrayList<Chats> arrayList ;
  private   String id ;
  private   ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massege);

        toolbar=findViewById(R.id.massege_tool);
        profile_image=findViewById(R.id.massege_image);
        user_name=findViewById(R.id.massege_tv_username);
        send_massege=findViewById(R.id.et_massege);
       btn_send =findViewById(R.id.icon_imagge);
       recyclerView =findViewById(R.id.recycler_massege);
        user =FirebaseAuth.getInstance().getCurrentUser();

       recyclerView.setHasFixedSize(true);
       LinearLayoutManager layoutManager= new LinearLayoutManager(MassegeActivity.this);
       layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent=getIntent();
       id = intent.getStringExtra("ID");



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        DatabaseReference     reference = FirebaseDatabase.getInstance().getReference("Con").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Converstion converstion =snapshot.getValue(Converstion.class);
               user_name.setText(converstion.getName_User());

                if (converstion.getImage().equals("default")){
                    profile_image.setImageResource(R.drawable.ic_baseline_perm_identity_24);
                }else {
                    Glide.with(getBaseContext()).load(converstion.getImage()).into(profile_image);
                }
                showMassege(user.getUid(),id,converstion.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (!send_massege.getText().toString().equals("")){
                    sendMassege(user.getUid(),id,send_massege.getText().toString());
                }else {
                    Toast.makeText(MassegeActivity.this, "You can't send empty massege", Toast.LENGTH_SHORT).show();
                }
                send_massege.setText("");
            }
        });

        seenMassege(id);
    }


    private void sendMassege(String sender ,String reciver ,String massege) {


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        Chats chats =new Chats(sender,reciver,massege,false);
        reference.child("chats").push().setValue(chats);

        DatabaseReference referenceUser =FirebaseDatabase.getInstance().getReference("chatlist")
                .child(sender).child(reciver);
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        ChatList chatList=new ChatList(reciver);
                        referenceUser.setValue(chatList);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public  void  showMassege( final String my_id ,final String user_id ,final  String image){
        DatabaseReference   reference=FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList=new ArrayList<>();
                arrayList.clear();

            for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                Chats chats =dataSnapshot.getValue(Chats.class);

                if (chats.getSender().equals(my_id)&&chats.getReciver().equals(user_id) ||
                    chats.getSender().equals(user_id)&&chats.getReciver().equals(my_id)){
                    arrayList.add(chats);
                }
                AdapterMassege adapter =new AdapterMassege(MassegeActivity.this,arrayList,image);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        reference1.removeEventListener(seenListener);
        Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
    }


    public  void seenMassege (final String userId ){

        reference1 =  FirebaseDatabase.getInstance().getReference("chats");
        seenListener= reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chats chats=dataSnapshot.getValue(Chats.class);
//                    if (chats.getSender().equals(userId) && chats.getReciver().equals(user.getUid())){
                    if (chats.getReciver().equals(user.getUid()) && chats.getSender().equals(userId)) {
//                        Toast.makeText(MassegeActivity.this, "massege:"+chats.getMassege(), Toast.LENGTH_SHORT).show();
                        HashMap<String ,Object> hashMap=new HashMap<>();
                        hashMap.put("seen",true);

                        dataSnapshot.getRef().updateChildren(hashMap);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private  void status(String status){
      final   DatabaseReference    reference=FirebaseDatabase.getInstance().getReference("Con").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Converstion converstion=snapshot.getValue(Converstion.class);
                converstion.setStatus(status);
                reference.setValue(converstion);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    


}