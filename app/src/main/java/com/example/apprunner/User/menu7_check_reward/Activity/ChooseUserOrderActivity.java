package com.example.apprunner.User.menu7_check_reward.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.addressQuery;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseUserOrderActivity extends AppCompatActivity {

    TextView text_show_NameEvent,Name,address,tel,transport,type;
    String name_event,first_name,last_name;
    int id_add,id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_order);

        text_show_NameEvent = (TextView) findViewById(R.id.text_show_NameEvent);
        Name = (TextView) findViewById(R.id.Name);
        address = (TextView) findViewById(R.id.address);
        tel = (TextView) findViewById(R.id.tel);
        transport = (TextView) findViewById(R.id.transport);
        type = (TextView) findViewById(R.id.type);


        name_event = getIntent().getExtras().getString("name_event");
        id_add = getIntent().getExtras().getInt("id_add");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        id_user = getIntent().getExtras().getInt("id_user");

        text_show_NameEvent.setText(name_event);

        view_user_reward();
    }

    private void view_user_reward() {
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
                Name.setText(addressQuery.getName());
                address.setText(addressQuery.getAddress() + "\t\t" + addressQuery.getDistrict() + "\t\t" + addressQuery.getMueangDistrict()
                        + "\t\t" + addressQuery.getProvince() + "\t\t" + addressQuery.getCountry_number());
                tel.setText(addressQuery.getTel());
                transport.setText(addressQuery.getTransport());
                type.setText(addressQuery.getPacel_number());
            }

            @Override
            public void onFailure(Call<addressQuery> call, Throwable t) {

            }
        });
    }
}