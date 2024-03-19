package com.example.otb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;

interface IntRunnable {
    void run(int intValue);
}

public class MainActivity extends AppCompatActivity {

    // Top Bar.
    private MaterialToolbar mToolbar;

    private static DatabaseHelper mDDHelper;

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

        mDDHelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            resetGameData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    static void animation(FragmentActivity fragmentActivity, int objectiveNumber) {

        int resID = fragmentActivity.getResources().getIdentifier("objective" + objectiveNumber, "id", fragmentActivity.getPackageName());
        Button button = fragmentActivity.findViewById(resID);

        button.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) button.getBackground()).start();
    }

    /*
        Show on the UI that an objective is solved if it is already solved according
        to the data base.

        @param puzzleId - The puzzle ID to check in the database.
        @param difficulty - The difficulty to check in the database. Can be empty if specific difficulty doesn't matter.
        @param function - Lambda that shows in UI that a objective is solved which is puzzle and puzzle selector specific.
     */
    static void reflectDataOnUI(final int puzzleId, final String difficulty, final IntRunnable function) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor test = mDDHelper.getPuzzleData(puzzleId, difficulty);
                while (test != null && test.moveToNext()) {
                    @SuppressLint("Range") int objNumber = test.getInt(test.getColumnIndex("obj_number"));
                    // Update UI components here
                    function.run(objNumber);
                }
                assert test != null;
                test.close();
            }
        }).start();
    }

    static boolean isObjectiveNumberInDatabase(Cursor cursor, int targetObjectiveNumber) {
        boolean found = false;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int objNumber = cursor.getInt(cursor.getColumnIndex("obj_number"));
                if (objNumber == targetObjectiveNumber) {
                    found = true;
                    break; // No need to continue searching if found
                }
            } while (cursor.moveToNext());

            cursor.close(); // Close the cursor when finished with it
        }

        return found;
    }

    private void resetGameData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
