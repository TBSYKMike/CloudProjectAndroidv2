package com.example.miketest.cloudprojectandroid;

import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

/**
 * Created by Henrik on 2017-05-05.
 */

public class AzureTableConnector extends AsyncTask<String, Void, String> {

    private String sensorType = "";
    private String value1 = "";
    private String value2 = "";
    private String value3 = "";
    private String timeNano = "";


    public AzureTableConnector(String sensorType, String value1, String value2, String value3, String timeNano) {
        this.sensorType = sensorType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.timeNano = timeNano;
    }

    @Override
    protected String doInBackground(String... params) {
        // we use the OkHttp library from https://github.com/square/okhttp

        // params[0] is for the type of sensor
        // params[1-3] is for the value from the sensor
        cloudTest(sensorType, value1, value2, value3, timeNano);
        return "Download failed";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private SensorEntity inputRightSensorData(String sensorType, String value1,String value2,String value3, String timeNano){
        SensorEntity sensor1 = new SensorEntity("1",  "user1"+";;"+timeNano );
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

    private void cloudTest(String sensorType, String value1, String value2, String value3, String timeNano) {

        System.out.println("cloud begin");
        String storageConnectionString =
                "DefaultEndpointsProtocol=https;AccountName=hkrtest;AccountKey=xMmOQjMFbLY6R5cHcfUAQjZXRRp50eLTiFspybB929IGYsBnuVbCME/6bcxejT2kd3rEJLBBfcQXi8e0TLfPbg==;EndpointSuffix=core.windows.net";
        try {
            System.out.println("1");
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);
            System.out.println("2");
            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();
            System.out.println("3");
            // Create the table if it doesn't exist.
            String tableName = "people";
            CloudTable cloudTable = tableClient.getTableReference(tableName);
            System.out.println("4");
            cloudTable.createIfNotExists();
            System.out.println("cloud trycatch");

            for (String table : tableClient.listTables())
            {
                // Output each table name.
                System.out.println(table);
            }





            // Create a new customer entity.
            SensorEntity sensor1 = inputRightSensorData(sensorType, value1, value2, value3 , timeNano);

            // Create an operation to add the new customer to the people table.
            TableOperation insertCustomer1 = TableOperation.insertOrReplace(sensor1);

            // Submit the operation to the table service.
            cloudTable.execute(insertCustomer1);









        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }

        System.out.println("cloud end");
    }

}
