package com.example.miketest.cloudprojectandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;

/**
 * Created by Henrik on 2017-05-17.
 */

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



        Switch onOffSwitchLight = (Switch)  findViewById(R.id.lightSwitch);
        onOffSwitchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
                else{

                }
            }

        });

        Switch onOffSwitchProximity = (Switch)  findViewById(R.id.proximitySwitch);
        onOffSwitchProximity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
                else{

                }
            }

        });

        Switch onOffSwitchAccelerometer = (Switch)  findViewById(R.id.accelerometerSwitch);
        onOffSwitchAccelerometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                    }
                    else{

                    }
            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
    }

    public void testButton(View view){

    }







    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.lowSampling:
                if (checked)
                    saveIntoSharedPreferences(10000000,"sensorFrequency");
                    break;
            case R.id.mediumSampling:
                if (checked)
                    saveIntoSharedPreferences(1000000,"sensorFrequency");
                    break;

            case R.id.fastSampling:
                if (checked)
                    saveIntoSharedPreferences(100000,"sensorFrequency");
                    break;
        }
    }

    private void saveIntoSharedPreferences(int value, String sharedPreferencesName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(sharedPreferencesName, value);
        editor.commit();
    }


}
