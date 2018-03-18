package com.example.ofir.goldmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ofir.goldmusic.adapters.AlbumAdapter;
import com.example.ofir.goldmusic.adapters.ArtistAdapter;
import com.example.ofir.goldmusic.adapters.PlaylistAdapter;
import com.example.ofir.goldmusic.adapters.SongAdapter;

public class MusicPlayerActivity extends AppCompatActivity
{
    GridView gridView;
    MusicPlayer musicPlayer;
    int flag;
    Toolbar toolbar;
    boolean playlistShow = false;
    Parcelable prePlaylistState;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        flag = getIntent().getIntExtra("flag", 0);
        gridView = findViewById(R.id.grid);
        musicPlayer = new MusicPlayer((ViewGroup) findViewById(android.R.id.content));
        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)//read permission
        {
            new Library(this);
            setGridViewAdapter();
        }
    }

    public void setGridViewAdapter()
    {
        switch (flag)
        {
            case 0://set all artists
                setArtists();
                break;

            case 1://set albums from artist
                setAlbums();
                break;

            case 2://set songs from album
                setSongs();
                break;

            case 3://set all songs from artist
                setAllSongs();
                break;

            default://artists
                setArtists();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
//     Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void playlist(MenuItem item)
    {
        if (playlistShow)//remove playlist
        {
            playlistShow = false;
            item.setIcon(R.drawable.playlist_off);
            setGridViewAdapter();
            gridView.onRestoreInstanceState(prePlaylistState);
        }
        else//show playlist
        {
            gridView.setNumColumns(1);
            playlistShow = true;
            item.setIcon(R.drawable.playlist_on);
            prePlaylistState = gridView.onSaveInstanceState();
            gridView.setAdapter(new PlaylistAdapter(this, musicPlayer));
        }
    }

    private void setArtists()
    {
//        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setNumColumns(3);
        toolbar.setTitle("Gold Music");
        toolbar.setSubtitle("By Ofir");
        ArtistAdapter artistAdapter = new ArtistAdapter(this, Library.artists);
        gridView.setAdapter(artistAdapter);
    }

    private void setAlbums()
    {
        gridView.setNumColumns(GridView.AUTO_FIT);
        String artist = getIntent().getStringExtra("artist");
        toolbar.setTitle("Music");
        toolbar.setSubtitle("By " + artist);
        gridView.setAdapter(new AlbumAdapter(this, Library.getAlbumsPlusAll(artist), true));
    }

    private void setSongs()
    {
        gridView.setNumColumns(1);
        String artist = getIntent().getStringExtra("artist");
        String album = getIntent().getStringExtra("album");
        toolbar.setTitle(album);
        toolbar.setSubtitle("By " + artist);
        gridView.setAdapter(new SongAdapter(this, Library.getAlbumSongs(artist, album), musicPlayer));
    }

    private void setAllSongs()
    {
        gridView.setNumColumns(1);
        String artist = getIntent().getStringExtra("artist");
        toolbar.setTitle("Music");
        toolbar.setSubtitle("By " + artist);
        gridView.setAdapter(new SongAdapter(this, Library.getArtist(artist).getSongs(), musicPlayer));
    }

    public void next(View view)
    {
        musicPlayer.next();
    }

    public void previous(View view)
    {
        musicPlayer.previous();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        musicPlayer.updateViews();
        TimeUpdater.setMusicPlayer(musicPlayer);
    }

    @Override
    public void onBackPressed()
    {
        if (flag != 0)
            super.onBackPressed();
    }
}
