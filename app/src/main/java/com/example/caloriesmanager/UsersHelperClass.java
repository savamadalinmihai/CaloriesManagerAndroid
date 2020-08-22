package com.example.caloriesmanager;

public class UsersHelperClass {
    String signUpUserName, signUpPassword, signUpEmail;

    public UsersHelperClass(String signUpUserName, String signUpPassword, String signUpEmail) {
        this.signUpUserName = signUpUserName;
        this.signUpPassword = signUpPassword;
        this.signUpEmail = signUpEmail;
    }

    public UsersHelperClass() {
    }

    public String getSignUpUserName() {
        return signUpUserName;
    }

    public void setSignUpUserName(String signUpUserName) {
        this.signUpUserName = signUpUserName;
    }

    public String getSignUpPassword() {
        return signUpPassword;
    }

    public void setSignUpPassword(String signUpPassword) {
        this.signUpPassword = signUpPassword;
    }

    public String getSignUpEmail() {
        return signUpEmail;
    }

    public void setSignUpEmail(String signUpEmail) {
        this.signUpEmail = signUpEmail;
    }
}
