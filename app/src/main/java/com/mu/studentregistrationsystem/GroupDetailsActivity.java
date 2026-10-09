package com.mu.studentregistrationsystem;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class GroupDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        MaterialButton btnRequestChange = findViewById(R.id.btnRequestChange);
        if (btnRequestChange != null) {
            btnRequestChange.setOnClickListener(v -> {
                Toast.makeText(this, "Group change request submitted to lecturer", Toast.LENGTH_LONG).show();
            });
        }
    }
}
