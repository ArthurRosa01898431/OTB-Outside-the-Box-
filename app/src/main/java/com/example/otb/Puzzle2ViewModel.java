package com.example.otb;

import static androidx.compose.runtime.SnapshotStateKt.mutableStateOf;

import androidx.compose.runtime.MutableState;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Puzzle2ViewModel extends ViewModel {
    private MeasurableSensor lightSensor;
    private MutableState<Boolean> isDark;

    @Inject
    public Puzzle2ViewModel(MeasurableSensor lightSensor) {
        this.lightSensor = lightSensor;
        init();
    }

    private void init() {
        lightSensor.startListening();
        lightSensor.setOnSensorValuesChangedListener(values -> {
            float lux = values.get(0);
            isDark.setValue(lux < 60f);
        });
    }

    public MutableState<Boolean> getIsDark() {
        return isDark;
    }
}