package com.example.otb;

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


/**
 * Fragment that shows (Easy, Medium, Hard) difficulty puzzles.
 */
public class difficultyMenuFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.
    private DifficultyMenuFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DifficultyMenuFragmentBinding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();


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
}
