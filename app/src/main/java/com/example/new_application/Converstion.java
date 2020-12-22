package com.example.new_application;

public class Converstion {


    String  email;
    String id ;
    String image;
    String name_User ;
    String  password;

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_User() {
        return name_User;
    }

    public void setName_User(String name_User) {
        this.name_User = name_User;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Converstion() {
    }


    public Converstion(String email, String id, String image, String name_User, String password , String status) {
        this.email = email;
        this.id = id;
        this.image = image;
        this.name_User = name_User;
        this.password = password;
        this.status=status;
    }

//    public Converstion(String email, String id, String image, String name_User, String password) {
//        this.email = email;
//        this.id = id;
//        this.image = image;
//        this.name_User = name_User;
//        this.password = password;
//    }
}
