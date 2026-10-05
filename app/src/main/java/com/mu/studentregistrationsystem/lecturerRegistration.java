package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class lecturerRegistration extends AppCompatActivity {

    private TextInputEditText etFullName, etEmployeeNumber, etEmail, etDepartment, etPassword, etConfirmPassword;
    private MaterialButton btnCreateAccount;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lecturer_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFullName = findViewById(R.id.etFullName);
        etEmployeeNumber = findViewById(R.id.etEmployeeNumber);
        etEmail = findViewById(R.id.etEmail);
        etDepartment = findViewById(R.id.etDepartment);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvBack = findViewById(R.id.tvBack);

        btnCreateAccount.setOnClickListener(v -> {
            String fullName = etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
            String employeeNo = etEmployeeNumber.getText() != null ? etEmployeeNumber.getText().toString().trim() : "";
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String department = etDepartment.getText() != null ? etDepartment.getText().toString().trim() : "";
            String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
            String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

            if (fullName.isEmpty() || employeeNo.isEmpty() || email.isEmpty() || department.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(lecturerRegistration.this, lecturer_confirm.class);
            intent.putExtra("FULL_NAME", fullName);
            intent.putExtra("EMPLOYEE_NO", employeeNo);
            intent.putExtra("EMAIL", email);
            intent.putExtra("DEPARTMENT", department);
            intent.putExtra("PASSWORD", password);
            startActivity(intent);
        });

        if (tvBack != null) {
            tvBack.setOnClickListener(v -> finish());
        }
    }
}
