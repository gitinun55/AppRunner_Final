package com.example.apprunner.User.menu2_profile_event.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.DistanceAdapter;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu2_profile_event.Adapter.ShowStatUserAdapter;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowDistanceEventActivity extends AppCompatActivity {

    RecyclerView recycler_stat_user;
    String name_event,first_name,last_name,type;
    int id_user,id_add;
    TextView text_name_event,kg_event,cal_user,Km_user;
    DistanceAdapter adapter;
    SwipeRefreshLayout refreshlayout_detail_stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_distance_event);

        id_user = getIntent().getExtras().getInt("id_user");
        id_add = getIntent().getExtras().getInt("id_add");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        name_event = getIntent().getExtras().getString("name_event");

        text_name_event = (TextView) findViewById(R.id.text_name_event);
        text_name_event.setText(name_event);

        recycler_stat_user= findViewById(R.id.recycler_view);
        kg_event = findViewById(R.id.kg_event);
        cal_user = findViewById(R.id.cal_user);
        Km_user = findViewById(R.id.Km_user);

        refreshlayout_detail_stat = findViewById(R.id.refreshlayout_detail_stat);
        refreshlayout_detail_stat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecyclerView();
            }
        });
        setRecyclerView();
        setDetailRegEvent();
    }

    private void setDetailRegEvent(){
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.user_detail_reg_event(id_user,id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                String distance = resultQuery.getDistance();
                kg_event.setText(distance);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void setRecyclerView() {
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.show_stat_distance(id_user,id_add);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                refreshlayout_detail_stat.setRefreshing(false);
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                int x = 0;
                double total_distance = 0;
                for(int i =0;i<resultQueries.size();i++){
                    String cal = resultQueries.get(i).getCal();
                    String string_distance = resultQueries.get(i).getDistance();
                    float value_distance = Float.parseFloat(string_distance);
                    int y = Integer.valueOf(cal);
                    total_distance = total_distance + value_distance;
                    x = x + y;
                    //Toast.makeText(ShowDistanceEventActivity.this,cal, Toast.LENGTH_SHORT).show();
                }
                Km_user.setText(String.format("%.02f", total_distance));
                cal_user.setText(String.valueOf(x));
                recycler_stat_user = (RecyclerView) findViewById(R.id.recycler_stat_user);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShowDistanceEventActivity.this);
                recycler_stat_user.setLayoutManager(layoutManager);
                ShowStatUserAdapter adapter = new ShowStatUserAdapter(ShowDistanceEventActivity.this,resultQueries);
                recycler_stat_user.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(ShowDistanceEventActivity.this,"ไม่พบสถิติวิ่งของคุณ", Toast.LENGTH_SHORT).show();
            }
        });

    }

}