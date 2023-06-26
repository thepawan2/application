package com.example.newappwork;

public class Message {

    String mText;
    boolean isSentbyUser;

    public Message(String mText, boolean isSentbyUser) {
        this.mText = mText;
        this.isSentbyUser = isSentbyUser;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public boolean isSentbyUser() {
        return isSentbyUser;
    }

    public void setSentbyUser(boolean sentbyUser) {
        isSentbyUser = sentbyUser;
    }
}
