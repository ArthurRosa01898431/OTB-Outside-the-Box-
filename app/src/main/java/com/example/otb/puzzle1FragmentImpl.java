package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

import com.example.otb.databinding.PuzzleFragment1Binding;

public class puzzle1FragmentImpl extends Fragment implements puzzle1Fragment {

    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;

    private final puzzle1LogicHandler mHandler = new puzzle1LogicHandler(this);

    private PuzzleFragment1Binding mBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = PuzzleFragment1Binding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());

        mHandler.startThread();
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
    public void onDestroy() {
        mHandler.stopThread();
        super.onDestroy();
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle1(1);
    }



    @Override
    public void puzzle1Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                if (!mDDHelper.isObjectiveNumberInDatabase("Easy", 1, 1)) {
                    mDDHelper.insertData(1, objectiveNumber, "Easy");
                    animation(getActivity(), 1);
                }
                break;
            case 2:
                if (!mDDHelper.isObjectiveNumberInDatabase("Easy", 1, 2)) {
                    mDDHelper.insertData(1, objectiveNumber, "Easy");
                    animation(getActivity(), 2);
                }
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

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
     */
    public void reflectDataOnUI_Puzzle1(final int puzzleId) {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(puzzleId, "", (int objectiveNumber) -> {
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

