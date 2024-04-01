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
        reflectDataOnUISelectorHard();

        mBinding.hardPuzzle1Objective1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorHardFragment_to_file_puzzle_hard);
            }
        });

    }

    public void reflectDataOnUISelectorHard() {
        mDDHelper.reflectDataOnUI(-1, "Hard", objectiveNumber -> {
            // Dynamically trigger animations based objectiveNumber
            switch (objectiveNumber) {
                case 1:
                    mBinding.hardPuzzle1Objective1.setImageResource(R.drawable.filled);
                    break;
                default:
                    break;
            }
        });
    }
}