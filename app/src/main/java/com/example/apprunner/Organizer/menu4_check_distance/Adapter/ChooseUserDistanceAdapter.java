package com.example.apprunner.Organizer.menu4_check_distance.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu4_check_distance.Activity.ChooseUserDistanceActivity;
import com.example.apprunner.Organizer.menu4_check_distance.Activity.DetailDistanceUserActivity;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

public class ChooseUserDistanceAdapter extends RecyclerView.Adapter<ChooseUserDistanceAdapter.Holder> {

    Context context;
    List<ResultQuery> resultQueries;

    public ChooseUserDistanceAdapter(Context context, List<ResultQuery> resultQueries){
        this.context = context;
        this.resultQueries = resultQueries;
    }

    @NonNull
    @Override
    public ChooseUserDistanceAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_choose_user_distance,parent,false);
        return new ChooseUserDistanceAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseUserDistanceAdapter.Holder holder, int position) {
        final Intent intent = ((Activity) context).getIntent();
        final ResultQuery resultQuery = resultQueries.get(position);
        final int id_organizer = intent.getExtras().getInt("id_user");
        final String type = intent.getExtras().getString("type");
        final int id_add = intent.getExtras().getInt("id_add");
        holder.id_user.setText(Integer.toString(resultQuery.getId()));
        holder.FName_user.setText(resultQuery.getFirst_name());
        holder.LName_user.setText(resultQuery.getLast_name());
        if(resultQuery.getStatus_reward() == 0){
            holder.status_user_distance.setText("รออนุมัติ");
        }
        if(resultQuery.getStatus_reward() == 1){
            holder.status_user_distance.setText("อนุมัติ");
        }
        holder.btn_detail_distance_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailDistanceUserActivity.class);
                intent.putExtra("id_user", resultQuery.getId());
                intent.putExtra("id_organizer", id_organizer);
                intent.putExtra("id_add", id_add);
                intent.putExtra("name_event",resultQuery.getName_event());
                intent.putExtra("first_name",resultQuery.getFirst_name());
                intent.putExtra("last_name", resultQuery.getLast_name());
                intent.putExtra("type", type);
                intent.putExtra("status_reward", resultQuery.getStatus_reward());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultQueries.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView id_user,FName_user,LName_user,status_user_distance;
        Button btn_detail_distance_event;
        public Holder(@NonNull View itemView) {
            super(itemView);
            id_user = (TextView)itemView.findViewById(R.id.id_user);
            FName_user = (TextView)itemView.findViewById(R.id.FName_user);
            LName_user = (TextView)itemView.findViewById(R.id.LName_user);
            status_user_distance = (TextView)itemView.findViewById(R.id.status_user_distance);
            btn_detail_distance_event = (Button)itemView.findViewById(R.id.btn_detail_distance_event);
        }
    }
}