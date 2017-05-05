package com.example.miketest.cloudprojectandroid;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

// Include the following imports to use table APIs


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new SensorHandler(this).execute();
        new AzureTableConnector().execute();

    }

    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
        //sensorManager.unregisterListener(this);
    }


}
