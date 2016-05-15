package com.example.max.lab3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SensorsAndContext extends Activity implements SensorEventListener, SeekBar.OnSeekBarChangeListener {

    // source: http://stackoverflow.com/questions/5180187/how-do-i-use-the-android-accelerometer
    SensorManager sensorManager;
    Sensor accSensor;
    AccDataView accDataView;
    SeekBar accSeekBar;
    TextView textView;

    String movementState = "";


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
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fUser){

    }
}
