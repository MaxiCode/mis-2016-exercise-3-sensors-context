package com.example.max.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by max on 15.05.16.
 */
public class FftDataView extends SensorView {

    FFT fft;
    double[] x = new double[sumPlots];
    double[] y = new double[sumPlots];

    public FftDataView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        fft = new FFT(sumPlots);
    }

    protected void onDraw(Canvas canvas){
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.originY = (this.height - this.padding) - height/2;
        this.xAxEnd = this.width - this.padding;

        super.onDraw(canvas);

        if(sensorDataset.size() > sumPlots) {
            x = new double[sumPlots];
            y = new double[sumPlots];
            //calc the magnitude of the sensor data
            for (int i = 0; i < sumPlots; ++i) {
                SensorData d1 = this.sensorDataset.get(this.sensorDataset.size() - 1 - i);
                x[i] = (double) calculateMaginitude(d1);
            }
            Arrays.fill(y, 0.0d);
            //do the fft
            fft.fft(x, y);
            //calc the magnitude of the resulting real and imaginary parts
            //since we have both parts real and imaginary in the result the plot is mirrored in the middle
            //so it is necessary to cut the result in the middle and go on with only the half
            for (int i = 0; i < sumPlots/2; ++i) {
                y[i] = Math.sqrt(Math.pow(x[i], 2) + Math.pow(y[i], 2));
            }
            //draw the lines
            for (int i = 1; i < (sumPlots/2) - 1; ++i) {
                float l = (float) y[i];
                float r = (float) y[i + 1];
                drawSensorLine(i, l, r, canvas, Color.YELLOW);
            }
        }
    }


    protected void drawSensorLine(int i, float val1, float val2, Canvas canvas, int color) {
        float posX1 = (i * (this.width / (sumPlots/2)));
        float posX2 = ((i + 1) * (this.width/(sumPlots/2)));

        float posY1 = this.height - (height/100)*val1;
        float posY2 = this.height - (height/100)*val2;

        Paint p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(2.0f);
        canvas.drawLine(posX1, posY1, posX2, posY2, p);
    }

    //clear all data
    public void removeData() {
        super.clearData();
        x = new double[sumPlots];
        y = new double[sumPlots];
        fft = new FFT(sumPlots);
    }

    //get average frequency
    private double getAverageFrequency() {
        double sum = 0.0d;
        for(int i=0; i<sumPlots/2; ++i) {
            sum += y[i];
        }
        return Math.round((sum/(sumPlots/2))*1000)/1000.0;
    }

    //get the max frequency
    private double getMaximumFrequency() {
        double max = 0;
        for(int i=0; i<sumPlots/2; ++i) {
            if(y[i] > max) max = y[i];
        }
        return Math.round(max*1000)/1000.0;
    }

    public String showActualActivity(TextView input) {
        // chiller-mode:
            // avg < 25

        //running-mode:
            //avg > 25 < 30

        //walking-mode:
            //avg > 30

        if(sensorDataset.size() > sumPlots) {

            input.setText(getAverageFrequency()
                    + "  -  "
                    + getMaximumFrequency());
            double avg = getAverageFrequency();
            double max = getMaximumFrequency();
            if(avg < 25) { // user is in chiller-mode
                input.setText("chilling - " + avg + " - " + max);
                return "you are in chiller-mode - some relaxing is good";
            }
            else if(avg >= 25 && avg <= 31) { // user is in walking-mode
                input.setText("walking  - " + avg + " - " + max);
                return "you are in walking-mode - go on...";
            }
            else { // user is in running-mode
                input.setText("running  - " + avg + " - " + max);
                return "running-mode - sporty...";
            }
        }
        return "";
    }
}
