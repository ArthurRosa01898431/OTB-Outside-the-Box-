package com.example.otb;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.otb.databinding.SettingsFragmentBinding;

public class SettingsFragmentImpl extends Fragment {
    private SettingsFragmentBinding mBinding;

    private DatabaseHelper mDDHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = SettingsFragmentBinding.inflate(inflater, container, false);

        View view = mBinding.getRoot();

        mDDHelper = new DatabaseHelper(getContext());

        mBinding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGameData();
            }
        });
        return view;
    }

    private void resetGameData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Reset Game Data");
        builder.setMessage("Are you sure you want to reset game data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDDHelper.deleteAllData();
            }
        });
        builder.setNegativeButton("No", null); // No action needed for "No" button
        builder.show();
    }
}
