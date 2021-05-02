package com.example.apprunner.User.menu3_submit_payment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Cricketer;
import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu1_home.Fragment.MainFragment;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Activity.RegAddressActivity;
import com.example.apprunner.User.menu1_home.Activity.RegEventActivity;
import com.example.apprunner.User.menu1_home.Activity.SecondActivity;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {

    TextView bank, number_payment, number_price;
    int id_user,id_add,id_organizer,Distance,distance,id_reg_event;
    String first_name,last_name,type,name_event;
    Button btn_payment,btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        id_user = getIntent().getExtras().getInt("id_user");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_add = getIntent().getExtras().getInt("id_add");
        name_event = getIntent().getExtras().getString("name_event");
        id_reg_event = getIntent().getExtras().getInt("id_reg_event");
        //Toast.makeText(PaymentActivity.this,String.valueOf(id_reg_event),Toast.LENGTH_SHORT).show();


        bank = findViewById(R.id.bank);
        number_payment = findViewById(R.id.number_payment);
        number_price = findViewById(R.id.number_price);
        btn_payment = findViewById(R.id.btn_payment);
        btn_back = findViewById(R.id.btn_back);
        check_status_payment();
        Detail_payment();
        setPrice();

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, UplodePaymentActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                intent.putExtra("id_add", id_add);
                intent.putExtra("name_event", name_event);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, SecondActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });
    }

    private void Detail_payment() {
        MainActivity mainActivity = new  MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.show_payment(id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                bank.setText(resultQuery.getBank());
                number_payment.setText(resultQuery.getNum_bank());
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void setPrice(){
        MainActivity mainActivity = new  MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.getId_reg_event(id_user,id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                Distance = Integer.parseInt(resultQuery.getDistance());
                setDistance();
//                Toast.makeText(PaymentActivity.this, resultQuery.getDistance(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void setDistance(){
        MainActivity mainActivity = new  MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<List<Cricketer>> call = service.value_distance(id_add);
        call.enqueue(new Callback<List<Cricketer>>() {
            @Override
            public void onResponse(Call<List<Cricketer>> call, Response<List<Cricketer>> response) {
                List<Cricketer> cricketers = (List<Cricketer>) response.body();
                for(int i =0;i<cricketers.size();i++){
                    distance = cricketers.get(i).getdistance();
                    if(Distance == distance){
                        number_price.setText(Integer.toString(cricketers.get(i).getprice()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cricketer>> call, Throwable t) {

            }
        });
    }

    private void check_status_payment(){
        MainActivity mainActivity = new  MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.check_status_payment(id_reg_event);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = response.body();
                if(resultQuery.getType_submit() == 1){
                    btn_payment.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }
}