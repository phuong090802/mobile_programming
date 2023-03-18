package com.ute.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_search, new ResultQueryFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_search);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
    }

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener = item -> {
        Fragment selectedFragment = null;
        int fragmentId = item.getItemId();

        if (fragmentId == R.id.search) {
            selectedFragment = new SearchFragment();
        } else if (fragmentId == R.id.yourLibrary) {
            selectedFragment = new YourLibraryFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_search, selectedFragment).commit();
            return true;
        }
        return false;
    };
}