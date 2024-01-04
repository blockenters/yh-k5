package com.block.memoapp.model;

public class Memo {

    public int id;
    public String title;
    public String date;
    public String content;

    public Memo(){

    }

    public Memo(String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }
}
