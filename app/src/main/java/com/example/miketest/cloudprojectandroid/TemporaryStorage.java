package com.example.miketest.cloudprojectandroid;

import java.util.ArrayList;

/**
 * Created by Mike on 2017-05-17.
 */

class TemporaryStorage {
    private static final TemporaryStorage ourInstance = new TemporaryStorage();

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



}
