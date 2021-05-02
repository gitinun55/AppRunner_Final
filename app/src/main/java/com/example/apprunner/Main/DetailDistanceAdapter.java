package com.example.apprunner.Main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprunner.Cricketer;
import com.example.apprunner.R;

import java.util.List;

public class DetailDistanceAdapter extends RecyclerView.Adapter<DetailDistanceAdapter.Holder> {
    List<Cricketer> cricketers;
    Context context;

    public DetailDistanceAdapter (Context context,List<Cricketer> cricketers) {
        this.context = context;
        this.cricketers = cricketers;
    }

    @NonNull
    @Override
    public DetailDistanceAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_detail_distance,parent,false);
        return new  DetailDistanceAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailDistanceAdapter.Holder holder, int position) {
        final Cricketer cricketer = cricketers.get(position);
        holder.KM.setText(Integer.toString(cricketer.getdistance()));
        holder.num_register_KM.setText(Integer.toString(cricketer.getnum_register()));
        holder.bath.setText(Integer.toString(cricketer.getprice()));
    }

    @Override
    public int getItemCount() {
        return cricketers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView KM,num_register_KM,bath;
        public Holder(@NonNull View itemView) {
            super(itemView);
            KM = (TextView)itemView.findViewById(R.id.KM);
            num_register_KM = (TextView)itemView.findViewById(R.id.num_register_KM);
            bath = (TextView)itemView.findViewById(R.id.bath);
        }
    }
}