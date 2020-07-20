package com.example.login;


import java.util.HashMap;

public class Profile {
    public String userAge;
    public String userEmail;
    public String userName;
    public HashMap contacts;

    public Profile(){
    }

    public Profile(String userAge, String userEmail, String userName,HashMap contacts) {
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userName = userName;
        this.contacts = contacts;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public HashMap getContacts() { return contacts;    }

    public void setContacts(HashMap contacts) { this.contacts = contacts; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}





















/*
    <TextView
        android:id="@+id/tvForgotPassword"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:text="@string/forgot_password"
        app:layout_constraintBottom_toTopOf="@+id/tvInfo"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btnLogin" />*/