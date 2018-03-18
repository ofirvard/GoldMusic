package com.example.ofir.goldmusic.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ofir on 8/4/2017.
 */

public class Song //implements Parcelable//todo restore this
{
    public String title;
    public String path;
    public String albumName;
    public String artistName;
    public String durationS;
    public String coverPath;
    public long duration;
    public long id;
    public Album album;

    public Song(String title, String albumName, String artistName, long duration, long id, String path)
    {
        this.title = title;
        this.albumName = albumName;
        this.artistName = artistName;
        this.duration = duration;
        this.id = id;
        this.path = path;

        //duration as string
        long temp = duration / 1000;
        long h = temp / 3600;
        long m = (temp - h * 3600) / 60;
        long s = temp - (h * 3600 + m * 60);
        if (h == 0)
            this.durationS = m + ":" + s;
        else
            this.durationS = h + ":" + m + ":" + s;
    }

    public String toString()
    {
        return title + "\n" +
                albumName + " | " + artistName;
    }

    public String info()
    {
        return albumName + " | " + artistName;
    }
}
