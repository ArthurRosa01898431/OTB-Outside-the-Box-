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
        return view;
    }

    private void reflectDataBaseToUIPuzzleCompleted() {
        // Get all easy difficulty data in database.
        mBinding.easyPuzzleComplete.setText(mDDHelper.howManyPuzzleCompleted("Easy") + "/3");

        // Get all medium difficulty data in database.
        mBinding.mediumPuzzleComplete.setText(mDDHelper.howManyPuzzleCompleted("Medium") + "/2");

        // Get all hard difficulty data in database.
        mBinding.hardPuzzleComplete.setText(mDDHelper.howManyPuzzleCompleted("Hard") + "/1");
    }
}
