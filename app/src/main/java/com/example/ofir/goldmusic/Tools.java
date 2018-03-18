package com.example.ofir.goldmusic;

import com.example.ofir.goldmusic.item.Album;
import com.example.ofir.goldmusic.item.Artist;
import com.example.ofir.goldmusic.item.Song;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ofir on 2/4/2018.
 */

public class Tools
{
    static Comparator<Album> albumComparator = new Comparator<Album>()
    {
        @Override
        public int compare(Album o1, Album o2)
        {
            return o1.name.compareTo(o2.name);
        }
    };

    static Comparator<Artist> artistComparator = new Comparator<Artist>()
    {
        @Override
        public int compare(Artist o1, Artist o2)
        {
            return o1.name.compareTo(o2.name);
        }
    };

    static Comparator<Song> songComparator = new Comparator<Song>()
    {
        @Override
        public int compare(Song o1, Song o2)
        {
            return o1.title.compareTo(o2.title);
        }
    };

    public static void sortArtists(ArrayList<Artist> list)
    {
        Collections.sort(list, artistComparator);
    }

    public static void sortAlbums(ArrayList<Album> list)
    {
        Collections.sort(list, albumComparator);
    }

    public static void sortSongs(ArrayList<Song> list)
    {
        Collections.sort(list, songComparator);
    }
}
