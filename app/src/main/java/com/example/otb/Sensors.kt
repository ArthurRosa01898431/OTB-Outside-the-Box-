package com.example.otb

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class LightSensor(
        context: Context
): AndroidSensor(
        context,
        PackageManager.FEATURE_SENSOR_LIGHT,
        Sensor.TYPE_LIGHT
)