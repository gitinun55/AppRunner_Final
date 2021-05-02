package com.example.apprunner.Organizer.menu4_check_distance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu4_check_distance.Adapter.ChooseUserDistanceAdapter;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.UserListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseUserDistanceActivity extends AppCompatActivity {

    TextView text_show_NameEvent;
    String name_event,first_name,last_name;
    int id_user,id_add;
    double result_distance;
    int distance_reg,id_user_run;
    RecyclerView recyclerView_chooseuserdistance;
    List<ResultQuery> resultQueries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_distance);

        text_show_NameEvent = (TextView) findViewById(R.id.text_show_NameEvent);

        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        id_user = getIntent().getExtras().getInt("id_user");

        text_show_NameEvent.setText(name_event);

        Choose_user_distance();
    }

    private void Choose_user_distance() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.list_name_reg_event(id_add);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                resultQueries = (List<ResultQuery>) response.body();
                for(int i=0;i<resultQueries.size();i++){
                    id_user_run = resultQueries.get(i).getId();
                    distance_reg = Integer.parseInt(resultQueries.get(i).getDistance());
                    Cal_distance(id_user_run,id_add);
                }
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(ChooseUserDistanceActivity.this,"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Cal_distance(int id_user_run,int id_add){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<UserListResponse>> call = services.list_history_distance(id_user_run,id_add);
        call.enqueue(new Callback<List<UserListResponse>>() {
            @Override
            public void onResponse(Call<List<UserListResponse>> call, Response<List<UserListResponse>> response) {
                List<UserListResponse> resultQuery_distance = (List<UserListResponse>) response.body();
                result_distance = 0;
                ArrayList<Double> seriesOfNumbers = new ArrayList<Double>();
                for(int j=0;j<resultQuery_distance.size();j++){
                    Float history_distance = resultQuery_distance.get(j).getDistance();
                    result_distance = result_distance + history_distance;


                }
                if(result_distance >= distance_reg){
                    //Toast.makeText(ChooseUserDistanceActivity.this,String.format("%.2f", result_distance),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChooseUserDistanceActivity.this,Double.toString(distance_reg),Toast.LENGTH_SHORT).show();
                    recyclerView_chooseuserdistance = (RecyclerView) findViewById(R.id.recycler_view_choose_user_distance);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChooseUserDistanceActivity.this);
                    recyclerView_chooseuserdistance.setLayoutManager(layoutManager);
                    ChooseUserDistanceAdapter adapter = new ChooseUserDistanceAdapter(ChooseUserDistanceActivity.this,resultQueries);
                    recyclerView_chooseuserdistance.setAdapter(adapter);
                    //Toast.makeText(ChooseUserDistanceActivity.this,"success",Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(ChooseUserDistanceActivity.this,"fail",Toast.LENGTH_SHORT).show();
                }
                seriesOfNumbers.add(result_distance);
            }

            @Override
            public void onFailure(Call<List<UserListResponse>> call, Throwable t) {
                Toast.makeText(ChooseUserDistanceActivity.this,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
}