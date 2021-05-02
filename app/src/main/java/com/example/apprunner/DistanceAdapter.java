package com.example.apprunner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprunner.DB.DistanceDB;

import java.util.List;

public class DistanceAdapter extends RecyclerView.Adapter<DistanceAdapter.ViewHolder> {

    Context context;
    List<DistanceDB> distance_list;

    public DistanceAdapter(Context context, List<DistanceDB> distance_list) {
        this.context = context;
        this.distance_list = distance_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.distance_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (distance_list != null && distance_list.size() > 0){
            DistanceDB modal = distance_list.get(position);
            holder.date_tv.setText(modal.getDate());
            holder.time_tv.setText(modal.getTime());
            holder.distance_tv.setText(modal.getDistance());
            holder.calorie_tv.setText(modal.getCalorie());
        }else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return distance_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_tv,time_tv,distance_tv,calorie_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date_tv = itemView.findViewById(R.id.date_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            distance_tv = itemView.findViewById(R.id.distance_tv);
            calorie_tv = itemView.findViewById(R.id.calorie_tv);
        }
    }
}
