package com.example.apprunner.User.menu1_home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apprunner.DB.AddressDB;
import com.example.apprunner.User.menu3_submit_payment.Activity.PaymentActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.addressQuery;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegAddressActivity extends AppCompatActivity {

    EditText edt_address,edt_district,edt_Mueang,edt_province,edt_country,edt_name,edt_Tel;
    Button btn_reg_address,btn_cancel;
    String address,district,Mueang,province,country,name,Tel,first_name,last_name,type,name_event;
    int id_user,id_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_address);

        edt_address = findViewById(R.id.edt_address);
        edt_district = findViewById(R.id.edt_district);
        edt_Mueang = findViewById(R.id.edt_Mueang);
        edt_province = findViewById(R.id.edt_province);
        edt_country = findViewById(R.id.edt_country_number);
        edt_name = findViewById(R.id.edt_name);
        edt_Tel = findViewById(R.id.edt_Tel);

        id_user = getIntent().getExtras().getInt("id_user");
        id_add = getIntent().getExtras().getInt("id_add");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        name_event = getIntent().getExtras().getString("name_event");
        check_address();
        btn_reg_address = findViewById(R.id.btn_reg_address);
        btn_reg_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = edt_address.getText().toString();
                district = edt_district.getText().toString();
                Mueang = edt_Mueang.getText().toString();
                province = edt_province.getText().toString();
                country = edt_country.getText().toString();
                name = edt_name.getText().toString();
                Tel = edt_Tel.getText().toString();
                showAlertDialog(view);
            }
        });

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActiviry();
            }
        });
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันการสมัคร");
        alert.setMessage("คุณต้องการยืนยันข้อมูลในการสมัครใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(validate(address,district,Mueang,province,country,name,Tel,id_user)){
                    RegAddress(address,district,Mueang,province,country,name,Tel,id_user);
                }
            }
        });
        alert.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    }

    private boolean validate(String address,String district,String Mueang,String province,String country,String name,String Tel,int id_user){
        if(address == null || address.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกชที่อยู่",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(district == null || district.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกตำบล",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Mueang == null || Mueang.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกเมือง",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(province == null || province.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกจังหวัด",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(country == null || country.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกประเทศ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name == null || name.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกชื่อนามสกุล",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Tel == null || Tel.trim().length() == 0){
            Toast.makeText(RegAddressActivity.this,"กรุณากรอกชเบอร์โทรศัพท์",Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }

    private void RegAddress(String address,String district,String Mueang,String province,String country,String name,String Tel,int id_user){
        MainActivity mainActivity = new MainActivity();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.insertAddress(new AddressDB(id_add,address,district,Mueang,province,country,name,Tel,id_user,null,null));
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                openPayment();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void check_address(){
        MainActivity mainActivity = new MainActivity();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<addressQuery> call = service.check_address(id_user);
        call.enqueue(new Callback<addressQuery>() {
            @Override
            public void onResponse(Call<addressQuery> call, Response<addressQuery> response) {
                addressQuery AddressQuery = response.body();
                edt_address.setText(AddressQuery.getAddress());
                edt_district.setText(AddressQuery.getDistrict());
                edt_Mueang.setText(AddressQuery.getMueangDistrict());
                edt_province.setText(AddressQuery.getProvince());
                edt_country.setText(AddressQuery.getCountry_number());
                edt_name.setText(AddressQuery.getName());
                edt_Tel.setText(AddressQuery.getTel());
            }

            @Override
            public void onFailure(Call<addressQuery> call, Throwable t) {

            }
        });
    }

    private void openPayment() {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("id_user", id_user);
        intent.putExtra("id_add", id_add);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name",last_name);
        intent.putExtra("type",type);
        intent.putExtra("name_event", name_event);
        startActivity(intent);
    }

    private void openMainActiviry() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("id_user", id_user);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name",last_name);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}