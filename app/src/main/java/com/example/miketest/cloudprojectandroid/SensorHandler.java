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
    private float currentAccelerationValue = SensorManager.GRAVITY_EARTH;
    private float previousAccelerationValue = SensorManager.GRAVITY_EARTH;
    private float accelerationValue = 0.00f;
    private Context context;
    private SensorManager sensorManager;



    public SensorHandler(Context context) {
        this.context = context;
    }

    /**
     * <p>Applications should preferably override {@link #onCancelled(Object)}.
     * This method is invoked by the default implementation of
     * {@link #onCancelled(Object)}.</p>
     * <p>
     * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object[])} has finished.</p>
     *
     * @see #onCancelled(Object)
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
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



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(TemporaryStorage.getInstance().isSensorStop()){
            sensorManager.unregisterListener(this);
            TemporaryStorage.getInstance().printArrayList();
        }

        else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float xValue = sensorEvent.values[0];
            float yValue = sensorEvent.values[1];
            float zValue = sensorEvent.values[2];

            previousAccelerationValue = currentAccelerationValue;
            currentAccelerationValue = (float) (float) Math.sqrt(xValue * xValue + yValue * yValue + zValue * zValue);
            float accelerationValueChange = currentAccelerationValue - previousAccelerationValue;
            accelerationValue = accelerationValue * 0.9f + accelerationValueChange;
            if (true) {
                System.out.println("Accelerometer change"+" X,Y,Z: "+xValue+","+yValue+","+zValue);
                TemporaryStorage.getInstance().addDataToArray("ACCEL",""+xValue+","+yValue+","+zValue);
             //  new AzureTableConnector("accelerometer", Float.toString(xValue),Float.toString(yValue),Float.toString(zValue)).execute();

                //TemporaryStorage.getInstance().printArrayList();
            }
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            System.out.println("Light sensor value " + sensorEvent.values[0]);
            TemporaryStorage.getInstance().addDataToArray("LIGHT",""+sensorEvent.values[0]);
           // new AzureTableConnector("lightsensor", Float.toString(sensorEvent.values[0]), null, null ).execute();
            checkBatteryLevel();
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            System.out.println("Proximity sensor value " + sensorEvent.values[0]);
            TemporaryStorage.getInstance().addDataToArray("PROXI",""+sensorEvent.values[0]);
         //   new AzureTableConnector("proximitysensor", Float.toString(sensorEvent.values[0]), null, null ).execute();


        }

        /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int sensorFrequency = prefs.getInt("sensorFrequency", 10000);
        System.out.println("new value  " + sensorFrequency);

       if(sensorFreqencySaved!=sensorFrequency){
            sensorManager.unregisterListener(this);
            setUpSensors(sensorFrequency);
            sensorFreqencySaved = sensorFrequency;
        }*/




    //    checkSensorStatus("accelerometerOnOff");
      //  checkSensorStatus("proximityOnOff");
      //  checkSensorStatus("lightOnOff");

    }

 /*   private void checkSensorStatus(String sensorPrefName){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int axOnOffStored = prefs.getInt(sensorPrefName, 1);
        if(axOnOffStored==0){
            turnOffSensor(sensorPrefName);
        }
        else{
            turnOnSensor(sensorPrefName);
        }
    }


    private void turnOffSensor(String sensorName){
            if (sensorName.equals("accelerometerOnOff"))
                sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            else if (sensorName.equals("lightOnOff"))
                sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
            else if (sensorName.equals("proximityOnOff"))
                sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
    }*/

   /* private void turnOnSensor(String sensorName){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int sensorFrequency = prefs.getInt("sensorFrequency", 10000);

        if (sensorName.equals("accelerometerOnOff"))
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorFrequency);
        else if (sensorName.equals("lightOnOff"))
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),sensorFrequency);
        else if (sensorName.equals("proximityOnOff"))
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),sensorFrequency);
    }*/


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    private void setUpSensors() {
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        int sensorDelay = SensorManager.SENSOR_DELAY_NORMAL;;
        if ( TemporaryStorage.getInstance().getSamplingRate() != null) {
            if (TemporaryStorage.getInstance().getSamplingRate().equals(1))
                sensorDelay = SensorManager.SENSOR_DELAY_NORMAL;
            else if (TemporaryStorage.getInstance().getSamplingRate().equals(2))
                sensorDelay = SensorManager.SENSOR_DELAY_GAME;
            else if (TemporaryStorage.getInstance().getSamplingRate().equals(3))
                sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
        }
/*
        if(TemporaryStorage.getInstance().getAcceleroMeterOnOff().equals(1))
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorDelay);

        if(TemporaryStorage.getInstance().getProximityOnoff().equals(1))
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), sensorDelay);

        if(TemporaryStorage.getInstance().getLightOnOff().equals(1))
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorDelay);
*/
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorDelay);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), 0);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorDelay);
    }

    private void checkBatteryLevel() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryCurrentStatus = context.registerReceiver(null, intentFilter);
        int level = batteryCurrentStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryCurrentStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercentLeft = level / (float) scale;
        System.out.println("Battery level is:   " + batteryPercentLeft);

        TemporaryStorage.getInstance().addDataToArray("BATRY",""+batteryPercentLeft);


        System.out.println("USAGE:   " + sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).getPower());
        System.out.println("USAGE light:   " + sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT).getPower());
        System.out.println("USAGE prox:   " + sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY).getPower());
    }


}
