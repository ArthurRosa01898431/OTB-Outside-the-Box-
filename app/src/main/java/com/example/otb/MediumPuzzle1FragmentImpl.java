package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.otb.databinding.MediumPuzzle1FragmentBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;

public class MediumPuzzle1FragmentImpl extends Fragment implements MediumPuzzle1Fragment {

    // keep these two, give the hint fragment and the database helper
    private final hintFragment mHintFragment = new hintFragment();
    private  DatabaseHelper mDDHelper;

    private final MediumPuzzle1LogicHandler mHandler = new MediumPuzzle1LogicHandler(this);
    private MediumPuzzle1FragmentBinding mBinding;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = MediumPuzzle1FragmentBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());

        mBinding.mediumPuzzle1Objective1.setOnLongClickListener(v ->  {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Permission granted by the user to access Camera
                new IntentIntegrator(getActivity()).initiateScan();
            } else {
                // Permission is not granted, request for permission.
                requestCameraPermission();
            }
            return true;
        });

        mBinding.mediumPuzzle1Objective1.setOnClickListener(v -> {
            mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(), 1);


        });

        return view;
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("QRCodeScanner", "Cancelled scan");
                Toast.makeText(getActivity(), "Scan was cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Log.d("QRCodeScanner", "Scanned: " + result.getContents());
                mediumPuzzle1Animation(1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, initiate the scan
                new IntentIntegrator(getActivity()).initiateScan();
            } else {
                // Permission denied, inform the user that camera access is required for scanning
                Toast.makeText(getActivity(), "Camera permission is required for scanning QR codes", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
        reflectDataOnUI_Puzzle1Medium(1);
    }

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle1MediumAllObjectives);
        String[] hintObj1Text = getResources().getStringArray(R.array.Puzzle1MediumObjective1);

        mHintFragment.createObjectiveHints(R.drawable.puzzle2_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.puzzle2_obj1_image, hintObj1Text, false);
    }


    public void reflectDataOnUI_Puzzle1Medium(final int puzzleId) {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(puzzleId, "Medium", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.mediumPuzzle1Objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed that represent objectives
                default:
            }
        });
    }

    @Override
    public void mediumPuzzle1Animation(int objectiveNumber) {
        switch (objectiveNumber) {
            case 1:
                if (!mDDHelper.isObjectiveNumberInDatabase("Medium", 1, 1)) {
                    mDDHelper.insertData(1, objectiveNumber, "Medium");
                    animation(getActivity(), 1, 1, "medium");
                }
                break;
            default:
                break;
        }
    }
}