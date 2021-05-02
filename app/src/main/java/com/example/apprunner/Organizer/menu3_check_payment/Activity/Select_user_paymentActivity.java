package com.example.apprunner.Organizer.menu3_check_payment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.Organizer.menu3_check_payment.Adapter.Select_user_paymentAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Select_user_paymentActivity extends AppCompatActivity {

    String first_name,last_name,type,name_event;
    int id_user,id_add;
    TextView name_event_textview;
    RecyclerView reclerview_user_payment;
    int type_submit,id_user_run;
    SwipeRefreshLayout refreshlayout_user_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_payment);

        id_user = getIntent().getExtras().getInt("id_user");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");

        name_event_textview = findViewById(R.id.name_event_textview);
        name_event_textview.setText(name_event);

        refreshlayout_user_payment = findViewById(R.id.refreshlayout_user_payment);
        refreshlayout_user_payment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                User_payment();
            }
        });
        User_payment();
    }

    private void getName_user(){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.check_send_submit(id_add);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                refreshlayout_user_payment.setRefreshing(false);
                List<ResultQuery> resultQuery = (List<ResultQuery>) response.body();
                for(int i=0;i < resultQuery.size();i++){
                    type_submit = resultQuery.get(i).getType_submit();
                    id_user_run = resultQuery.get(i).getId();
                    if(type_submit == 0){
                        //User_payment(id_user_run);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {

            }
        });

    }

    private void User_payment() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.list_user_reg_payment(id_add);
        //Toast.makeText(Select_user_paymentActivity.this,Integer.toString(id_add),Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                if(resultQueries == null || resultQueries.isEmpty()){
                    Toast.makeText(Select_user_paymentActivity.this, "ไม่พบ", Toast.LENGTH_SHORT).show();
                }else {
                    reclerview_user_payment = (RecyclerView) findViewById(R.id.recycler_view_user_payment);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Select_user_paymentActivity.this,3);
                    reclerview_user_payment.setLayoutManager(layoutManager);
                    Select_user_paymentAdapter adapter = new Select_user_paymentAdapter(Select_user_paymentActivity.this,resultQueries);
                    reclerview_user_payment.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(Select_user_paymentActivity.this, "ฐานข้อมูลผิดพลาด", Toast.LENGTH_SHORT).show();

            }
        });
    }

}