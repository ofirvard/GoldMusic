package com.example.ofir.goldmusic;

import android.media.MediaPlayer;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ofir.goldmusic.item.Song;
import com.example.ofir.goldmusic.item.ViewActivityHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by ofir on 2/23/2018.
 */

public class MusicPlayer implements MediaPlayer.OnCompletionListener
{
    //todo each activity has its own instance of mediaPlayer, sharing media mediaPlayer and playlist
    public static ArrayList<Song> playlist = new ArrayList<>();
    static Song playing;//if null nothing is playing
    static int position = -1;//-1 mean no songs
    static MediaPlayer mediaPlayer = new MediaPlayer();
    private ViewActivityHolder holder;//activity views, so info can be changed from adapters

    public MusicPlayer(ViewGroup viewGroup)
    {
        holder = new ViewActivityHolder();
        holder.title = viewGroup.findViewById(R.id.title);
        holder.info = viewGroup.findViewById(R.id.info);
        holder.seekBar = viewGroup.findViewById(R.id.seek_bar);
        holder.currentTime = viewGroup.findViewById(R.id.current_time);
        holder.totalTime = viewGroup.findViewById(R.id.total_time);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        next();
    }

    public void updateViews()//todo add this and an update for playlist view when going back
    {
        if (playing == null)//no song
        {
            holder.title.setText(R.string.title);
            holder.info.setText(R.string.info);
        }
        else//there is a song
        {
            holder.title.setText(playing.title);
            holder.info.setText(playing.info());
        }
        updateTime();
        //todo update seek bar
    }

    public void updateTime()
    {
        if (playing == null)//no song{
        {
            holder.currentTime.setText(R.string.time);
            holder.totalTime.setText(R.string.time);
        }
        else//there is a song
        {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            int totalMinutes = (int) (totalDuration % (1000 * 60 * 60)) / (1000 * 60);
            int totalSeconds = (int) ((totalDuration % (1000 * 60 * 60)) % (1000 * 60) / 1000);
            int currentMinutes = (int) (currentDuration % (1000 * 60 * 60)) / (1000 * 60);
            int currentSeconds = (int) ((currentDuration % (1000 * 60 * 60)) % (1000 * 60) / 1000);

            holder.currentTime.setText(String.format(Locale.ENGLISH, "%02d:%02d", currentMinutes, currentSeconds));
            holder.totalTime.setText(String.format(Locale.ENGLISH, "%02d:%02d", totalMinutes, totalSeconds));
        }
    }

    public void play()//plays playing
    {
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(playing.path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            updateViews();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play(int i)//plays song from playlist at i
    {
        position = i;
        playing = playlist.get(position);
        play();
    }

    public void addToEnd(Song song)
    {
        if (playlist.isEmpty())//first song
        {
            playlist.add(song);
            playing = song;
            position = 0;
            play();
        }
        else
            playlist.add(song);
    }

    public void addToEnd(ArrayList<Song> songs)
    {
        if (playlist.isEmpty())//first song
        {
            playlist.addAll(songs);
            playing = songs.get(0);
            position = 0;
            play();
        }
        else
            playlist.addAll(songs);
    }

    public void addNext(Song song)
    {
        if (playlist.isEmpty())//first song
        {
            playlist.add(song);
            playing = song;
            position = 0;
            play();
        }
        else
            playlist.add(position + 1, song);
    }

    public void addNext(ArrayList<Song> songs)
    {
        if (playlist.isEmpty())//first song
        {
            playlist.addAll(songs);
            playing = songs.get(0);
            position = 0;
            play();
        }
        else
            playlist.addAll(position + 1, songs);
    }

    public void remove(int i)
    {
        if (playing != null)
            if (playlist.size() > 1)//more then one song
            {
                if (position == i)//if removing playing, move to next
                    next();
                if (position > i)//if removing song before playing
                    position--;
                playlist.remove(i);
            }
            else//there is only one song
                clear();
        updateViews();
    }

    public void next()
    {
        if (playing != null)//checks that a song is playing
            if (position < playlist.size() - 1)//its not the last
                play(position + 1);
            else if (position == playlist.size() - 1)//move to start
                play(0);
        updateViews();
    }

    public void previous()
    {
        if (playing != null)//checks that a song is playing
            if (position > 0)//its not the first
                play(position - 1);
            else if (position == 0)//it is the first
                play();
        updateViews();
    }

    public void randomize()
    {
        if (playing != null)
        {
            playlist.remove(position);
            Collections.shuffle(playlist);
            playlist.add(0, playing);
            position = 0;
        }
    }

    public void clear()
    {
        mediaPlayer.stop();
        playlist.clear();
        position = -1;
        playing = null;
    }

    public void pauseOrUnpause()
    {
        if (playing != null)
        {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            else
                mediaPlayer.start();
        }
    }
}
