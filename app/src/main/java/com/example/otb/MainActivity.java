package com.example.otb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    // Top Bar.
    private MaterialToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        //get a reference to the navigation controller from navigation host
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //create an “AppBarConfiguration” object
        AppBarConfiguration.Builder builder = new
                AppBarConfiguration.Builder(navController.getGraph());

        AppBarConfiguration appBarConfiguration = builder.build();

        //link “AppBarConfigtureation” to the toolbar
        NavigationUI.setupWithNavController(mToolbar, navController,
                appBarConfiguration);

    }
}