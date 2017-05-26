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

    // Storing all Sampling data
    private ArrayList <String> ArrayOfSamplingData = new ArrayList<>();

    public void addDataToArray(String SensorName, String SensorData){
        String newSamplingData = ""+SensorName+";;"+SensorData+";;"+System.nanoTime();
        ArrayOfSamplingData.add(newSamplingData);
    }

    public void printArrayList(){
        for (int i=0; i < ArrayOfSamplingData.size(); i++){
            System.out.println( ArrayOfSamplingData.get(i) );
            dataSortAndAdd( ArrayOfSamplingData.get(i) );
        }
    }


    private void dataSortAndAdd(String rowOfData){
        String[] splitedData = rowOfData.split(";;");
        String nanoTime = splitedData[2];

        if(splitedData[0].equals("ACCEL")){
            String[] values = splitedData[1].split(",");
            new AzureTableConnector("accelerometer", values[0], values[1], values[2], nanoTime ).execute();
        }
        else if(splitedData[0].equals("LIGHT")){
            new AzureTableConnector("lightsensor", splitedData[1], null, null, nanoTime ).execute();
        }
        else if(splitedData[0].equals("PROXI")){
            new AzureTableConnector("proximitysensor", splitedData[1], null, null, nanoTime ).execute();
        }
        else if(splitedData[0].equals("METAD")){
            new AzureTableConnector("metadata", splitedData[1], null, null, nanoTime ).execute();
        }


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
