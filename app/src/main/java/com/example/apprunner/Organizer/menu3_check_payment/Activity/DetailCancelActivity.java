package com.example.apprunner.Organizer.menu3_check_payment.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apprunner.DB.history_cancelDB;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu1_home.Fragment.MainFragment;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailCancelActivity extends AppCompatActivity {

    EditText edt_detail;
    String first_name,last_name,type,name_event;
    int id_user_run,id_add,id_user_organizer,id_reg_event,id_history_payment;
    Button btn_submit_cancel_payment;
    String image_link,date,time,bank,first_name_run,last_name_run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cancel);

        id_user_organizer = getIntent().getExtras().getInt("id_user_organizer");
        id_user_run = getIntent().getExtras().getInt("id_user_run");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        id_reg_event = getIntent().getExtras().getInt("id_reg_event");
        id_history_payment = getIntent().getExtras().getInt("id_history_payment");

        Toast.makeText(DetailCancelActivity.this, Integer.toString(id_history_payment) , Toast.LENGTH_LONG).show();

        edt_detail = findViewById(R.id.edt_detail);
        btn_submit_cancel_payment = findViewById(R.id.btn_submit_cancel_payment);
        btn_submit_cancel_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DetailCancelActivity.this, "บันทึกข้อมูลสำเร็จ" , Toast.LENGTH_LONG).show();
                showAlertDialog_submit_cancel(view);
            }
        });
    }

    public void showAlertDialog_submit_cancel(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันข้อมูล");
        alert.setMessage("คุณต้องการยืนยันการไม่อนุมัติการชำระใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //insert_detail();
                call_detail_history(id_history_payment,id_reg_event);
                //Toast.makeText(DetailCancelActivity.this, "res alert" , Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }

    private void call_detail_history(final int id_history_payment, final int id_reg_event){
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
                ResultQuery resultQuery = response.body();
                first_name_run = resultQuery.getFirst_name();
                last_name_run = resultQuery.getLast_name();
                id_user_run = resultQuery.getId();
                image_link = resultQuery.getImage_link();
                date = resultQuery.getDate();
                time = resultQuery.getTime();
                bank = resultQuery.getBank();
                cancel_history_payment(id_history_payment,id_reg_event);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Toast.makeText(DetailCancelActivity.this, "fail call" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cancel_history_payment(int id_history_payment, final int id_reg_event) {
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.cancel_history_payment(id_history_payment,new history_cancelDB(id_user_run,id_add
        ,name_event,first_name_run,last_name_run,image_link,date,time,bank,2,edt_detail.getText().toString()));
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                cancel_payment(id_reg_event);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Toast.makeText(DetailCancelActivity.this, "fail cancel" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cancel_payment(int id_reg_event) {
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.cancel_payment(id_reg_event);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Intent intent = new Intent(DetailCancelActivity.this, MainActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user_organizer);
                intent.putExtra("id_add", id_add);
                intent.putExtra("name_event", name_event);
                startActivity(intent);
                Toast.makeText(DetailCancelActivity.this, "บันทึกข้อมูลสำเร็จ" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

}