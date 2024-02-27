package com.example.otb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.Settings;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;

public class brightness_puzzle_easy_puzzlefragment extends Fragment {
    private final hintFragment mHintFragment = new hintFragment();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.brightness_puzzle_easy_puzzlefragment, container, false);

        setUpHintFragment();

        Button objective0 = view.findViewById(R.id.puzzle1_objective1);
        Button objective1 = view.findViewById(R.id.puzzle1_objective2);

        objective0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.toggleHintDisplay(1);
                mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");
            }
        });

        objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.toggleHintDisplay(2);
                mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");
            }
        });

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

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle1HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.Puzzle1HintsObjective1);
        String[] hintObj2Text = getResources().getStringArray(R.array.Puzzle1HintsObjective2);

        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj1_image, hintObj1Text, false);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj2_image, hintObj2Text, false);
    }
}