package com.example.ofir.goldmusic.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.ofir.goldmusic.R;

public class FragmentMusic extends Fragment
{
    ArrayAdapter adapter;
    int col;

    public FragmentMusic()
    {
    }

    public static FragmentMusic newInstance(ArrayAdapter adapter, int col, String label)
    {
        FragmentMusic fragment = new FragmentMusic();
        Bundle args = new Bundle();
        args.putString("label", label);
        fragment.setArguments(args);
        fragment.setFragment(adapter, col);

        return fragment;
    }


    public void setFragment(ArrayAdapter adapter, int col)
    {
        this.adapter = adapter;
        this.col = col;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.list_fragment, container, false);

        GridView gridView = rootView.findViewById(R.id.grid);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(col);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("savedStateLabel", getArguments().getString("label"));
    }
}
