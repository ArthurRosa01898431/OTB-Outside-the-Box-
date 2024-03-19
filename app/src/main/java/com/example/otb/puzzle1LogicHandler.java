package com.example.otb;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

public class puzzle1LogicHandler {
    private static final int BRIGHTNESS_MAX_THRESHOLD = 220;
    private static final int BRIGHTNESS_MIN_THRESHOLD = 10;
    private final puzzle1Fragment mFragment;

    puzzle1LogicHandler(puzzle1Fragment fragment) {
        mFragment = fragment;
    }


    void handleSensorChange() {
        int brightness = mFragment.getAndroidBrightness();

        if ((brightness > BRIGHTNESS_MAX_THRESHOLD)) {
            mFragment.puzzle1Animation(1);
        }

        if ((brightness < BRIGHTNESS_MIN_THRESHOLD)) {
            mFragment.puzzle1Animation(2);
        }
    }
}
