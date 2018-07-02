package com.example.ofir.goldmusic.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ofir on 8/4/2017.
 */

public class Album //implements Parcelable//todo try to figure this out
{
    public String name;
    public Bitmap cover;
    public String artistName;
    public ArrayList<Song> songs = new ArrayList<>();
    public Artist artistPath;

    public Album(String name, String artistName, String cover)
    {
        this.name = name;
        this.artistName = artistName;
        this.cover = BitmapFactory.decodeFile(cover);
    }

    public Album(Song song, Artist artist)
    {
        this.name = song.albumName;
        this.artistName = song.artistName;
//        this.cover = BitmapFactory.decodeFile(song.coverPath);
        song.album = this;
        songs.add(song);
        this.artistPath = artist;
    }

    public void add(Song song)
    {
        song.album = this;
        songs.add(song);
    }

    public String toString()
    {
        return name + "|" + artistName;
    }
}
