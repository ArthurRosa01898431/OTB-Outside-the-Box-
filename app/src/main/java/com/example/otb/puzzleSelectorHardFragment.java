package com.example.otb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.otb.databinding.PuzzleSelectorHardFragmentBinding;


/**
 * Fragment that shows (Easy, Medium, Hard) difficulty puzzles.
 */
public class puzzleSelectorHardFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.
    private PuzzleSelectorHardFragmentBinding mBinding;

    private DatabaseHelper mDDHelper;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PuzzleSelectorHardFragmentBinding.inflate(inflater, container, false);
        mDDHelper = new DatabaseHelper(getContext());
        // Inflate the layout for this fragment
        View view = mBinding.getRoot();
        return view;
    }

    /*
        Show on the UI that an hard objective is solved if
        it is already solved according to the data base.
     */

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectAllHardPuzzlesDataOnUI();

        mBinding.puzzle2Objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorHardFragment_to_file_puzzle_hard);
            }
        });

    }

    public void reflectAllHardPuzzlesDataOnUI() {
        int[] puzzleIds = {2}; //  add more ids to array based number of actual puzzle IDs.
        for (int puzzleId : puzzleIds) {
            reflectDataOnUISelectorHard(puzzleId);
        }
    }

    public void reflectDataOnUISelectorHard(int puzzleId) {
        mDDHelper.reflectDataOnUI(puzzleId, "Hard", objectiveNumber -> {
            // Dynamically trigger animations based on puzzleId and objectiveNumber
            triggerAnimationForObjective(puzzleId, objectiveNumber);
        });
    }
    private void triggerAnimationForObjective(int puzzleId, int objectiveNumber) {
        switch (puzzleId) {
            case 2:
            // 2 represents the id, so with the file puzzle now being moved to hard, just change the id number with the case
                if (objectiveNumber == 1)
                    mBinding.puzzle2Objective1.setBackgroundResource(R.drawable.blink88);
                break;
            // put more cases here for more hard puzzles
            default:
                break;
        }
    }
}