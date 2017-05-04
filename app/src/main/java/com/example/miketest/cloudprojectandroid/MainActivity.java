package com.example.miketest.cloudprojectandroid;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private float currentAccelerationValue = SensorManager.GRAVITY_EARTH;
    private float previousAccelerationValue = SensorManager.GRAVITY_EARTH;
    private float accelerationValue = 0.00f;
    private Sensor sensor;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        setUpAccelerometer();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    private void setUpAccelerometer() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xValue = sensorEvent.values[0];
        float yValue = sensorEvent.values[1];
        float zValue = sensorEvent.values[2];

        previousAccelerationValue = currentAccelerationValue;
        currentAccelerationValue = (float) (float) Math.sqrt(xValue * xValue + yValue * yValue + zValue * zValue);
        float accelerationValueChange = currentAccelerationValue - previousAccelerationValue;
        accelerationValue = accelerationValue * 0.9f + accelerationValueChange;
        if (accelerationValue > 15) {
            System.out.println("Sensor change");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
