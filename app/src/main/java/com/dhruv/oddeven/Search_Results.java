package com.dhruv.oddeven;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

public class Search_Results extends AppCompatActivity {

    private MaterialViewPager mViewPager;

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    Toolbar toolbar;

    public static int NUM_PAGES = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__results);


       // toolbar = (Toolbar) findViewById(R.id.app_bar_results);
        //setSupportActionBar(toolbar);
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mPager = mViewPager.getViewPager();

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);;
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());




        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {

                switch(page)
                {
                    case 0:
                        return HeaderDesign.fromColorAndDrawable(getResources().getColor(R.color.accent),getResources().getDrawable(R.drawable.header4));

                    case 1:
                        return HeaderDesign.fromColorAndDrawable(getResources().getColor(R.color.green),getResources().getDrawable(R.drawable.header2));
                }


                return null;
            }
        });

        Toolbar toolbar = mViewPager.getToolbar();

        /*if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }*/




    }

    @Override
     public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return "Odd Results";
                else
            return "Even Results";

        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return new Odd_Result_Frag();
            else if (position==1)
                return new Even_Result_Frag();

            return new Odd_Result_Frag();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
