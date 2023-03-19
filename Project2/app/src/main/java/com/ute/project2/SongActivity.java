package com.ute.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.Slider;
import com.ute.project2.model.Song;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SongActivity extends AppCompatActivity {

    MaterialToolbar tbTop;
    ImageView ivSongImage;
    BottomNavigationView bottomNavigationView;
    TextView tvCountDown;
    TextView tvEndTime;
    Slider sliderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        tbTop = findViewById(R.id.tbTop);
        ivSongImage = findViewById(R.id.ivSongImage);
        bottomNavigationView = findViewById(R.id.bottom_navigation_song);
        bottomNavigationView.setSelectedItemId(R.id.play_pause);
        tvCountDown = findViewById(R.id.tvCountDown);
        tbTop = findViewById(R.id.tbTop);
        tvEndTime = findViewById(R.id.tvEndTime);
        sliderTime = findViewById(R.id.sliderTime);
        Intent intent = getIntent();
        Song song = (Song) intent.getSerializableExtra("song");
        tbTop.setTitle(song.getSongName());
        tbTop.setSubtitle(song.getArtistsName());
        ivSongImage.setImageResource(song.getSongImage());
        DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String time = formatter.format(new Date(song.getSongTime()));
        tvEndTime.setText(time);
    }

}