package com.example.apprunner.Organizer.menu1_home.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprunner.Main.DetailEventUserActivity;
import com.example.apprunner.R;
import com.example.apprunner.UserListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerAdapter_event extends RecyclerView.Adapter<MyRecyclerAdapter_event.Holder> {

    private List<UserListResponse> mDataSet;
    private Context context;

    public MyRecyclerAdapter_event(Context context, List<UserListResponse> mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter_event.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_event, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter_event.Holder holder, int position) {
        final Intent intent = ((Activity) context).getIntent();
        final String type = intent.getExtras().getString("type");
        final String first_name = intent.getExtras().getString("first_name");
        final String last_name = intent.getExtras().getString("last_name");
        final int id_user = intent.getExtras().getInt("id_user");
        final UserListResponse userListResponse = mDataSet.get(position);
        Picasso.with(context).load(userListResponse.getPic_event()).into(holder.iv_image);
        holder.tv_event_name.setText(userListResponse.getName_event());
        holder.tv_producer_name.setText(userListResponse.getName_producer());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailEventUserActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("name_event", userListResponse.getName_event());
                intent.putExtra("name_producer", userListResponse.getName_producer());
                intent.putExtra("date_reg_start", userListResponse.date_reg_start());
                intent.putExtra("date_reg_end", userListResponse.date_reg_end());
                intent.putExtra("detail", userListResponse.getDetail());
                intent.putExtra("pic_event", userListResponse.getPic_event());
                intent.putExtra("id_add", userListResponse.get_eventID());
                intent.putExtra("first_name",first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("id_user", id_user);
                intent.putExtra("num_register", userListResponse.getNum_register());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_event_name,tv_producer_name;
        ConstraintLayout mainLayout;
        CardView card_view;
        ImageView iv_image;
        public Holder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_event_name = itemView.findViewById(R.id.tv_event_name);
            tv_producer_name = itemView.findViewById(R.id.tv_producer_name);
            mainLayout = itemView.findViewById(R.id.recycler_view);
            mainLayout = itemView.findViewById(R.id.recycler_view);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
