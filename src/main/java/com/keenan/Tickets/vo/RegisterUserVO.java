package com.keenan.Tickets.vo;

/**
 * @author keenan on 15/03/2018
 */
public class RegisterUserVO {
    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    /**
     * USER
     * VENUE
     */
    private String userType;

    public RegisterUserVO() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "RegisterUserVO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
