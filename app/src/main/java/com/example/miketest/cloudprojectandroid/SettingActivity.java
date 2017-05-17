package com.example.miketest.cloudprojectandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by Henrik on 2017-05-17.
 */

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
                    updateSharedStorageSamplingRate(10000000);
                    break;
            case R.id.mediumSampling:
                if (checked)
                    updateSharedStorageSamplingRate(1000000);
                    break;

            case R.id.fastSampling:
                if (checked)
                    updateSharedStorageSamplingRate(100000);
                    break;
        }
    }

    private void updateSharedStorageSamplingRate(int samplingRate){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sensorFrequency", samplingRate);
        editor.commit();
    }


}
