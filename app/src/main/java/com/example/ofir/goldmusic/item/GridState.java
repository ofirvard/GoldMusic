package com.example.ofir.goldmusic.item;

import android.os.Parcelable;
import android.widget.ListAdapter;

/**
 * Created by ofir on 25-Jun-18.
 */

public class GridState
{
    ListAdapter adapter;
    Parcelable parcelable;

    public GridState(ListAdapter adapter, Parcelable parcelable)
    {
        this.adapter = adapter;
        this.parcelable = parcelable;
    }
}
