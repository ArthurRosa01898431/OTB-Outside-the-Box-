package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.otb.databinding.HardPuzzle1FragmentBinding;

public class HardPuzzle1FragmentImpl extends Fragment implements HardPuzzle1Fragment{
    // keep these two, give the hint fragment and the database helper
    private final hintFragment mHintFragment = new hintFragment();
    private DatabaseHelper mDDHelper;

    private final HardPuzzle1LogicHandler mHandler = new HardPuzzle1LogicHandler(this);
    private ActivityResultLauncher<String> fileSelectorLauncher;
    private HardPuzzle1FragmentBinding mBinding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ActivityResultLauncher
        fileSelectorLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    // trigger the animation after the user comes back to the puzzle screen
                    hardPuzzle1Animation(1);
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = HardPuzzle1FragmentBinding.inflate(inflater, container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());


        //so clicking the button would just show the hints instead
        mBinding.hardPuzzle1Objective1.setOnClickListener(v -> {
            mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(), 1);


        });

        // Long press to go to the file system, making the puzzle harder to solve
        mBinding.hardPuzzle1Objective1.setOnLongClickListener(v -> {
            fileSelectorLauncher.launch("*/*"); // Single tap to trigger file selection
            return true; // Indicate that the long press event is consumed
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this needs to have the correct puzzle ID
        reflectDataOnUI_Puzzle2(2);
    }

    @Override
    public void hardPuzzle1Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                if (!mDDHelper.isObjectiveNumberInDatabase ("Hard", 1, 1)) {
                    mDDHelper.insertData(1, objectiveNumber, "Hard");
                    animation(getActivity(), 1, 1, "hard");
                }
                break;
            default:
                break;
        }
    }


    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle2HintsAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.Puzzle2HintsObjective1);

        mHintFragment.createObjectiveHints(R.drawable.puzzle2_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle2_obj1_image, hintObj1Text, false);
    }


    public void reflectDataOnUI_Puzzle2(final int puzzleId) {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(puzzleId, "", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.hardPuzzle1Objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }
}