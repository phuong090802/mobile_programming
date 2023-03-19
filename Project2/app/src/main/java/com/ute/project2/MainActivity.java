package com.ute.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationListener {

    BottomNavigationView bottomNavigationView;
    BottomNavigationManager bottomNavigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationManager = new BottomNavigationManager(this, bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SearchFragment()).commit();
    }

    @Override
    public void onBottomNavigationItemSelected(int itemId) {
        Fragment selectedFragment = null;

        if (itemId == R.id.search) {
            selectedFragment = new SearchFragment();
        } else if (itemId == R.id.yourLibrary) {
            selectedFragment = new YourLibraryFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
        }
    }
}