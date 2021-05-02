package com.example.apprunner.Organizer.menu6_profile_organizer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.DB.ProfileUserDB;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Activity.SecondActivity;
import com.example.apprunner.User.menu6_profile_user.Activity.EditProfileUserActivity;
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

public class EditProfileOrganizerActivity extends AppCompatActivity {

    EditText FNameEdt,LNameEdt,TelEdt,IdEdt;
    TextView text_date;
    RadioButton selectedRadioButton;
    RadioGroup rd_gender;
    Button btn_addPicture,btn_bd,btn_submit;
    String gender,first_name,last_name,type;
    String photoStringLink;
    int id_user;
    Task<Uri> downloadUri;
    StorageReference ref;
    ImageView imageView;

    private  Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;

    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_organizer);

        id_user = getIntent().getExtras().getInt("id_user");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");

        final MainActivity mainActivity = new MainActivity();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView = findViewById(R.id.imgProfile);
        btn_addPicture = findViewById(R.id.btn_addPicture);
        FNameEdt = findViewById(R.id.FNameEdt);
        LNameEdt = findViewById(R.id.LNameEdt);
        TelEdt = findViewById(R.id.telEdt);
        IdEdt = findViewById(R.id.idEdt);
        text_date = findViewById(R.id.text_date);
        rd_gender = findViewById(R.id.rd_gender );
        btn_bd = findViewById(R.id.btn_date);
        btn_submit = findViewById(R.id.btn_edit_profile);

        btn_addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage_profile();
            }
        });

        Calendar calendar_bd = Calendar.getInstance();
        final int year = calendar_bd.get(Calendar.YEAR);
        final int month = calendar_bd.get(Calendar.MONTH);
        final int day = calendar_bd.get(Calendar.DAY_OF_MONTH);

        btn_bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditProfileOrganizerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day + "/" + month + "/" + year;
                        text_date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        rd_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedRadioButton  = (RadioButton)findViewById(rd_gender.getCheckedRadioButtonId());
                gender = selectedRadioButton.getText().toString();
                Toast.makeText(EditProfileOrganizerActivity.this, gender , Toast.LENGTH_LONG).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
                Intent intent = new Intent(EditProfileOrganizerActivity.this, MainActivity.class);
                intent.putExtra("first_name", FNameEdt.getText().toString());
                intent.putExtra("last_name", LNameEdt.getText().toString());
                intent.putExtra("type", type);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });
    }

    private void uploadImage() {

        if(filePath == null){
            Toast.makeText(EditProfileOrganizerActivity.this, "Empty image", Toast.LENGTH_SHORT).show();
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
                                    edt_profile();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            //Toast.makeText(MainActivityAddEvent.this, filePath.toString(), Toast.LENGTH_SHORT).show();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                //Toast.makeText(MainActivityAddEvent.this,bitmap.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void edt_profile() {
        MainActivity mainActivity = new MainActivity();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.edit_ProfileUser(id_user,new ProfileUserDB(photoStringLink,
                FNameEdt.getText().toString(),
                LNameEdt.getText().toString(),
                TelEdt.getText().toString(),
                IdEdt.getText().toString(),
                gender,
                text_date.getText().toString()));

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Toast.makeText(EditProfileOrganizerActivity.this,"แก้ไขข้อมูลสำเร็จ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Toast.makeText(EditProfileOrganizerActivity.this,"แก้ไขข้อมูลไม่สำเร็จ",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void chooseImage_profile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }
}