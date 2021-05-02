package com.example.apprunner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter_address  extends RecyclerView.Adapter<MyRecyclerAdapter_address.Holder> {

    private List<addressQuery> mDataSet;
    private Context context;

    public MyRecyclerAdapter_address(Context context, List<addressQuery> mDataSet){
        this.context = context;
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter_address.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter_address.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_address,tv_district,tv_province,tv_country,tv_fname,tv_lname,tv_Tel;
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
