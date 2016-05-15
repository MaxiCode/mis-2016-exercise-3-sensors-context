package com.example.max.lab3;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SensorsAndContext extends Activity implements SensorEventListener, SeekBar.OnSeekBarChangeListener {

    // source: http://stackoverflow.com/questions/5180187/how-do-i-use-the-android-accelerometer
    SensorManager sensorManager;
    Sensor accSensor;
    AccDataView accDataView;
    SeekBar accSeekBar;
    TextView textView;
    FftDataView fftDataView;
    SeekBar fftSeekBar;



    String movementState = "";
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_and_context);

        // connect graphical elements
        accDataView = (AccDataView) findViewById(R.id.accView);
        accDataView.setBackgroundColor(Color.GRAY);

        accSeekBar = (SeekBar) findViewById(R.id.accSeekBar);
        accSeekBar.setOnSeekBarChangeListener(this);

        textView = (TextView) findViewById(R.id.textview);

        fftDataView = (FftDataView) findViewById(R.id.fftView);
        fftDataView.setBackgroundColor(Color.GRAY);

        fftSeekBar = (SeekBar) findViewById(R.id.fftSeekBar);
        fftSeekBar.setOnSeekBarChangeListener(this);


        // read Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),100000);

        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // works :)
        //if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        //    System.out.println("Values x: " + event.values[0] + " y: " + event.values[1] + " z: " + event.values[2]);
        //}
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            SensorData data = new SensorData(event.values[0], event.values[1], event.values[2]);
            this.accDataView.addData(data);
            this.accDataView.invalidate();

            this.fftDataView.addData(data);
            this.fftDataView.invalidate();

            if (!movementState.equals(fftDataView.showActualActivity(textView)) &&
                    !fftDataView.showActualActivity(textView).equals("")){
                movementState = fftDataView.showActualActivity(textView);


            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar){

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar){
        if (seekBar.getId() == accSeekBar.getId()){
            int value = seekBar.getProgress();
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            // repaint and clear all
            accDataView.clearData();
            accDataView.invalidate();
            // Other stuff

            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), value * 1000);
        } else if (seekBar.getId() == fftSeekBar.getId()){
            int value = seekBar.getProgress()*4;
            System.out.println("Size: " + value);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) fftDataView .getLayoutParams();
            params.height = value;
            fftDataView.setLayoutParams(params);

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fUser){

    }
}
