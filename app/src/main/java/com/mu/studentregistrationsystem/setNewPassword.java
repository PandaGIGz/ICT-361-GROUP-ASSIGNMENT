package com.mu.studentregistrationsystem;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class setNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        TextInputEditText newPasswordInput = findViewById(R.id.newPasswordInput);
        TextInputEditText confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        TextInputLayout newPasswordLayout = findViewById(R.id.newPasswordLayout);
        TextInputLayout confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        MaterialButton resetButton = findViewById(R.id.resetPasswordButton);

        resetButton.setOnClickListener(v -> {
            String newPass = newPasswordInput.getText() != null ? newPasswordInput.getText().toString() : "";
            String confirmPass = confirmPasswordInput.getText() != null ? confirmPasswordInput.getText().toString() : "";

            // Clear previous errors
            newPasswordLayout.setError(null);
            confirmPasswordLayout.setError(null);

            // Validation 1: minimum length + number + symbol
            if (newPass.length() < 8
                    || !newPass.matches(".*\\d.*")
                    || !newPass.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                newPasswordLayout.setError("Must be at least 8 characters, include a number and a symbol");
                return;
            }

            // Validation 2: passwords match
            if (!newPass.equals(confirmPass)) {
                confirmPasswordLayout.setError("Passwords do not match");
                return;
            }

            // Loading state
            resetButton.setEnabled(false);
            resetButton.setText("RESETTING...");

            resetButton.postDelayed(() -> {
                Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_LONG).show();
                resetButton.setEnabled(true);
                resetButton.setText("RESET PASSWORD");
                finish();
            }, 1500);
        });
    }
}
