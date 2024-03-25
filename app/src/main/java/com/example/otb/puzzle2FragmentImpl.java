package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.otb.databinding.PuzzleFragment2Binding;

public class puzzle2FragmentImpl extends Fragment implements puzzle2Fragment {

    // keep these two, give the hint fragment and the database helper
    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;

    private final puzzle2LogicHandler mHandler = new puzzle2LogicHandler(this);
    private ActivityResultLauncher<String> fileSelectorLauncher;
    private PuzzleFragment2Binding mBinding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ActivityResultLauncher
        fileSelectorLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    // trigger the animation after the user comes back to the puzzle screen
                    puzzle2Animation(1);
                });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = PuzzleFragment2Binding.inflate(inflater, container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());

        // pressing the button would result in the hint fragment and the file screen triggering
        //mBinding.objective1.setOnClickListener(v -> { triggerFileSelection();
        //    mHintFragment.toggleHintDisplay(1);
        //    mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");
        //});

        //so clicking the button would just show the hints instead
        mBinding.objective1.setOnClickListener(v -> {
            mHintFragment.toggleHintDisplay(1);
            mHintFragment.show(requireActivity().getSupportFragmentManager(), "hintFragment");

        });

        // Long press to go to the file system, making the puzzle harder to solve
        mBinding.objective1.setOnLongClickListener(v -> {
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


    /*
    @Override

    public void puzzle2Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                // Ensure we use the mDDHelper instance to call the method
                if (!mDDHelper.isObjectiveNumberInDatabase("Easy", 1, objectiveNumber)) {
                    animation(getActivity(), objectiveNumber);
                    mDDHelper.insertData(1, objectiveNumber, "Easy");
                }
                break;
            // You can handle more cases if needed
            default:
                break;
        }
    }
    */

    //@Override
    //public void puzzle2Animation(int objectiveNumber) {
    //    Log.d("Puzzle2", "triggerFileSelection called.");

    //    animation(getActivity(), objectiveNumber); // Temporarily bypass the database check for testing
    //    mDDHelper.insertData(1, objectiveNumber, "Easy"); // This will still record the objective as complete
    //}

    public void puzzle2Animation(int objectiveNumber) {
        Log.d("Puzzle2", "triggerFileSelection called.");
        switch (objectiveNumber) {
            case 1:
                if (!mDDHelper.isObjectiveNumberInDatabase("Hard", 2, 1)) {
                    mDDHelper.insertData(2, objectiveNumber, "Hard");
                    animation(getActivity(), 1);
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
                    mBinding.objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }

}
