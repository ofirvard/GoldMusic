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

    public Album(String name, String artistName, int cover, Context context)
    {
        this.name = name;
        this.artistName = artistName;
        this.cover = BitmapFactory.decodeResource(context.getResources(), cover);
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

//    protected Album(Parcel in)
//    {
//        name = in.readString();
//        cover = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
//        artistName = in.readString();
//        coverPath = in.readString();
//        if (in.readByte() == 0x01)
//        {
//            songs = new ArrayList<Song>();
//            in.readList(songs, Song.class.getClassLoader());
//        }
//        else
//        {
//            songs = null;
//        }
//    }
//
//    @Override
//    public int describeContents()
//    {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags)
//    {
//        dest.writeString(name);
//        dest.writeValue(cover);
//        dest.writeString(artistName);
//        dest.writeString(coverPath);
//        if (songs == null)
//        {
//            dest.writeByte((byte) (0x00));
//        }
//        else
//        {
//            dest.writeByte((byte) (0x01));
//            dest.writeList(songs);
//        }
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>()
//    {
//        @Override
//        public Album createFromParcel(Parcel in)
//        {
//            return new Album(in);
//        }
//
//        @Override
//        public Album[] newArray(int size)
//        {
//            return new Album[size];
//        }
//    };
}
