package com.example.navwihatbbed;

import java.sql.Date;
import java.sql.Time;

public class EventModel {

    private int id;
    private String title;
    private String date;
    private String time;
    private String type;
    private String description;

    public EventModel(int id, String title, String date, String time, String type, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.type = type;
        this.description = description;
    }

    public EventModel(){

    }

    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
