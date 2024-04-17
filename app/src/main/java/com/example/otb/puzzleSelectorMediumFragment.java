package com.example.otb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.example.otb.databinding.PuzzleSelectorMediumFragmentBinding;


/**
 * Fragment that shows (Easy, Medium, Hard) difficulty puzzles.
 */
public class puzzleSelectorMediumFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.
    private PuzzleSelectorMediumFragmentBinding mBinding;

    private DatabaseHelper mDDHelper;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PuzzleSelectorMediumFragmentBinding.inflate(inflater, container, false);
        mDDHelper = new DatabaseHelper(getContext());
        // Inflate the layout for this fragment
        View view = mBinding.getRoot();
        return view;
    }

    /*
        Show on the UI that an medium objective is solved if
        it is already solved according to the data base.
     */

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reflectDataOnUISelectorMedium();


        mBinding.mediumPuzzle1Objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorMediumFragment_to_QR_puzzle_medium);
            }
        });

        mBinding.mediumPuzzle2Objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorMediumFragment_to_ai_camera_puzzle_medium);
            }
        });

        mBinding.mediumPuzzle2Objective3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorMediumFragment_to_ai_camera_puzzle_medium);
            }
        });

        mBinding.mediumPuzzle2Objective3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorMediumFragment_to_ai_camera_puzzle_medium);
            }
        });

    }

    public void reflectDataOnUISelectorMedium() {
        mDDHelper.reflectDataOnUI(-1, "Medium", objectiveNumber -> {
            // Dynamically trigger animations based objectiveNumber
            switch (objectiveNumber) {
                case 1:
                    mBinding.mediumPuzzle1Objective1.setImageResource(R.drawable.filled);
                    break;
                case 2:
                    mBinding.mediumPuzzle2Objective2.setImageResource(R.drawable.filled);
                    break;
                case 3:
                    mBinding.mediumPuzzle2Objective3.setImageResource(R.drawable.filled);
                    break;
                case 4:
                    mBinding.mediumPuzzle2Objective4.setImageResource(R.drawable.filled);
                    break;
                default:
                    break;
            }
        });
    }
}

