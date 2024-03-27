package com.example.otb

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EasyPuzzle2ViewModel @Inject constructor(
        private val lightSensor: MeasurableSensor
): ViewModel() {
    val isDark: MutableLiveData<Boolean> = MutableLiveData();
    init {
        lightSensor.startListening()
        lightSensor.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
            Log.d("ARTHUR", ": $lux")
            if (lux < 10f) {
                isDark.value = true
            } else if (lux > 1000f) {
                isDark.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        lightSensor.stopListening()
    }
}