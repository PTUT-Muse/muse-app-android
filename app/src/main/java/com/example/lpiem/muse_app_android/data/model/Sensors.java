package com.example.lpiem.muse_app_android.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sensors {

    @SerializedName("sensor1")
    @Expose
    private List<Double> sensor1;
    @SerializedName("sensor2")
    @Expose
    private List<Double> sensor2;
    @SerializedName("sensor3")
    @Expose
    private List<Double> sensor3;
    @SerializedName("sensor4")
    @Expose
    private List<Double> sensor4;

    public Sensors() {
        this.sensor1 = new ArrayList<>();
        this.sensor2 = new ArrayList<>();
        this.sensor3 = new ArrayList<>();
        this.sensor4 = new ArrayList<>();
    }

    public List<Double> getSensor1() {
        return sensor1;
    }

    public void setSensor1(List<Double> sensor1) {
        this.sensor1 = sensor1;
    }

    public List<Double> getSensor2() {
        return sensor2;
    }

    public void setSensor2(List<Double> sensor2) {
        this.sensor2 = sensor2;
    }

    public List<Double> getSensor3() {
        return sensor3;
    }

    public void setSensor3(List<Double> sensor3) {
        this.sensor3 = sensor3;
    }

    public List<Double> getSensor4() {
        return sensor4;
    }

    public void setSensor4(List<Double> sensor4) {
        this.sensor4 = sensor4;
    }

    public void pushSensor1(double d) {
        this.sensor1.add(d);
    }

    public void pushSensor2(double d) {
        this.sensor2.add(d);
    }

    public void pushSensor3(double d) {
        this.sensor3.add(d);
    }

    public void pushSensor4(double d) {
        this.sensor4.add(d);
    }
}