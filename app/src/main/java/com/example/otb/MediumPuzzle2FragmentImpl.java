package com.example.otb;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.otb.MainActivity.animation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.otb.databinding.EasyPuzzle1FragmentBinding;
import com.example.otb.databinding.MediumPuzzle2FragmentBinding;
import com.example.otb.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutionException;

/**
 * Fruit Recognition Puzzle.
 */
public class MediumPuzzle2FragmentImpl extends Fragment {
    private static final int PUZZLE_ID = 2;
    private static final int IMAGE_SIZE = 32;
    private final hintFragment mHintFragment = new hintFragment();
    private DatabaseHelper mDDHelper;
    private MediumPuzzle2FragmentBinding mBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = MediumPuzzle2FragmentBinding.inflate(inflater,
                container, false);

        View view = mBinding.getRoot();
        setUpHintFragment();
        mDDHelper = new DatabaseHelper(getContext());


        // Request camera permission and access camera.
        mBinding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(getContext(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
                }
            }
        });
        mBinding.mediumPuzzle2Objective2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(), 1);
            }
        });

        mBinding.mediumPuzzle2Objective3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(),  2);
            }
        });

        mBinding.mediumPuzzle2Objective4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintFragment.showHintFragment(requireActivity().getSupportFragmentManager(),  3);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                mBinding.cameraPicture.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, IMAGE_SIZE, IMAGE_SIZE, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reflectDataOnUI_Puzzle2();
    }

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(getContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < IMAGE_SIZE; i ++){
                for(int j = 0; j < IMAGE_SIZE; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Apple", "Banana", "Orange"};

            switch (classes[maxPos]) {
                case "Apple":
                    mediumPuzzle2Animation(2);
                    break;
                case "Banana":
                    mediumPuzzle2Animation(3);
                    break;
                case "Orange":
                    mediumPuzzle2Animation(4);
                    break;
                default:
            }

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    public void mediumPuzzle2Animation(int objectiveNumber) {
        if (!mDDHelper.isObjectiveNumberInDatabase ("Medium", PUZZLE_ID, objectiveNumber)) {
            mDDHelper.insertData(PUZZLE_ID, objectiveNumber, "Medium");
            animation(getActivity(), objectiveNumber, PUZZLE_ID, "medium");
        }
    }

    private void setUpHintFragment() {
        String[] hintAllObjText = getResources().getStringArray(R.array.Puzzle2MediumAllObjectives);;
        String[] hintObj2Text = getResources().getStringArray(R.array.Puzzle2MediumObjective2);;
        String[] hintObj3Text = getResources().getStringArray(R.array.Puzzle2MediumObjective3);;
        String[] hintObj4Text = getResources().getStringArray(R.array.Puzzle2MediumObjective4);;


        mHintFragment.createObjectiveHints(R.drawable.medium_puzzle2_obj_all_image, hintAllObjText, true);
        mHintFragment.createObjectiveHints(R.drawable.medium_puzzle2_obj_2_image, hintObj2Text, false);
        mHintFragment.createObjectiveHints(R.drawable.medium_puzzle2_obj_3_image, hintObj3Text, false);
        mHintFragment.createObjectiveHints(R.drawable.medium_puzzle2_obj_4_image, hintObj4Text, false);
    }

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
     */
    public void reflectDataOnUI_Puzzle2() {
        // Main Activity gets data from the database and each puzzle will have it's own lambda.
        mDDHelper.reflectDataOnUI(PUZZLE_ID, "Medium", (int objectiveNumber) -> {
            switch (objectiveNumber) {
                case 2:
                    mBinding.mediumPuzzle2Objective2.setBackgroundResource(R.drawable.blink88);
                    break;
                case 3:
                    mBinding.mediumPuzzle2Objective3.setBackgroundResource(R.drawable.blink88);
                    break;
                case 4:
                    mBinding.mediumPuzzle2Objective4.setBackgroundResource(R.drawable.blink88);
                    break;
                // Add more cases as needed
                default:
                    break;
            }
        });
    }
}
