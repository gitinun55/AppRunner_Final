package com.example.apprunner.Organizer.menu5_update_reward.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.DB.AddressDB;
import com.example.apprunner.DB.ProfileUserDB;
import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu4_check_distance.Activity.DetailDistanceUserActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.addressQuery;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertRewardActivity extends AppCompatActivity {

    int id_user,id_add,id_organizer,status_send;
    String name_event,first_name,last_name,type;
    TextView user,event,name,address;
    EditText send,num_order;
    Button btn_submit;
    String Address,District,MueangDistrict,province,Country_number,Name,tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_reward);

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        id_user = getIntent().getExtras().getInt("id_user");
        id_organizer = getIntent().getExtras().getInt("id_organizer");
        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        status_send = getIntent().getExtras().getInt("status_send");
        type = getIntent().getExtras().getString("type");

        user = findViewById(R.id.user);
        event = findViewById(R.id.event);
        name = findViewById(R.id.Name);
        address = findViewById(R.id.address);
        btn_submit = findViewById(R.id.btn_submit);
        send = findViewById(R.id.send);
        num_order = findViewById(R.id.num_order);

        user.setText(Integer.toString(id_user));
        event.setText(name_event);
        
        show_name();
        show_address();
        call_address();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(view);
            }
        });

    }
    public void showAlertDialog(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันข้อมูล");
        alert.setMessage("คุณต้องการยืนยันเลขพัสดุใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(InsertRewardActivity.this, "เพิ่มเลขพัสดุสำเร็จ", Toast.LENGTH_SHORT).show();
                Submit_type_send();
            }
        });
        alert.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }

    private void call_address(){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<addressQuery> call = services.show_address(id_add,id_user);
        call.enqueue(new Callback<addressQuery>() {
            @Override
            public void onResponse(Call<addressQuery> call, Response<addressQuery> response) {
                addressQuery resultQuery = (addressQuery) response.body();
                Address = resultQuery.getAddress();
                District = resultQuery.getAddress();
                MueangDistrict = resultQuery.getAddress();
                province = resultQuery.getAddress();
                Country_number = resultQuery.getAddress();
                Name = resultQuery.getAddress();
                tel = resultQuery.getAddress();
            }

            @Override
            public void onFailure(Call<addressQuery> call, Throwable t) {

            }
        });
    }

    private void insert_order() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.update_transport(id_user,id_add,new AddressDB(id_add,Address,District,MueangDistrict,province,Country_number,Name,tel,id_user,send.getText().toString(),num_order.getText().toString()));
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Toast.makeText(InsertRewardActivity.this,"Response",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void Submit_type_send() {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.submit_type_send(id_add,id_user);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                insert_order();
                Intent intent = new Intent(InsertRewardActivity.this, MainActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_organizer);
                startActivity(intent);
                Toast.makeText(InsertRewardActivity.this,"เพิ่มเลขพัสดุเรียบร้อย",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void show_address() {
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

    private void show_name() {
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
                name.setText(addressQuery.getName());
            }

            @Override
            public void onFailure(Call<addressQuery> call, Throwable t) {

            }
        });
    }
}