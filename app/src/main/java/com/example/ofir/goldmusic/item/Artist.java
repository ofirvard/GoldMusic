package com.example.ofir.goldmusic.item;

import java.util.ArrayList;

/**
 * Created by ofir on 10-Aug-17.
 */

public class Artist
{
    public String name;
    public ArrayList<Album> albums = new ArrayList<>();

    public Artist(Song song)
    {
        this.name = song.artistName;
        addSong(song);
    }

    public void addSong(Song song)
    {
        boolean found = false;
        for (Album album : albums)
        {
            if (album.name.equals(song.albumName))
            {
                found = true;
                album.add(song);
                break;
            }
        }
        if (!found)
        {
            albums.add(new Album(song, this));
        }
    }

    public ArrayList<Song> getSongs()
    {
        ArrayList<Song> songs = new ArrayList<>();
        for (Album album : albums)
        {
            songs.addAll(album.songs);
        }
        return songs;
    }

    public String toString()
    {
        return name;
    }

    public ArrayList<Album> albumsPlusAll()
    {
//        if (albums.size() <= 1)
//            return albums;
        ArrayList<Album> temp = new ArrayList<>();

        Album album = new Album(name, name, null);
        for (Song song : getSongs())
            album.add(song);
//        Tools.sortAlbums(albums);//todo check if you sort all list
        temp.add(album);
        temp.addAll(albums);

        return temp;
    }
}
