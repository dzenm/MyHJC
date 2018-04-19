package com.din.myhjc.content;

/**
 * Created by dinzhenyan on 2018/4/17.
 */

public class Single {
    private int imageId;
    private String text;

    public Single(int imageId, String text) {
        this.imageId = imageId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
