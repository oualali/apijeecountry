package com.example.pulco.Model;

public class Credentials {

    private String email;
    private String password;
    private boolean isValid;

    private String error;
    private int userId;

    public Credentials() {
        this.error = "";
        this.isValid = false;
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
        this.error = "";
        this.isValid = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
