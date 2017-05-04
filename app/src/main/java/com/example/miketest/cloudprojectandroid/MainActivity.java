package com.example.miketest.cloudprojectandroid;


import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private float currentAccelerationValue = SensorManager.GRAVITY_EARTH;
    private float previousAccelerationValue = SensorManager.GRAVITY_EARTH;
    private float accelerationValue = 0.00f;

    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSensors();



    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
        sensorManager.unregisterListener(this);
    }

    private void setUpSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), sensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float xValue = sensorEvent.values[0];
            float yValue = sensorEvent.values[1];
            float zValue = sensorEvent.values[2];

            previousAccelerationValue = currentAccelerationValue;
            currentAccelerationValue = (float) (float) Math.sqrt(xValue * xValue + yValue * yValue + zValue * zValue);
            float accelerationValueChange = currentAccelerationValue - previousAccelerationValue;
            accelerationValue = accelerationValue * 0.9f + accelerationValueChange;
            if (accelerationValue > 15) {
                System.out.println("Accelerometer change");
            }
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            System.out.println("Light sensor value " + sensorEvent.values[0]);
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            System.out.println("Proximity sensor value " + sensorEvent.values[0]);
            checkBatteryLevel();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void checkBatteryLevel(){
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryCurrentStatus = getApplicationContext().registerReceiver(null, intentFilter);
        int level = batteryCurrentStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryCurrentStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercentLeft = level / (float)scale;
        System.out.println("Battery level is:   " + batteryPercentLeft);
    }

}
