package com.example.otb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.net.Uri;

public class brightness_puzzle_easy_puzzlefragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brightness_puzzle_easy_puzzlefragment, container, false);
        return view;
    }

    // get the current screen brightness
    public int getScreenBrightness() throws Settings.SettingNotFoundException {
        return (Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS));
    }

    public void setSystemScreenBrightness(int brightnessValue) { // brightnessValue: 0 to MAX
        if (Settings.System.canWrite(getContext())) {
            Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
        }
        else {
            // give the user permission to access settings to change brightness
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
            }
        }


    public boolean isPuzzleSolved() throws Settings.SettingNotFoundException {
        int currentBrightness = getScreenBrightness();
        int targetBrightness = 200; // Example threshold
        return currentBrightness > targetBrightness;
    }
}