package com.example.superheroes;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<com.example.superheroes.Data> data;
    Context context;
    public Adapter(Context ctx, List<com.example.superheroes.Data> data){
        this.inflater =LayoutInflater.from(ctx);
        this.data =data;
        this.context=ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //bind the data
        Picasso.get().load(data.get(position).getImages().getSm()).into(holder.imageView);
        holder.name.setText("ID:"+data.get(position).getId()+"\n"+data.get(position).getName()+"\nGender:"+data.get(position).getApperance().getGender());
      holder.view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,MainActivity2.class);
               intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
             intent.putExtra("id",data.get(position).id);
             intent.putExtra("name",data.get(position).getName());
              intent.putExtra("url",data.get(position).getImages().getSm());
             intent.putExtra("gender",data.get(position).getApperance().getGender());
              intent.putExtra("height",data.get(position).getApperance().getHeight()[1]);
              intent.putExtra("weight",data.get(position).getApperance().getWeight()[1]);
              intent.putExtra("power",data.get(position).getPowerstats().getPower());
               intent.putExtra("combat",data.get(position).getPowerstats().getCombat());
               intent.putExtra("durability",data.get(position).getPowerstats().getDurability());
               intent.putExtra("intelligence",data.get(position).getPowerstats().getIntelligence());
               intent.putExtra("speed",data.get(position).getPowerstats().getSpeed());
               context.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
       ImageView imageView;
       View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
         imageView = itemView.findViewById(R.id.imageView);
         this.view = itemView;
        }
    }
 }
