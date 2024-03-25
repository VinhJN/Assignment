package com.vinhbqph33437.assignment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edName, edEmail,  edPass, edRePass;
    Button btnRegister, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        edName = findViewById(R.id.edNameRegister);
        edEmail = findViewById(R.id.edEmailRegister);
        edPass = findViewById(R.id.edPasswordRegister);
        edRePass = findViewById(R.id.edRePasswordRegister);
        btnRegister = findViewById(R.id.btnSignUp);
        btnReturn = findViewById(R.id.btnReturn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPass.getText().toString().trim();
                String rePass = edRePass.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Chưa nhập tên. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Chưa nhập email. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Chưa nhập mật khẩu. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Độ dài mật khẩu ít nhất là 6 ký tự. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rePass)) {
                    Toast.makeText(RegisterActivity.this, "Chưa nhập lại mật khẩu. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(rePass)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu và nhập lại mật khẩu không trùng khớp. Mời nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("RegisterActivity", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RegisterActivity.this, "Account created for " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {
                                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}