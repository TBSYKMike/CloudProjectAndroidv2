package com.example.miketest.cloudprojectandroid;

import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableBatchOperation;

import java.util.ArrayList;

/**
 * Created by Henrik on 2017-05-05.
 */

public class AzureTableConnectorV3 extends AsyncTask<String, Void, String> {


    TableBatchOperation batchOperation;

    ArrayList<String> ArrayOfSamplingData;

    String email;

    int batchSize = 50 * 1;

    public AzureTableConnectorV3(ArrayList<String> ArrayOfSamplingData) {
        this.ArrayOfSamplingData = ArrayOfSamplingData;
    }

    @Override
    protected String doInBackground(String... params) {
        // we use the OkHttp library from https://github.com/square/okhttp
        TemporaryStorage.getInstance().cloudQueueStarted();
        email = TemporaryStorage.getInstance().getLoggedInuserEmail();
        // params[0] is for the type of sensor
        // params[1-3] is for the value from the sensor
        TemporaryStorage.getInstance().isUploading = true;

        if (ArrayOfSamplingData.size() % batchSize > 0) {
            TemporaryStorage.getInstance().uploadTasksTotal = ArrayOfSamplingData.size() / batchSize;
            TemporaryStorage.getInstance().uploadTasksTotal++;
        } else {
            TemporaryStorage.getInstance().uploadTasksTotal = ArrayOfSamplingData.size() / batchSize;
        }

        // establishing the connection to azure storage
        establishAzureConnection();

        // run upload method
        uploadArrayList();

        TemporaryStorage.getInstance().cloudQueueFinished();
        TemporaryStorage.getInstance().isUploading = false;
        return "Download failed";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private SensorEntity inputRightSensorData(String sensorType, String value1, String value2, String value3, String timeNano) {

        // this method is to sort alla the data and add it to a custom TableServiceEntity.

        SensorEntity sensor1 = new SensorEntity("1", email + ";" + timeNano);
        if (sensorType.equals("accelerometer")) {
            sensor1.setSensorAccelerometerX(value1);
            sensor1.setSensorAccelerometerY(value2);
            sensor1.setSensorAccelerometerZ(value3);
        } else if (sensorType.equals("lightsensor")) {
            sensor1.setSensorLight(value1);
        } else if (sensorType.equals("proximitysensor")) {
            sensor1.setSensorProximity(value1);
        } else if (sensorType.equals("metadata")) {
            sensor1.setMETAData(value1);
        } else if (sensorType.equals("batterylevel")) {
            sensor1.setBatteryLevel(value1);
        } else if (sensorType.equals("New Sensor Name")) {
            // Example for adding new sensors for future messurments
            // sensor1.getSensorPlaceholder1(value1);
        }


        return sensor1;
    }

    private CloudTable cloudTable;

    private void establishAzureConnection() {


        String storageConnectionString =
                "DefaultEndpointsProtocol=https;AccountName=hkrtest;AccountKey=xMmOQjMFbLY6R5cHcfUAQjZXRRp50eLTiFspybB929IGYsBnuVbCME/6bcxejT2kd3rEJLBBfcQXi8e0TLfPbg==;EndpointSuffix=core.windows.net";
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();
            // Create the table if it doesn't exist.
            String tableName = "people";
            cloudTable = tableClient.getTableReference(tableName);

            cloudTable.createIfNotExists();


        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }


    private void dataSortAndAddToDatabaseV2(String rowOfData) {
        String[] splitedData = rowOfData.split(";;");
        String nanoTime = splitedData[2];

        // this method is to add all Entities to Batch Operations
        // this will fill the global BatchOperation

        if (splitedData[0].equals("ACCEL")) {
            String[] values = splitedData[1].split(",");
            batchOperation.insertOrReplace(inputRightSensorData("accelerometer", values[0], values[1], values[2], nanoTime));
        } else if (splitedData[0].equals("LIGHT")) {
            batchOperation.insertOrReplace(inputRightSensorData("lightsensor", splitedData[1], null, null, nanoTime));
        } else if (splitedData[0].equals("PROXI")) {
            batchOperation.insertOrReplace(inputRightSensorData("proximitysensor", splitedData[1], null, null, nanoTime));
        } else if (splitedData[0].equals("METAD")) {
            batchOperation.insertOrReplace(inputRightSensorData("metadata", splitedData[1], null, null, nanoTime));
        } else if (splitedData[0].equals("BATRY")) {
            batchOperation.insertOrReplace(inputRightSensorData("batterylevel", splitedData[1], null, null, nanoTime));
        }
        /*
        else if (splitedData[0].equals("NewSensor")) {
            batchOperation.insertOrReplace(inputRightSensorData("NewSensorName", splitedData[1], null, null, nanoTime));
        }
        */

    }

    public void uploadArrayList() {
        // this method will run though all data in the ArrayList and upload the it to Azure.
        batchOperation = new TableBatchOperation();


        for (int i = 0; i < ArrayOfSamplingData.size(); i++) {
            // run method to fill the batch
            dataSortAndAddToDatabaseV2(ArrayOfSamplingData.get(i));
            if (batchOperation.size() >= batchSize) {
                // when batch is of certain size it will upload the batch
                try {

                    cloudTable.execute(batchOperation);
                    TemporaryStorage.getInstance().uploadTasksFinished++;
                } catch (StorageException e) {
                    e.printStackTrace();
                    System.err.println(e.getErrorCode());
                    System.err.println(e.getExtendedErrorInformation());
                    System.err.println(e.getLocalizedMessage());
                    System.err.println(e.getMessage());
                }
                batchOperation = new TableBatchOperation();

            }
        }
        if (batchOperation.size() > 0) {
            // after the loop is done if Batch still contains data, it will upload the rest of the batch.
            try {
                cloudTable.execute(batchOperation);
            } catch (StorageException e) {
                e.printStackTrace();
                System.err.println(e.getErrorCode());
                System.err.println(e.getExtendedErrorInformation());
                System.err.println(e.getLocalizedMessage());
                System.err.println(e.getMessage());

            }
            batchOperation = new TableBatchOperation();

        }
        ArrayOfSamplingData.clear();
        if (TemporaryStorage.getInstance().lastUploadPart) {
            // this method will only run when user has pressed on stop button
            // this will clear the global ArrayList which is the last batch of the data.
            // this is done to prevent old data to be uploaded when a new reading starts
            TemporaryStorage.getInstance().clearArrayOfSamplingData();
            TemporaryStorage.getInstance().lastUploadPart = false;
        }

    }


}
