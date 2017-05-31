package com.example.miketest.cloudprojectandroid;

import com.microsoft.azure.storage.table.TableBatchOperation;

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
    private ArrayList <String> ArrayOfSamplingData = new ArrayList<>();

    private String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

    public void addDataToArray(String SensorName, String SensorData){

        if (getCurrentTimeStamp().equals(currentTimeStamp)){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currentTimeStamp = getCurrentTimeStamp();
        String newSamplingData = ""+SensorName+";;"+SensorData+";;"+currentTimeStamp;
        ArrayOfSamplingData.add(newSamplingData);
        //System.out.println(getCurrentTimeStamp());
        System.out.println( "---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- timestamp: "+currentTimeStamp );
        autoUpload();
    }


    public void clearArrayOfSamplingData(){
        ArrayOfSamplingData.clear();
    }

    public void autoUpload(){
        // auto upload when ArrayOfSamplingData size is the size of uploadInterval
        int uploadInterval = 200;
        if(ArrayOfSamplingData.size() == uploadInterval) {
               ArrayList<String> tempArrayOfSamplingData = new ArrayList<>();
               for (int i = 0; i < uploadInterval; i++) {
                   tempArrayOfSamplingData.add(ArrayOfSamplingData.get(0));
                   ArrayOfSamplingData.remove(0);
               }
               new AzureTableConnectorV3(tempArrayOfSamplingData).execute();
        }
    }

    public boolean lastUploadPart = false;

    public void printArrayListV2(){
        while(isUploading){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastUploadPart = true;
        System.out.println( ArrayOfSamplingData.size() );
        uploadTasksTotal=0;
        uploadTasksFinished=0;
        new AzureTableConnectorV3( ArrayOfSamplingData ).execute();
        //ArrayOfSamplingData.clear();
    }

    public void printArrayList(){
        batchOperation = new TableBatchOperation();
        uploadTasksTotal=0;
        uploadTasksFinished=0;


        for (int i=0; i < ArrayOfSamplingData.size(); i++) {
            //System.out.println(ArrayOfSamplingData.get(i));
            dataSortAndAddToDatabaseV2(ArrayOfSamplingData.get(i));
            if (batchOperation.size() > 50 ) {
                new AzureTableConnectorV2( batchOperation ).execute();
                batchOperation = new TableBatchOperation();
                uploadTasksTotal++;
            }
        }
        if (batchOperation.size() > 0 ) {
            new AzureTableConnectorV2( batchOperation ).execute();
            batchOperation = new TableBatchOperation();
            uploadTasksTotal++;
        }
        ArrayOfSamplingData.clear();

/*
        while(ArrayOfSamplingData.size() > 0){
            dataSortAndAddToDatabaseV2(ArrayOfSamplingData.get(0));
            ArrayOfSamplingData.remove(0);
            if (batchOperation.size() > 50 ) {
                new AzureTableConnectorV2( batchOperation ).execute();
                batchOperation = new TableBatchOperation();
            }
        }
        if (batchOperation.size() > 0 ) {
            new AzureTableConnectorV2( batchOperation ).execute();
            batchOperation = new TableBatchOperation();
        }

*/

        //ArrayOfSamplingData.clear();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    private TableBatchOperation batchOperation;;

    private void dataSortAndAddToDatabase(String rowOfData){
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
        else if(splitedData[0].equals("BATRY")){
            new AzureTableConnector("batterylevel", splitedData[1], null, null, nanoTime ).execute();
        }


    }

    private void dataSortAndAddToDatabaseV2(String rowOfData){
        String[] splitedData = rowOfData.split(";;");
        String nanoTime = splitedData[2];



        if(splitedData[0].equals("ACCEL")){
            String[] values = splitedData[1].split(",");
            batchOperation.insertOrReplace( inputRightSensorData("accelerometer", values[0], values[1], values[2], nanoTime ) );
        }
        else if(splitedData[0].equals("LIGHT")){
            batchOperation.insertOrReplace( inputRightSensorData("lightsensor", splitedData[1], null, null, nanoTime ) );
        }
        else if(splitedData[0].equals("PROXI")){
            batchOperation.insertOrReplace( inputRightSensorData("proximitysensor", splitedData[1], null, null, nanoTime ) );
        }
        else if(splitedData[0].equals("METAD")){
            batchOperation.insertOrReplace( inputRightSensorData("metadata", splitedData[1], null, null, nanoTime ) );
        }
        else if(splitedData[0].equals("BATRY")){
            batchOperation.insertOrReplace( inputRightSensorData("batterylevel", splitedData[1], null, null, nanoTime ) );
        }


    }

    private SensorEntity inputRightSensorData(String sensorType, String value1,String value2,String value3, String timeNano){
        SensorEntity sensor1 = new SensorEntity("1",  loggedInuserEmail+";"+timeNano );
        if (sensorType.equals("accelerometer")) {
            sensor1.setSensorAccelerometerX(value1);
            sensor1.setSensorAccelerometerY(value2);
            sensor1.setSensorAccelerometerZ(value3);
        }
        else if (sensorType.equals("lightsensor")) {
            sensor1.setSensorLight(value1);
        }
        else if (sensorType.equals("proximitysensor")) {
            sensor1.setSensorProximity(value1);
        }
        else if (sensorType.equals("metadata")) {
            sensor1.setMETAData(value1);
        }
        else if (sensorType.equals("batterylevel")) {
            sensor1.setBatteryLevel(value1);
        }
        else if (sensorType.equals("New Sensor Name")) {
            // Example for adding new sensors for future messurments
            // sensor1.getSensorPlaceholder1(value1);
        }


        return sensor1;
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


    private int cloudQueue = 0;

    public void cloudQueueStarted(){
        cloudQueue++;
        System.out.println("cloudQueueStarted");
        uploadTasksFinished++;
    }

    public void cloudQueueFinished(){
        cloudQueue--;
        System.out.println("cloudQueueFinished");

    }
    public boolean cloudQueueUploading(){
        if(cloudQueue == 0){
            return false;
        }
        return true;
    }
    public boolean cloudQueueUploading2(){
        System.out.println("total "+uploadTasksTotal+" - done "+uploadTasksFinished);
        if(uploadTasksTotal==uploadTasksFinished){
            return false;
        }
        if(!isUploading){
            return false;
        }
        return true;
    }

    public int uploadTasksTotal=0;
    public int uploadTasksFinished=0;

    public boolean isUploading = false;



}
