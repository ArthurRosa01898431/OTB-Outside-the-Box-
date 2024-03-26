package com.example.otb;

import android.os.Handler;

public class EasyPuzzle1LogicHandler {
    private static final int BRIGHTNESS_MAX_THRESHOLD = 220;
    private static final int BRIGHTNESS_MIN_THRESHOLD = 10;
    private final EasyPuzzle1Fragment mFragment;
    private int mPreviousBrightness = -1;

    private brightnessThread mBrightnessThread;


    EasyPuzzle1LogicHandler(EasyPuzzle1Fragment fragment) {
        mFragment = fragment;
    }

    void startThread() {
        mBrightnessThread = new brightnessThread();
        mBrightnessThread.start();
    }

    void stopThread() {
        mBrightnessThread.stopThread();
    }
    void handleSensorChange() {
        int brightness = mFragment.getAndroidBrightness();

        if (brightness > BRIGHTNESS_MAX_THRESHOLD) {
            mFragment.easyPuzzle1Animation(1);
        }

        if (brightness < BRIGHTNESS_MIN_THRESHOLD) {
            mFragment.easyPuzzle1Animation(2);
        }
    }
    private class brightnessThread extends Thread {
        private boolean mIsRunning = true;
        private final Handler mHandler;

        public brightnessThread() {
            mHandler = new Handler();
        }

        @Override
        public void run() {
            while (mIsRunning) {
                mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Get the current screen brightness setting
                            int currentBrightness = mFragment.getAndroidBrightness();

                            // Check if the brightness has changed
                            if (currentBrightness != mPreviousBrightness) {
                                mPreviousBrightness = currentBrightness;
                                handleSensorChange();
                            }
                        }
                    });
                    // Wait for 3 second.
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        public void stopThread() {
            mIsRunning = false;
            mHandler.removeCallbacksAndMessages(null); // Remove any pending callbacks
        }
    }
}

