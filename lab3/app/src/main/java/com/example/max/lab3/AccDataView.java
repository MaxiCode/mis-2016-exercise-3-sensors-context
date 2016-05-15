package com.example.max.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by max on 14.05.16.
 */
public class AccDataView extends SensorView{

    public AccDataView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    protected void onDraw(Canvas canvas){
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.originY = (this.height - this.padding) - height/2;
        this.xAxEnd = this.width - this.padding;

        super.onDraw(canvas);

        if (sensorDataset.size() > sumPlots){
            for (int i = 1; i < sumPlots-1; ++i){
                SensorData data1 = this.sensorDataset.get(this.sensorDataset.size() - i);
                SensorData data2 = this.sensorDataset.get(this.sensorDataset.size() - i - 1 );

                drawLine(i, data1.getX(), data2.getX(), canvas, Color.RED);
                drawLine(i, data1.getY(), data2.getY(), canvas, Color.GREEN);
                drawLine(i, data1.getZ(), data2.getZ(), canvas, Color.BLUE);
                drawLine(i, calculateMaginitude(data1), calculateMaginitude(data2),
                        canvas, Color.WHITE);
            }
        } else if (this.sensorDataset.size() < sumPlots && this.sensorDataset.size() > 2) {
            for (int i = 1; i < this.sensorDataset.size(); ++i) {
                SensorData data1 = this.sensorDataset.get(this.sensorDataset.size() - i);
                SensorData data2 = this.sensorDataset.get(this.sensorDataset.size() - i - 1);

                drawLine(i, data1.getX(), data2.getX(), canvas, Color.RED);
                drawLine(i, data1.getY(), data2.getY(), canvas, Color.GREEN);
                drawLine(i, data1.getZ(), data2.getZ(), canvas, Color.BLUE);
                drawLine(i, calculateMaginitude(data1), calculateMaginitude(data2),
                        canvas, Color.WHITE);
            }
        }
    }
}
