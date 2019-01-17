package com.example.lpiem.muse_app_android.data.model;

public class Capture {
    private Sensors sensors;
    private int id;
    private int state;
    private String title;
    private String time;
    private String description;
    private String date;

    public Capture(int id, int state, String title, String description, String date, String time, Sensors sensors) {
        this.state = state;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.id = id;
        this.sensors = sensors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public Sensors getSensors() {
        return sensors;
    }

    public void setSensors(Sensors sensors) {
        this.sensors = sensors;
    }
}
