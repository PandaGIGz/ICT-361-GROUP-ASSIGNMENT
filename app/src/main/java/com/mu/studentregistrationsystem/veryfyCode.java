package com.mu.studentregistrationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class veryfyCode extends AppCompatActivity {

    private EditText hiddenOtpInput;
    private TextView[] boxArray;
    private TextView errorText;
    private TextView instructionText;
    private TextView resendCodeLink;
    private MaterialButton verifyButton;
    private MaterialToolbar toolbar;
    private CountDownTimer resendTimer;
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veryfy_code);

        // Initialize Views
        toolbar = findViewById(R.id.toolbar);
        instructionText = findViewById(R.id.instructionText);
        hiddenOtpInput = findViewById(R.id.hiddenOtpInput);
        errorText = findViewById(R.id.errorText);
        verifyButton = findViewById(R.id.verifyButton);
        resendCodeLink = findViewById(R.id.resendCodeLink);

        boxArray = new TextView[]{
                findViewById(R.id.box1),
                findViewById(R.id.box2),
                findViewById(R.id.box3),
                findViewById(R.id.box4),
                findViewById(R.id.box5),
                findViewById(R.id.box6)
        };

        // Toolbar navigation
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Retrieve email from Intent extra
        if (getIntent() != null && getIntent().hasExtra("email")) {
            userEmail = getIntent().getStringExtra("email");
            if (userEmail != null && !userEmail.isEmpty()) {
                instructionText.setText("We sent a 6-digit code to " + userEmail);
            }
        }

        // Focus hidden input when user clicks on any box
        View.OnClickListener boxClickListener = v -> focusHiddenInput();
        for (TextView box : boxArray) {
            if (box != null) {
                box.setOnClickListener(boxClickListener);
            }
        }

        // Auto-focus the input field when screen loads
        hiddenOtpInput.post(this::focusHiddenInput);

        // TextWatcher to handle digit entry across boxes
        hiddenOtpInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorText.setVisibility(View.GONE);
                updateOtpBoxes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Verify button click listener
        verifyButton.setOnClickListener(v -> {
            String code = hiddenOtpInput.getText().toString().trim();
            if (code.length() < 6) {
                errorText.setText("⚠ Please enter the full 6-digit verification code.");
                errorText.setVisibility(View.VISIBLE);
                return;
            }

            errorText.setVisibility(View.GONE);
            verifyButton.setEnabled(false);
            verifyButton.setText("VERIFYING...");

            verifyButton.postDelayed(() -> {
                Toast.makeText(veryfyCode.this, "Code verified successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(veryfyCode.this, setNewPassword.class);
                intent.putExtra("email", userEmail);
                startActivity(intent);

                verifyButton.setEnabled(true);
                verifyButton.setText("VERIFY CODE");
                finish();
            }, 1500);
        });

        // Resend code click listener
        resendCodeLink.setOnClickListener(v -> {
            Toast.makeText(this, "A new verification code has been sent to " + (userEmail.isEmpty() ? "your email" : userEmail), Toast.LENGTH_LONG).show();
            hiddenOtpInput.setText("");
            errorText.setVisibility(View.GONE);
            startResendTimer();
        });

        // Start 30s resend timer
        startResendTimer();
    }

    private void updateOtpBoxes(String code) {
        for (int i = 0; i < boxArray.length; i++) {
            if (i < code.length()) {
                boxArray[i].setText(String.valueOf(code.charAt(i)));
            } else {
                boxArray[i].setText("");
            }
        }
    }

    private void focusHiddenInput() {
        hiddenOtpInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(hiddenOtpInput, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void startResendTimer() {
        if (resendCodeLink == null) return;

        if (resendTimer != null) {
            resendTimer.cancel();
        }

        resendCodeLink.setEnabled(false);
        resendCodeLink.setTextColor(0xFF888888);

        resendTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                resendCodeLink.setText("Resend Code (" + seconds + "s)");
            }

            @Override
            public void onFinish() {
                resendCodeLink.setText("Resend Code");
                resendCodeLink.setEnabled(true);
                resendCodeLink.setTextColor(0xFF1976D2);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendTimer != null) {
            resendTimer.cancel();
        }
    }
}
