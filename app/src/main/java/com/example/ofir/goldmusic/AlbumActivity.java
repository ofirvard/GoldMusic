package com.example.ofir.goldmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toolbar;

import com.example.ofir.goldmusic.Library;
import com.example.ofir.goldmusic.MusicPlayer;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.adapters.AlbumAdapter;
import com.example.ofir.goldmusic.item.Album;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity
{
    ArrayList<Album> albums;
    MusicPlayer musicPlayer;
//    boolean includesAll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

//        albums = (ArrayList<Album>) getIntent().getExtras().get("list");
//        includesAll = getIntent().getExtras().getBoolean("includesAll");
        String artist = getIntent().getStringExtra("artist");
        albums = Library.getAlbumsPlusAll(artist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(artist);
        musicPlayer = new MusicPlayer((ViewGroup) findViewById(android.R.id.content));

        GridView gridView = findViewById(R.id.grid);
        gridView.setAdapter(new AlbumAdapter(this, albums, true));
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        musicPlayer.updateViews();
    }
}
