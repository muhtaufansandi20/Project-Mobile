package com.example.project_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityRegister extends AppCompatActivity {

    private EditText etNama, etEmail, etPassword;
    private Button btnRegister;
    private DatabaseHelper databaseHelper;
    private SharedPreferencesUtils sharedPreferencesUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        EdgeToEdge.enable(this);

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferencesUtil = new SharedPreferencesUtils(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!nama.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (!databaseHelper.checkUserExists(email)) {
                        databaseHelper.addLogin(nama, email, password);
                        sharedPreferencesUtil.saveLoginInfo(nama, email, password);

                        Toast.makeText(MainActivityRegister.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivityRegister.this, MainActivityLogin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivityRegister.this, "Email sudah digunakan, silakan gunakan email lain", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivityRegister.this, "Harap isi semua bidang", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
