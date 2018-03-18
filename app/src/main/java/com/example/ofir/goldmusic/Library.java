package com.example.ofir.goldmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.ofir.goldmusic.item.Album;
import com.example.ofir.goldmusic.item.Artist;
import com.example.ofir.goldmusic.item.Song;

import java.util.ArrayList;

/**
 * Created by ofir on 12/27/2017.
 */

public class Library
{
    public static ArrayList<Artist> artists = new ArrayList<>();
    public static ArrayList<Song> songs = new ArrayList<>();
    private static boolean libraryLoaded = false;

    public Library(Context context)
    {
        if (!libraryLoaded)
        {
            ContentResolver musicResolver = context.getContentResolver();
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
            Cursor musicCursor = musicResolver.query(musicUri, null, selection, null, null);

            if (musicCursor != null && musicCursor.moveToFirst())
            {
                //get columns
                int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int durationColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int dataColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int isMusicColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);

                //add songs to list
                do
                {
                    if (musicCursor.getInt(isMusicColumn) != 0)
                    {
                        long id = musicCursor.getLong(idColumn);
                        String title = musicCursor.getString(titleColumn);
                        String artist = musicCursor.getString(artistColumn);
                        String album = musicCursor.getString(albumColumn);
                        long duration = musicCursor.getLong(durationColumn);
                        String path = musicCursor.getString(dataColumn);

                        if (artist.equals("<unknown>"))
                            artist = "Unknown";
                        if (album.equals("<unknown>"))
                            album = "Unknown";

                        songs.add(new Song(title, album, artist, duration, id, path));
                    }
                }
                while (musicCursor.moveToNext());
            }
            musicCursor.close();

            //set artist and album
            for (Song song : songs)
            {
                songToArtist(song);
            }

            //todo check why this messes up with song adapter
            //set covers
            ArrayList<Cover> covers = getCovers(context);
            for (Artist artist : artists)
            {
                for (Album album : artist.albums)
                {
                    boolean found = false;
                    for (Cover cover : covers)
                    {
                        if (album.name.equals(cover.albumName))
                        {
                            found = true;
                            album.cover = BitmapFactory.decodeFile(cover.path);
                            break;
                        }
                    }
                    if (!found)
                    {
                        album.cover = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_mark_low_res);
                    }
                }
            }


//            organiseAlbums(context);
//            organiseArtists(context);
//            setSongsToAlbums();
//            setAlbumsToArtists();
            sort();
            libraryLoaded = true;
        }
    }

    private ArrayList<Cover> getCovers(Context context)
    {
        ArrayList<Cover> covers = new ArrayList<>();

        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst())
        {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int coverColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

            //add songs to list
            do
            {
                String name = musicCursor.getString(titleColumn);
                String cover = musicCursor.getString(coverColumn);

                if (cover != null)
                    covers.add(new Cover(name, cover));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
        return covers;
    }

    private void songToArtist(Song song)
    {
        boolean found = false;
        for (Artist artist : artists)
        {
            if (artist.name.equals(song.artistName))
            {
                found = true;
                artist.addSong(song);
                break;
            }
        }
        if (!found)
        {
            artists.add(new Artist(song));
        }
    }

    void organiseAlbums(Context context)
    {
        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst())
        {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int coverColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

            //add songs to list
            do
            {
                String name = musicCursor.getString(titleColumn);
                String cover = musicCursor.getString(coverColumn);
                String artist = musicCursor.getString(artistColumn);

                if (artist.equals("<unknown>"))
                    artist = "Unknown";

                if (cover == null)
                    albums.add(new Album(name, artist, R.drawable.question_mark_low_res, context));
                else
                    albums.add(new Album(name, artist, cover));
            }
            while (musicCursor.moveToNext());
        }
//        musicCursor.close();
    }

    void organiseArtists(Context context)
    {
        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst())
        {
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do
            {
                String artist = musicCursor.getString(artistColumn);

                if (artist.equals("<unknown>"))
                    artist = "Unknown";

                artists.add(new Artist(artist));
            }
            while (musicCursor.moveToNext());
        }
//        musicCursor.close();
    }

    void setAlbumsToArtists()
    {
        for (Album album : albums)
        {
            for (Artist artist : artists)
            {
                if (artist.name.equals(album.artistName))
                {
//                    album.artistPath = artist;//todo restore this
                    artist.add(album);
                    break;
                }
            }
        }
    }

    void setSongsToAlbums()
    {
        for (Song song : songs)
        {
            for (Album album : albums)
            {
                if (album.name.equals(song.albumName))
                {
//                    song.coverPath = album.coverPath;
                    song.album = album;//todo solve later
                    album.add(song);
                    break;
                }
            }
        }
    }

    void sort()
    {
        //first i remove empty albums
//        for (Album album : albums)
//            if (album.songs.isEmpty())
//                albums.remove(album);
//
//        //remove empty artists
//        for (Artist artist : artists)
//            if (artist.albums.isEmpty())
//                artists.remove(artist);

        //sort
        Tools.sortArtists(artists);
        for (Artist artist : artists)
            Tools.sortAlbums(artist.albums);
        Tools.sortAlbums(albums);
        for (Album album : albums)
            Tools.sortSongs(album.songs);
        Tools.sortSongs(songs);
    }

    public static Artist getArtist(String artistName)
    {
        for (Artist artist : artists)
        {
            if (artist.name.equals(artistName))
                return artist;
        }
        return null;
    }

    public static ArrayList<Album> getAlbumsPlusAll(String artistName)
    {
        for (Artist artist : artists)
        {
            if (artist.name.equals(artistName))
                return artist.albumsPlusAll();
        }
        return null;
    }

    public static ArrayList<Album> getAlbums(String artistName)
    {
        for (Artist artist : artists)
        {
            if (artist.name.equals(artistName))
                return artist.albums;
        }
        return null;
    }

    public static ArrayList<Song> getAlbumSongs(String artistName, String albumName)//todo check this out
    {
        for (Album album : getAlbums(artistName))
        {
            if (album.name.equals(albumName))
                return album.songs;
        }
        return null;
    }

    class Cover
    {
        String albumName;
        String path;

        public Cover(String albumName, String path)
        {
            this.albumName = albumName;
            this.path = path;
        }

        @Override
        public String toString()
        {
            return albumName;
        }
    }

//    ArrayList<Song> search(String name)//// TODO: 8/19/2017 impalement this
//    {
//        ArrayList<Song> list = new ArrayList<>();
//        for (Song song : songs)
//        {
//            if (song.title.contains(name))
//                list.add(song);
//        }
//        return list;
//    }

    //too slow
//                if (song.album.cover == null)//set cover to album
//                {
//                    mmr.setDataSource(context, Uri.parse(song.path));
//                    byte[] rawArt = mmr.getEmbeddedPicture();
//                    if (null != rawArt)
//                        song.album.cover = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
//                    else
//                        song.album.cover = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_mark_low_res);
//                }
}
