package com.ute.project2;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationManager implements BottomNavigationView.OnItemSelectedListener {

    private final BottomNavigationListener listener;

    public BottomNavigationManager(BottomNavigationListener listener, BottomNavigationView bottomNavigationView) {
        this.listener = listener;
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        listener.onBottomNavigationItemSelected(itemId);
        return true;
    }
}
