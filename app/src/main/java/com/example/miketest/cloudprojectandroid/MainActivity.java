package com.example.miketest.cloudprojectandroid;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private final int SPEECH_RECOGNITION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  new WebServiceConnector().execute();

    }


    private void initializeSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> speechConvertedToText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String convertResult = speechConvertedToText.get(0); //Vi skickar bara upp detta till cloud så blir det lättare
                    Toast.makeText(getApplicationContext(), "To text   " + convertResult, Toast.LENGTH_SHORT).show();
                    System.out.println("To text   " + convertResult);
                }
                break;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
        //sensorManager.unregisterListener(this);
    }

    public void buttonClicked(View view) {
        Intent intent;

        switch (view.getId()) {
           // case R.id.settingButton:
             //   intent = new Intent(MainActivity.this, SettingActivity.class);
               // startActivity(intent);
               // break;
            case R.id.sensorStartButton:
                initializeSpeechToText();
                new SensorHandler(this).execute();
                break;
            case R.id.sensorStopButton:
                //Upload to cloud
                initializeSpeechToText();
                break;
        }

    }





}
