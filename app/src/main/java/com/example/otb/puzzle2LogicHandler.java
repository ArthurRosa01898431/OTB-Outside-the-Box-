package com.example.otb;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;


public class puzzle2LogicHandler {
    private final puzzle2Fragment mFragment;

    public puzzle2LogicHandler(puzzle2Fragment fragment) {
        mFragment = fragment;
    }

}
