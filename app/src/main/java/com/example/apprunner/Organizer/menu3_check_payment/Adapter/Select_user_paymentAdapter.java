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
import android.widget.TextView;

import com.example.apprunner.Organizer.menu3_check_payment.Activity.DetailSubmitPaymentActivity;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

public class Select_user_paymentAdapter extends RecyclerView.Adapter<Select_user_paymentAdapter.Holder> {

    List<ResultQuery> eventList;
    Context context;
    int type_sub;


    public Select_user_paymentAdapter (Context context, List<ResultQuery> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_select_user_payment,parent,false);
        return new Select_user_paymentAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Intent intent = ((Activity) context).getIntent();
        final ResultQuery event = eventList.get(position);
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user_organizer = intent.getExtras().getInt("id_user");
        final int type_sub = event.getType_submit();
//        Toast.makeText(context,Integer.toString(type_sub),Toast.LENGTH_LONG).show();
        if(type_sub == 0){
            holder.id_user.setText(Integer.toString(event.getId()));
            holder.FName1.setText(event.getFirst_name() + "  " + event.getLast_name());
            holder.card_view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailSubmitPaymentActivity.class);
                    intent.putExtra("id_add", event.get_eventID());
                    intent.putExtra("first_name",first_name);
                    intent.putExtra("last_name", last_name);
                    intent.putExtra("id_user_organizer", id_user_organizer);
                    intent.putExtra("type", type);
                    intent.putExtra("id_user_run", event.getId());
                    intent.putExtra("name_event", event.getName_event());
                    intent.putExtra("id_reg_event", event.getId_reg_event());
                    intent.putExtra("id_history_payment", event.getId_history_payment());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView FName1,id_user;
        CardView card_view1;
        public Holder(@NonNull View itemView) {
            super(itemView);
            id_user = (TextView)itemView.findViewById(R.id.text_id_user);
            FName1 = (TextView)itemView.findViewById(R.id.FName1);
            card_view1 = itemView.findViewById(R.id.card_view1);
        }
    }
}