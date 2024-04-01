package com.example.otb;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class hintFragment extends BottomSheetDialogFragment {

    private final ArrayList<hintModel> mHintModel = new ArrayList<>();

    // Individual objective's hints that is displayed.
    private Integer mCurrentObjectiveHintDisplayed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hint_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.hintRecyclerView);

        hintRecyclerViewAdapter adapter = new hintRecyclerViewAdapter(getContext(), mHintModel);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        mHintModel.get(mCurrentObjectiveHintDisplayed).toggleShouldBeDisplayed();
        // No individual hint is displayed anymore.
        mCurrentObjectiveHintDisplayed = null;
    }

    public void createObjectiveHints(
            int objImage, String[] hintTexts, boolean isObjectiveHintForAll) {

        // Only Objectives that been clicked or Hints for all objective should be displayed.
        if (isObjectiveHintForAll) {
            mHintModel.add(new hintModel(objImage, hintTexts, true, true));
        } else {
            mHintModel.add(new hintModel(objImage, hintTexts, false, false));
        }
    }

    public void showHintFragment(FragmentManager fragmentManager, int hintObjectiveNumber) {
        if (mCurrentObjectiveHintDisplayed == null) {
            show(fragmentManager, "hintFragment");
            mCurrentObjectiveHintDisplayed = hintObjectiveNumber;
            mHintModel.get(hintObjectiveNumber).toggleShouldBeDisplayed();
        }
    }
}
