package com.example.miketest.cloudprojectandroid;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.BatteryManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Henrik on 2017-05-05.
 */

public class SensorHandler extends AsyncTask<String, Void, String> implements SensorEventListener {

    private Context context;
    private SensorManager sensorManager;


    public SensorHandler(Context context) {
        this.context = context;
    }

    @Override
    protected void onCancelled() {

        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... params) {
        System.out.println("SensorHandler");
        setUpSensors();
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }

    private long delayMili = 500; // set delay in milliseconds
    private long battery0Time = System.currentTimeMillis();
    private long sensor1Time = System.currentTimeMillis();
    private long sensor2Time = System.currentTimeMillis();
    private long sensor3Time = System.currentTimeMillis();
    /*
    private long sensor4Time = System.currentTimeMillis();
     */


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (TemporaryStorage.getInstance().isSensorStop()) {
            // stop all sensor readings
            sensorManager.unregisterListener(this);

            // Add stop data to ArrayList
            TemporaryStorage.getInstance().addDataToArray("METAD", "Measurement STOP");
            threadSleep(2000);

            // run the Upload function.
            TemporaryStorage.getInstance().printArrayListV2();
            return;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && (System.currentTimeMillis() - sensor1Time) > delayMili) {
            sensor1Time = System.currentTimeMillis();

            float xValue = sensorEvent.values[0];
            float yValue = sensorEvent.values[1];
            float zValue = sensorEvent.values[2];

            System.out.println("Accelerometer change" + " X,Y,Z: " + xValue + "," + yValue + "," + zValue);
            TemporaryStorage.getInstance().addDataToArray("ACCEL", "" + xValue + "," + yValue + "," + zValue);

        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT && (System.currentTimeMillis() - sensor2Time) > delayMili) {
            sensor2Time = System.currentTimeMillis();

            float value1 = sensorEvent.values[0];

            System.out.println("Light sensor value " + value1);
            TemporaryStorage.getInstance().addDataToArray("LIGHT", "" + value1);

        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY && (System.currentTimeMillis() - sensor3Time) > delayMili) {
            sensor3Time = System.currentTimeMillis();

            float value1 = sensorEvent.values[0];

            threadSleep(25);
            System.out.println("Proximity sensor value " + value1);
            TemporaryStorage.getInstance().addDataToArray("PROXI", "" + value1);

        }/*

        else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && (System.currentTimeMillis() - sensor4Time) > delayMili) {
            // Sensor4Time is to set the reading delay
            sensor4Time = System.currentTimeMillis();

            // If you have multiple values from the device
            float xValue = sensorEvent.values[0];
            float yValue = sensorEvent.values[1];
            float zValue = sensorEvent.values[2];

            System.out.println("Accelerometer change" + " X,Y,Z: " + xValue + "," + yValue + "," + zValue);
            // Add the data to ArrayList
            TemporaryStorage.getInstance().addDataToArray("ACCEL", "" + xValue + "," + yValue + "," + zValue);

        }*/


        if ((System.currentTimeMillis() - battery0Time) > delayMili) {
            threadSleep(25);
            battery0Time = System.currentTimeMillis();
            checkBatteryLevel();
            threadSleep(25);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void setUpSensors() {

        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        int sensorDelay = SensorManager.SENSOR_DELAY_NORMAL;


        if (TemporaryStorage.getInstance().getSamplingRate().equals("1")) {
            delayMili = 500;
        } else if (TemporaryStorage.getInstance().getSamplingRate().equals("2")) {
            delayMili = 1000;
        } else if (TemporaryStorage.getInstance().getSamplingRate().equals("3")) {
            delayMili = 2000;
        }


        if (TemporaryStorage.getInstance().getAcceleroMeterOnOff().equals("1")) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorDelay);
        }
        if (TemporaryStorage.getInstance().getProximityOnoff().equals("1")) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), sensorDelay);
        }
        if (TemporaryStorage.getInstance().getLightOnOff().equals("1")) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorDelay);
        }
        /*
        // Example Code of adding new sensor
        if (TemporaryStorage.getInstance().getLightOnOff().equals("1")) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorDelay);
        }
        */

    }

    private void checkBatteryLevel() {

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryCurrentStatus = context.registerReceiver(null, intentFilter);
        int level = batteryCurrentStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryCurrentStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercentLeft = level / (float) scale;
        System.out.println("Battery level is:   " + batteryPercentLeft);

        // Add data to ArrayList
        TemporaryStorage.getInstance().addDataToArray("BATRY", "" + batteryPercentLeft);

    }

    private void threadSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
