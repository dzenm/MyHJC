package com.din.myhjc.content;

public class ListNote {
    private int id;
    private String time;
    private String title;
    private String content;

    public ListNote(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}