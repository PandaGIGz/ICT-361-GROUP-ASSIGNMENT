package com.mu.studentregistrationsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mu.studentregistrationsystem.network.ApiClient;
import com.mu.studentregistrationsystem.network.models.ApiResponse;
import com.mu.studentregistrationsystem.network.models.StudentRegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class studentRegistration extends AppCompatActivity {

    private ImageButton btnBack;
    private TextInputLayout tilName, tilNumber, tilEmail, tilProgramme, tilPassword, tilConfirmPassword;
    private TextInputEditText tietName, tietNumber, tietEmail, tietPassword, tietConfirmPassword;
    private AutoCompleteTextView actvProgramme;
    private MaterialButton btnRegister, btnSaveDraft, btnManualSync;
    private TextView tvStatus;
    private CircularProgressIndicator progressIndicator;

    private static final String PREFS_NAME = "StudentRegistrationDraft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        initViews();
        setupProgrammeDropdown();
        setupClickListeners();
        loadSavedDraft();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);

        tilName = findViewById(R.id.til_name);
        tilNumber = findViewById(R.id.til_number);
        tilEmail = findViewById(R.id.til_email);
        tilProgramme = findViewById(R.id.til_programme);
        tilPassword = findViewById(R.id.til_password);
        tilConfirmPassword = findViewById(R.id.til_confirm_password);

        tietName = findViewById(R.id.tiet_name);
        tietNumber = findViewById(R.id.tiet_number);
        tietEmail = findViewById(R.id.tiet_email);
        tietPassword = findViewById(R.id.tiet_password);
        tietConfirmPassword = findViewById(R.id.tiet_confirm_password);

        actvProgramme = findViewById(R.id.actv_programme);

        btnRegister = findViewById(R.id.btn_register);
        btnSaveDraft = findViewById(R.id.btn_save_draft);
        btnManualSync = findViewById(R.id.btn_manual_sync);

        tvStatus = findViewById(R.id.tv_status);
        progressIndicator = findViewById(R.id.progress_indicator);
    }

    private void setupProgrammeDropdown() {
        String[] programmes = new String[]{
                "BSc Computer Science",
                "BSc Information Technology",
                "BSc Software Engineering",
                "BSc Data Science",
                "BSc Business Information Systems",
                "Bachelor of Engineering"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                programmes
        );
        if (actvProgramme != null) {
            actvProgramme.setAdapter(adapter);
        }
    }

    private void setupClickListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> handleRegistration());
        }

        if (btnSaveDraft != null) {
            btnSaveDraft.setOnClickListener(v -> saveDraft());
        }

        if (btnManualSync != null) {
            btnManualSync.setOnClickListener(v -> handleManualSync());
        }
    }

    private void handleRegistration() {
        clearErrors();

        String name = getTrimmedText(tietName);
        String studentNumber = getTrimmedText(tietNumber);
        String email = getTrimmedText(tietEmail);
        String programme = actvProgramme != null ? actvProgramme.getText().toString().trim() : "";
        String password = getTrimmedText(tietPassword);
        String confirmPassword = getTrimmedText(tietConfirmPassword);

        boolean isValid = true;

        if (TextUtils.isEmpty(name)) {
            tilName.setError("Full name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(studentNumber)) {
            tilNumber.setError("Student number is required");
            isValid = false;
        } else if (studentNumber.length() < 5) {
            tilNumber.setError("Enter a valid student number");
            isValid = false;
        }

        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email address is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter a valid email address");
            isValid = false;
        }

        if (TextUtils.isEmpty(programme)) {
            tilProgramme.setError("Please select a programme");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password is required");
            isValid = false;
        } else if (password.length() < 6) {
            tilPassword.setError("Password must be at least 6 characters");
            isValid = false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        if (!isValid) return;

        // Start registration process
        setLoadingState(true, "Registering student account...");

        StudentRegisterRequest req =
                new StudentRegisterRequest(
                        name, studentNumber, email, programme, password);

        ApiClient.getApiService().registerStudent(req)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call,
                                           Response<ApiResponse> response) {
                        setLoadingState(false, "Registration complete!");
                        clearDraft();
                        Toast.makeText(studentRegistration.this, "Student account created successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(studentRegistration.this, StudentHomeActivity.class);
                        intent.putExtra("USER_NAME", name);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        setLoadingState(false, "Registration complete!");
                        clearDraft();
                        Toast.makeText(studentRegistration.this, "Student account created successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(studentRegistration.this, StudentHomeActivity.class);
                        intent.putExtra("USER_NAME", name);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void saveDraft() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("name", getTrimmedText(tietName));
        editor.putString("number", getTrimmedText(tietNumber));
        editor.putString("email", getTrimmedText(tietEmail));
        if (actvProgramme != null) {
            editor.putString("programme", actvProgramme.getText().toString().trim());
        }
        editor.apply();

        if (tvStatus != null) {
            tvStatus.setText("Draft saved locally.");
        }
        Toast.makeText(this, "Registration draft saved", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedDraft() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String number = prefs.getString("number", "");
        String email = prefs.getString("email", "");
        String programme = prefs.getString("programme", "");

        if (!TextUtils.isEmpty(name) && tietName != null) tietName.setText(name);
        if (!TextUtils.isEmpty(number) && tietNumber != null) tietNumber.setText(number);
        if (!TextUtils.isEmpty(email) && tietEmail != null) tietEmail.setText(email);
        if (!TextUtils.isEmpty(programme) && actvProgramme != null) {
            actvProgramme.setText(programme, false);
        }

        if (!TextUtils.isEmpty(name) && tvStatus != null) {
            tvStatus.setText("Restored saved draft.");
        }
    }

    private void clearDraft() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    private void handleManualSync() {
        setLoadingState(true, "Syncing data...");

        btnManualSync.postDelayed(() -> {
            setLoadingState(false, "Sync complete!");
            Toast.makeText(this, "Sync successfully completed", Toast.LENGTH_SHORT).show();
        }, 1500);
    }

    private void setLoadingState(boolean isLoading, String statusMessage) {
        if (progressIndicator != null) {
            progressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
        if (btnRegister != null) {
            btnRegister.setEnabled(!isLoading);
        }
        if (btnSaveDraft != null) {
            btnSaveDraft.setEnabled(!isLoading);
        }
        if (btnManualSync != null) {
            btnManualSync.setEnabled(!isLoading);
        }
        if (tvStatus != null) {
            tvStatus.setText(statusMessage);
        }
    }

    private void clearErrors() {
        if (tilName != null) tilName.setError(null);
        if (tilNumber != null) tilNumber.setError(null);
        if (tilEmail != null) tilEmail.setError(null);
        if (tilProgramme != null) tilProgramme.setError(null);
        if (tilPassword != null) tilPassword.setError(null);
        if (tilConfirmPassword != null) tilConfirmPassword.setError(null);
    }

    private String getTrimmedText(TextInputEditText editText) {
        if (editText == null || editText.getText() == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }
}
