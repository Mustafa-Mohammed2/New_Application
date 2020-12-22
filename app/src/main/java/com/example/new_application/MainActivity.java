package com.example.new_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference ;
    CircleImageView profileImage;
    Toolbar toolbar;
    TextView  username;
    Button button ;
    TabLayout tabLayout;
    ViewPager viewPager;
    FirebaseUser user;

    private static final String CHANNEL_NAME = "Channel name";
    private static final String CHANNEL_ID = "Channel ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
         username=findViewById(R.id.tv_username);
        profileImage=findViewById(R.id.circle_image);
        toolbar=findViewById(R.id.main_tool);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.item_logOut){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(MainActivity.this,StartActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                return false;
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        ArrayList<RelativeFragment>relativeFragments =new ArrayList<>();
        relativeFragments.add(new RelativeFragment("Chat",new FragmentMain()));
        relativeFragments.add(new RelativeFragment("User",new FragmentUser()));
        relativeFragments.add(new RelativeFragment("Profile",new FragmentProfile()));

        AdapterFrafment adapterFrafment =new AdapterFrafment(getSupportFragmentManager(),relativeFragments);
        viewPager.setAdapter(adapterFrafment);

         user  = FirebaseAuth.getInstance().getCurrentUser();
            reference=FirebaseDatabase.getInstance().getReference("Con").child(user.getUid());
        Log.d("key1",user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Converstion converstion=snapshot.getValue(Converstion.class);
                    Log.d("key2",converstion.getId());
                    if (converstion.getImage().equals("default")){
                     profileImage.setImageResource(R.drawable.ic_baseline_perm_identity_24);
                    }else {
                        Glide.with(MainActivity.this).load(converstion.getImage()).into(profileImage);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

               if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
 NotificationChannel channel =new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                   NotificationManager nm =getSystemService(NotificationManager.class);
                   nm.createNotificationChannel(channel);
                  }


            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            PendingIntent pendingIntent =PendingIntent.getActivity(getBaseContext(),0,intent,0);
         NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
         builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
         .setContentTitle("Hello ")
         .setContentText("Mustafa mohammed")
                 .setContentIntent(pendingIntent)
         .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getBaseContext());
        managerCompat.notify(5,builder.build());


                }
            });
    }


    private  void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("Con").child(user.getUid());
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        status("online");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        status("offline");
//    }
}

