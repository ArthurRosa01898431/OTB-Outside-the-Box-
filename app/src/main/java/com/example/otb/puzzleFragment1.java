package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.Settings;
import androidx.fragment.app.Fragment;

import android.widget.Button;

import com.example.otb.databinding.DifficultyMenuFragmentBinding;
import com.example.otb.databinding.PuzzleFragment1Binding;

public class puzzleFragment1 extends Fragment implements SensorEventListener {

    private static final int BRIGHTNESS_MAX_THRESHOLD = 220;
    private static final int BRIGHTNESS_MIN_THRESHOLD = 10;
    private final hintFragment mHintFragment = new hintFragment();

    // Notices when changes have been made to the sensor.
    private SensorManager mSensorManager;

    private boolean mIsObjective1Solved;

    private boolean mIsObjective2Solved;

    PuzzleFragment1Binding mBinding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = PuzzleFragment1Binding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();

        setUpHintFragment();


        mBinding.objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.toggleHintDisplay(1);
                mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");
            }
        });

        mBinding.objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.toggleHintDisplay(2);
                mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        try {
            int brightness = android.provider.Settings.System.getInt(
                    requireContext().getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            Log.d("ARTHUR", "onSensorChanged: " + brightness);

            if ((brightness > BRIGHTNESS_MAX_THRESHOLD) && !mIsObjective1Solved) {
                puzzle1Animation(1);
            }

            if ((brightness < BRIGHTNESS_MIN_THRESHOLD) && !mIsObjective2Solved) {
                puzzle1Animation(2);
            }
        } catch (Settings.SettingNotFoundException ignored) {
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle1HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.Puzzle1HintsObjective1);
        String[] hintObj2Text = getResources().getStringArray(R.array.Puzzle1HintsObjective2);

        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj1_image, hintObj1Text, false);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj2_image, hintObj2Text, false);
    }

    private void puzzle1Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                Log.d("ARTHUR", "puzzle1Animation: 1 is solved");
                mIsObjective1Solved = true;
                animation(getActivity(),1);
                break;

            case 2:
                Log.d("ARTHUR", "puzzle1Animation: 2 is solved");
                mIsObjective2Solved = true;
                animation(getActivity(),2);
                break;
            default:
                break;
        }
    }
}