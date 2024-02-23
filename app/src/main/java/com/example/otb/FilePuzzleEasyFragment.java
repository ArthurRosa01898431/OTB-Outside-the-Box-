package com.example.otb;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FilePuzzleEasyFragment extends Fragment {
    //private static final String PUZZLE_STATE_KEY = "file_puzzle_state";
    private boolean isPuzzleSolved = false;
    private Button puzzleButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.file_puzzle_easy, container, false);
        puzzleButton = view.findViewById(R.id.puzzleButton); // Make sure the ID matches your layout
        //loadPuzzleState();
        updateButtonAppearance();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if the puzzle has been solved
        // This check depends on your specific logic to determine if the user went to the file explorer and came back
        // For demonstration, we'll toggle the state; you need to replace this with your actual check
        isPuzzleSolved = !isPuzzleSolved;

        updateButtonAppearance();
        //savePuzzleState();
    }

    private void updateButtonAppearance() {
        if (isPuzzleSolved) {
            // Set the button to a filled background indicating the puzzle is solved
            puzzleButton.setBackgroundResource(R.drawable.button_filled); // Create this drawable resource
        } else {
            // Set the button to an outline background indicating the puzzle is unsolved
            puzzleButton.setBackgroundResource(R.drawable.button_outline); // You should already have this drawable resource
        }
    }

    /*
    private void savePuzzleState() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PUZZLE_STATE_KEY, isPuzzleSolved);
        editor.apply();
    }

    private void loadPuzzleState() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        isPuzzleSolved = sharedPref.getBoolean(PUZZLE_STATE_KEY, false);
    }
    */

}
