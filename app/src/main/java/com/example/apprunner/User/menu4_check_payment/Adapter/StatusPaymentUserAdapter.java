package com.example.apprunner.User.menu4_check_payment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprunner.User.menu4_check_payment.Activity.DetailPaymentUserActivity;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

public class StatusPaymentUserAdapter extends RecyclerView.Adapter<StatusPaymentUserAdapter.Holder> {

    private List<ResultQuery> mDataSet;
    private Context context;

    public StatusPaymentUserAdapter(Context context, List<ResultQuery> mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public StatusPaymentUserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_status_payment_user, null);
        return new StatusPaymentUserAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusPaymentUserAdapter.Holder holder, int position) {
        final Intent intent = ((Activity) context).getIntent();
        final ResultQuery event = mDataSet.get(position);
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        final ResultQuery resultQuery = mDataSet.get(position);
        holder.btn_detail_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, DetailPaymentUserActivity.class);
                intent.putExtra("id_add", event.get_eventID());
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                intent.putExtra("name_event",event.getName_event());
                intent.putExtra("date",event.getDate());
                intent.putExtra("time",event.getTime());
                intent.putExtra("bank",event.getBank());
                intent.putExtra("id_history_payment",event.getId_history_payment());
                intent.putExtra("image_link",event.getImage_link());
                context.startActivity(intent);
            }
        });
        holder.name_event_tv.setText(resultQuery.getName_event());
        if(resultQuery.getType_submit() == 0){
            holder.status_tv.setText("รออนุมัติ");
        }else if(resultQuery.getType_submit() == 1){
            holder.status_tv.setText("อนุมัติแล้ว");
        }else if(resultQuery.getType_submit() == 2){
            holder.status_tv.setText("ไม่อนุมัติ");
    }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name_event_tv,status_tv;
        Button btn_detail_payment;
        public Holder(@NonNull View itemView) {
            super(itemView);
            btn_detail_payment = (Button) itemView.findViewById(R.id.btn_detail_payment);
            name_event_tv = (TextView) itemView.findViewById(R.id.name_event_tv);
            status_tv = (TextView) itemView.findViewById(R.id.status_tv);
        }
    }
}
