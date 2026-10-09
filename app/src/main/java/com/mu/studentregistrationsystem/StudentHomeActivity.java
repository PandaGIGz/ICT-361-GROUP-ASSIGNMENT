package com.mu.studentregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class StudentHomeActivity extends AppCompatActivity {

    private TextView tvStudentName;
    private MaterialButton btnViewGroup;
    private View btnShortcutProfile, btnShortcutGroups, btnShortcutNotifications;
    private ImageButton btnNotificationBell;
    private BottomNavigationView bottomNavigation;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        tvStudentName = findViewById(R.id.tvStudentName);
        btnViewGroup = findViewById(R.id.btnViewGroup);
        btnShortcutProfile = findViewById(R.id.btnShortcutProfile);
        btnShortcutGroups = findViewById(R.id.btnShortcutGroups);
        btnShortcutNotifications = findViewById(R.id.btnShortcutNotifications);
        btnNotificationBell = findViewById(R.id.btnNotificationBell);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // Get user name passed from Login/Registration
        if (getIntent() != null && getIntent().hasExtra("USER_NAME")) {
            String userName = getIntent().getStringExtra("USER_NAME");
            if (userName != null && !userName.isEmpty()) {
                tvStudentName.setText("Hello, " + userName);
            }
        }

        // Swipe Refresh Layout
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                swipeRefreshLayout.postDelayed(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(this, "Dashboard updated successfully", Toast.LENGTH_SHORT).show();
                }, 1200);
            });
        }

        // View Group Click
        if (btnViewGroup != null) {
            btnViewGroup.setOnClickListener(v -> {
                Intent intent = new Intent(StudentHomeActivity.this, GroupDetailsActivity.class);
                startActivity(intent);
            });
        }

        // Profile Shortcut
        if (btnShortcutProfile != null) {
            btnShortcutProfile.setOnClickListener(v -> {
                Intent intent = new Intent(StudentHomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }

        // Groups Shortcut
        if (btnShortcutGroups != null) {
            btnShortcutGroups.setOnClickListener(v -> {
                Intent intent = new Intent(StudentHomeActivity.this, GroupDetailsActivity.class);
                startActivity(intent);
            });
        }

        // Notifications Shortcut
        if (btnShortcutNotifications != null) {
            btnShortcutNotifications.setOnClickListener(v -> {
                Intent intent = new Intent(StudentHomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            });
        }

        if (btnNotificationBell != null) {
            btnNotificationBell.setOnClickListener(v -> {
                Intent intent = new Intent(StudentHomeActivity.this, NotificationsActivity.class);
                startActivity(intent);
            });
        }

        // Bottom Navigation
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_home);
            bottomNavigation.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    startActivity(new Intent(StudentHomeActivity.this, ProfileActivity.class));
                    return true;
                } else if (id == R.id.nav_groups) {
                    startActivity(new Intent(StudentHomeActivity.this, GroupDetailsActivity.class));
                    return true;
                } else if (id == R.id.nav_notifications) {
                    startActivity(new Intent(StudentHomeActivity.this, NotificationsActivity.class));
                    return true;
                }
                return true;
            });
        }
    }
}
