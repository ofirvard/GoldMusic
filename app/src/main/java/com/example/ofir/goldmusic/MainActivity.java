package com.example.ofir.goldmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toolbar;

import com.example.ofir.goldmusic.adapters.ArtistAdapter;
import com.example.ofir.goldmusic.adapters.PlaylistAdapter;
import com.example.ofir.goldmusic.item.GridState;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    //todo make it tabs
    GridView gridView;
    MusicPlayer musicPlayer;
    Toolbar toolbar;
    boolean playlistShow = false;
    //    Parcelable prePlaylistState;
//    ArrayList<GridState> saveGridStates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        gridView.setNumColumns(2);
        toolbar.setTitle("Gold Music");
        toolbar.setSubtitle("By Ofir");
//        ArtistAdapter artistAdapter = new ArtistAdapter(this, Library.artists, gridView);
//        gridView.setAdapter(artistAdapter);
    }

    public void next(View view)
    {
        musicPlayer.next();
    }

    public void previous(View view)
    {
        musicPlayer.previous();
    }

    public void playlist(MenuItem item)//todo make this a drawer
    {
        if (playlistShow)//remove playlist
        {
            playlistShow = false;
            item.setIcon(R.drawable.playlist_off);
            setGridViewAdapter();
//            gridView.onRestoreInstanceState(prePlaylistState);
        }
        else//show playlist
        {
            gridView.setNumColumns(1);
            playlistShow = true;
            item.setIcon(R.drawable.playlist_on);
//            prePlaylistState = gridView.onSaveInstanceState();
            gridView.setAdapter(new PlaylistAdapter(this, musicPlayer));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
//     Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
