package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class creatAccount extends AppCompatActivity {

    private CardView cardStudent, cardLecturer;
    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);

        // Initialize UI Views
        cardStudent = findViewById(R.id.cardStudent);
        cardLecturer = findViewById(R.id.cardLecturer);
        btnBack = findViewById(R.id.btnBack);

        // Open Student Registration
        if (cardStudent != null) {
            cardStudent.setOnClickListener(v -> {
                Intent intent = new Intent(creatAccount.this, studentRegistration.class);
                startActivity(intent);
            });
        }

        // Open Lecturer Registration
        if (cardLecturer != null) {
            cardLecturer.setOnClickListener(v -> {
                Intent intent = new Intent(creatAccount.this, lecturerRegistration.class);
                startActivity(intent);
            });
        }

        // Go Back to previous activity
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
