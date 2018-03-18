package com.example.ofir.goldmusic;

import android.os.Handler;
import android.widget.TextView;

/**
 * Created by ofir on 2/28/2018.
 */

public class TimeUpdater implements Runnable
{
    static private MusicPlayer musicPlayer;
    static private boolean running = false;
    static private final Handler handler = new Handler();
    static private TimeUpdater timeUpdater = new TimeUpdater();

    public static void setMusicPlayer(MusicPlayer mp)
    {
        musicPlayer = mp;
        if (!running)
        {
            running = true;
            timeUpdater.run();
        }
    }

    @Override
    public void run()
    {
        if (MusicPlayer.mediaPlayer != null)
            musicPlayer.updateTime();
        handler.postDelayed(this, 1000);
    }
}
