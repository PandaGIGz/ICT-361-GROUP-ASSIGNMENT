package com.mu.studentregistrationsystem.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "local_users")
public class AppUserEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String identity;
    private String name;
    private String email;
    private String role;
    private boolean isSyncedLocally;

    public AppUserEntity(String identity, String name, String email, String role, boolean isSyncedLocally) {
        this.identity = identity;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isSyncedLocally = isSyncedLocally;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getIdentity() { return identity; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isSyncedLocally() { return isSyncedLocally; }
    public void setSyncedLocally(boolean syncedLocally) { isSyncedLocally = syncedLocally; }
}
