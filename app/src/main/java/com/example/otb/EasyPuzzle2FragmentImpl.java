package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.otb.databinding.EasyPuzzle2FragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EasyPuzzle2FragmentImpl extends Fragment implements EasyPuzzle2Fragment {
    private static final int PUZZLE_ID = 2;
    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;
    private EasyPuzzle2FragmentBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = EasyPuzzle2FragmentBinding.inflate(inflater,
                container, false);

        EasyPuzzle2ViewModel mViewModel = new ViewModelProvider(getActivity()).get(EasyPuzzle2ViewModel.class);

        mViewModel.isDark().observe(getViewLifecycleOwner(), isDark -> {
            Log.d("ARTHUR", "onCreateView: " + isDark);
            if (isDark) {
                EasyPuzzle2Animation(3);
            } else {
                EasyPuzzle2Animation(4);
            }
        });

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());

        mBinding.easyPuzzle2Objective3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(), 1);
            }
        });

        mBinding.easyPuzzle2Objective4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(), 2);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Destroy ViewModel once user exits puzzle.
        getActivity().getViewModelStore().clear();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle2();
    }

    @Override
    public void EasyPuzzle2Animation(int objectiveNumber) {
        if (!mDDHelper.isObjectiveNumberInDatabase("Easy", PUZZLE_ID, objectiveNumber)) {
            mDDHelper.insertData(PUZZLE_ID, objectiveNumber, "Easy");
            animation(getActivity(), objectiveNumber, PUZZLE_ID, "easy");
        }
    }

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.EasyPuzzle2HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.EasyPuzzle2HintsObjective1);
        String[] hintObj2Text = getResources().getStringArray(R.array.EasyPuzzle2HintsObjective2);

        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj2_image, hintObj1Text, false);
        mHintFragment.createObjectiveHints(R.drawable.puzzle1_obj1_image, hintObj2Text, false);
    }

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
     */
    public void reflectDataOnUI_Puzzle2() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(PUZZLE_ID, "Easy", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 3:
                    mBinding.easyPuzzle2Objective3.setBackgroundResource(R.drawable.blink88);
                    break;
                case 4:
                    mBinding.easyPuzzle2Objective4.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }
}
