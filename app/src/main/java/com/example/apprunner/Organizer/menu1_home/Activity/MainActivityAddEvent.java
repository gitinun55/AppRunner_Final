package com.example.apprunner.Organizer.menu1_home.Activity;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Cricketer;
import com.example.apprunner.DB.CricketerDB;
import com.example.apprunner.DB.EventFormDB;
import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityAddEvent extends AppCompatActivity  {

    TextView date_start,date_end;
    Button pickDate_start,pickDate_end,buttonsubmit,buttonAdd,addpicture;
    ImageView imageView;
    DatePickerDialog.OnDateSetListener setListener;
    LinearLayout layoutlist;
    String first_name,last_name,type,ID_E;
    int id_user;
    Task<Uri> downloadUri;
    StorageReference ref;
    Uri downloadURL;
    FirebaseAuth mAuth;
    String photoStringLink;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;

    //Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;

    List<String> distanceList = new ArrayList<>();
    ArrayList<Cricketer> cricketersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_event);

        id_user = getIntent().getExtras().getInt("id_user");
        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");
        id_user = getIntent().getExtras().getInt("id_user");

        final MainActivity mainActivity = new MainActivity();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Firebase Init
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        date_start = findViewById(R.id.tv_date_start);
        pickDate_start = findViewById(R.id.btn_pickDate1);

        Calendar calendar_start = Calendar.getInstance();
        final int year1 = calendar_start.get(Calendar.YEAR);
        final int month1 = calendar_start.get(Calendar.MONTH);
        final int day1 = calendar_start.get(Calendar.DAY_OF_MONTH);

        pickDate_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivityAddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        month1 = month1+1;
                        String date = day1 + "/" + month1 + "/" + year1;
                        date_start.setText(date);
                    }
                },year1,month1,day1);
                datePickerDialog.show();
            }
        });

        date_end = findViewById(R.id.tv_date_end);
        pickDate_end = findViewById(R.id.btn_pickDate2);

        Calendar calendar_end = Calendar.getInstance();
        final int year2 = calendar_end.get(Calendar.YEAR);
        final int month2 = calendar_end.get(Calendar.MONTH);
        final int day2 = calendar_end.get(Calendar.DAY_OF_MONTH);

        pickDate_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivityAddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year2, int month2, int day2) {
                        month2 = month2+1;
                        String date = day2 + "/" + month2 + "/" + year2;
                        date_end.setText(date);
                    }
                },year2,month2,day2);
                datePickerDialog.show();
            }
        });

        //setButtonSubmit
        buttonsubmit = findViewById(R.id.btnRegEvent);
        layoutlist = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.button_add:
                        addView();
                        break;
                    case R.id.btnRegEvent:
                        if(checkIfValidAndRead()){
                            Intent intent = new Intent(MainActivityAddEvent.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("list",cricketersList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                }
            }
        });

        final EditText editorganizer = findViewById(R.id.userEDT);
        final EditText edittel = findViewById(R.id.telEdt);
        final EditText editevent = findViewById(R.id.eventEDT);
        final EditText editproducer = findViewById(R.id.producerEDT);
        final TextView textdatestart = findViewById(R.id.tv_date_start);
        final TextView textdateend = findViewById(R.id.tv_date_end);
        final EditText editobjective = findViewById(R.id.objectiveEDT);
        final EditText editdetail = findViewById(R.id.detailsEDT);
        final Spinner spinner_bank = findViewById(R.id.bank_spinner);
        final EditText editnumbank = findViewById(R.id.numberbank_EDT);

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String organizer = editorganizer.getText().toString();
                String tel = edittel.getText().toString();
                String event = editevent.getText().toString();
                String producer = editproducer.getText().toString();
                String date_start = textdatestart.getText().toString();
                String date_end = textdateend.getText().toString();
                String bank = spinner_bank.getSelectedItem().toString();
                String num_bank = editnumbank.getText().toString();
                String objective = editobjective.getText().toString();
                String detail = editdetail.getText().toString();
                if (validateAddevent(id_user,organizer ,tel, event, producer, date_start, date_end, bank, num_bank, objective, detail)) {
                    doAddevent(id_user ,editorganizer , edittel ,editevent ,editproducer ,textdatestart ,textdateend, spinner_bank ,editnumbank ,editobjective ,editdetail);
                }
            }

            private boolean validateAddevent(int id_user,String organizer,String tel , String event, String producer, String date_start, String date_end, String bank, String num_bank, String objective, String detail) {
                if(organizer == null || organizer.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this,"Organizer is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(event == null || event.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this, "Event is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(producer == null || producer.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this,"Producer is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(date_start.equals("dd / mm / yyyy")){
                    Toast.makeText(MainActivityAddEvent.this, "DateStart is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(date_end.equals("dd / mm / yyyy")){
                    Toast.makeText(MainActivityAddEvent.this,"DateEnd is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(objective == null || objective.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this, "Objective is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(detail == null || detail.trim().length() == 0){
                    Toast.makeText(MainActivityAddEvent.this,"Detail is required",Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            private void doAddevent(int id_user, EditText editorganizer, EditText edittel, final EditText editevent, final EditText editproducer, TextView textdatestart, TextView textdateend, Spinner spinner_bank , EditText editnumbank , EditText editobjective, EditText editdetail) {
                uploadImage(id_user, editorganizer, edittel, editevent, editproducer, textdatestart, textdateend, spinner_bank, editnumbank, editobjective, editdetail);
            }
        });


        //Int view
        addpicture = findViewById(R.id.btn_addPicture);
        imageView = findViewById(R.id.imgView);

        addpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

    }

    private void getIDEvent(EditText editevent, EditText editproducer) {
        final String name_event = editevent.getText().toString();
        final String producer = editproducer.getText().toString();
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);
        Call call = services.get_idEvent(name_event,producer);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                ResultQuery resultQuery = (ResultQuery) response.body();
                if(response.isSuccessful()){
                    ID_E = Integer.toString(resultQuery.get_eventID());
                    //ID_Event = resultQuery.get_eventID();
                    //Toast.makeText(MainActivityAddEvent.this,ID_E,Toast.LENGTH_LONG).show();
                    checkIfValidAndRead();
                    //Toast.makeText(MainActivityAddEvent.this,"Response",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MainActivityAddEvent.this,"Fail",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadImage(final int id_user, final EditText editorganizer, final EditText edittel, final EditText editevent, final EditText editproducer, final TextView textdatestart, final TextView textdateend, final Spinner spinner_bank, final EditText editnumbank, final EditText editobjective, final EditText editdetail) {

        if(filePath == null){
            Toast.makeText(MainActivityAddEvent.this, "Empty image", Toast.LENGTH_SHORT).show();
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
                                    add_event(id_user, photoStringLink, editorganizer, edittel, editevent, editproducer, textdatestart, textdateend, spinner_bank, editnumbank, editobjective, editdetail);
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

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
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
                imageView.setImageBitmap(bitmap);
                //Toast.makeText(MainActivityAddEvent.this,bitmap.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean checkIfValidAndRead() {
        cricketersList.clear();
        boolean result = true;
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI services = retrofit.create(OrganizerAPI.class);

        for(int i=0;i<layoutlist.getChildCount();i++){

            View cricketerView = layoutlist.getChildAt(i);
            EditText editTextNameDistance = (EditText)cricketerView.findViewById(R.id.edt_namedistance);
            EditText editTextPrice = (EditText)cricketerView.findViewById(R.id.edt_price);
            EditText editTextDistance = (EditText)cricketerView.findViewById(R.id.edt_distance);
            EditText editTextNumRegister = (EditText)cricketerView.findViewById(R.id.edt_num_register);

            String name_distance = editTextNameDistance.getText().toString();
            String edt_price = editTextPrice.getText().toString();
            String edt_distance = editTextDistance.getText().toString();
            String edt_num_register = editTextNumRegister.getText().toString();
            int price = Integer.parseInt(edt_price);
            int distance = Integer.parseInt(edt_distance);
            int num_register = Integer.parseInt(edt_num_register);
            final EditText editevent = findViewById(R.id.eventEDT);

            Cricketer cricketer = new Cricketer(Integer.parseInt(ID_E), name_distance, distance, price, num_register);
            if(!editTextNameDistance.getText().toString().equals("")){
                cricketer.setNameDistance(editTextNameDistance.getText().toString());
                cricketer.setDistance(distance);
                cricketer.setPrice(price);
                cricketer.setNum_registere(num_register);
            }else {
                result = false;
                break;
            }

            cricketersList.add(cricketer);
            Call<Cricketer> call = services.insertDistance(new CricketerDB(Integer.parseInt(ID_E),editevent.getText().toString(),cricketersList.get(i).getNameDistance(),cricketersList.get(i).getDistance(),cricketersList.get(i).getPrice(),cricketersList.get(i).getNum_register()));
            //Toast.makeText(MainActivityAddEvent.this,ID_E + " " +cricketersList.get(i).getPrice() + " "+cricketersList.get(i).getDistance(),Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivityAddEvent.this,cricketersList.get(i).getDistance(),Toast.LENGTH_LONG).show();
            call.enqueue(new Callback<Cricketer>() {
                @Override
                public void onResponse(Call<Cricketer> call, Response<Cricketer> response) {
                    //Toast.makeText(MainActivityAddEvent.this,"distance success",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Cricketer> call, Throwable t) {

                }
            });

        }

        if(cricketersList.size()==0){
            result = false;
            Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private void addView() {
        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_event,null,false);
        EditText editTextNameDistance = (EditText)cricketerView.findViewById(R.id.edt_namedistance);
        EditText editTextPrice = (EditText)cricketerView.findViewById(R.id.edt_price);
        EditText editTextDistance = (EditText)cricketerView.findViewById(R.id.edt_distance);
        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(cricketerView);
            }
        });
        layoutlist.addView(cricketerView);
    }

    private void removeView(View view){
        layoutlist.removeView(view);
    }

    public void add_event(int id_user, String photoStringLink, EditText editorganizer, EditText edittel, final EditText editevent, final EditText editproducer, TextView textdatestart, TextView textdateend, Spinner spinner_bank, EditText editnumbank, EditText editobjective, EditText editdetail){
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call<ResultQuery> call = service.insertEvent(new EventFormDB(
                id_user,
                photoStringLink,
                editorganizer.getText().toString(),
                edittel.getText().toString(),
                editevent.getText().toString(),
                editproducer.getText().toString(),
                textdatestart.getText().toString(),
                textdateend.getText().toString(),
                spinner_bank.getSelectedItem().toString(),
                editnumbank.getText().toString(),
                editobjective.getText().toString(),
                editdetail.getText().toString()));

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                Toast.makeText(MainActivityAddEvent.this,"ลงทะเบียนเสร็จสิ้นโปรดรอแอดมินอนุมัติ",Toast.LENGTH_LONG).show();
                getIDEvent(editevent,editproducer);
                openMainActiviry();
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Toast.makeText(MainActivityAddEvent.this,"ข้อมูลของท่านยังกรอกไม่ครบกรุณาลองใหม่อีกครั้ง",Toast.LENGTH_LONG).show();
            }
        });
    }
    //intent
    public void openMainActiviry(){
        Intent intent = new Intent(MainActivityAddEvent.this, MainActivity.class);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name", last_name);
        intent.putExtra("type", type);
        intent.putExtra("id_user", id_user);
        startActivity(intent);
    }
}
