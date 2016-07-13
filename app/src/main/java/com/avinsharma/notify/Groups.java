package com.avinsharma.notify;

/**
 * Created by Avin on 18-04-2016.
 */
public class Groups {

    private String title;
    private String description;
    private String key;

    public Groups() {

    }

    public Groups(String title, String description, String key) {
        this.title = title;
        this.description = description;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }
}
