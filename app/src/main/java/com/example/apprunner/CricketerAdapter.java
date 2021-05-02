package com.example.apprunner;

import  android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CricketerAdapter extends RecyclerView.Adapter<CricketerAdapter.CricketerView> {

    ArrayList<Cricketer> cricketersList = new ArrayList<>();

    public CricketerAdapter(ArrayList<Cricketer> cricketersList) {
        this.cricketersList = cricketersList;
    }

    @NonNull
    @Override
    public CricketerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cricketer,parent,false);

        return new CricketerView(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull CricketerView holder, int position) {

        Cricketer cricketer = cricketersList.get(position);
        holder.textCricketerPrice.setText(cricketer.getPrice());
        holder.textPrice.setText(cricketer.getDistance());

    }

    @Override
    public int getItemCount() {
        return cricketersList.size();
    }

    public  class  CricketerView extends RecyclerView.ViewHolder{

        TextView textCricketerPrice,textPrice;
        public CricketerView(@NonNull View itemView) {
            super(itemView);

            textCricketerPrice = (TextView)itemView.findViewById(R.id.text_cricketer_price);
            textPrice = (TextView)itemView.findViewById(R.id.text_cricketer_price);
        }
    }
}
