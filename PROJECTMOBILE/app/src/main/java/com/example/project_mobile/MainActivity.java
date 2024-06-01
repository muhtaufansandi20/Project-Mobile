package com.example.project_mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_mobile.fragment.BacaFragment;
import com.example.project_mobile.fragment.HomeFragment;
import com.example.project_mobile.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferencesUtils sharedPreferencesUtil;
    private SharedPreferences sharedPreferences;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferencesUtil = new SharedPreferencesUtils(this);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.container, homeFragment);
                    fragmentTransaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.navigation_search) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    BacaFragment bacaNantiFragment = new BacaFragment();
                    fragmentTransaction.replace(R.id.container, bacaNantiFragment);
                    fragmentTransaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.navigation_profile) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ProfileFragment profileFragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.container, profileFragment);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferencesUtil.isLoggedIn();
        if (!isLoggedIn) {
            Intent intent = new Intent(this, MainActivityLogin.class);
            startActivity(intent);
            finish();
        } else {
            displayUserInfo();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.commit();
    }

    private void displayUserInfo() {
        Cursor cursor = databaseHelper.getAllLogins();

        if (cursor != null) {
            cursor.close();
        }


    }


}

