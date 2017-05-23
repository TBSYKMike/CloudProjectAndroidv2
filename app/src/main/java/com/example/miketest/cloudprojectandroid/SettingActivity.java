package com.example.miketest.cloudprojectandroid;

/**
 * Created by Henrik on 2017-05-17.
 */

/*public class SettingActivity extends AppCompatActivity {


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

       Switch onOffSwitchLight = (Switch)  findViewById(R.id.lightSwitch);
        onOffSwitchLight.setChecked(getValueFromSharedPreferences("lightOnfOff"));
        onOffSwitchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSensorStatus(isChecked, "lightOnOff");
            }
        });

        Switch onOffSwitchProximity = (Switch)  findViewById(R.id.proximitySwitch);
        onOffSwitchLight.setChecked(getValueFromSharedPreferences("proximityOnOff"));
        onOffSwitchProximity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSensorStatus(isChecked,"proximityOnOff");
            }
        });

        Switch onOffSwitchAccelerometer = (Switch)  findViewById(R.id.accelerometerSwitch);
        onOffSwitchLight.setChecked(getValueFromSharedPreferences("accelerometerOnOff"));
        onOffSwitchAccelerometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSensorStatus(isChecked, "accelerometerOnOff");
            }
        });
    }

  /*  private void setSensorStatus(boolean isChecked, String sharedPreferenceKey){
        if(isChecked){
            saveIntoSharedPreferences(1, sharedPreferenceKey);
        }
        else{
            saveIntoSharedPreferences(0, sharedPreferenceKey);
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
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

    private boolean getValueFromSharedPreferences(String sensorPrefName){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int status = prefs.getInt(sensorPrefName, 1);
        if(status==1)
            return true;
        else
            return false;
    }

*/


