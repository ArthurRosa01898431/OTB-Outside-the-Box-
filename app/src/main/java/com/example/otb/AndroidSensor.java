package com.example.otb;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.List;

public abstract class AndroidSensor extends MeasurableSensor implements SensorEventListener {
    private final Context context;
    private final String sensorFeature;
    private SensorManager sensorManager;
    private Sensor sensor;

    public AndroidSensor(Context context, String sensorFeature, int sensorType) {
        super(sensorType);
        this.context = context;
        this.sensorFeature = sensorFeature;
    }

    @Override
    public boolean doesSensorExist() {
        return context.getPackageManager().hasSystemFeature(sensorFeature);
    }

    @Override
    public void startListening() {
        if (!doesSensorExist()) {
            return;
        }
        if (sensorManager == null && sensor == null) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(sensorType);
        }
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void stopListening() {
        if (!doesSensorExist() || sensorManager == null) {
            return;
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!doesSensorExist()) {
            return;
        }
        if (event != null && event.sensor != null && event.sensor.getType() == sensorType) {
            if (onSensorValuesChanged != null) {
                onSensorValuesChanged.onValuesChanged(toList(event.values));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Implementation specific to accuracy change handling
    }

    private List<Float> toList(float[] array) {
        List<Float> list = new java.util.ArrayList<>(array.length);
        for (float value : array) {
            list.add(value);
        }
        return list;
    }
}