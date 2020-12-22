package com.example.new_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdapterMassege extends RecyclerView.Adapter<AdapterMassege.ViewHolder> {

    public static final int MSG_TYPE_RAIGT=1;
    public static final int MSG_TYPE_LEFT=0;
    Context context;
    ArrayList<Chats>arrayList;
    String imageUrl;

    public AdapterMassege(Context context, ArrayList<Chats> arrayList, String imageUrl) {
        this.context = context;
        this.arrayList = arrayList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RAIGT){
            View view= LayoutInflater.from(context).inflate(R.layout.bacground_right,null,false);
            return new ViewHolder(view);
        }else {
            View view= LayoutInflater.from(context).inflate(R.layout.background_left,null,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chats chats=arrayList.get(position);
        holder.text_massege.setText(chats.getMassege());


        if (position == (arrayList.size()- 1)){
                if (chats.isSeen()){
                    holder.text_seen.setText("seen");
                 }else {
                    holder.text_seen.setText("no watch");
                 }
        }else {
            holder.text_seen.setVisibility(View.GONE);
        }

        if (imageUrl.equals("default")){
               holder.profile_image.setImageResource(R.drawable.ic_baseline_perm_identity_24);
            } else {
                Glide.with(context).load(imageUrl).into(holder.profile_image);
            }


     }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_massege;
        TextView text_seen;
        ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_massege=itemView.findViewById(R.id.text_massege);
            text_seen=itemView.findViewById(R.id.tv_seen);
            profile_image =itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

     if (arrayList.get(position).getSender().equals(user.getUid())){
         return MSG_TYPE_RAIGT;
     }else {
         return MSG_TYPE_LEFT;
     }
    }
}