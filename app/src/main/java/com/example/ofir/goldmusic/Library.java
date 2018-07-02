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
import java.util.Collections;
import java.util.Comparator;

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
                int albumIdColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
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
                        long albumId = musicCursor.getLong(albumIdColumn);

                        if (artist.equals("<unknown>"))
                            artist = "Unknown";
                        if (album.equals("<unknown>"))
                            album = "Unknown";

                        songs.add(new Song(title, album, artist, duration, id, path, albumId));
                    }
                }
                while (musicCursor.moveToNext());
            }
            musicCursor.close();

            sortSongs(context);
            libraryLoaded = true;
        }
    }

    //sets songs to artists and albums, then sorts it all
    private void sortSongs(Context context)
    {
        boolean foundArtist;
        for (Song song : songs)
        {
            foundArtist = false;
            for (Artist artist : artists)
            {
                if (song.artistName.equals(artist.name))
                {
                    artist.addSong(song);
                    foundArtist = true;
                    break;
                }
            }
            if (!foundArtist)
                artists.add(new Artist(song));
        }

        //sorts all songs
        sortSongs(songs);

        //sort artists
        sortArtists(artists);

        //sort albums, songs and set cover
        for (Artist artist : artists)
        {
            //albums
            sortAlbums(artist.albums);

            for (Album album : artist.albums)
            {
                //sort album songs
                sortSongs(album.songs);

                //set cover
                album.cover = ImageLoader.getAlbumart(context, album.songs.get(0).albumId);
            }
        }
    }

    private static Comparator<Album> albumComparator = new Comparator<Album>()
    {
        @Override
        public int compare(Album o1, Album o2)
        {
            return o1.name.compareTo(o2.name);
        }
    };

    private static Comparator<Artist> artistComparator = new Comparator<Artist>()
    {
        @Override
        public int compare(Artist o1, Artist o2)
        {
            return o1.name.compareTo(o2.name);
        }
    };

    private static Comparator<Song> songComparator = new Comparator<Song>()
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
