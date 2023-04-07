package com.ute.project2.database;

import android.content.Context;

import com.ute.project2.R;
import com.ute.project2.model.Genre;
import com.ute.project2.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Database {
    public static List<Song> getSongListFavorite(Context context) {
        List<Song> songListFavorite = new ArrayList<>();
        songListFavorite.add(new Song("Neil", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.edm_deep_house_ish_female_vocal_112184).replace(":", "/"), "Dax", "Skyler", "1:25"));
        songListFavorite.add(new Song("Salem", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.groovy_light_future_bass_15881).replace(":", "/"), "Dax", "Mohammed", "1:23"));
        songListFavorite.add(new Song("Remi", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lkb_one_hip_hop_beat_300_138129).replace(":", "/"), "Dax", "Dennis", "2:40"));
        songListFavorite.add(new Song("Liam", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lonely_girl_beat_9522).replace(":", "/"), "Dax", "Nixon", "2:15"));
        songListFavorite.add(new Song("Noah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.solitarymaninblack_putin_99465).replace(":", "/"), "Dax", "Rex", "5:59"));
        songListFavorite.add(new Song("Oliver", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song).replace(":", "/"), "Dax", "Uriah", "6:12"));
        songListFavorite.add(new Song("Elijah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_10).replace(":", "/"), "Dax", "Lee", "8:47"));
        songListFavorite.add(new Song("James", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_11).replace(":", "/"), "Dax", "Louie", "9:11"));
        return songListFavorite;
    }

    public static List<Song> getSongList(Context context) {
        List<Song> songList = new ArrayList<>();
        songList.add(new Song("Neil", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.edm_deep_house_ish_female_vocal_112184).replace(":", "/"), "Dax", "Skyler", "1:25"));
        songList.add(new Song("Salem", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.groovy_light_future_bass_15881).replace(":", "/"), "Dax", "Mohammed", "1:23"));
        songList.add(new Song("Remi", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lkb_one_hip_hop_beat_300_138129).replace(":", "/"), "Dax", "Dennis", "2:40"));
        songList.add(new Song("Liam", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.lonely_girl_beat_9522).replace(":", "/"), "Dax", "Nixon", "2:15"));
        songList.add(new Song("Noah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.solitarymaninblack_putin_99465).replace(":", "/"), "Dax", "Rex", "5:59"));
        songList.add(new Song("Oliver", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song).replace(":", "/"), "Dax", "Uriah", "6:12"));
        songList.add(new Song("Elijah", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_10).replace(":", "/"), "Dax", "Lee", "8:47"));
        songList.add(new Song("James", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_11).replace(":", "/"), "Dax", "Louie", "9:11"));
        songList.add(new Song("William", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_12).replace(":", "/"), "Dax", "Alberto", "8:33"));
        songList.add(new Song("Benjamin", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_13).replace(":", "/"), "Dax", "Reese", "7:50"));
        songList.add(new Song("Lucas", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_14).replace(":", "/"), "Dax", "Quinton", "8:36"));
        songList.add(new Song("Henry", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_15).replace(":", "/"), "Dax", "Kingsley", "7:18"));
        songList.add(new Song("Theodore", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_16).replace(":", "/"), "Francis", "Chaim", "8:03"));
        songList.add(new Song("Jack", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_17).replace(":", "/"), "Francis", "Alfredo", "11:15"));
        songList.add(new Song("Levi", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_2).replace(":", "/"), "Francis", "Mauricio", "7:05"));
        songList.add(new Song("Alexander", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_3).replace(":", "/"), "Francis", "Caspian", "5:44"));
        songList.add(new Song("Jackson", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_4).replace(":", "/"), "Francis", "Legacy", "5:02"));
        songList.add(new Song("Mateo", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_5).replace(":", "/"), "Francis", "Ocean", "5:53"));
        songList.add(new Song("Daniel", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_6).replace(":", "/"), "Francis", "Ozzy", "4:39"));
        songList.add(new Song("Michael", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_7).replace(":", "/"), "Francis", "Briar", "7:00"));
        songList.add(new Song("Mason", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_8).replace(":", "/"), "Francis", "Wilson", "5:25"));
        songList.add(new Song("Sebastian", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.soundhelix_song_9).replace(":", "/"), "Francis", "Forest", "6:29"));
        songList.add(new Song("Ethan", R.drawable.song, "android.resource://" + context.getResources().getResourceName(R.raw.tomorrow_114848).replace(":", "/"), "Francis", "Grey", "2:24"));
        return songList;
    }


    public static List<Genre> getGenreList() {
        List<Genre> GenreList = new ArrayList<>();
        GenreList.add(new Genre("Dax", R.drawable.genre));
        GenreList.add(new Genre("Francis", R.drawable.genre));
        GenreList.add(new Genre("Levi", R.drawable.genre));
        GenreList.add(new Genre("Deacon", R.drawable.genre));
        GenreList.add(new Genre("Alexis", R.drawable.genre));
        GenreList.add(new Genre("Princeton", R.drawable.genre));
        GenreList.add(new Genre("Iker", R.drawable.genre));
        GenreList.add(new Genre("Wells", R.drawable.genre));
        GenreList.add(new Genre("Nikolai", R.drawable.genre));
        GenreList.add(new Genre("Moshe", R.drawable.genre));
        GenreList.add(new Genre("Cassius", R.drawable.genre));
        GenreList.add(new Genre("Gregory", R.drawable.genre));
        GenreList.add(new Genre("Lewis", R.drawable.genre));
        GenreList.add(new Genre("Kieran", R.drawable.genre));
        GenreList.add(new Genre("Isaias", R.drawable.genre));
        GenreList.add(new Genre("Seth", R.drawable.genre));
        GenreList.add(new Genre("Marcos", R.drawable.genre));
        GenreList.add(new Genre("Omari", R.drawable.genre));
        GenreList.add(new Genre("Keegan", R.drawable.genre));
        return GenreList;
    }

    public static Song getRandomElement(Context context) {
        Random rand = new Random();
        List<Song> songList = getSongList(context);
        int index = rand.nextInt(Objects.requireNonNull(songList).size());
        return songList.get(index);
    }

}
