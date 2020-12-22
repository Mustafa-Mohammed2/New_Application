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

import java.util.ArrayList;

public class AdapterRecuclerUser extends RecyclerView.Adapter<AdapterRecuclerUser.ViewHolder> {

    Context context;
    ArrayList<Converstion>converstions;
    boolean ischat;

    public AdapterRecuclerUser(Context context, ArrayList<Converstion> converstions , boolean ischat) {
        this.context = context;
        this.converstions = converstions;
        this.ischat = ischat;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_recycler,null,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Converstion converstion=converstions.get(position);
        holder.user_name.setText(converstion.getName_User());
        if (converstion.getImage().equals("default")){
            holder.profile.setImageResource(R.drawable.ic_baseline_person_24);
        }else {
            Glide.with(context).load(converstion.getImage()).into(holder.profile);
        }

        if (ischat==true){
            if (converstion.getStatus().equals("online"))
            {

             holder.img_on.setVisibility(View.VISIBLE);
            holder.img_off.setVisibility(View.GONE);
        }
        else {

                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }else {
            Toast.makeText(context, "000", Toast.LENGTH_SHORT).show();
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MassegeActivity.class);
                intent.putExtra("ID",converstion.getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return converstions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
        ImageView profile;
        ImageView img_on;
        ImageView img_off;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name=itemView.findViewById(R.id.recycler_user_name);
            profile =itemView.findViewById(R.id.profile_image);
            img_on =itemView.findViewById(R.id.im_on);
            img_off =itemView.findViewById(R.id.im_off);

         }
    }
}
