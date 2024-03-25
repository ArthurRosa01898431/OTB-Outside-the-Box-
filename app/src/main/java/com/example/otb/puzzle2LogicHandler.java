package com.example.otb;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;


public class puzzle2LogicHandler {

    private final puzzle2Fragment mFragment;
    puzzle2LogicHandler(puzzle2Fragment fragment) {
        mFragment = fragment;
    }

    // write out the rest of the logic here

    void goToFileApp() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Set MIME type as per your requirement
        mFragment.startActivity(intent); // Use Fragment's context to start activity
    }
}
