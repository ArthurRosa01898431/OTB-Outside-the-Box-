package com.example.otb;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.otb.databinding.PuzzleSelectorEasyFragmentBinding;


/**
 * Fragment that shows (Easy, Medium, Hard) difficulty puzzles.
 */

public class puzzleSelectorEasyFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.
    private PuzzleSelectorEasyFragmentBinding mBinding;

    private DatabaseHelper mDDHelper;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PuzzleSelectorEasyFragmentBinding.inflate(inflater,
                container, false);
        mDDHelper = new DatabaseHelper(getContext())
        ;
        // Inflate the layout for this fragment
        View view = mBinding.getRoot();

        reflectDataOnUISelectorEasy();

        mBinding.easyPuzzle1Objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_brightness_puzzle_easy);
            }
        });
        mBinding.easyPuzzle1Objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_brightness_puzzle_easy);
            }
        });

        mBinding.easyPuzzle2Objective3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_light_puzzle_easy);
            }
        });
        mBinding.easyPuzzle2Objective4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_light_puzzle_easy);
            }
        });

        mBinding.easyPuzzle3Objective5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_charger_puzzle_easy);
            }
        });

        return view;
    }

    /*
        Show on the UI that an easy objective is solved if
        it is already solved according to the data base.
     */
    public void reflectDataOnUISelectorEasy() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(-1, "Easy", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.easyPuzzle1Objective1.setImageResource(R.drawable.filled);
                    break;
                case 2:
                    mBinding.easyPuzzle1Objective2.setImageResource(R.drawable.filled);
                    break;
                case 3:
                    mBinding.easyPuzzle2Objective3.setImageResource(R.drawable.filled);
                    break;
                case 4:
                    mBinding.easyPuzzle2Objective4.setImageResource(R.drawable.filled);
                    break;
                case 5:
                    mBinding.easyPuzzle3Objective5.setImageResource(R.drawable.filled);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }
}
