package com.example.apprunner.Organizer.menu2_profile_event.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

public class ListNameRegEventAdapter extends RecyclerView.Adapter<ListNameRegEventAdapter.Holder> {

    Context context;
    List<ResultQuery> list_name_reg_event;

    public ListNameRegEventAdapter(Context context, List<ResultQuery> list_name_reg_event){
        this.context = context;
        this.list_name_reg_event = list_name_reg_event;
    }

    @NonNull
    @Override
    public ListNameRegEventAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_name_reg_event_layout,parent,false);
        return new ListNameRegEventAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNameRegEventAdapter.Holder holder, int position) {
        final ResultQuery profile = list_name_reg_event.get(position);
        final Intent intent = ((Activity) context).getIntent();
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_add = intent.getExtras().getInt("id_add");
        final int id_user = intent.getExtras().getInt("id_user");
        holder.id_tv.setText(Integer.toString(profile.getId()));
        holder.FName_tv.setText(profile.getFirst_name());
        holder.LName_tv.setText(profile.getLast_name());
        holder.Tel_tv.setText(profile.getTel());

    }

    @Override
    public int getItemCount() {
        return list_name_reg_event.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView id_tv,FName_tv,LName_tv,Tel_tv;
        public Holder(@NonNull View itemView) {
            super(itemView);
            id_tv = (TextView)itemView.findViewById(R.id.id_tv);
            FName_tv = (TextView)itemView.findViewById(R.id.FName_tv);
            LName_tv = (TextView)itemView.findViewById(R.id.LName_tv);
            Tel_tv = (TextView)itemView.findViewById(R.id.Tel_tv);
        }
    }
}