package com.example.ofir.goldmusic.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.item.ViewAdapterHolder;
import com.example.ofir.goldmusic.item.Album;
import com.example.ofir.goldmusic.item.Artist;
import com.example.ofir.goldmusic.tabs.TabAdapter;

import java.util.ArrayList;

/**
 * Created by ofir on 2/15/2018.
 */

public class ArtistAdapter extends ArrayAdapter
{
    Context context;
    ArrayList<Artist> artists;
    TabAdapter tabAdapter;
//    MusicPlayer musicPlayer;

    public ArtistAdapter(Context context, ArrayList<Artist> artists, TabAdapter tabAdapter)
    {
        super(context, 0, artists);
        this.context = context;
        this.artists = artists;
        this.tabAdapter = tabAdapter;
    }

    @NonNull
    public View getView(final int i, View convertView, ViewGroup parent)
    {
        ViewAdapterHolder holder;
        final Artist artist = artists.get(i);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.switchable_item, parent, false);
            holder = new ViewAdapterHolder();
            holder.single = convertView.findViewById(R.id.single);
            holder.cover = convertView.findViewById(R.id.cover);
            holder.multiple = convertView.findViewById(R.id.multiple);
            holder.top_left = convertView.findViewById(R.id.top_left);
            holder.top_right = convertView.findViewById(R.id.top_right);
            holder.bottom_left = convertView.findViewById(R.id.bottom_left);
            holder.bottom_right = convertView.findViewById(R.id.bottom_right);
            holder.name = convertView.findViewById(R.id.name);
            holder.menu = convertView.findViewById(R.id.menu);

            convertView.setTag(holder);
        }
        else
            holder = (ViewAdapterHolder) convertView.getTag();

        //set text
        holder.name.setText(artist.name);

        //set cover/s and visibility
        if (artist.albums.size() <= 1)
        {
            if (holder.multiple.getVisibility() == View.VISIBLE)
                holder.multiple.setVisibility(View.GONE);

            if (holder.single.getVisibility() == View.GONE)
                holder.single.setVisibility(View.VISIBLE);

            setCover(holder.cover, artist.albums.get(0).cover);
        }
        else
        {
            if (holder.multiple.getVisibility() == View.GONE)
                holder.multiple.setVisibility(View.VISIBLE);

            if (holder.single.getVisibility() == View.VISIBLE)
                holder.single.setVisibility(View.GONE);

            setCovers(artist.albums, holder);
        }

        //set onclick openArtist
        View.OnClickListener openArtist = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                pagerAdapter.addFragment(new AlbumAdapter(context, artist.albumsPlusAll(), true, pagerAdapter), 2);
                tabAdapter.add(new AlbumAdapter(context, artist.albumsPlusAll(), true, tabAdapter), 2);
                tabAdapter.next();
            }
        };
        holder.single.setOnClickListener(openArtist);
        holder.multiple.setOnClickListener(openArtist);
        holder.name.setOnClickListener(openArtist);

        // TODO: 2/23/2018 set menu

        return convertView;
    }

    private void setCovers(ArrayList<Album> albums, ViewAdapterHolder holder)
    {
        switch (albums.size())
        {
            case 2:
//                setCover(holder.top_left, ImageLoader.getAlbumart(context,albums.get(0).songs.get(0).albumId));
//                setCover(holder.top_right, ImageLoader.getAlbumart(context,albums.get(1).songs.get(0).albumId));
//                setCover(holder.bottom_left, ImageLoader.getAlbumart(context,albums.get(1).songs.get(0).albumId));
//                setCover(holder.bottom_right, ImageLoader.getAlbumart(context,albums.get(0).songs.get(0).albumId));
                setCover(holder.top_left, albums.get(0).cover);
                setCover(holder.top_right, albums.get(1).cover);
                setCover(holder.bottom_left, albums.get(1).cover);
                setCover(holder.bottom_right, albums.get(0).cover);
                break;

            case 3:
                setCover(holder.top_left, albums.get(0).cover);
                setCover(holder.top_right, albums.get(1).cover);
                setCover(holder.bottom_left, albums.get(2).cover);
                setCover(holder.bottom_right, albums.get(0).cover);
                break;

            default:// four or more
                setCover(holder.top_left, albums.get(0).cover);
                setCover(holder.top_right, albums.get(1).cover);
                setCover(holder.bottom_left, albums.get(2).cover);
                setCover(holder.bottom_right, albums.get(3).cover);
        }
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
