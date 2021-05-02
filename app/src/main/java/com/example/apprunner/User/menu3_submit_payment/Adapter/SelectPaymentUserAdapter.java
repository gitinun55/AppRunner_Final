package com.example.apprunner.User.menu3_submit_payment.Adapter;

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

import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu3_submit_payment.Activity.PaymentActivity;
import com.example.apprunner.User.menu3_submit_payment.Activity.UplodePaymentActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SelectPaymentUserAdapter extends RecyclerView.Adapter<SelectPaymentUserAdapter.Holder> {

    List<ResultQuery> eventList;
    Context context;

    public SelectPaymentUserAdapter(Context context,List<ResultQuery> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public SelectPaymentUserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_select_payment_user,parent,false);
        return new SelectPaymentUserAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectPaymentUserAdapter.Holder holder, int position) {
        final ResultQuery event = eventList.get(position);
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        Picasso.with(context).load(event.getPic_event()).into(holder.iv_image);
        holder.tv_event_name_menu_event_user.setText(event.getName_event());
        holder.tv_producer_name_menu_event_user.setText(event.getName_producer());
        holder.card_view_select_payment_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("name_event", event.getName_event());
                intent.putExtra("id_add", event.get_eventID());
                intent.putExtra("type",type);
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("id_user",id_user);
                intent.putExtra("id_reg_event",event.getId_reg_event());
                context.startActivity(intent);
            }
        });
        if(event.getType_submit() == 0){
            holder.type_submit.setText("รอการชำระ");
        }else if(event.getType_submit() == 1){
            holder.type_submit.setText("ชำระเงินแล้ว");
        }else if(event.getType_submit() == 2){
            holder.type_submit.setText("ชำระเงินใหม่");
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_event_name_menu_event_user,tv_producer_name_menu_event_user,type_submit;
        CardView card_view_select_payment_event;
        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_image = (ImageView)itemView.findViewById(R.id.iv_image);
            tv_event_name_menu_event_user = (TextView)itemView.findViewById(R.id.tv_event_name_menu_event_user);
            tv_producer_name_menu_event_user = (TextView)itemView.findViewById(R.id.tv_producer_name_menu_event_user);
            type_submit = (TextView)itemView.findViewById(R.id.type_submit);
            card_view_select_payment_event = itemView.findViewById(R.id.card_view_select_payment_event);
        }
    }
}