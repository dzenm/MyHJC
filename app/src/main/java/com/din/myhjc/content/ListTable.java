package com.din.myhjc.content;

public class ListTable {
    private int id;
    private String title;
    private String content;

    public ListTable(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}