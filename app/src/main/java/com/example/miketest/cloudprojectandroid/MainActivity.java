package com.example.miketest.cloudprojectandroid;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private final int SPEECH_RECOGNITION_CODE = 1;

    public Button buttonRecord;
    public Button buttonStart;
    public Button buttonStop;

    public TextView textViewStatus;
    public TextView textViewTimeStart;
    public TextView textViewTimeStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  new WebServiceConnector().execute();

        setUserLabel();

        System.out.println("User Settings:");
        System.out.println("accelero:   " + TemporaryStorage.getInstance().getAcceleroMeterOnOff());
        System.out.println("light:   " + TemporaryStorage.getInstance().getLightOnOff());
        System.out.println("proximity;    " + TemporaryStorage.getInstance().getProximityOnoff());
        System.out.println("sampling:    " + TemporaryStorage.getInstance().getSamplingRate());

        buttonRecord = (Button) findViewById( R.id.buttonRecord );
        buttonStart = (Button) findViewById( R.id.sensorStartButton );
        buttonStop = (Button) findViewById( R.id.sensorStopButton );
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewTimeStart = (TextView) findViewById(R.id.textViewTimeStart);
        textViewTimeStop = (TextView) findViewById(R.id.textViewTimeStop);

        buttonRecord.setEnabled(false);
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);

        textViewTimeStart.setText("TimeStart: ");
        textViewTimeStop.setText(" :TimeStop");

    }

    private void setUserLabel(){
        String userEmail = TemporaryStorage.getInstance().getLoggedInuserEmail();
        TextView textView=(TextView)findViewById(R.id.loginLabel);
        textView.setText("Logged in as:   " + userEmail);
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
                    TemporaryStorage.getInstance().addDataToArray("METAD", "Voice: "+convertResult );
                }
                break;
            }
        }
    }
    @Override
    protected void onResume() {


        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        System.out.println("On resume");
        //sensorManager.unregisterListener(this);
    }


    public void buttonClicked(View view) {
        switch (view.getId()) {
            case R.id.sensorStartButton:
                TemporaryStorage.getInstance().addDataToArray("METAD", "Measurement START" );
                initializeSpeechToText();
                TemporaryStorage.getInstance().setSensorStop(false);
                new SensorHandler(this).execute();

                buttonRecord.setEnabled(true);
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
                textViewStatus.setText("Measurement Ongoing");
                textViewTimeStart.setText("TimeStart: " + TemporaryStorage.getInstance().getCurrentTimeStamp());
                textViewTimeStop.setText(" :TimeStop");
                break;
            case R.id.sensorStopButton:
                //Stop sensors
                TemporaryStorage.getInstance().setSensorStop(true);

                TemporaryStorage.getInstance().addDataToArray("METAD", "Measurement STOP" );
                //Upload to cloud
                buttonRecord.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(false);

                textViewStatus.setText("Uploading to Cloud");
                //textViewTimeStart.setText("TimeStart: " + TemporaryStorage.getInstance().getCurrentTimeStamp());
                textViewTimeStop.setText(TemporaryStorage.getInstance().getCurrentTimeStamp() + " :TimeStop");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //int count = TemporaryStorage.getInstance().cloudQueueCount();
                                while (TemporaryStorage.getInstance().cloudQueueUploading2()) {
                                    textViewStatus.setText("Uploading to Cloud " +TemporaryStorage.getInstance().uploadTasksFinihed+"/"+TemporaryStorage.getInstance().uploadTasksTotal);
                                    //System.out.println("cloudQueue: "+TemporaryStorage.getInstance().cloudQueueCount());
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                                buttonRecord.setEnabled(false);
                                buttonStart.setEnabled(true);
                                buttonStop.setEnabled(false);
                                textViewStatus.setText("Upload Finished "+TemporaryStorage.getInstance().uploadTasksFinihed+"/"+TemporaryStorage.getInstance().uploadTasksTotal+"\n"+TemporaryStorage.getInstance().getCurrentTimeStamp());

                            }
                        });


                    }
                }, 1000);


                break;
            case R.id.logoutButton:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonRecord:
                initializeSpeechToText();
                break;

        }

    }






}
