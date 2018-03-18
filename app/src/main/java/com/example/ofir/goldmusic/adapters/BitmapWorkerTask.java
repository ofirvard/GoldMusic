package com.example.ofir.goldmusic.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.ofir.goldmusic.R;

import java.lang.ref.WeakReference;

/**
 * Created by ofir on 2/4/2018.
 */

public class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Bitmap>
{
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;

    public BitmapWorkerTask(ImageView imageView)
    {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Bitmap... params)
    {
        return params[0];
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (imageViewReference != null && bitmap != null)
        {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null)
                imageView.setImageBitmap(bitmap);
        }
    }
}
