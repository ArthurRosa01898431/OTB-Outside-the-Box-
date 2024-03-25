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


import com.example.otb.databinding.PuzzleFragment2Binding;

public class puzzle2FragmentImpl extends Fragment implements puzzle2Fragment {

    // keep these two, give the hint fragment and the database helper
    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;

    private final puzzle2LogicHandler mHandler = new puzzle2LogicHandler(this);

    private PuzzleFragment2Binding mBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = PuzzleFragment2Binding.inflate(inflater, container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());
        mBinding.objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.goToFileApp();
                mHintFragment.toggleHintDisplay(1);
                mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle2(1);
    }


    @Override
    public void puzzle2Animation(int objectiveNumber) {
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


    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle1HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.Puzzle1HintsObjective1);
        String[] hintObj2Text = getResources().getStringArray(R.array.Puzzle1HintsObjective2);

        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj1_image, hintObj1Text, false);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj2_image, hintObj2Text, false);
    }


    public void reflectDataOnUI_Puzzle2(final int puzzleId) {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        reflectDataOnUI(puzzleId, "", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }

}
