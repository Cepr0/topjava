package ru.javawebinar.topjava.model;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public enum Role {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return getRole();
    }

}
