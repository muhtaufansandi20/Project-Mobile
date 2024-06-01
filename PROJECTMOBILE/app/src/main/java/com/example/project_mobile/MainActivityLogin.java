package com.example.project_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityLogin extends AppCompatActivity {

    private EditText etNama, etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper databaseHelper;
    private SharedPreferencesUtils sharedPreferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        EdgeToEdge.enable(this);

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferencesUtil = new SharedPreferencesUtils(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!nama.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (databaseHelper.isValidLogin(nama, email, password)) {
                        sharedPreferencesUtil.saveLoginInfo(nama, email, password);
                        Toast.makeText(MainActivityLogin.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = MainActivityLogin.this.getSharedPreferences("username", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("usernamelog",nama);
                        editor.putString("email",email);
                        editor.apply();
                        Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivityLogin.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivityLogin.this, "Harap isi semua bidang", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityLogin.this, MainActivityRegister.class);
                startActivity(intent);
            }
        });
    }
}
