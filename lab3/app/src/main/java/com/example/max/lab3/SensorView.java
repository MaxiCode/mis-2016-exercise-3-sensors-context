package com.example.max.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by max on 14.05.16.
 * Tis is an abstract class to prepare some functionality for
 * AccDataView and FFTView.
 */
public abstract class SensorView extends View{

    float width;
    float height;
    float padding = 50.0f;
    float originY;
    float xAxEnd;
    public int sumPlots = 64;
    ArrayList<SensorData> sensorDataset;


    public SensorView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        sensorDataset = new ArrayList<>(sumPlots);
    }

    public void clearData(){
        sensorDataset.clear();
        sensorDataset = new ArrayList<>(sumPlots);
    }

    public void addData(SensorData d){
        sensorDataset.add(d);
        // keep memory in mind
        if (sensorDataset.size() > sumPlots+1){
            sensorDataset.remove(0);
        }
    }

    public float calculateMaginitude(SensorData d){
        double x = (double)d.getX();
        double y = (double)d.getY();
        double z = (double)d.getZ();
        return (float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
    }

    protected void drawLine(int i, float v1, float v2, Canvas canvas, int color){
        float pos_x1 = (i * ((this.width) / sumPlots));
        float pos_y1 = (this.height / 2 - ((this.height/2)/100) * v1);
        float pos_x2 = ((i + 1) * ((this.width/sumPlots)));
        float pos_y2 = (this.height / 2 - ((this.height/2)/100) * v2);

        Paint line = new Paint();
        line.setColor(color);
        line.setStrokeWidth(2.0f);
        canvas.drawLine(pos_x1, pos_y1, pos_x2, pos_y2, line);
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }
}
