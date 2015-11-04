package com.onkelsmo.stepcounter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

public class StepCounterService extends Service {
    public static Handler eventHandler;
    public static int steps = 0;
    private SensorManager sensorManager;
    private Sensor sensor;
    private ShockHandler handler = new ShockHandler();
    private ShockListener listener = new ShockListener(handler);

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(listener);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ShockHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            steps++;
            if (eventHandler != null) {
                eventHandler.sendEmptyMessage(steps);
            }
        }
    }
}
