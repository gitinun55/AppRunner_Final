package com.example.apprunner.Organizer.menu2_profile_event.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu2_profile_event.Adapter.ListNameRegEventAdapter;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListNameRegEventActivity extends AppCompatActivity {

    TextView text_show_NameEvent;
    String name_event,first_name,last_name;
    int id_user,id_add;
    RecyclerView recycleview_tableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_name_reg_event);

        text_show_NameEvent = (TextView) findViewById(R.id.text_show_NameEvent);

        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        id_user = getIntent().getExtras().getInt("id_user");

        text_show_NameEvent.setText(name_event);

        list_name_reg_event();
    }

    private void list_name_reg_event() {
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
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                recycleview_tableData = (RecyclerView) findViewById(R.id.recycler_view_tableData);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListNameRegEventActivity.this);
                recycleview_tableData.setLayoutManager(layoutManager);
                ListNameRegEventAdapter adapter = new ListNameRegEventAdapter(ListNameRegEventActivity.this, resultQueries);
                recycleview_tableData.setAdapter(adapter);
                Toast.makeText(ListNameRegEventActivity.this,"???????????????????????????????????????????????????????????????",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(ListNameRegEventActivity.this,"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
}