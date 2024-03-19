package com.example.otb;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.otb.databinding.DifficultyMenuFragmentBinding;

import java.util.Vector;


/**
 * Fragment that shows (Easy, Medium, Hard) difficulty puzzles.
 */
public class difficultyMenuFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.
    private DifficultyMenuFragmentBinding mBinding;

    private DatabaseHelper mDDHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DifficultyMenuFragmentBinding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();

        mDDHelper = new DatabaseHelper(getContext());

        reflectDataBaseToUIPuzzleCompleted();

        mBinding.easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(
                        R.id.action_difficultyMenuFragment_to_puzzleSelectorEasyFragment);
            }
        });

        mBinding.mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(
                        R.id.action_difficultyMenuFragment_to_puzzleSelectorMediumFragment);
            }
        });

        mBinding.hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(
                        R.id.action_difficultyMenuFragment_to_puzzleSelectorHardFragment);
            }
        });




        //TODO Show how many puzzles are completed for each difficulty using database of objectives
        // that have been solved. Ex: Puzzle one consist of obj 1 and 2. If obj 1 and 2 is in
        // database, add 1 to puzzle completed.


        return view;
    }

    private void reflectDataBaseToUIPuzzleCompleted() {
        // Get all easy difficulty data in database.
        Cursor cur = mDDHelper.getPuzzleData(-1, "Easy");

        mBinding.easyPuzzleComplete.setText(howManyPuzzleCompleted("Easy", cur) + "/2");

        // Get all medium difficulty data in database.
        cur = mDDHelper.getPuzzleData(-1, "Medium");

        mBinding.mediumPuzzleComplete.setText(howManyPuzzleCompleted("Medium", cur) + "/1");

        // Get all medium difficulty data in database.
        cur = mDDHelper.getPuzzleData(-1, "Hard");

        mBinding.hardPuzzleComplete.setText(howManyPuzzleCompleted("Hard", cur) + "/1");





    }

    private int howManyPuzzleCompleted(final String difficulty, Cursor objectiveCompleted) {
        boolean puzzle1_objective1Completed = false;
        boolean puzzle1_objective2Completed = false;

        int totalCompleted = 0;
        switch(difficulty) {
            case "Easy":
                while (objectiveCompleted != null && objectiveCompleted.moveToNext()) {
                    @SuppressLint("Range") int objNumber = objectiveCompleted.getInt(objectiveCompleted.getColumnIndex("obj_number"));
                    if (objNumber == 1) {
                        puzzle1_objective1Completed = true;
                    } else if (objNumber == 2) {
                        puzzle1_objective2Completed = true;
                    }
                }
                if (puzzle1_objective1Completed && puzzle1_objective2Completed) {
                    totalCompleted++;
                }
        }
        return totalCompleted;
    }
}
