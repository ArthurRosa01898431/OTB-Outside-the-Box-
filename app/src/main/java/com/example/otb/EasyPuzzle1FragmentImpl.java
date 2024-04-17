package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

import com.example.otb.databinding.EasyPuzzle1FragmentBinding;

public class EasyPuzzle1FragmentImpl extends Fragment implements EasyPuzzle1Fragment {
    private static final int PUZZLE_ID = 1;
    private final hintFragment mHintFragment = new hintFragment();
    private DatabaseHelper mDDHelper;

    private final EasyPuzzle1LogicHandler mHandler = new EasyPuzzle1LogicHandler(this);

    private EasyPuzzle1FragmentBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = EasyPuzzle1FragmentBinding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());

        mHandler.startThread();
        mBinding.easyPuzzle1Objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(), 1);

            }
        });

        mBinding.easyPuzzle1Objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(),  2);

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mHandler.stopThread();
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle1();
    }

    @Override
    public void easyPuzzle1Animation(int objectiveNumber) {
        if (!mDDHelper.isObjectiveNumberInDatabase ("Easy", PUZZLE_ID, objectiveNumber)) {
            mDDHelper.insertData(PUZZLE_ID, objectiveNumber, "Easy");
            animation(getActivity(), objectiveNumber, PUZZLE_ID, "easy");
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
        String[] hintAllObjText = getResources().getStringArray(R.array.EasyPuzzle1HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.EasyPuzzle1HintsObjective1);
        String[] hintObj2Text = getResources().getStringArray(R.array.EasyPuzzle1HintsObjective2);

        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj1_image, hintObj1Text, false);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj2_image, hintObj2Text, false);
    }

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
     */
    public void reflectDataOnUI_Puzzle1() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(PUZZLE_ID, "Easy", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.easyPuzzle1Objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                case 2:
                    mBinding.easyPuzzle1Objective2.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }
}
