package com.avinsharma.notify;

/**
 * Created by Avin on 18-04-2016.
 */
public class Groups {

    private String title;
    private String description;
    //TODO: Instead of integer
    private int type;


    public Groups(String title, String description, int type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }
}
