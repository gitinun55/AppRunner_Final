package com.example.apprunner.Organizer.menu3_check_payment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu1_home.Fragment.MainFragment;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailSubmitPaymentActivity extends AppCompatActivity {

    String first_name,last_name,type,name_event;
    int id_user_run,id_add,id_user_organizer;
    TextView edt_FName,edt_LName,edt_date,edt_time,edt_bank;
    ImageView edt_Pic;
    Button btn_summit,btn_cancel;
    int id_reg_event,id_history_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_submit_payment);

        id_user_organizer = getIntent().getExtras().getInt("id_user_organizer");
        id_user_run = getIntent().getExtras().getInt("id_user_run");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        id_reg_event = getIntent().getExtras().getInt("id_reg_event");
        id_history_payment = getIntent().getExtras().getInt("id_history_payment");

        edt_FName = findViewById(R.id.edt_FName);
        edt_LName = findViewById(R.id.edt_LName);
        edt_date = findViewById(R.id.edt_date);
        edt_time = findViewById(R.id.edt_time);
        edt_bank = findViewById(R.id.edt_bank);
        edt_Pic = findViewById(R.id.edt_Pic);
        btn_summit = findViewById(R.id.btn_summit);
        btn_cancel = findViewById(R.id.btn_cancel);
//        Toast.makeText(DetailSubmitPaymentActivity.this, Integer.toString(id_history_payment), Toast.LENGTH_LONG).show();
        setName();
        setDetail_payment();
        getId_reg_event();

        btn_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog_submit(view);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSubmitPaymentActivity.this, DetailCancelActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user_organizer);
                intent.putExtra("id_add", id_add);
                intent.putExtra("name_event", name_event);
                intent.putExtra("id_history_payment",id_history_payment);
                startActivity(intent);
            }
        });
    }

    public void showAlertDialog_submit(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันข้อมูล");
        alert.setMessage("คุณต้องการยืนยันการอนุมัติการชำระใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                approve_history_payment(id_history_payment,id_reg_event);
            }
        });
        alert.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }

//    public void showAlertDialog_cancel(View view) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("ยืนยันข้อมูล");
//        alert.setMessage("คุณต้องการยืนยันการไม่อนุมัติการชำระใช่หรือไม่");
//        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                cancel_history_payment(id_history_payment,id_reg_event);
//            }
//        });
//        alert.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
//        alert.create().show();
//    }

//    private void cancel_payment(int id_reg_event) {
//        MainFragment mainFragament = new MainFragment();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(mainFragament.url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
//        Call<ResultQuery> call = service.cancel_payment(id_reg_event);
//        call.enqueue(new Callback<ResultQuery>() {
//            @Override
//            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
//                Intent intent = new Intent(DetailSubmitPaymentActivity.this, DetailCancelActivity.class);
//                intent.putExtra("first_name", first_name);
//                intent.putExtra("last_name", last_name);
//                intent.putExtra("type", type);
//                intent.putExtra("id_user", id_user_organizer);
//                intent.putExtra("id_add", id_add);
//                intent.putExtra("name_event", name_event);
//                startActivity(intent);
//              Toast.makeText(DetailSubmitPaymentActivity.this, "บันทึกข้อมูลสำเร็จ" , Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResultQuery> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void cancel_history_payment(int id_history_payment,final int id_reg_event) {
//        MainFragment mainFragament = new MainFragment();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(mainFragament.url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
//        Call<ResultQuery> call = service.cancel_history_payment(id_history_payment);
//        call.enqueue(new Callback<ResultQuery>() {
//            @Override
//            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
//                cancel_payment(id_reg_event);
//            }
//
//            @Override
//            public void onFailure(Call<ResultQuery> call, Throwable t) {
//
//            }
//        });
//    }

    private void approve_history_payment(int id_history_payment,final int id_reg_event){
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.approve_history_payment(id_history_payment);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                summit_payment(id_reg_event);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void summit_payment(int id_reg_event){
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.summit_payment(id_reg_event);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Intent intent = new Intent(DetailSubmitPaymentActivity.this, MainActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user_organizer);
                intent.putExtra("id_add", id_add);
                intent.putExtra("name_event", name_event);
                startActivity(intent);
                Toast.makeText(DetailSubmitPaymentActivity.this, "บันทึกข้อมูลสำเร็จ" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void setDetail_payment(){
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.show_history_user_payment(id_history_payment);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                edt_date.setText(resultQuery.getDate());
                edt_time.setText(resultQuery.getTime());
                edt_bank.setText(resultQuery.getBank());
                Picasso.with(DetailSubmitPaymentActivity.this).load(resultQuery.getImage_link()).into(edt_Pic);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void getId_reg_event(){
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.getId_reg_event(id_user_run,id_add);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                id_reg_event = resultQuery.getId_reg_event();
                //Toast.makeText(DetailSubmitPaymentActivity.this,Integer.toString(id_reg_event),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void setName(){
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.setName(id_user_run);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                edt_FName.setText(resultQuery.getFirst_name());
                edt_LName.setText(resultQuery.getLast_name());
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }
}