package com.example.apprunner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectUserUplodeActivity extends AppCompatActivity {
    String first_name,last_name,type,name_event;
    int id_user,id_add;
    TextView name_event_textview;
    RecyclerView reclerview_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_uplode);

        id_user = getIntent().getExtras().getInt("id_user");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");

        name_event_textview = findViewById(R.id.name_event_textview);
        name_event_textview.setText(name_event);

        Detail_Reg_Event();
        Refresh();
    }

    private void Detail_Reg_Event() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.event_reg_list(id_user,id_add);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                //Toast.makeText(Event_Profile.this,Integer.toString(resultQueries.size()),Toast.LENGTH_SHORT).show();
                reclerview_user = (RecyclerView) findViewById(R.id.recycler_view_user_upload_list);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SelectUserUplodeActivity.this,3);
                reclerview_user.setLayoutManager(layoutManager);
                RegEventAdapterUser adapter = new RegEventAdapterUser(SelectUserUplodeActivity.this,resultQueries);
                reclerview_user.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {

            }
        });
    }

    public void Refresh(){
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}