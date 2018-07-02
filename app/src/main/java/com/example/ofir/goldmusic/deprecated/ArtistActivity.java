package com.example.ofir.goldmusic.deprecated;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Toolbar;

import com.example.ofir.goldmusic.Library;
import com.example.ofir.goldmusic.MusicPlayer;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.adapters.AlbumAdapter;
import com.example.ofir.goldmusic.adapters.ArtistAdapter;
import com.example.ofir.goldmusic.adapters.SongAdapter;

public class ArtistActivity extends AppCompatActivity
{
    //    ArrayList<ArrayAdapter> adapterHistory = new ArrayList<>();
    GridView gridView;
    SeekBar seekBar;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)//todo make this into tab layout with artist and album
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(GridView.AUTO_FIT);
//        this.seekBar = findViewById(R.id.c_seek);
        musicPlayer = new MusicPlayer((ViewGroup) findViewById(android.R.id.content));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gold Music");
        toolbar.setSubtitle("By Ofir");
        setActionBar(toolbar);//todo remove this? do i need it?

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)//read permission
        {
            new Library(this);

//            ArtistAdapter artistAdapter = new ArtistAdapter(this, Library.artists);
//            gridView.setAdapter(artistAdapter);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        musicPlayer.updateViews();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
//     Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public void changeList(MenuItem view)//todo maybe remove this and always start at artist or move back to tabs
    {
//        switch (view.getItemId())
//        {
//            case R.id.artist:
//                ArtistAdapter artistAdapter = new ArtistAdapter(this, Library.artists);
//                gridView.setAdapter(artistAdapter);
//                break;
//
//            case R.id.album:
//                AlbumAdapter albumAdapter = new AlbumAdapter(this, Library.albums, false);
//                gridView.setAdapter(albumAdapter);
//                break;
//
//            case R.id.song:
//                SongAdapter songAdapter = new SongAdapter(this, Library.songs, musicPlayer);
//                gridView.setNumColumns(1);
//                gridView.setAdapter(songAdapter);
//                break;
//
//            default:
//                ArtistAdapter defaultAdapter = new ArtistAdapter(this, Library.artists);
//                gridView.setAdapter(defaultAdapter);
//        }
    }
}
