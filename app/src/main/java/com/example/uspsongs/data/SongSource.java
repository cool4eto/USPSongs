package com.example.uspsongs.data;

import com.example.uspsongs.model.Song;

import java.util.ArrayList;

public class SongSource {
    ArrayList<Song> songs;

    public SongSource()
    {
        songs=new ArrayList<Song>();
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }
    public void addSong(Song song1)
    {
        songs.add(song1);
    }

}
