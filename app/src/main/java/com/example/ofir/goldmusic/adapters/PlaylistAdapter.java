package com.example.ofir.goldmusic.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ofir.goldmusic.MusicPlayer;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.item.Album;
import com.example.ofir.goldmusic.item.Song;
import com.example.ofir.goldmusic.item.ViewAdapterHolder;
import com.example.ofir.goldmusic.item.ViewPlaylistHolder;
import com.example.ofir.goldmusic.item.ViewSongHolder;

import java.util.ArrayList;

/**
 * Created by ofir on 2/28/2018.
 */

public class PlaylistAdapter extends ArrayAdapter
{
    Context context;
    MusicPlayer musicPlayer;

    public PlaylistAdapter(Context c, MusicPlayer musicPlayer)
    {
        super(c, 0, MusicPlayer.playlist);
        context = c;
        this.musicPlayer = musicPlayer;
    }

    public View getView(final int i, View convertView, ViewGroup parent)
    {
        ViewPlaylistHolder holder;
        final Song song = MusicPlayer.playlist.get(i);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
            holder = new ViewPlaylistHolder();
            holder.cover = convertView.findViewById(R.id.song_cover);
            holder.name = convertView.findViewById(R.id.title);
            holder.info = convertView.findViewById(R.id.info);
            holder.menu = convertView.findViewById(R.id.menu);
            holder.remove = convertView.findViewById(R.id.remove);

            convertView.setTag(holder);
        }
        else
            holder = (ViewPlaylistHolder) convertView.getTag();

        //set text
        holder.name.setText(song.title);
        holder.info.setText(song.info());

        //set cover
        setCover(holder.cover, song.album.cover);

        //remove
        View.OnClickListener remove = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                musicPlayer.remove(i);
                notifyDataSetChanged();
            }
        };
        holder.remove.setOnClickListener(remove);

        //todo onclick play now
        //todo menu

        return convertView;
    }

    private void setCover(ImageView imageView, Bitmap cover)
    {
        if (cover != null)
        {
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(cover);
        }
    }
}
