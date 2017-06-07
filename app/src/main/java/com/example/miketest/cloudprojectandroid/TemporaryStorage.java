package com.example.miketest.cloudprojectandroid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mike on 2017-05-17.
 */

class TemporaryStorage {
    private static final TemporaryStorage ourInstance = new TemporaryStorage();
    String acceleroMeterOnOff = "1";
    String lightOnOff = "1";
    String proximityOnoff = "1";
    String samplingRate = "1";
    private boolean sensorStop;
    private String loggedInuserEmail = "Pick@stick.se";

    static TemporaryStorage getInstance() {
        return ourInstance;
    }


    // Storing all Sampling data
    private ArrayList<String> ArrayOfSamplingData = new ArrayList<>();

    private String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

    // This Method will add new data to ArrayList
    public void addDataToArray(String SensorName, String SensorData) {

        if (getCurrentTimeStamp().equals(currentTimeStamp)) {
            threadSleep(50);
        }

        currentTimeStamp = getCurrentTimeStamp();
        // the data will contain SensorName, SensorData, and The time it was added to the ArrayList
        String newSamplingData = "" + SensorName + ";;" + SensorData + ";;" + currentTimeStamp;
        ArrayOfSamplingData.add(newSamplingData);
        System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- timestamp: " + currentTimeStamp);

        // this method is the Auto Upload function
        autoUpload();
    }


    public void clearArrayOfSamplingData() {
        ArrayOfSamplingData.clear();
    }

    public void autoUpload() {
        // auto upload when ArrayOfSamplingData size is the size of uploadInterval
        int uploadInterval = 400;
        if (ArrayOfSamplingData.size() == uploadInterval) {
            // This will copy the first part of the current size of the ArrayList to a new ArrayList,
            // before sending it sending the ArrayList to the Upload AsyncTask.
            // By doing this is to prevent write and read conflicts that may or may not happen
            // and to remove the current items from the global ArrayList
            ArrayList<String> tempArrayOfSamplingData = new ArrayList<>();
            for (int i = 0; i < uploadInterval; i++) {
                tempArrayOfSamplingData.add(ArrayOfSamplingData.get(0));
                ArrayOfSamplingData.remove(0);
            }
            // this will run the asynctask for upload.
            new AzureTableConnectorV3(tempArrayOfSamplingData).execute();
        }
    }

    public boolean lastUploadPart = false;

    public void printArrayListV2() {
        // this method will run when sensor reading has stopped.
        while (isUploading) {
            threadSleep(1000);
        }
        lastUploadPart = true;
        System.out.println(ArrayOfSamplingData.size());
        uploadTasksTotal = 0;
        uploadTasksFinished = 0;
        new AzureTableConnectorV3(ArrayOfSamplingData).execute();
        //ArrayOfSamplingData.clear();
    }


    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }


    public TemporaryStorage() {
        if (storage001 == null) {
            storage001 = new ArrayList<>();
        }
    }

    private ArrayList<String> storage001;


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


    private int cloudQueue = 0;

    public void cloudQueueStarted() {
        cloudQueue++;
        System.out.println("cloudQueueStarted");
        uploadTasksFinished++;
    }

    public void cloudQueueFinished() {
        cloudQueue--;
        System.out.println("cloudQueueFinished");

    }

    public boolean cloudQueueUploading() {
        if (cloudQueue == 0) {
            return false;
        }
        return true;
    }

    public boolean cloudQueueUploading2() {
        System.out.println("total " + uploadTasksTotal + " - done " + uploadTasksFinished);
        if (uploadTasksTotal == uploadTasksFinished) {
            return false;
        }
        if (!isUploading) {
            return false;
        }
        return true;
    }

    public int uploadTasksTotal = 0;
    public int uploadTasksFinished = 0;

    public boolean isUploading = false;


    private void threadSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
