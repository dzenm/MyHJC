package com.din.myhjc.content;

public class ListDiary {
    private int id;
    private String year;
    private String month;
    private String day;
    private String contents;
    private String week;
    private String weather;

    public ListDiary(int id, String year, String month, String day, String contents, String week, String weather) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.contents = contents;
        this.week = week;
        this.weather = weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}