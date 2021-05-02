package com.example.apprunner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprunner.User.menu5_uplode_stat.Activity.UplodeStatistics;

import java.util.List;

public class RegEventAdapterUser extends RecyclerView.Adapter<RegEventAdapterUser.Holder> {

    List<ResultQuery> eventList;
    Context context;

    public RegEventAdapterUser (Context context,List<ResultQuery> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public RegEventAdapterUser.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview__user,parent,false);
        return new RegEventAdapterUser.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegEventAdapterUser.Holder holder, int position) {
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        final ResultQuery event = eventList.get(position);
        holder.FName1.setText(event.getFirst_name());
        holder.card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UplodeStatistics.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView FName1;
        CardView card_view1;
        public Holder(@NonNull View itemView) {
            super(itemView);
            FName1 = (TextView)itemView.findViewById(R.id.FName1);
            card_view1 = itemView.findViewById(R.id.card_view1);
        }
    }
}
