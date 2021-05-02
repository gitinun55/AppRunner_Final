package com.example.apprunner.User.menu4_check_payment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
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

public class DetailPaymentUserActivity extends AppCompatActivity {

    TextView show_name_event,show_bank,show_date,show_time,detail_cancel,text_detail_cancel;
    String first_name,last_name,type,name_event,date,bank,time;
    int id_user,id_add,id_history_payment;
    ImageView image_payment_detail;
//    ImageView image_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_payment_user);

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_user = getIntent().getExtras().getInt("id_user");
        id_add = getIntent().getExtras().getInt("id_add");
        name_event = getIntent().getExtras().getString("name_event");
        date = getIntent().getExtras().getString("date");
        bank = getIntent().getExtras().getString("bank");
        time = getIntent().getExtras().getString("time");
        id_history_payment = getIntent().getExtras().getInt("id_history_payment");

        show_name_event = findViewById(R.id.name_event);
        show_bank = findViewById(R.id.bank);
        show_date = findViewById(R.id.date);
        show_time = findViewById(R.id.time);
        detail_cancel = findViewById(R.id.detail_cancel);
        text_detail_cancel = findViewById(R.id.text_detail_cancel);
        image_payment_detail = findViewById(R.id.image_payment_detail);

        show_name_event.setText(name_event);

        Detail_payment();
    }

    private void Detail_payment() {
        MainFragment mainFragament = new MainFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainFragament.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.show_user_payment(id_history_payment);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                show_date.setText(resultQuery.getDate());
                show_time.setText(resultQuery.getTime());
                show_bank.setText(resultQuery.getBank());
                if (resultQuery.getType_submit() == 0){
                    text_detail_cancel.setVisibility(View.INVISIBLE);
                    detail_cancel.setVisibility(View.INVISIBLE);
                }else if (resultQuery.getType_submit() == 1){
                    text_detail_cancel.setVisibility(View.INVISIBLE);
                    detail_cancel.setVisibility(View.INVISIBLE);
                }else {
                    detail_cancel.setText(resultQuery.getDetail_cancel());
                }
                Picasso.with(DetailPaymentUserActivity.this).load(resultQuery.getImage_link()).into(image_payment_detail);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }
}