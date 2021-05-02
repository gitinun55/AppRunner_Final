package com.example.apprunner.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Cricketer;
import com.example.apprunner.Organizer.menu2_profile_event.Activity.ListNameRegEventActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu4_check_distance.Activity.ChooseUserDistanceActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Activity.RegEventActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailEventUserActivity extends AppCompatActivity {
    TextView tv_event_name,tv_date_start,tv_detail,tv_date_end,count_register;
    String name_event,detail,type,name_producer;
    String date_reg_start,date_reg_end,pic_event;
    Button btn_register;
    ImageView imageView_user;
    int id_add,id_user,num_register,result_register,count,id_event;
    String first_name,last_name;
    RecyclerView recyclerview_distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_user);

        imageView_user = (ImageView) findViewById(R.id.imageView_user);
        tv_event_name = (TextView) findViewById(R.id.tv_event_name_user);
        tv_date_start = (TextView) findViewById(R.id.tv_date_start_user);
        tv_date_end = (TextView) findViewById(R.id.tv_date_end_user);
        tv_detail = (TextView) findViewById(R.id.tv_detail_user);
        count_register = (TextView) findViewById(R.id.num_register);


        pic_event = getIntent().getExtras().getString("pic_event");
        name_event = getIntent().getExtras().getString("name_event");
        date_reg_start = getIntent().getExtras().getString("date_reg_start");
        date_reg_end = getIntent().getExtras().getString("date_reg_end");
        detail = getIntent().getExtras().getString("detail");
        type = getIntent().getExtras().getString("type");
        id_add = getIntent().getExtras().getInt("id_add");
        id_user = getIntent().getExtras().getInt("id_user");
        name_producer = getIntent().getExtras().getString("name_producer");
        num_register = getIntent().getExtras().getInt("num_register");

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        //Toast.makeText(DetailEventUserActivity.this,pic_event, Toast.LENGTH_SHORT).show();

        DetailDistanceEvent();

        Picasso.with(DetailEventUserActivity.this).load(pic_event).into(imageView_user);
        tv_event_name.setText(name_event);
        tv_date_start.setText(date_reg_start);
        tv_date_end.setText(date_reg_end);
        tv_detail.setText(detail);
        btn_register = (Button) findViewById(R.id.btn_register);
        if(type != null && type.equals("นักวิ่ง")){
            check_user_reg_event();
            btn_register.setText("สมัครงาน");
        }
        if(type != null && type.equals("ผู้จัดกิจกรรม")){
            check_organizer_event();
            btn_register.setText("รายชื่อนักวิ่ง");
        }
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null && type.equals("นักวิ่ง")) {
                    Intent intent = new Intent(DetailEventUserActivity.this, RegEventActivity.class);
                    intent.putExtra("id_user", id_user);
                    intent.putExtra("first_name", first_name);
                    intent.putExtra("last_name", last_name);
                    intent.putExtra("type", getIntent().getExtras().getString("type"));
                    intent.putExtra("pic_event", pic_event);
                    intent.putExtra("id_add", id_add);
                    intent.putExtra("name_event", name_event);
                    intent.putExtra("name_producer", name_producer);
                    startActivity(intent);
                }
                if (type != null && type.equals("ผู้จัดกิจกรรม")) {
                    Intent intent = new Intent(DetailEventUserActivity.this, ListNameRegEventActivity.class);
                    intent.putExtra("id_user", id_user);
                    intent.putExtra("first_name",first_name);
                    intent.putExtra("last_name",last_name);
                    intent.putExtra("type", type);
                    intent.putExtra("id_add", id_add);
                    intent.putExtra("name_event", name_event);
                    intent.putExtra("name_producer", name_producer);
                    startActivity(intent);
                }
            }
        });
    }

    private void check_user_reg_event(){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.check_user_reg_event(id_user,id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                btn_register.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void check_organizer_event(){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.check_organizer_event(id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                int id_organizer = resultQuery.getId();
                if(id_user != id_organizer){
                    btn_register.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void DetailDistanceEvent(){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<Cricketer>> call = services.value_distance(id_add);
        call.enqueue(new Callback<List<Cricketer>>() {
            @Override
            public void onResponse(Call<List<Cricketer>> call, Response<List<Cricketer>> response) {
                List<Cricketer> cricketers = (List<Cricketer>) response.body();
                //Toast.makeText(fragment_main.this, "Response_eventList" +"  "+ userListResponses.size(), Toast.LENGTH_SHORT).show();
                for(int i =0;i<cricketers.size();i++){
                    int num_register = cricketers.get(i).getnum_register();
                    result_register = result_register + num_register;
                }
                count_register.setText(Integer.toString(result_register));
                recyclerview_distance = (RecyclerView) findViewById(R.id.recyclerview_distance);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailEventUserActivity.this);
                recyclerview_distance.setLayoutManager(layoutManager);
                DetailDistanceAdapter adapter = new DetailDistanceAdapter(DetailEventUserActivity.this,cricketers);
                recyclerview_distance.setAdapter(adapter);
                Count_user_reg();
            }

            @Override
            public void onFailure(Call<List<Cricketer>> call, Throwable t) {

            }
        });
    }

    private void Count_user_reg(){
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
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                count = result_register - resultQueries.size();
                if(count == 0){
                    Toast.makeText(DetailEventUserActivity.this,"ผู้เข้าร่วมงานครบจำนวนที่จำกัดแล้ว" + Integer.toString(count),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailEventUserActivity.this,"เหลือจำนวนที่เปิดรับสมัครอีก" + Integer.toString(count) + "คน",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {

            }
        });
    }

}