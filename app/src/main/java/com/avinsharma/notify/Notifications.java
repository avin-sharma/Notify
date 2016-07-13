package com.avinsharma.notify;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Avin on 02-05-2016.
 */
public class Notifications {
    private String title;
    private String description;
    private String type;
    private String groupName;
    private String groupId;
    private String date;
    private String notificationId;

    public Notifications(){

    }


    public Notifications(String title, String description, String type, String groupId, String date, String groupName, String notificationId) {
        //for tests and assignments
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        this.title = title;
        this.description = description;
        this.type = type;
        this.groupId = groupId;
        this.date = date;
        this.groupName = groupName;
        this.notificationId = notificationId;

    }

    public Notifications(String title, String description, String type, String groupId, String groupName, String notificationId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //for notifications
        this.title = title;
        this.description = description;
        this.type = type;
        this.groupId = groupId;
        date = null;
        this.groupName = groupName;
        this.notificationId = notificationId;

    }


    public Notifications(String type, String groupId, String groupName, String description, String notificationId) {
        // for invitations
        this.type = type;
        this.groupId = groupId;
        title = null;
        date = null;
        this.groupName = groupName;
        this.description = description;
        this.notificationId = notificationId;

    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getDate() {
        return date;
    }
}
