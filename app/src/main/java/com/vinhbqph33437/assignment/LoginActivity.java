package com.vinhbqph33437.assignment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edEmail,  edPass;
    Button btnLogin;
    TextView tvSignUp;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edEmail);
        edPass = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String pass = edPass.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Chưa nhập email. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "Chưa nhập mật khẩu. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }else {
                                Log.w(TAG, "signInWithEmail: failure",task.getException() );
                                Toast.makeText(LoginActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
        });
    }
}