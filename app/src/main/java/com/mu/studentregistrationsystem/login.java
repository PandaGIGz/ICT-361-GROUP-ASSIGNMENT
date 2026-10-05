package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        // Validate login (simulated logic)
        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");

        btnLogin.postDelayed(() -> {
            Toast.makeText(login.this, "Welcome back, " + identity + "!", Toast.LENGTH_LONG).show();

            // Reset state
            btnLogin.setEnabled(true);
            btnLogin.setText("login");
        }, 1500);
    }
}
