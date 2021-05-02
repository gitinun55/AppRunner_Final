package com.example.apprunner.User.menu2_profile_event.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

public class ShowStatUserAdapter extends RecyclerView.Adapter<ShowStatUserAdapter.Holder> {

    List<ResultQuery> profile_stat;
    Context context;

    public ShowStatUserAdapter(Context context,List<ResultQuery> profile_stat) {
        this.context = context;
        this.profile_stat = profile_stat;
    }

    @NonNull
    @Override
    public ShowStatUserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_show_stat,parent,false);
        return new ShowStatUserAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowStatUserAdapter.Holder holder, int position) {
        final ResultQuery stat = profile_stat.get(position);
        holder.kg.setText(stat.getDistance());
        holder.cal.setText(stat.getCal());
        holder.pace.setText(stat.getPace());
    }

    @Override
    public int getItemCount() {
        return profile_stat.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView kg,cal,pace;
        public Holder(@NonNull View itemView) {
            super(itemView);
            kg = (TextView)itemView.findViewById(R.id.kg);
            cal = (TextView)itemView.findViewById(R.id.cal);
            pace = (TextView)itemView.findViewById(R.id.pace);
        }
    }
}