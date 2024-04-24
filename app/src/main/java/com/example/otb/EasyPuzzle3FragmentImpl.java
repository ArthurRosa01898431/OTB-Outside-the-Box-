package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.otb.databinding.EasyPuzzle3FragmentBinding;

/**
 * Charger Puzzle.
 */
public class EasyPuzzle3FragmentImpl extends Fragment {
    public static final int PUZZLE_ID = 3;
    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;
    private EasyPuzzle3FragmentBinding mBinding;

    private BroadcastReceiver mChargingReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = EasyPuzzle3FragmentBinding.inflate(inflater, container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());

        mChargingReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                        || status == BatteryManager.BATTERY_STATUS_FULL;
                if (isCharging) {
                    easyPuzzle3Animation();
                }
            }
        };

        mBinding.easyPuzzle3Objective5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(),  1);
            }
        });
        return view;
    }

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.EasyPuzzle3HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.EasyPuzzle3HintsObjective1);

        mHintFragment.createObjectiveHints(R.drawable.easy_puzzle3_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.easy_puzzle3_obj_all_image, hintObj1Text, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getContext().registerReceiver(mChargingReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mChargingReceiver);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle3();
    }

    public void easyPuzzle3Animation() {
        if (!mDDHelper.isObjectiveNumberInDatabase("Easy", PUZZLE_ID, 5)) {
            mDDHelper.insertData(PUZZLE_ID, 5, "Easy");
            animation(getActivity(), 5, PUZZLE_ID, "easy");
        }
    }

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
     */
    private void reflectDataOnUI_Puzzle3() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(PUZZLE_ID, "Easy", (int objectiveNumber) -> {
            if (objectiveNumber == 5) {
                mBinding.easyPuzzle3Objective5.setBackgroundResource(R.drawable.blink88);
                // Add more cases as needed
            }
        });
    }
}
