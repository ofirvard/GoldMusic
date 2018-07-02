package com.example.ofir.goldmusic;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

/**
 * Created by ofir on 24-Jun-18.
 */

public class ImageLoader
{
    static public Bitmap getAlbumart(Context context, Long album_id)
    {
        Bitmap albumArtBitMap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try
        {
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                albumArtBitMap = BitmapFactory.decodeFileDescriptor(fd, null,
                        options);
                pfd = null;
                fd = null;
            }
        }
        catch (Error ee)
        {
        }
        catch (Exception e)
        {
        }

        if (null != albumArtBitMap)
        {
            return albumArtBitMap;
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.question_mark_low_res);
    }


//    public Bitmap getDefaultAlbumArtEfficiently(Resources resource) {
//
//        if (defaultBitmapArt == null) {
//
//            defaultBitmapArt = decodeSampledBitmapFromResource(resource,
//                    R.drawable.default_album_art, UtilFunctions
//                            .getUtilFunctions().dpToPixels(85, resource),
//                    UtilFunctions.getUtilFunctions().dpToPixels(85, resource));
//
//        }
//        return defaultBitmapArt;
//    }
}
