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
    private boolean mIsObjective1Solved;
    private boolean mIsObjective2Solved;

    puzzle1LogicHandler(puzzle1Fragment fragment) {
        mFragment = fragment;
    }
    
    
    public void setIsObjective1Solved(boolean mIsObjective1Solved) {
        this.mIsObjective1Solved = mIsObjective1Solved;
    }

    public void setIsObjective2Solved(boolean mIsObjective2Solved) {
        this.mIsObjective2Solved = mIsObjective2Solved;
    }

    void handleSensorChange() {
        int brightness = mFragment.getAndroidBrightness();

        if ((brightness > BRIGHTNESS_MAX_THRESHOLD) && !mIsObjective1Solved) {
            mFragment.puzzle1Animation(1);
        }

        if ((brightness < BRIGHTNESS_MIN_THRESHOLD) && !mIsObjective2Solved) {
            mFragment.puzzle1Animation(2);
        }
    }
}
