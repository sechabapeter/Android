package com.example.sechaba.groupl.Classes;

/**
 * Created by l225 on 2017/05/22.
 */

public class UserClass {
    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String userno;


    private String name;
    private String surname;
    private String email;
    private String password;
    private String photoLocation;

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;


    public UserClass() {
        this.userno = null;
        this.role = null;
        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
        this.photoLocation = null;
    }


}

