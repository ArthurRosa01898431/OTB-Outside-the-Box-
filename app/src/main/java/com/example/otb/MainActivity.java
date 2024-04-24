/*
Mobile App Development II -- COMP.4631 Honor Statement
The practice of good ethical behavior is essential for maintaining
good order in the classroom, providing an enriching learning
experience for students, and training as a practicing computing
professional upon graduation. This practice is manifested in the
University's Academic Integrity policy. Students are expected to
strictly avoid academic dishonesty and adhere to the Academic
Integrity policy as outlined in the course catalog. Violations
will be dealt with as outlined therein. All programming assignments
in this class are to be done by the student alone unless otherwise
specified. No outside help is permitted except the instructor and
approved tutors.
I certify that the work submitted with this assignment is mine and
was generated in a manner consistent with this document, the course
academic policy on the course website on Blackboard, and the UMass
Lowell academic code.
Date: 4/24/24
Name: Arthur Rosa
*/

package com.example.otb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.appbar.MaterialToolbar;

import dagger.hilt.android.AndroidEntryPoint;

interface IntRunnable {
    void run(int intValue);
}
@AndroidEntryPoint
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

        mDDHelper = new DatabaseHelper(this);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }


    static void animation(FragmentActivity fragmentActivity, int objectiveNumber, int puzzleNumber, String difficulty) {
        String buttonID = difficulty + "_puzzle" + puzzleNumber+ "_objective" + objectiveNumber;

        int resID = fragmentActivity.getResources().getIdentifier(buttonID, "id", fragmentActivity.getPackageName());
        ImageButton button = fragmentActivity.findViewById(resID);

        button.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) button.getBackground()).start();
    }
}
