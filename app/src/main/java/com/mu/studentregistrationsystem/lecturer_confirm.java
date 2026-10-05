package com.mu.studentregistrationsystem;



import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

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
            // Perform actual database registration here
            Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_LONG).show();
            finish();
        });

        // Allows user to go back to MainActivity and edit
        tvBack.setOnClickListener(v -> finish());
    }
}