package com.example.ofir.goldmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toolbar;

import com.example.ofir.goldmusic.Library;
import com.example.ofir.goldmusic.MusicPlayer;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.adapters.SongAdapter;
import com.example.ofir.goldmusic.item.Song;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity
{
    ArrayList<Song> songs;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)//todo figure out how to go back to artist from here
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        musicPlayer = new MusicPlayer((ViewGroup) findViewById(android.R.id.content));
        ((GridView) findViewById(R.id.grid)).setNumColumns(1);

        String artist = getIntent().getStringExtra("artist");
        int flag = getIntent().getExtras().getInt("flag");
        if (flag == 1)
        {
            songs = Library.getArtist(artist).getSongs();
            toolbar.setTitle(artist);
        }
        if (flag == 2)
        {
            String album = getIntent().getStringExtra("album");
            songs = Library.getAlbumSongs(artist, album);
            toolbar.setTitle(album);
        }

        GridView listView = findViewById(R.id.grid);
        listView.setNumColumns(1);
        listView.setAdapter(new SongAdapter(this, songs, musicPlayer));
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        musicPlayer.updateViews();
    }
}
