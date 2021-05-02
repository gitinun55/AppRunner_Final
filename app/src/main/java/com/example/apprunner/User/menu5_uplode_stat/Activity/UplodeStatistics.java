package com.example.apprunner.User.menu5_uplode_stat.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.Organizer.menu3_check_payment.Activity.DetailSubmitPaymentActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.DB.UplodeStatisticsDB;
import com.example.apprunner.User.menu1_home.Activity.SecondActivity;
import com.example.apprunner.User.menu1_home.Fragment.SecondFragment;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UplodeStatistics extends AppCompatActivity {

    TextView mResultEt,result_distance,result_date;
    EditText result_cal,result_pace;
    ImageView mPreviewIv;
    Button btn_summit,btn_date;
    String first_name,last_name,type,name_event,date,cal,distance,pace;
    int id_user,id_add;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Float num,result;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplode_statistics);

        mResultEt = findViewById(R.id.result_distance);
        mPreviewIv = findViewById(R.id.imageIv);
        result_date = findViewById(R.id.result_date);
        result_distance = findViewById(R.id.result_distance);
        result_cal = findViewById(R.id.result_cal);
        result_pace = findViewById(R.id.result_pace);
        btn_date = findViewById(R.id.btn_date);

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_user = getIntent().getExtras().getInt("id_user");
        id_add = getIntent().getExtras().getInt("id_add");
        name_event = getIntent().getExtras().getString("name_event");

        //camera permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        Calendar calendar_bd = Calendar.getInstance();
        final int year = calendar_bd.get(Calendar.YEAR);
        final int month = calendar_bd.get(Calendar.MONTH);
        final int day = calendar_bd.get(Calendar.DAY_OF_MONTH);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UplodeStatistics.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day + "/" + month + "/" + year;
                        result_date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btn_summit = findViewById(R.id.btn_summit);
        btn_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = result_date.getText().toString();
                distance = result_distance.getText().toString();
                cal = result_cal.getText().toString();
                pace = result_pace.getText().toString();
                showAlertDialog(view);
            }
        });

    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("ยืนยันข้อมูล");
        alert.setMessage("คุณต้องการยืนยันสถิติระยะทางวิ่งใช่หรือไม่");
        alert.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(validate(result_date,result_distance,result_cal,result_pace)){
                    UploadStatic();
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

    public void UploadStatic(){
        SecondFragment secondFragment = new SecondFragment();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(secondFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.UploadStatic(new UplodeStatisticsDB(id_user,id_add,date,Float.parseFloat(mResultEt.getText().toString()),cal,pace));
        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Bundle bundle = new Bundle();
                bundle.putString("first_name", first_name);
                bundle.putString("last_name",last_name);
                bundle.putString("type",type);
                bundle.putInt("id_user",id_user);
                Toast.makeText(UplodeStatistics.this,"อัปโหลดสำเร็จ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {

            }
        });
    }

    private boolean validate(TextView result_date,TextView result_distance,EditText result_cal,EditText result_pace){
        if(result_date == null || result_date.getText().length() == 0){
            Toast.makeText(UplodeStatistics.this,"กรุณากรอกวันของคุณ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(result_distance == null || result_distance.getText().length() == 0){
            Toast.makeText(UplodeStatistics.this,"กรุณาเอัปโหลดรูปภาพของคุณ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(result_cal == null || result_cal.getText().length() == 0){
            Toast.makeText(UplodeStatistics.this,"กรุณากรอกแคลลอรี่ของคุณ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(result_pace == null || result_pace.getText().length() == 0){
            Toast.makeText(UplodeStatistics.this,"กรุณากรอกค่าเฉลี่ยเฑซของคุณ",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_uplodeimage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //hand menu item clicks
        int id = item.getItemId();

        if (id == R.id.addImage) {
            //do your fuction here
            showImageImportDialog();
            Toast.makeText(this, "เลือกการอัปโหลดสถิติ", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        //items to display in dialog
        String[] items = {" Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //set title
        dialog.setTitle("เลือกรูปภาพ");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // camera option clicked
                    if(!checkCameraPermission()) {
                        //Camera permission not allowed, request it
                        requestCameraPermissions();
                    }
                    else {
                        //permission allowed, take picture
                        pickCamera();
                    }
                }
                if (which == 1) {
                    // gallery option clicked
                    if(!checkStoragePermission()) {
                        //Storage camera permission not allowed, request it
                        requestStoragePermissions();
                    }
                    else {
                        //permission allowed, take picture
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show(); //show dialog
    }

    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermissions() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermissions() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result&&result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    }
                    else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    }
                    else {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK ) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image from gallery now crop it
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);  //activity:this
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image from camera now crop it
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }

        //get cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri(); //get image uri
                //set image to image view
                mPreviewIv.setImageURI(resultUri);
                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable)mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational()) {
                    Toast.makeText(this,"อัพโหลดใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //get text from sb  until there is no text
                    for (int i=0; i<items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                    }
                    //set text to edit text
                    String x = sb.toString();
                    try {
                        num = Float.parseFloat(x);
                        mResultEt.setText(String.format("%.2f",num));
                        //Toast.makeText(this,"Float", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this,"กรุณาอัปโหลดใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //if there is any error show it
                Exception error = result.getError();
                Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}