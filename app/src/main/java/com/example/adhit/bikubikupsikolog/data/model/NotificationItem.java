package com.example.adhit.bikubikupsikolog.data.model;

/**
 * Created by adhit on 09/01/2018.
 */

public class NotificationItem {
    private int idNotification;
    private int idRoom;
    private String sender;
    private String message;
    private String item;


    public NotificationItem(int idNotification, int idRoom, String sender, String message, String item) {
        this.idNotification = idNotification;
        this.idRoom = idRoom;
        this.sender = sender;
        this.message = message;
        this.item = item;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getItem() {
        return item;
    }
}
