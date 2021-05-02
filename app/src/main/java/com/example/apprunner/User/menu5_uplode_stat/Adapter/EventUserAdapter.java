package com.example.apprunner.User.menu5_uplode_stat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu5_uplode_stat.Activity.UplodeStatistics;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventUserAdapter extends RecyclerView.Adapter<EventUserAdapter.Holder>{
    List<ResultQuery> eventList;
    Context context;

    public EventUserAdapter(Context context,List<ResultQuery> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventUserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_user_uplode_statistics,parent,false);
        return new EventUserAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final ResultQuery event = eventList.get(position);
        final Intent intent = ((Activity) context).getIntent();
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final String type = intent.getExtras().getString("type");
        final int id_user = intent.getExtras().getInt("id_user");
        Picasso.with(context).load(event.getPic_event()).into(holder.iv_image_menu_event_user);
        holder.tv_event_name.setText(event.getName_event());
        holder.tv_producer_name.setText(event.getName_producer());
        holder.btn_uplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, UplodeStatistics.class);
                intent.putExtra("id_add", event.get_eventID());
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                intent.putExtra("name_event", event.getName_event());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_producer_name,tv_event_name,text_km2;
        CardView card_view;
        Button btn_uplode;
        ImageView iv_image_menu_event_user;
        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_image_menu_event_user =(ImageView)itemView.findViewById(R.id.iv_image_menu_event_user);
            tv_producer_name = (TextView)itemView.findViewById(R.id.tv_producer_name);
            tv_event_name = (TextView)itemView.findViewById(R.id.tv_event_name);
            card_view = itemView.findViewById(R.id.card_view);
            btn_uplode = itemView.findViewById(R.id.btn_uplode);;
        }
    }
}
