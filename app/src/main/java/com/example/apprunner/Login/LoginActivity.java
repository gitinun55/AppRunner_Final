package com.example.apprunner.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.ForgotActivity;
import com.example.apprunner.Organizer.menu1_home.Activity.MainActivity;
import com.example.apprunner.OrganizerAPI;
import com.example.apprunner.R;
import com.example.apprunner.ResultQuery;
import com.example.apprunner.User.menu1_home.Activity.SecondActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

  EditText edtemail;
  EditText edtPassword;
  Button btnLogin,btnReg,btnForget;
  String email,password;
  FirebaseAuth firebaseAuth;
  TextView bt_forget;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    firebaseAuth = FirebaseAuth.getInstance(); //need firebase authentication instance

    edtemail = (EditText) findViewById(R.id.bt_email);
    edtPassword = (EditText) findViewById(R.id.bt_password);
    btnLogin = (Button) findViewById(R.id.bt_login);
    btnReg = (Button) findViewById(R.id.bt_reg);
    bt_forget = (TextView) findViewById(R.id.bt_forget);

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        email = edtemail.getText().toString();
        password = edtPassword.getText().toString();
        if(validateLogin(email, password)){
          //do login
          firebaseAuth.signInWithEmailAndPassword(email,password)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this,"รหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
                      } else {
                        doLogin(email);
                      }
                    }
                  });
        }
      }

      private boolean validateLogin(String email, String password) {
        if(email.isEmpty() || email.trim().length() == 0){
          Toast.makeText(LoginActivity.this,"กรุณากรอกอีเมลล์",Toast.LENGTH_SHORT).show();
          return false;
        }
        if(password.isEmpty() || password.trim().length() == 0){
          Toast.makeText(LoginActivity.this, "กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show();
          return false;
        }
        return true;
      }

      private void doLogin(String email) {
        MainActivity mainActivity = new MainActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mainActivity.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrganizerAPI service = retrofit.create(OrganizerAPI.class);
        Call call = service.login(email);
        call.enqueue(new Callback() {
          @Override
          public void onResponse(Call call, Response response) {
            // User is logged in
            if (response.isSuccessful()) {
              //.makeText(LoginActivity.this, "Response", Toast.LENGTH_SHORT).show();
              ResultQuery resultQuery = (ResultQuery) response.body();
              if (resultQuery.getType().equals("นักวิ่ง")) {
                Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
                intent.putExtra("id_user", resultQuery.getId());
                intent.putExtra("first_name", resultQuery.getFirst_name());
                intent.putExtra("last_name", resultQuery.getLast_name());
                intent.putExtra("type", resultQuery.getType());
                intent.putExtra("email", resultQuery.getEmail());
                intent.putExtra("password", resultQuery.getPassword());
                startActivity(intent);
              }
              if (resultQuery.getType().equals("ผู้จัดกิจกรรม")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id_user", resultQuery.getId());
                intent.putExtra("first_name", resultQuery.getFirst_name());
                intent.putExtra("last_name", resultQuery.getLast_name());
                intent.putExtra("type", resultQuery.getType());
                intent.putExtra("email", resultQuery.getEmail());
                intent.putExtra("password", resultQuery.getPassword());
                startActivity(intent);
              }
              Toast.makeText(LoginActivity.this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();
            }
          }
          @Override
          public void onFailure(Call call, Throwable t) {
            Toast.makeText(LoginActivity.this, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
          }
        });

      }

    });

    btnReg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openRegisterappActivity();
      }
    });

    bt_forget.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openForgotActivity();
      }
    });
  }

  private void openForgotActivity() {
    Intent intent = new Intent(this, ForgotActivity.class);
    startActivity(intent);
  }

  private void openRegisterappActivity() {
    Intent intent = new Intent(this, RegisterappActivity.class);
    startActivity(intent);
  }

}
