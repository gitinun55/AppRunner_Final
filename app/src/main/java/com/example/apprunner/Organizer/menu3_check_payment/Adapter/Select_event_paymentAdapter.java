package com.example.apprunner.Organizer.menu3_check_payment.Adapter;

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

import com.example.apprunner.Organizer.menu3_check_payment.Activity.Select_user_paymentActivity;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Select_event_paymentAdapter extends RecyclerView.Adapter<Select_event_paymentAdapter.Holder> {

    List<ResultQuery> event_organizer;
    Context context;

    public Select_event_paymentAdapter(Context context, List<ResultQuery> event_organizer) {
        this.context = context;
        this.event_organizer = event_organizer;
    }

    @NonNull
    @Override
    public Select_event_paymentAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_select_event_payment,parent, false);
        return new Select_event_paymentAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Select_event_paymentAdapter.Holder holder, int position) {
        final ResultQuery event = event_organizer.get(position);
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        Picasso.with(context).load(event.getPic_event()).into(holder.iv_image_menu_event_user);
        holder.tv_event_name_organizer.setText(event.getName_event());
        holder.tv_producer_name_organizer.setText(event.getName_producer());
        holder.card_view_payment_organizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, Select_user_paymentActivity.class);
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
        return event_organizer.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_event_name_organizer,tv_producer_name_organizer;
        CardView card_view_payment_organizer;
        ImageView iv_image_menu_event_user;
        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_image_menu_event_user = (ImageView)itemView.findViewById(R.id.iv_image_menu_event_user);
            tv_event_name_organizer = (TextView)itemView.findViewById(R.id.tv_event_name_organizer);
            tv_producer_name_organizer = (TextView)itemView.findViewById(R.id.tv_producer_name_organizer);
            card_view_payment_organizer = (CardView)itemView.findViewById(R.id.card_view_payment_organizer);
        }
    }
}