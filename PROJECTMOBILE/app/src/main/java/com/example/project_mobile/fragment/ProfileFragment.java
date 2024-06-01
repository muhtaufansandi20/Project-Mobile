package com.example.project_mobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project_mobile.DatabaseHelper;
import com.example.project_mobile.MainActivityLogin;
import com.example.project_mobile.R;
import com.example.project_mobile.SharedPreferencesUtils;

public class ProfileFragment extends Fragment {

    private SharedPreferencesUtils sharedPreferencesUtil;
    private TextView usernameTextView;
    private TextView emailTextView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferencesUtil = new SharedPreferencesUtils(getActivity());
        usernameTextView = view.findViewById(R.id.text_username);
        emailTextView = view.findViewById(R.id.text_email);
        progressBar = view.findViewById(R.id.progressBar);

        loadProfileData();

        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesUtil.clearLoginInfo();
                Intent intent = new Intent(getActivity(), MainActivityLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void loadProfileData() {
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("usernamelog", " ");
        String email = sharedPreferences.getString("email", "");

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Set the TextViews with the retrieved data
                        usernameTextView.setText(username);
                        emailTextView.setText(email);

                        // Hide the ProgressBar
                        progressBar.setVisibility(View.GONE);
                    }
                }, 1000);
    }
}