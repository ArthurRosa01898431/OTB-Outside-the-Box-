package com.example.otb;
import java.util.List;

public abstract class MeasurableSensor {
    protected int sensorType;
    protected OnSensorValuesChangedListener onSensorValuesChanged;

    public MeasurableSensor(int sensorType) {
        this.sensorType = sensorType;
    }

    protected abstract boolean doesSensorExist();
    protected abstract void startListening();
    protected abstract void stopListening();

    public void setOnSensorValuesChangedListener(OnSensorValuesChangedListener listener) {
        this.onSensorValuesChanged = listener;
    }

    public interface OnSensorValuesChangedListener {
        void onValuesChanged(List<Float> values);
    }
}
