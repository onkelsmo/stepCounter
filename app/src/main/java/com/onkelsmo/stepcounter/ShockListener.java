package com.onkelsmo.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;

public class ShockListener implements SensorEventListener {
    private static final float LEVEL = 1.0f;
    private Handler handler;
    private boolean stepStarted;

    public void setLevel(float level) {
        this.level = level;
    }

    private float level = LEVEL;

    public ShockListener(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float y = event.values[1];
        if (stepStarted) {
            if (y > level) {
                stepStarted = false;
                handler.sendEmptyMessage(1);
            }
        } else {
            if (y < -level) {
                stepStarted = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
