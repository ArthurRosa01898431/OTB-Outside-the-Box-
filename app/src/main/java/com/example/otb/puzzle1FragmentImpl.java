package com.example.otb;

import static com.example.otb.MainActivity.animation;
import static com.example.otb.MainActivity.fetchPuzzleData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
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

import java.sql.ResultSet;
import java.sql.SQLException;

public class puzzle1FragmentImpl extends Fragment implements SensorEventListener, puzzle1Fragment {
    private final hintFragment mHintFragment = new hintFragment();
    private DatabaseHelper dbHelper;

    private final puzzle1LogicHandler mHandler = new puzzle1LogicHandler(this);

    // Notices when changes have been made to the sensor.
    private SensorManager mSensorManager;

    PuzzleFragment1Binding mBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = PuzzleFragment1Binding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();
        dbHelper = new DatabaseHelper(getContext());
        setUpHintFragment();

        // TODO Put all database related stuff here.
        //fetchPuzzleData(1);


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
        // Call your updateData method to update the UI based on the database
        updateData(1); // Make sure to pass the correct puzzle ID here
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
                Log.d("ARTHUR", "puzzle1Animation: 1 is solved");
                mHandler.setIsObjective1Solved(true); ; // TODO replace tell database that it been solved.
                animation(getActivity(),1);
                dbHelper.insertData(1, objectiveNumber, "Easy");
                break;

            case 2:
                Log.d("ARTHUR", "puzzle1Animation: 2 is solved");
                mHandler.setIsObjective2Solved(true); // TODO replace tell database that it been solved.
                animation(getActivity(),2);
                dbHelper.insertData(1, objectiveNumber, "Easy");
                break;
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


    // fetch data from database
    // once you have data update the textview
    // New method to fetch puzzle data

    public void updateData(final int puzzleId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ResultSet rs = MainActivity.fetchPuzzleData(puzzleId, "easy");
                    if (!rs.next()) {
                        // ResultSet is empty
                        return;
                    }
                    rs.beforeFirst();

                    // Since we are going to update the UI
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (rs.next()) {
                                    int objectiveNumber = rs.getInt("objectiveNumber");
                                    String difficulty = rs.getString("difficulty");
                                    // Log.d("UpdateData", "Objective Number: " + objectiveNumber + ", Difficulty: " + difficulty);
                                    updateButtonState(objectiveNumber, difficulty);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void updateButtonState(int objectiveNumber, String difficulty) {
        int buttonId;
        switch (objectiveNumber) {
            case 1:
                buttonId = R.id.puzzle1_Objective1;
                break;
            case 2:
                buttonId = R.id.puzzle1_Objective2;
                break;
            // Add more cases as needed
            default:
                return; // Invalid ID or not interested in updating
        }

        View button = getView().findViewById(buttonId);
        if (button != null) {
            if ("solved".equals(difficulty)) {
                button.setBackgroundResource(R.drawable.button_filled);
            } else {
                button.setBackgroundResource(R.drawable.button_outline);
            }
        }
    }


}






//        Cursor cursor = dbHelper.getPuzzleData(puzzleId);
//        if(cursor != null && cursor.moveToFirst()) {
//            // Use the fetched data as required
//            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("puzzle_id"));
//            @SuppressLint("Range") int ObjNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
//            @SuppressLint("Range") String Dif = cursor.getString(cursor.getColumnIndex("difficulty"));
//            // You can now use this data as needed, for example, to check objectives
//
//            cursor.close();
//        }

