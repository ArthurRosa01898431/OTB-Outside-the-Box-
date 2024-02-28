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
        reflectAllMediumPuzzlesDataOnUI();

        mBinding.puzzle1MediumObjective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorMediumFragment_to_QR_puzzle_medium);
            }
        });

    }

    public void reflectAllMediumPuzzlesDataOnUI() {
        int[] puzzleIds = {1}; //  add more ids to array based number of actual puzzle IDs.
        for (int puzzleId : puzzleIds) {
            reflectDataOnUISelectorMedium(puzzleId);
        }
    }

    public void reflectDataOnUISelectorMedium(int puzzleId) {
        mDDHelper.reflectDataOnUI(puzzleId, "Medium", objectiveNumber -> {
            // Dynamically trigger animations based on puzzleId and objectiveNumber
            triggerAnimationForObjective(puzzleId, objectiveNumber);
        });
    }
    private void triggerAnimationForObjective(int puzzleId, int objectiveNumber) {
        switch (puzzleId) {
            case 1:
                // 1 represents the id, representing the medium puzzle
                if (objectiveNumber == 1)
                    mBinding.puzzle1MediumObjective1.setBackgroundResource(R.drawable.blink88);
                break;
            // put more cases here for more hard puzzles
            default:
                break;
        }
    }
}
