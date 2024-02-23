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

public class puzzleSelectorEasyFragment extends Fragment {
    // View binding object for the DifficultyMenuFragment's layout.


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.puzzle_selector_easy_fragment, container, false);

        View easyButton = view.findViewById(R.id.brightnessPuzzleSelecter);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtain a NavController and navigate
                Navigation.findNavController(v).navigate(R.id.action_puzzleSelectorEasyFragment_to_brightness_puzzle_easy);
            }
        });

        return view;
    }
}
