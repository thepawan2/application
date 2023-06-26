package com.example.newappwork;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class classDataAdapter extends RecyclerView.Adapter<classDataAdapter.viewHolder> {


    Context context;
    ArrayList<classDataforcontent> classData;
    classDataAdapter(Context context, ArrayList<classDataforcontent> classData){
           this.context = context;
           this.classData= classData;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(context).inflate(R.layout.recycle_data,parent,false);
       viewHolder viewHolder = new viewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.img.setImageResource(classData.get(position).image);
        holder.cl.setText(classData.get(position).nclass);

    }

    @Override
    public int getItemCount() {

        return classData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView cl;
        public viewHolder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.imagecl);
            cl = itemView.findViewById(R.id.textcl);

        }

    }


}
