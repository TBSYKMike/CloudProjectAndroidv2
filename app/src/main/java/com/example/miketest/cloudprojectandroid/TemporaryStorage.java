package com.example.miketest.cloudprojectandroid;

import java.util.ArrayList;

/**
 * Created by Mike on 2017-05-17.
 */

class TemporaryStorage {
    private static final TemporaryStorage ourInstance = new TemporaryStorage();
    String acceleroMeterOnOff;
    String lightOnOff;
    String proximityOnoff ;
    String samplingRate;
    private boolean sensorStop;
    private String loggedInuserEmail;

    static TemporaryStorage getInstance() {
        return ourInstance;
    }


    public TemporaryStorage() {
        if(storage001 == null) {
            storage001 = new ArrayList<>();
        }
    }

    private ArrayList<String> storage001;

    public void add(String SensorNameSensorData){
        storage001.add( Long.toString( System.nanoTime() ) );
        storage001.add(SensorNameSensorData);
    }


    public boolean isSensorStop() {
        return sensorStop;
    }

    public void setSensorStop(boolean sensorStop) {
        this.sensorStop = sensorStop;
    }

    public String getLoggedInuserEmail() {
        return loggedInuserEmail;
    }

    public void setLoggedInuserEmail(String loggedInuserEmail) {
        this.loggedInuserEmail = loggedInuserEmail;
    }

    public String getLightOnOff() {
        return lightOnOff;
    }

    public void setLightOnOff(String lightOnOff) {
        this.lightOnOff = lightOnOff;
    }

    public String getProximityOnoff() {
        return proximityOnoff;
    }

    public void setProximityOnoff(String proximityOnoff) {
        this.proximityOnoff = proximityOnoff;
    }

    public String getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(String samplingRate) {
        this.samplingRate = samplingRate;
    }
    public String getAcceleroMeterOnOff() {
        return acceleroMeterOnOff;
    }

    public void setAcceleroMeterOnOff(String acceleroMeterOnOff) {
        this.acceleroMeterOnOff = acceleroMeterOnOff;
    }
}
