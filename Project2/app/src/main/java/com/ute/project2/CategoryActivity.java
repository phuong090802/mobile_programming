package com.ute.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ute.project2.model.Category;

public class CategoryActivity extends AppCompatActivity implements BottomNavigationListener {
    BottomNavigationView bottomNavigationView;
    BottomNavigationManager bottomNavigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        SongForCategoryFragment fragment = new SongForCategoryFragment();
        Intent intent = getIntent();
        Category category = (Category) intent.getSerializableExtra("category");
        bottomNavigationView = findViewById(R.id.bottom_navigation_song_for_category);
        bottomNavigationManager = new BottomNavigationManager(this, bottomNavigationView);
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", category.getCategoryName());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_song_for_category, fragment).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_song_for_category, selectedFragment).commit();
        }
    }
}