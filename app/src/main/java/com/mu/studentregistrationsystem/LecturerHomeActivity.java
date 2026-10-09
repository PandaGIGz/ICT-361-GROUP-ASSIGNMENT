package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LecturerHomeActivity extends AppCompatActivity {

    private TextView tvLecturerName;
    private View btnShortcutStudents, btnShortcutGroups, btnShortcutSearch;
    private ImageButton btnNotificationBell;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_home);

        tvLecturerName = findViewById(R.id.tvLecturerName);
        btnShortcutStudents = findViewById(R.id.btnShortcutStudents);
        btnShortcutGroups = findViewById(R.id.btnShortcutGroups);
        btnShortcutSearch = findViewById(R.id.btnShortcutSearch);
        btnNotificationBell = findViewById(R.id.btnNotificationBell);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        if (getIntent() != null && getIntent().hasExtra("USER_NAME")) {
            String userName = getIntent().getStringExtra("USER_NAME");
            if (userName != null && !userName.isEmpty()) {
                tvLecturerName.setText("Good Morning, " + userName);
            }
        }

        // Shortcuts
        if (btnShortcutStudents != null) {
            btnShortcutStudents.setOnClickListener(v -> {
                Intent intent = new Intent(LecturerHomeActivity.this, GroupDetailsActivity.class);
                startActivity(intent);
            });
        }

        if (btnShortcutGroups != null) {
            btnShortcutGroups.setOnClickListener(v -> {
                Intent intent = new Intent(LecturerHomeActivity.this, GroupDetailsActivity.class);
                startActivity(intent);
            });
        }

        if (btnShortcutSearch != null) {
            btnShortcutSearch.setOnClickListener(v -> {
                Intent intent = new Intent(LecturerHomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            });
        }

        if (btnNotificationBell != null) {
            btnNotificationBell.setOnClickListener(v -> {
                Intent intent = new Intent(LecturerHomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            });
        }

        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_home);
            bottomNavigation.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    startActivity(new Intent(LecturerHomeActivity.this, ProfileActivity.class));
                    return true;
                } else if (id == R.id.nav_groups) {
                    startActivity(new Intent(LecturerHomeActivity.this, GroupDetailsActivity.class));
                    return true;
                } else if (id == R.id.nav_notifications) {
                    startActivity(new Intent(LecturerHomeActivity.this, NotificationsActivity.class));
                    return true;
                }
                return true;
            });
        }
    }
}
