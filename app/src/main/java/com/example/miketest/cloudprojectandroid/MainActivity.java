package com.example.miketest.cloudprojectandroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    }

    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
        //sensorManager.unregisterListener(this);
    }

    public void buttonClicked(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.settingButton:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.sensorStartButton:

                break;
            case R.id.sensorStopButton:

                break;
        }

    }



}
