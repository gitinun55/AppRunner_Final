package com.example.apprunner.Organizer.menu5_update_reward.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu4_check_distance.Adapter.Event_distanceAdapter;
import com.example.apprunner.Organizer.menu5_update_reward.Adapter.ChooseUserOrderAdapter;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseUserRewardActivity extends AppCompatActivity {

    TextView text_show_NameEvent;
    int id_user,id_add;
    String name_event,first_name,last_name;
    RecyclerView recyclerview_user_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_reward);

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
        Call<List<ResultQuery>> call = services.show_user_send_reward(id_add);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recyclerview_user_order = (RecyclerView) findViewById(R.id.recycler_view_choose_user_reward);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChooseUserRewardActivity.this);
                recyclerview_user_order.setLayoutManager(layoutManager);
                ChooseUserOrderAdapter adapter = new ChooseUserOrderAdapter(ChooseUserRewardActivity.this, resultQueries);
                recyclerview_user_order.setAdapter(adapter);
                Toast.makeText(ChooseUserRewardActivity.this,"Pass",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(ChooseUserRewardActivity.this,"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }


}