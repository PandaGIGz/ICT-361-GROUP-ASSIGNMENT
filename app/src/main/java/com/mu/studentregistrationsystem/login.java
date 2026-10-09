package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mu.studentregistrationsystem.network.ApiClient;
import com.mu.studentregistrationsystem.network.models.ApiResponse;
import com.mu.studentregistrationsystem.network.models.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private EditText etIdentity, etPassword;
    private TextView tvForgotPassword;
    private Button btnLogin, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind Views
        etIdentity = findViewById(R.id.etIdentity);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Forgot Password Click Handler
        if (tvForgotPassword != null) {
            tvForgotPassword.setOnClickListener(v -> {
                Intent intent = new Intent(login.this, forgotPassword.class);
                startActivity(intent);
            });
        }

        // Create Account Click Handler
        if (btnCreateAccount != null) {
            btnCreateAccount.setOnClickListener(v -> {
                Intent intent = new Intent(login.this, creatAccount.class);
                startActivity(intent);
            });
        }

        // Login Button Click Handler
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> handleLogin());
        }
    }

    private void handleLogin() {
        String identity = etIdentity != null ? etIdentity.getText().toString().trim() : "";
        String password = etPassword != null ? etPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(identity)) {
            if (etIdentity != null) etIdentity.setError("Please enter your email or ID");
            Toast.makeText(this, "Email or ID is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            if (etPassword != null) etPassword.setError("Please enter your password");
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");

        ApiClient.getApiService().login(new LoginRequest(identity, password)).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                btnLogin.setEnabled(true);
                btnLogin.setText("Log In");

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    Toast.makeText(login.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                }

                boolean isLecturer = identity.toLowerCase().contains("lecturer") || identity.toLowerCase().startsWith("lec");
                Intent intent = new Intent(login.this, isLecturer ? LecturerHomeActivity.class : StudentHomeActivity.class);
                intent.putExtra("USER_NAME", identity);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                btnLogin.setText("Log In");
                Toast.makeText(login.this, "Welcome back, " + identity + "!", Toast.LENGTH_SHORT).show();

                boolean isLecturer = identity.toLowerCase().contains("lecturer") || identity.toLowerCase().startsWith("lec");
                Intent intent = new Intent(login.this, isLecturer ? LecturerHomeActivity.class : StudentHomeActivity.class);
                intent.putExtra("USER_NAME", identity);
                startActivity(intent);
                finish();
            }
        });
    }
}
