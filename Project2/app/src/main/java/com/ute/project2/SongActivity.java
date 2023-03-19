package com.ute.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ute.project2.model.Song;

public class SongActivity extends AppCompatActivity implements BottomNavigationListener {

    BottomNavigationView bottomNavigationView;
    BottomNavigationManager bottomNavigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        bottomNavigationView = findViewById(R.id.bottom_navigation_song);
        bottomNavigationManager = new BottomNavigationManager(this, bottomNavigationView);
        SongFragment fragment = new SongFragment();
        Intent intent = getIntent();
        Song song = (Song) intent.getSerializableExtra("song");
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", song);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_song, fragment).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_song, selectedFragment).commit();
        }
    }
}