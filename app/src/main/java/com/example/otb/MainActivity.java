package com.example.otb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewbinding.ViewBinding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.otb.databinding.PuzzleFragment1Binding;
import com.google.android.material.appbar.MaterialToolbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    // Top Bar.
    private MaterialToolbar mToolbar;
    private DatabaseHelper dbHelper; // Database helper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Only allow portrait mode for this app.
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        // Get a reference to the navigation controller from navigation host.
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        // Create an “AppBarConfiguration” object,
        AppBarConfiguration.Builder builder = new
                AppBarConfiguration.Builder(navController.getGraph());

        AppBarConfiguration appBarConfiguration = builder.build();

        // Link “AppBarConfigtureation” to the toolbar,
        NavigationUI.setupWithNavController(mToolbar, navController,
                appBarConfiguration);

        dbHelper = new DatabaseHelper(this);
        insertDataIntoDatabase();

    }

    private void insertDataIntoDatabase() {
        // Example data

        // puzzle id: 1 -> brightness Easy

        int puzzleId = 1; // Example puzzle ID
        int numberOfObjectives = 3; // Example number of objects
        String difLevel = "Easy";

        // Insert data using insertData method
        dbHelper.insertData(puzzleId, numberOfObjectives, difLevel);

        // You can insert more data as needed
    }

    static void animation(FragmentActivity fragmentActivity, int objectiveNumber) {

        int resID = fragmentActivity.getResources().getIdentifier("objective" + objectiveNumber, "id", fragmentActivity.getPackageName());
        Button button = fragmentActivity.findViewById(resID);

//        ImageView iv = activity.findViewById(activity.getResources().getIdentifier("imageView" + index, "id", activity.getPackageName()));
        button.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) button.getBackground()).start();
    }

    public static ResultSet fetchPuzzleData(int puzzleId, String Difficulty) throws SQLException {
        ResultSet rs;
        // Determine the SQL query to use based on the condition
        String sql;
        if (puzzleId == 0) {
            sql = "SELECT * FROM data WHERE obj_num_difficulty = ?";
        } else {
            sql = "SELECT * FROM data WHERE puzzle_id = ? AND obj_num_difficulty = ?";
        }

        try (Connection conn = DriverManager.getConnection("myDatabase.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (puzzleId == 0) {
                pstmt.setString(1, Difficulty);
            } else {
                pstmt.setInt(1, puzzleId);
                pstmt.setString(2, Difficulty);
            }

            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }


}

/*
        Cursor cursor = dbHelper.getPuzzleData(puzzleId);
        if(cursor != null && cursor.moveToFirst()) {
            // Use the fetched data as required
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("puzzle_id"));
            @SuppressLint("Range") String Dif = cursor.getString(cursor.getColumnIndex("difficulty"));
            cursor.close();
        }
        return ;
        */