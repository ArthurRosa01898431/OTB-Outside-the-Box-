package com.example.otb;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.otb.databinding.MediumPuzzle3FragmentBinding;

/**
 * Timer Puzzle.
 */
public class MediumPuzzle3FragmentImpl extends Fragment {

    private MediumPuzzle3FragmentBinding mBinding;
    private DatabaseHelper mDBHelper;

    private CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBHelper = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = MediumPuzzle3FragmentBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mBinding.mediumPuzzle3Objective5.setOnClickListener(v -> startTimer());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle3Medium();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                mBinding.timerTextView.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mBinding.timerTextView.setText("Done!");
                puzzleSolved();
            }
        }.start();
    }

    private void puzzleSolved() {
        Toast.makeText(getActivity(), "Puzzle Solved!", Toast.LENGTH_SHORT).show();
        mediumPuzzle3Animation(5);
    }

    public void mediumPuzzle3Animation(int objectiveNumber) {
        if (!mDBHelper.isObjectiveNumberInDatabase("Medium", 3, objectiveNumber)) {
            mDBHelper.insertData(3, objectiveNumber, "Medium");
            MainActivity.animation(getActivity(), 5, 3, "medium");
        }
    }

    public void reflectDataOnUI_Puzzle3Medium() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDBHelper.reflectDataOnUI(3, "Medium", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 5:
                    mBinding.mediumPuzzle3Objective5.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed that represent objectives
                default:
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}






