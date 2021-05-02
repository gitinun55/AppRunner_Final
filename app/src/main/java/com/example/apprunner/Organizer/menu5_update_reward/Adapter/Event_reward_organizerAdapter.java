package com.example.apprunner.Organizer.menu5_update_reward.Adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apprunner.Organizer.menu5_update_reward.Activity.ChooseUserRewardActivity;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Event_reward_organizerAdapter extends RecyclerView.Adapter<Event_reward_organizerAdapter.Holder> {

    List<ResultQuery> event_organizer;
    Context context;

    public Event_reward_organizerAdapter(Context context, List<ResultQuery> event_organizer) {
        this.context = context;
        this.event_organizer = event_organizer;
    }

    @NonNull
    @Override
    public Event_reward_organizerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_event_organizer,parent,false);
        return new Event_reward_organizerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Event_reward_organizerAdapter.Holder holder, int position) {
        final ResultQuery event = event_organizer.get(position);
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        Picasso.with(context).load(event.getPic_event()).into(holder.iv_image_menu_event_user);
        holder.tv_event_name_menu_event_organizer.setText(event.getName_event());
        holder.tv_producer_name_menu_event_organizer.setText(event.getName_producer());
        holder.card_view_reward_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChooseUserRewardActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("pic_event", event.getPic_event());
                intent.putExtra("name_event", event.getName_event());
                intent.putExtra("name_producer", event.getName_producer());
                intent.putExtra("date_reg_start", event.date_reg_start());
                intent.putExtra("date_reg_end", event.date_reg_end());
                intent.putExtra("detail", event.getDetail());
                intent.putExtra("id_add", event.get_eventID());
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("id_user", id_user);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return event_organizer.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_event_name_menu_event_organizer,tv_producer_name_menu_event_organizer;
        ImageView iv_image_menu_event_user;
        CardView card_view_reward_user;
        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_image_menu_event_user = (ImageView)itemView.findViewById(R.id.iv_image_menu_event_user);
            tv_event_name_menu_event_organizer = (TextView)itemView.findViewById(R.id.tv_event_name_organizer);
            tv_producer_name_menu_event_organizer = (TextView)itemView.findViewById(R.id.tv_producer_name_organizer);
            card_view_reward_user = (CardView)itemView.findViewById(R.id.card_view_reward_user);
        }
    }
}