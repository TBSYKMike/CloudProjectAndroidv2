package com.example.miketest.cloudprojectandroid;

import java.util.ArrayList;

/**
 * Created by Mike on 2017-05-17.
 */

class TemporaryStorage {
    private static final TemporaryStorage ourInstance = new TemporaryStorage();
    private boolean accelerometerOnOff;
    private boolean proximityOnOff;
    private boolean lightOnOff;
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
}
