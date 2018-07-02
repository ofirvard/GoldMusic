package com.example.ofir.goldmusic.tabs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.ofir.goldmusic.Library;
import com.example.ofir.goldmusic.R;
import com.example.ofir.goldmusic.adapters.ArtistAdapter;

public class MainTabbedActivity extends AppCompatActivity
{
    CustomViewPager pager;
    TabAdapter tabAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);
        gridView = findViewById(R.id.grid);

        // Instantiate a ViewPager and a PagerAdapter.
        pager = findViewById(R.id.pager);
        pager.setPagingEnabled(false);
//        pagerAdapter = new TabAdapter(getSupportFragmentManager(), pager);
        tabAdapter = new TabAdapter(getApplicationContext(), pager);
        pager.setAdapter(tabAdapter);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)//read permission
        {
            new Library(this);

            tabAdapter.add(new ArtistAdapter(getApplicationContext(), Library.artists, tabAdapter), 2);
            pager.setCurrentItem(0);
//            pagerAdapter.addFragment(new ArtistAdapter(getApplicationContext(), Library.artists, pagerAdapter), 2);
        }
    }

    @Override
    public void onBackPressed()
    {
        if (tabAdapter.getCount() > 0)
        {
//            pagerAdapter.removeLastFragment();
            tabAdapter.remove(pager.getCurrentItem());
            tabAdapter.back();
        }
        else
            super.onBackPressed();
    }
}
