package com.example.otb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewbinding.ViewBinding;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.otb.databinding.PuzzleFragment1Binding;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    // Top Bar.
    private MaterialToolbar mToolbar;

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

    }
    static void animation(FragmentActivity fragmentActivity, int objectiveNumber) {

        int resID = fragmentActivity.getResources().getIdentifier("objective" + objectiveNumber, "id" , fragmentActivity.getPackageName());
        Button button = fragmentActivity.findViewById(resID);

//        ImageView iv = activity.findViewById(activity.getResources().getIdentifier("imageView" + index, "id", activity.getPackageName()));
        button.setBackgroundResource(R.drawable.animation);
        ((AnimationDrawable) button.getBackground()).start();
    }

}