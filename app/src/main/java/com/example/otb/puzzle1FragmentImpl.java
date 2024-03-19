package com.example.otb;

import static com.example.otb.MainActivity.animation;
import static com.example.otb.MainActivity.isObjectiveNumberInDatabase;
import static com.example.otb.MainActivity.reflectDataOnUI;

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

import com.example.otb.databinding.PuzzleFragment1Binding;

public class puzzle1FragmentImpl extends Fragment implements SensorEventListener, puzzle1Fragment {
    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;

    private final puzzle1LogicHandler mHandler = new puzzle1LogicHandler(this);

    // Notices when changes have been made to the sensor.
    private SensorManager mSensorManager;

    private PuzzleFragment1Binding mBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = PuzzleFragment1Binding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle1(1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mHandler.handleSensorChange();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void puzzle1Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                if (!isObjectiveNumberInDatabase(mDDHelper.getPuzzleData(1, "Easy"), 1)) {
                    animation(getActivity(), 1);
                    mDDHelper.insertData(1, objectiveNumber, "Easy");
                    break;
                }
            case 2:
                if (!isObjectiveNumberInDatabase(mDDHelper.getPuzzleData(1, "Easy"), 2)) {
                    animation(getActivity(), 2);
                    mDDHelper.insertData(1, objectiveNumber, "Easy");
                    break;
                }
            default:
                break;
        }
    }

    @Override
    public int getAndroidBrightness() {
        try {
            return Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1; // Default or error value
        }
    }

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle1HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.Puzzle1HintsObjective1);
        String[] hintObj2Text = getResources().getStringArray(R.array.Puzzle1HintsObjective2);

        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj1_image, hintObj1Text, false);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj2_image, hintObj2Text, false);
    }



    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
     */
    public void reflectDataOnUI_Puzzle1(final int puzzleId) {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        reflectDataOnUI(puzzleId, "", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                case 2:
                    mBinding.objective2.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }
}

