package com.example.new_application;

public class Chats {

    String sender;
    String reciver;
    String massege;
    boolean seen;

    public Chats() {
//            com.google.firebase.database.DatabaseException: Class com.example.
//            new_application.Chats does not define a no-argument constructor.
//            If you are using ProGuard, make sure these constructors are not stripped.
    }



    public Chats(String sender, String reciver, String massege, boolean seen) {
        this.sender = sender;
        this.reciver = reciver;
        this.massege = massege;
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }
}
