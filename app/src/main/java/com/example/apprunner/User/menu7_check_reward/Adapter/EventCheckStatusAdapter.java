package com.example.apprunner.User.menu7_check_reward.Adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Organizer.menu4_check_distance.Activity.ChooseUserDistanceActivity;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu7_check_reward.Activity.ChooseUserOrderActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventCheckStatusAdapter extends RecyclerView.Adapter<EventCheckStatusAdapter.Holder> {

    List<ResultQuery> eventList;
    Context context;

    public EventCheckStatusAdapter(Context context,List<ResultQuery> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventCheckStatusAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_event_check_status,parent,false);
        return new EventCheckStatusAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCheckStatusAdapter.Holder holder, int position) {
        final ResultQuery event = eventList.get(position);
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        Picasso.with(context).load(event.getPic_event()).into(holder.iv_image);
        holder.tv_event_name_menu_event_user.setText(event.getName_event());
        holder.tv_producer_name_menu_event_user.setText(event.getName_producer());
        holder.card_view_user_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChooseUserOrderActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("name_event", event.getName_event());
                intent.putExtra("id_add", event.get_eventID());
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("id_user", id_user);
                context.startActivity(intent);
            }
        });
        if(event.getStatus_send() == 0){
            holder.type_send_reward.setText("รอการจัดส่ง");
        }else if(event.getStatus_send() == 1){
            holder.type_send_reward.setText("จัดส่งแล้ว");
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_event_name_menu_event_user,tv_producer_name_menu_event_user,type_send_reward;
        CardView card_view_user_order;
        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_image = (ImageView)itemView.findViewById(R.id.iv_image);
            tv_event_name_menu_event_user = (TextView)itemView.findViewById(R.id.tv_event_name_menu_event_user);
            tv_producer_name_menu_event_user = (TextView)itemView.findViewById(R.id.tv_producer_name_menu_event_user);
            type_send_reward = (TextView)itemView.findViewById(R.id.type_send_reward);
            card_view_user_order = (CardView)itemView.findViewById(R.id.card_view_user_order);
        }
    }
}