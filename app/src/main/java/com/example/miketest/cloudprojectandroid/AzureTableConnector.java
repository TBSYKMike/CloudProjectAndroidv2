package com.example.miketest.cloudprojectandroid;

import android.os.AsyncTask;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

/**
 * Created by Henrik on 2017-05-05.
 */

public class AzureTableConnector extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        // we use the OkHttp library from https://github.com/square/okhttp
        cloudTest();
        return "Download failed";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private void cloudTest() {

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

        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }

        System.out.println("cloud end");
    }

}
