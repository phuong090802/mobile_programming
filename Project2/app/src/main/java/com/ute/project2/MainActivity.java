package com.ute.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ute.project2.constant.Constant;
import com.ute.project2.event.ItemLibraryListener;
import com.ute.project2.event.OnViewClickListener;
import com.ute.project2.model.Genre;
import com.ute.project2.model.ItemLibrary;
import com.ute.project2.model.Song;
import com.ute.project2.sharedpreferences.StorageSingleton;

import java.util.Stack;


public class MainActivity extends AppCompatActivity implements OnViewClickListener, ItemLibraryListener {
    BottomNavigationView bottomNavigationView;
    private final Stack<Fragment> fragmentStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);

        if (getIntent() != null && getIntent().hasExtra("song") && getIntent().hasExtra("isPlaying")) {
            Song song = (Song) getIntent().getSerializableExtra("song");
            boolean isPlaying = getIntent().getBooleanExtra("isPlaying", false);
            StorageSingleton.putString(Constant.CURRENT_SONG_NAME, song.getSongName());
            StorageSingleton.putString(Constant.STORAGE_SONG_NAME, song.getSongName());
            fragmentStack.push(new SearchFragment());
            SongFragment songFragment = new SongFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", song);
            bundle.putSerializable("isPlaying", isPlaying);
            songFragment.setArguments(bundle);

            replaceFragment(songFragment);
        } else {
            replaceFragment(new SearchFragment());
        }
    }


    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.mnSearch) {
            replaceFragment(new SearchFragment());
            return true;
        } else if (itemId == R.id.mnYourLibrary) {
            replaceFragment(new YourLibraryFragment());
            return true;
        }

        return false;
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentStack.push(fragment);
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            fragmentStack.pop();
            Fragment previousFragment = fragmentStack.peek();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, previousFragment);
            fragmentTransaction.commit();

            if (previousFragment instanceof SearchFragment) {
                bottomNavigationView.getMenu().findItem(R.id.mnSearch).setChecked(true);
            } else if (previousFragment instanceof YourLibraryFragment) {
                bottomNavigationView.getMenu().findItem(R.id.mnYourLibrary).setChecked(true);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSearchBarClicked() {
        replaceFragment(new OnSearchFragment());
    }

    @Override
    public void onCardViewGenreClicked(Genre genre) {
        GenreFragment genreFragment = new GenreFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("genre", genre);
        genreFragment.setArguments(bundle);
        replaceFragment(genreFragment);
    }

    @Override
    public void onItemSongClicked(Song song) {
        SongFragment songFragment = new SongFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", song);
        songFragment.setArguments(bundle);
        replaceFragment(songFragment);
    }

    @Override
    public void onItemLibraryOnClick(ItemLibrary itemLibrary) {
        if (itemLibrary.getText() == R.string.favorites) {
            replaceFragment(new FavoriteFragment());
        } else if (itemLibrary.getText() == R.string.download) {
            replaceFragment(new DownloadFragment());
        }
    }

}