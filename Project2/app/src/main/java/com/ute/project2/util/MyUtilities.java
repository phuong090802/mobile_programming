package com.ute.project2.util;

import com.ute.project2.model.Song;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class MyUtilities {
    public static String formatTime(int time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return formatter.format(time);
    }

    public static Song nextSong(List<Song> songList, Song currentSong) {
        int index = IntStream.range(0, songList.size())
                .filter(i -> songList.get(i).getSongId().equals(currentSong.getSongId()))
                .findFirst()
                .orElse(0);
        index = (index + 1 < songList.size()) ? index + 1 : 0;
        return songList.get(index);
    }

    public static Song previousSong(List<Song> songList, Song currentSong) {
        int index = IntStream.range(0, songList.size())
                .filter(i -> songList.get(i).getSongId().equals(currentSong.getSongId()))
                .findFirst()
                .orElse(0);
        index = (index - 1 > 0) ? index - 1 : songList.size() - 1;
        return songList.get(index);
    }
}
