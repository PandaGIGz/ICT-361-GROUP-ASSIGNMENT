package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mu.studentregistrationsystem.network.ApiClient;
import com.mu.studentregistrationsystem.network.models.ApiResponse;
import com.mu.studentregistrationsystem.network.models.ForgotPasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgotPassword extends AppCompatActivity {

    private TextView resendCodeLink;
    private CountDownTimer resendTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        TextInputEditText emailInput = findViewById(R.id.emailInput);
        TextInputLayout emailInputLayout = findViewById(R.id.emailInputLayout);
        MaterialButton sendButton = findViewById(R.id.sendResetButton);
        TextView backToSignIn = findViewById(R.id.backToSignIn);
        resendCodeLink = findViewById(R.id.resendCodeLink);

        if (resendCodeLink != null) {
            resendCodeLink.setOnClickListener(v -> {
                String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";
                if (email.isEmpty()) {
                    emailInputLayout.setError("Enter your email address first");
                    return;
                }
                emailInputLayout.setError(null);
                Toast.makeText(this, "Reset code resent to " + email, Toast.LENGTH_LONG).show();
                startResendTimer();
            });
        }

        sendButton.setOnClickListener(v -> {
            String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInputLayout.setError("Enter a valid email address (e.g., name@gmail.com)");
                return;
            } else {
                emailInputLayout.setError(null);
            }

            sendButton.setEnabled(false);
            sendButton.setText("SENDING...");

            ForgotPasswordRequest req =
                    new ForgotPasswordRequest(email);

            ApiClient.getApiService().forgotPassword(req)
                    .enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call,
                                               Response<ApiResponse> response) {
                            sendButton.setEnabled(true);
                            sendButton.setText("SEND RESET CODE");
                            Toast.makeText(forgotPassword.this, "Reset code sent to " + email, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(forgotPassword.this, veryfyCode.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                            startResendTimer();
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            sendButton.setEnabled(true);
                            sendButton.setText("SEND RESET CODE");
                            Toast.makeText(forgotPassword.this, "Reset code sent to " + email, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(forgotPassword.this, veryfyCode.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                            startResendTimer();
                        }
                    });
        });

        if (backToSignIn != null) {
            backToSignIn.setOnClickListener(v -> finish());
        }
    }

    private void startResendTimer() {
        if (resendCodeLink == null) return;

        if (resendTimer != null) resendTimer.cancel();

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
