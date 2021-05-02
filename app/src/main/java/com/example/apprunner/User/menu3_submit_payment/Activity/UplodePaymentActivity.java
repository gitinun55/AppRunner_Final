package com.example.apprunner.User.menu3_submit_payment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.DB.UploadPaymentDB;
import com.example.apprunner.User.menu1_home.Activity.SecondActivity;
import com.example.apprunner.User.menu3_submit_payment.Freagment.SelectPaymentUserFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UplodePaymentActivity extends AppCompatActivity {

    TextView text_name_event,tv_date;
    String name_event;
    Button btn_upload_image_payment,btn_pickDate_payment,btn_submit;
    private final int PICK_IMAGE_REQUEST = 0;
    private Uri filePath;
    ImageView image_payment;
    String photoStringLink,first_name,last_name,type,date,time,bank,detail_cancel;
    int id_user,id_add;
    StorageReference ref;
    StorageReference storageReference;
    Task<Uri> downloadUri;
    EditText text_time;
    Spinner edt_bank;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplode_payment);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        text_name_event = (TextView) findViewById(R.id.name_event);
        name_event = getIntent().getExtras().getString("name_event");
        id_user = getIntent().getExtras().getInt("id_user");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_add = getIntent().getExtras().getInt("id_add");

        text_name_event.setText(name_event);
        image_payment = findViewById(R.id.image_payment);


        btn_upload_image_payment = findViewById(R.id.btn_upload_image_payment);
        btn_upload_image_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        tv_date = findViewById(R.id.tv_date);
        btn_pickDate_payment = findViewById(R.id.btn_pickDate_payment);
        btn_pickDate_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picdate();
            }
        });

        edt_bank = findViewById(R.id.edt_bank);
        text_time = findViewById(R.id.text_time);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = tv_date.getText().toString();
                time = text_time.getText().toString();
                bank = edt_bank.getSelectedItem().toString();
                showAlertDialog(view);
            }
        });
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันข้อมูล");
        alert.setMessage("คุณต้องการยืนยันหลักฐานการชำระใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(validate(id_user,id_add,date,time,bank)){
                    uploadImage(id_user,id_add,name_event,date,time,bank);
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

    private boolean validate(int id_user,int id_add,String date,String time,String bank){
        if(date == null || date.trim().length() == 0){
            Toast.makeText(UplodePaymentActivity.this,"กรุณาเลือกวันที่",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(time == null || time.trim().length() == 0){
            Toast.makeText(UplodePaymentActivity.this,"กรุณาเกรอกเวลา",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(bank.equals("เลือกธนาคาร")){
            Toast.makeText(UplodePaymentActivity.this,"กรุณาเลือกวธนาคาร",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void picdate(){
        Calendar calendar_start = Calendar.getInstance();
        final int year1 = calendar_start.get(Calendar.YEAR);
        final int month1 = calendar_start.get(Calendar.MONTH);
        final int day1 = calendar_start.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UplodePaymentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                month1 = month1+1;
                String date = day1 + "/" + month1 + "/" + year1;
                tv_date.setText(date);
            }
        },year1,month1,day1);
        datePickerDialog.show();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage(final int id_user,final int id_add,final String name_event,final String date,final String time,final String bank) {

        if(filePath == null){
            Toast.makeText(UplodePaymentActivity.this, "Empty image", Toast.LENGTH_SHORT).show();
        }

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading....");
            progressDialog.show();
            //** ชื่อ path ที่ส่งไป firebase
            ref = storageReference.child("images/" + UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                            progressDialog.dismiss();
                            downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    photoStringLink = uri.toString();
                                    Log.i("urlimage", photoStringLink);
                                    //Toast.makeText(MainActivityAddEvent.this, photoStringLink, Toast.LENGTH_SHORT).show();
                                    upload_payment(id_user,id_add,name_event,photoStringLink,date,time,bank,detail_cancel);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            //Toast.makeText(MainActivityAddEvent.this, "Failed " +e.getMessage() ,Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" +(int)progress+"%");
                            //Toast.makeText(MainActivityAddEvent.this, Double.toString(progress), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void upload_payment(final int id_user, final int id_add,String name_event, String photoStringLink, String date, String time, String bank, String detail_cancel){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.upload_payment(new UploadPaymentDB(id_user,id_add,name_event,first_name,last_name,photoStringLink,date,time,bank,detail_cancel));
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Intent intent = new Intent(UplodePaymentActivity.this, SecondActivity.class);
                Toast.makeText(UplodePaymentActivity.this,"อัปโหลดเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                intent.putExtra("first_name", first_name);
                intent.putExtra("last_name", last_name);
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                intent.putExtra("id_add", id_add);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            //Toast.makeText(MainActivityAddEvent.this, filePath.toString(), Toast.LENGTH_SHORT).show();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image_payment.setImageBitmap(bitmap);
                //Toast.makeText(MainActivityAddEvent.this,bitmap.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}