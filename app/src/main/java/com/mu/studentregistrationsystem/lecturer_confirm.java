package com.mu.studentregistrationsystem;



import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mu.studentregistrationsystem.network.ApiClient;
import com.mu.studentregistrationsystem.network.models.ApiResponse;
import com.mu.studentregistrationsystem.network.models.LecturerRegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lecturer_confirm extends AppCompatActivity {

    private TextInputEditText etFullName, etEmployeeNumber, etEmail, etDepartment, etPassword;
    private MaterialButton btnConfirmAccount;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_confirm);

        etFullName = findViewById(R.id.etFullName);
        etEmployeeNumber = findViewById(R.id.etEmployeeNumber);
        etEmail = findViewById(R.id.etEmail);
        etDepartment = findViewById(R.id.etDepartment);
        etPassword = findViewById(R.id.etPassword);
        btnConfirmAccount = findViewById(R.id.btnConfirmAccount);
        tvBack = findViewById(R.id.tvBack);

        // Retrieve inputs sent from MainActivity
        String fullName = getIntent().getStringExtra("FULL_NAME");
        String employeeNo = getIntent().getStringExtra("EMPLOYEE_NO");
        String email = getIntent().getStringExtra("EMAIL");
        String department = getIntent().getStringExtra("DEPARTMENT");
        String password = getIntent().getStringExtra("PASSWORD");

        // Display in read-only input fields
        etFullName.setText(fullName);
        etEmployeeNumber.setText(employeeNo);
        etEmail.setText(email);
        etDepartment.setText(department);
        etPassword.setText(password);

        btnConfirmAccount.setOnClickListener(v -> {
            btnConfirmAccount.setEnabled(false);
            btnConfirmAccount.setText("CONFIRMING...");

            LecturerRegisterRequest req =
                    new LecturerRegisterRequest(
                            fullName, employeeNo, email, department, password);

            ApiClient.getApiService().registerLecturer(req)
                    .enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call,
                                               Response<ApiResponse> response) {
                            btnConfirmAccount.setEnabled(true);
                            btnConfirmAccount.setText("CONFIRM & CREATE ACCOUNT");
                            Toast.makeText(lecturer_confirm.this, "Lecturer Account Created Successfully!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(lecturer_confirm.this, LecturerHomeActivity.class);
                            intent.putExtra("USER_NAME", fullName);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            btnConfirmAccount.setEnabled(true);
                            btnConfirmAccount.setText("CONFIRM & CREATE ACCOUNT");
                            Toast.makeText(lecturer_confirm.this, "Lecturer Account Created Successfully!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(lecturer_confirm.this, LecturerHomeActivity.class);
                            intent.putExtra("USER_NAME", fullName);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
        });

        // Allows user to go back to MainActivity and edit
        tvBack.setOnClickListener(v -> finish());
    }
}