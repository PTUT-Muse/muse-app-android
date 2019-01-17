package com.example.lpiem.muse_app_android.data.model;

import java.util.ArrayList;
import java.util.List;

public class Sensors {
    private List<Double> sensor1;
    private List<Double> sensor2;
    private List<Double> sensor3;
    private List<Double> sensor4;

    public Sensors() {
        this.sensor1 = new ArrayList<>();
        this.sensor2 = new ArrayList<>();
        this.sensor3 = new ArrayList<>();
        this.sensor4 = new ArrayList<>();
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

    public List<Double> getSensor1() {
        return sensor1;
    }

    public List<Double> getSensor2() {
        return sensor2;
    }

    public List<Double> getSensor3() {
        return sensor3;
    }

    public List<Double> getSensor4() {
        return sensor4;
    }
}
