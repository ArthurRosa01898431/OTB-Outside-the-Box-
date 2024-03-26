package com.example.otb;

import androidx.compose.runtime.MutableState;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EasyPuzzle2ViewModel extends ViewModel {
    private MeasurableSensor lightSensor;
    private MutableState<Boolean> isDark;

    @Inject
    public EasyPuzzle2ViewModel(MeasurableSensor lightSensor) {
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