package com.din.myhjc.content;

public class ListDiary {
    private int id;
    private String year;
    private String month;
    private String day;
    private String contents;
    private String week;
    private String weather;
    private int itemType;
    private String nav;
    private static final int ITEM_TYPE_HEAD = 10;
    private static final int ITEM_TYPE_CONTENT = 11;
    private static final int ITEM_TYPE_NAV = 12;

    // RecyclerView头部数据
    public ListDiary(String year, String month, String day, int itemType) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.itemType = itemType;
    }

    // RecyclerView中间数据
    public ListDiary(int id, String contents, String week, String weather, int itemType) {
        this.id = id;
        this.contents = contents;
        this.week = week;
        this.weather = weather;
        this.itemType = itemType;
    }

    // RecyclerView尾部数据
    public ListDiary(String nav, int itemType) {
        this.nav = nav;
        this.itemType = itemType;
    }

    public ListDiary(String year, String month, String day) {
        this(year, month, day, ITEM_TYPE_HEAD);
    }

    public ListDiary(int id, String contents, String week, String weather) {
        this(id, contents, week, weather, ITEM_TYPE_CONTENT);
    }

    public ListDiary(String nav) {
        this(nav, ITEM_TYPE_NAV);
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

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}