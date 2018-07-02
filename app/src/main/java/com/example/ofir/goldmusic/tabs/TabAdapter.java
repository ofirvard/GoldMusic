package com.example.ofir.goldmusic.tabs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.ofir.goldmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ofir on 25-Jun-18.
 */

public class TabAdapter extends PagerAdapter
{
    final String TAG = "TabAdapter";
    //    ArrayList<FragmentMusic> tabs = new ArrayList<>();
    CustomViewPager pager;
    ArrayList<FragInfo> pages = new ArrayList<>();
    LayoutInflater li;
    List<View> views = new ArrayList<>();


    TabAdapter(Context context, CustomViewPager pager)
    {
        this.li = LayoutInflater.from(context);
        this.pager = pager;
    }

    public void next()
    {
        pager.setCurrentItem(pager.getCurrentItem() + 1);
    }

    public void back()
    {
        pager.setCurrentItem(pager.getCurrentItem() - 1);
    }

    public void add(ArrayAdapter adapter, int col)
    {
//        FragmentMusic fragment = new FragmentMusic();
//        fragment.setFragment(adapter, col);
//        tabs.add(fragment);
        pages.add(new FragInfo(adapter, col));
        notifyDataSetChanged();
    }

    public void remove(int position)
    {
//        tabs.remove(position);
        pages.remove(position);
        views.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View view = li.inflate(R.layout.list_fragment, container, false);
        FragInfo info = pages.get(position);
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(info.adapter);
        gridView.setNumColumns(info.col);
        view.setTag(info);
        // Add the newly created View to the ViewPager
        container.addView(view);
        views.add(position, view);
        Log.i(TAG, "instantiateItem() [position: " + position + "]" + " childCount:" + container.getChildCount());
        // Return the View
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
        Log.i(TAG, "destroyItem() [position: " + position + "]" + " childCount:" + container.getChildCount());
    }

    @Override
    public int getCount()
    {
//        return tabs.size();
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return object == view;
    }

    @Override
    public int getItemPosition(@NonNull Object object)
    {
        if (views.contains((View) object))
            return views.indexOf((View) object);
        else
            return POSITION_NONE;
    }

    private class FragInfo
    {
        ArrayAdapter adapter;
        int col;

        FragInfo(ArrayAdapter adapter, int col)
        {
            this.adapter = adapter;
            this.col = col;
        }
    }
}
