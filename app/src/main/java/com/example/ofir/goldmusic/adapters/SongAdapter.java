package com.example.ofir.goldmusic.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ofir.goldmusic.ImageLoader;
import com.example.ofir.goldmusic.MusicPlayer;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.item.ViewAdapterHolder;
import com.example.ofir.goldmusic.item.Song;
import com.example.ofir.goldmusic.item.ViewSongHolder;

import java.util.ArrayList;

/**
 * Created by ofir on 2/20/2018.
 */

public class SongAdapter extends ArrayAdapter
{
    Context context;
    ArrayList<Song> songs;
    MusicPlayer musicPlayer;

    public SongAdapter(Context c, ArrayList<Song> songs, MusicPlayer musicPlayer)
    {
        super(c, 0, songs);
        context = c;
        this.songs = songs;
        this.musicPlayer = musicPlayer;
    }

    public View getView(final int i, View convertView, ViewGroup parent)
    {
        ViewSongHolder holder;
        final Song song = songs.get(i);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.song_item_test, parent, false);
            holder = new ViewSongHolder();
            holder.cover = convertView.findViewById(R.id.song_cover);
            holder.name = convertView.findViewById(R.id.name);
            holder.info = convertView.findViewById(R.id.info);
            holder.menu = convertView.findViewById(R.id.menu);

            convertView.setTag(holder);
        }
        else
            holder = (ViewSongHolder) convertView.getTag();

        //set text
        holder.name.setText(song.title);
        holder.info.setText(song.info());

        //set cover
        setCover(holder.cover, song.albumId);

        //todo onclick
        //set onclick addToEnd
        View.OnClickListener addSongToEnd = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                musicPlayer.addToEnd(song);
            }
        };
        holder.cover.setOnClickListener(addSongToEnd);
        holder.name.setOnClickListener(addSongToEnd);
        holder.info.setOnClickListener(addSongToEnd);

        //todo menu

        return convertView;
    }

    private void setCover(ImageView imageView, long cover)
    {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(ImageLoader.getAlbumart(context, cover));
    }
}
