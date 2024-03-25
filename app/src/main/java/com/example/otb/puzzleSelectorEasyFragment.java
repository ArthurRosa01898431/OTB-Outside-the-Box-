package com.example.otb;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.otb.databinding.DifficultyMenuFragmentBinding;
import com.example.otb.databinding.PuzzleFragment1Binding;
import com.example.otb.databinding.PuzzleSelectorEasyFragmentBinding;


/**
 * Fragment that shows (Easy, Medium, Hard) difficulty puzzles.
 */

public class puzzleSelectorEasyFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.
    private PuzzleSelectorEasyFragmentBinding mBinding;

    private DatabaseHelper mDDHelper;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PuzzleSelectorEasyFragmentBinding.inflate(inflater, container, false);
        mDDHelper = new DatabaseHelper(getContext());
        // Inflate the layout for this fragment
        View view = mBinding.getRoot();
        return view;
    }

    /*
        Show on the UI that an easy objective is solved if
        it is already solved according to the data base.
     */

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectAllEasyPuzzlesDataOnUI();
        mBinding.puzzle1Objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_brightness_puzzle_easy);
            }
        });
        mBinding.puzzle1Objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_brightness_puzzle_easy);
            }
        });
    }

    public void reflectAllEasyPuzzlesDataOnUI() {
        int[] puzzleIds = {1}; //  add more ids to array based number of actual puzzle IDs.
        for (int puzzleId : puzzleIds) {
            reflectDataOnUISelectorEasy(puzzleId);
        }
    }

    public void reflectDataOnUISelectorEasy(int puzzleId) {
        mDDHelper.reflectDataOnUI(puzzleId, "Easy", objectiveNumber -> {
            // Dynamically trigger animations based on puzzleId and objectiveNumber
            triggerAnimationForObjective(puzzleId, objectiveNumber);
        });
    }
    private void triggerAnimationForObjective(int puzzleId, int objectiveNumber) {
        switch (puzzleId) {
            case 1:
                if (objectiveNumber == 1)
                    mBinding.puzzle1Objective1.setBackgroundResource(R.drawable.blink88);
                else if (objectiveNumber == 2)
                    mBinding.puzzle1Objective2.setBackgroundResource(R.drawable.blink88);
                break;
            //case 2:
            // 2 represents the id, so with the file puzzle now being moved to hard, just change the id number with the case
            //    if (objectiveNumber == 1)
            //        mBinding.puzzle2Objective1.setBackgroundResource(R.drawable.blink88);
            //    break;
            // put more cases here for more puzzles
            default:
                break;
        }
    }
}



// needs be changed quickly to adapt to multiple puzzles, old function that doesn't need to be used anymore cause it assumed there was just 1 puzzle
    /*public void reflectDataOnUISelectorEasy() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(-1, "Easy", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.puzzle1Objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                case 2:
                    mBinding.puzzle1Objective2.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }

        });
    }*/
