package com.example.otb;

import static com.example.otb.MainActivity.reflectDataOnUI;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PuzzleSelectorEasyFragmentBinding.inflate(inflater,
                container, false);
        // Inflate the layout for this fragment
        View view = mBinding.getRoot();

        reflectDataOnUISelectorEasy();

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

        return view;
    }

    /*
        Show on the UI that an easy objective is solved if
        it is already solved according to the data base.
     */
    public void reflectDataOnUISelectorEasy() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        reflectDataOnUI(-1, "Easy", (int objectiveNumber) -> {
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
    }
}
