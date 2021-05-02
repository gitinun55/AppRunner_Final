package com.example.apprunner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprunner.Login.LoginActivity;
import com.example.apprunner.Login.RegisterappActivity;
import com.example.apprunner.User.menu1_home.Activity.SecondActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    EditText reset_email;
    Button send_password;
    String mail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        reset_email = (EditText) findViewById(R.id.reset_email);
        send_password = findViewById(R.id.send_password);


        firebaseAuth = FirebaseAuth.getInstance(); //need firebase authentication instance


        send_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = reset_email.getText().toString();
                //Toast.makeText(ForgotActivity.this,mail,Toast.LENGTH_LONG).show();
                firebaseAuth.sendPasswordResetEmail(mail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotActivity.this, "ระบบได้ทำการส่งอีเมล์ไปทางเมล์ของคุณแล้ว", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ForgotActivity.this, "ไม่พบอีเมล์ของคุณในระบบ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}