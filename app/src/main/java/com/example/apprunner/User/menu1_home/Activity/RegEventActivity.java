package com.example.apprunner.User.menu1_home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apprunner.Cricketer;
import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.DB.RegEventDB;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegEventActivity extends AppCompatActivity {
    EditText edt_fname,edt_lname,edt_tel,edt_idcard,edt_nationality,edt_emergency,edt_relationTel;
    Spinner edt_blood,edt_distance,edt_relation;
    Button btn_reg_event,btn_cancel;
    String tel,idcard,nationality,emergency,relationTel,name_event,name_producer;
    String edt_first_name,edt_last_name;
    String blood,distance,relation;
    int id_add,id_user;
    String pic_event;
    String first_name,last_name,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_event);

        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_tel = findViewById(R.id.edt_tel);
        edt_idcard = findViewById(R.id.edt_idcard);
        edt_nationality = findViewById(R.id.edt_nationality);
        edt_blood = findViewById(R.id.edt_blood);
        edt_distance = findViewById(R.id.edt_distance);
        edt_emergency = findViewById(R.id.edt_emergency);
        edt_relationTel = findViewById(R.id.edt_relationTel);
        edt_relation = findViewById(R.id.edt_relation);

        id_add = getIntent().getExtras().getInt("id_add");
        name_event = getIntent().getExtras().getString("name_event");
        name_producer = getIntent().getExtras().getString("name_producer");
        pic_event = getIntent().getExtras().getString("pic_event");
        //Toast.makeText(RegEventActivity.this,pic_event,Toast.LENGTH_SHORT).show();

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_user = getIntent().getExtras().getInt("id_user");
        profile();
        value_distance();
        btn_reg_event = (Button) findViewById(R.id.btn_reg_event);
        btn_reg_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_first_name = edt_fname.getText().toString();
                edt_last_name = edt_lname.getText().toString();
                tel = edt_tel.getText().toString();
                idcard = edt_idcard.getText().toString();
                nationality = edt_nationality.getText().toString();
                blood = edt_blood.getSelectedItem().toString();
                distance = edt_distance.getSelectedItem().toString();
                emergency = edt_emergency.getText().toString();
                relation = edt_relation.getSelectedItem().toString();
                relationTel = edt_relationTel.getText().toString();
                showAlertDialog(view);
            }
        });

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegEventActivity.this, SecondActivity.class);
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
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
                if(validate(id_user,edt_first_name,edt_last_name,tel,idcard,nationality,blood,distance,emergency,relation,relationTel)){
                    regEvent(id_user,edt_first_name,edt_last_name,tel,idcard,nationality,blood,distance,emergency,relation,relationTel);
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

    private boolean validate(int id_user,String edt_first_name,String edt_last_name,String tel,String idcard,String nationality,String blood,String distance,String emergency,String relation,String relationTel){
        if(edt_first_name == null || edt_first_name.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกชื่อนามสกุล",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edt_last_name == null || edt_last_name.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกชื่อนามสกุล",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tel == null || tel.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกเบอร์โทรศัพท์",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(idcard == null || idcard.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกเบอร์โทรศัพท์",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nationality == null || nationality.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกเสัญชาติ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(blood.equals("Choose blood")){
            Toast.makeText(RegEventActivity.this,"กรุณาเลือกกรุ๊ปเลือด",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(distance.equals("Choose KG")){
            Toast.makeText(RegEventActivity.this,"กรุณาเลือกระยะทางวิ่ง",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(emergency == null || emergency.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกชื่อนามสกุลบุคคลติดต่อฉุกเฉิน",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(relation.equals("ความสัมพันธ์")){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกความสัมพันธิ์",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(relationTel == null || relationTel.trim().length() == 0){
            Toast.makeText(RegEventActivity.this,"กรุณากรอกเบอร์ติดต่อบุคคลติดต่อฉุกเฉิน",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void regEvent(final int id_user,final String edt_first_name, final String edt_last_name, String tel, String idcard, String nationality, String blood, String distance, String emergency, String relation, String relationTel) {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.reg_event(new RegEventDB(id_user,edt_first_name,edt_last_name,tel,idcard,nationality,blood,distance,emergency,relation,relationTel,id_add,name_event,name_producer,pic_event));
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Check_address();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private void value_distance(){
        final Spinner spinner = findViewById(R.id.edt_distance);
        final List<Integer> cricketerList = new ArrayList<>();
        final ArrayAdapter<Integer> cricketerArrayAdapter = new ArrayAdapter<Integer>(this,   android.R.layout.simple_spinner_item, cricketerList);
        MainActivity mainActivity = new MainActivity();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<List<Cricketer>> call = service.value_distance(id_add);
        call.enqueue(new Callback<List<Cricketer>>() {
            @Override
            public void onResponse(Call<List<Cricketer>> call, Response<List<Cricketer>> response) {
                if(response.isSuccessful()){
                    for(Cricketer post : response.body()){
                        int value = post.getdistance();
                        //Toast.makeText(RegEventActivity.this,post.getNameDistance(),Toast.LENGTH_SHORT).show();
                        cricketerList.add(value);
                        cricketerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(cricketerArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cricketer>> call, Throwable t) {

            }
        });
    }

    public void profile(){
        SecondFragment secondFragment = new SecondFragment();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = services.profile_user(id_user);
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                edt_fname.setText(resultQuery.getFirst_name());
                edt_lname.setText(resultQuery.getLast_name());
                if(!resultQuery.getTel().isEmpty()){
                    edt_tel.setText(resultQuery.getTel());
                }
                if(!resultQuery.getId_card().isEmpty()){
                    edt_idcard.setText(resultQuery.getId_card());
                }


            }
            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });

    }

    private void Check_address(){
        Intent intent = new Intent(RegEventActivity.this, RegAddressActivity.class);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name", last_name);
        intent.putExtra("type", type);
        intent.putExtra("id_user", id_user);
        intent.putExtra("id_add", id_add);
        intent.putExtra("name_event", name_event);
        startActivity(intent);
    }
}