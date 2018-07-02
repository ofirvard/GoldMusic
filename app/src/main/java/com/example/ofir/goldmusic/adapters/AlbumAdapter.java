package com.example.ofir.goldmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.ofir.goldmusic.MusicPlayer;
import com.example.ofir.goldmusic.deprecated.MusicPlayerActivity;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.item.ViewAdapterHolder;
import com.example.ofir.goldmusic.item.Album;
import com.example.ofir.goldmusic.tabs.TabAdapter;

import java.util.ArrayList;

/**
 * Created by ofir on 2/15/2018.
 */

public class AlbumAdapter extends ArrayAdapter
{
    Context context;
    ArrayList<Album> albums;
    boolean includesAll;
    //    MusicPlayer musicPlayer;
    TabAdapter pagerAdapter;

    AlbumAdapter(Context c, ArrayList<Album> albums, boolean includesAll, TabAdapter pagerAdapter)
    {
        super(c, 0, albums);
        context = c;
        this.albums = albums;
        this.includesAll = includesAll;
        this.pagerAdapter = pagerAdapter;
    }

    @NonNull
    public View getView(final int i, View convertView, ViewGroup parent)
    {
        ViewAdapterHolder holder;
        final Album album = albums.get(i);

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
        holder.name.setText(album.name);

        //set cover/s and visibility
        if (includesAll && i == 0)//all cover, four covers
        {
            if (holder.multiple.getVisibility() == View.GONE)
                holder.multiple.setVisibility(View.VISIBLE);

            if (holder.single.getVisibility() == View.VISIBLE)
                holder.single.setVisibility(View.GONE);

            setAllAlbum(albums, holder);
        }
        else//normal album
        {
            if (holder.multiple.getVisibility() == View.VISIBLE)
                holder.multiple.setVisibility(View.GONE);

            if (holder.single.getVisibility() == View.GONE)
                holder.single.setVisibility(View.VISIBLE);

            setCover(holder.cover, album.cover);
        }

        //set onclick openAlbum
        OnClickListener openAlbum = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: 28-Jun-18 add here to song adapter
            }
        };
        holder.single.setOnClickListener(openAlbum);
        holder.multiple.setOnClickListener(openAlbum);
        holder.name.setOnClickListener(openAlbum);

        // TODO: 2/23/2018 set menu
        OnClickListener openMenu = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.album_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.play_next:
//                                musicplayer.
                                break;

                            case R.id.add_all:
                                break;
                        }
                        return true;
                    }
                });
                popup.show();

            }
        };
        holder.menu.setOnClickListener(openMenu);

        return convertView;
    }

    private void setAllAlbum(ArrayList<Album> albums, ViewAdapterHolder holder)
    {
        switch (albums.size())
        {
            case 2://one more
                setCover(holder.top_left, albums.get(1).cover);
                setCover(holder.top_right, albums.get(1).cover);
                setCover(holder.bottom_left, albums.get(1).cover);
                setCover(holder.bottom_right, albums.get(1).cover);
                break;

            case 3://two more
                setCover(holder.top_left, albums.get(1).cover);
                setCover(holder.top_right, albums.get(2).cover);
                setCover(holder.bottom_left, albums.get(2).cover);
                setCover(holder.bottom_right, albums.get(1).cover);
                break;
            case 4://three more
                setCover(holder.top_left, albums.get(1).cover);
                setCover(holder.top_right, albums.get(2).cover);
                setCover(holder.bottom_left, albums.get(3).cover);
                setCover(holder.bottom_right, albums.get(1).cover);
                break;

            case 5://four more
                setCover(holder.top_left, albums.get(1).cover);
                setCover(holder.top_right, albums.get(2).cover);
                setCover(holder.bottom_left, albums.get(3).cover);
                setCover(holder.bottom_right, albums.get(4).cover);
                break;

            default://five or more
                setCover(holder.top_left, albums.get(1).cover);
                setCover(holder.top_right, albums.get(2).cover);
                setCover(holder.bottom_left, albums.get(3).cover);
                setCover(holder.bottom_right, albums.get(4).cover);
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
