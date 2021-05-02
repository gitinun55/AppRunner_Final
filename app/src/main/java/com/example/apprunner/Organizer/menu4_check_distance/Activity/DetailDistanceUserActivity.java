package com.example.apprunner.Organizer.menu4_check_distance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu3_check_payment.Activity.DetailSubmitPaymentActivity;
import com.example.apprunner.Organizer.menu3_check_payment.Activity.Select_user_paymentActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;
import com.example.apprunner.User.menu2_profile_event.Activity.ShowDistanceEventActivity;
import com.example.apprunner.User.menu2_profile_event.Adapter.ShowStatUserAdapter;
import com.example.apprunner.addressQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailDistanceUserActivity extends AppCompatActivity {

    TextView id,event,FName,LName,distance_user,distance_event,type_tv,address;
    String name_event,first_name,last_name,type;
    int id_user,id_add,status_reward,id_organizer;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_distance_user);

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        id_user = getIntent().getExtras().getInt("id_user");
        id_organizer = getIntent().getExtras().getInt("id_organizer");
        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        status_reward = getIntent().getExtras().getInt("status_reward");
        type = getIntent().getExtras().getString("type");

        id = findViewById(R.id.id);
        event = findViewById(R.id.event);
        FName = findViewById(R.id.FName);
        LName = findViewById(R.id.LName);
        distance_user = findViewById(R.id.distance_user);
        distance_event = findViewById(R.id.distance_event);
        address = findViewById(R.id.address);
        type_tv = findViewById(R.id.type_tv);
        btn_submit = findViewById(R.id.btn_submit);
        if(status_reward == 0){
            type_tv.setText("รออนุมัติ");
        }
        if(status_reward == 1){
            type_tv.setText("อนุมัติ");
        }

        id.setText(Integer.toString(id_user));
        event.setText(name_event);
        FName.setText(first_name);
        LName.setText(last_name);

        showdetail_address();
        setDetailRegEvent();
        setProfileDistance();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(view);
            }
        });

    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันการอนุมัติระยะทางวิ่ง");
        alert.setMessage("คุณต้องการอนุมัติระยะทางวิ่งใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DetailDistanceUserActivity.this, "อนุมติสำเร็จ", Toast.LENGTH_SHORT).show();
                Submit_type();
            }
        });
        alert.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }

    private void Submit_type() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.submit_type_distance(id_add,id_user);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Intent intent = new Intent(DetailDistanceUserActivity.this, MainActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_organizer);
                startActivity(intent);
                Toast.makeText(DetailDistanceUserActivity.this,"อนุมัติแล้ว",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void showdetail_address() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<addressQuery> call = services.show_address(id_user,id_add);
        call.enqueue(new Callback<addressQuery>() {
            @Override
            public void onResponse(Call<addressQuery> call, Response<addressQuery> response) {
                addressQuery addressQuery = (addressQuery) response.body();
                address.setText(addressQuery.getAddress() + "\t\t" + addressQuery.getDistrict() + "\t\t" + addressQuery.getMueangDistrict()
                        + "\t\t" + addressQuery.getProvince() + "\t\t" + addressQuery.getCountry_number());
            }

            @Override
            public void onFailure(Call<addressQuery> call, Throwable t) {

            }
        });
    }

    private void setDetailRegEvent(){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.user_detail_reg_event(id_user,id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                String distance = resultQuery.getDistance();
                distance_event.setText(distance);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void setProfileDistance() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<List<ResultQuery>> call = services.show_stat_distance(id_user,id_add);
        call.enqueue(new Callback<List<ResultQuery>>() {
            @Override
            public void onResponse(Call<List<ResultQuery>> call, Response<List<ResultQuery>> response) {
                List<ResultQuery> resultQueries = (List<ResultQuery>) response.body();
                double x = 0.00;
                for(int i =0;i<resultQueries.size();i++){
                    String cal = resultQueries.get(i).getDistance();
                    Float y = Float.valueOf(cal);
                    x = x + y;
                    //Toast.makeText(ShowDistanceEventActivity.this,cal, Toast.LENGTH_SHORT).show();
                }
                distance_user.setText(String.format("%.2f",x));
            }

            @Override
            public void onFailure(Call<List<ResultQuery>> call, Throwable t) {
                Toast.makeText(DetailDistanceUserActivity.this,"ไม่พบสถิติวิ่งของคุณ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}