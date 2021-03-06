package com.example.theme.medmanager01.helpers.model;


import android.app.PendingIntent;

public class Medication {
   private int id;
    private String name;
    private String description;
    private String time;
    private String start_date;
    private String end_date;
    private String pendingIntent;

    public Medication(int id, String name, String description, String time, String start_date, String end_date
    ,String pendingIntent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.start_date = start_date;
        this.end_date = end_date;
        this.pendingIntent = pendingIntent;
    }

    public Medication() {
    }

    public String getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(String pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", interval='" + time + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
