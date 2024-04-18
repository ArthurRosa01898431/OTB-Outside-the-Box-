package com.example.otb;

import static com.example.otb.MainActivity.animation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.example.otb.databinding.HardPuzzle1FragmentBinding;

public class HardPuzzle1FragmentImpl extends Fragment implements HardPuzzle1Fragment{
    // keep these two, give the hint fragment and the database helper
    private final hintFragment mHintFragment = new hintFragment();
    private DatabaseHelper mDDHelper;

    private final HardPuzzle1LogicHandler mHandler = new HardPuzzle1LogicHandler(this);
    private ActivityResultLauncher<String> fileSelectorLauncher;
    private HardPuzzle1FragmentBinding mBinding;

    private ActivityResultLauncher<Intent> createDocumentLauncher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ActivityResultLauncher
        fileSelectorLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        String content = readFileContent(uri);
                        if ("This is a secret puzzle message.".equals(content)) {
                            // Correct file selected, proceed with puzzle.
                            hardPuzzle1Animation(1);
                        } else {
                            // Incorrect file or content, prompt user or give a hint.
                        }
                    }
                });

        /// Launcher for creating a file in the Documents folder
        createDocumentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            try {
                                writeToFile(getContext(), uri, "This is a secret puzzle message.");
                                // Directly proceed with puzzle completion after file is saved
                                hardPuzzle1Animation(1); // Move this inside the try block if writeToFile only throws exception on failure
                            } catch (IOException e) {
                                e.printStackTrace();
                                // Handle error, maybe prompt user or give a hint
                            }
                        }
                    }
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
            createPuzzleFileWithSAF();
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
        mDDHelper.reflectDataOnUI(puzzleId, "Hard", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 1:
                    mBinding.hardPuzzle1Objective1.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
            }
        });
    }



    public String readFileContent(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void createPuzzleFileWithSAF() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "puzzleFile.txt");

        createDocumentLauncher.launch(intent);
    }


    public void writeToFile(Context context, Uri uri, String content) throws IOException {
        try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
            if (outputStream != null) {
                outputStream.write(content.getBytes());
                outputStream.close();
            }
        }
    }
}