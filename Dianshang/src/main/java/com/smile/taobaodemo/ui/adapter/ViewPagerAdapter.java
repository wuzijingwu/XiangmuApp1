package com.smile.taobaodemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smile.taobaodemo.ui.fragment.NavFenleiFragment;
import com.smile.taobaodemo.ui.fragment.NavGowuFragment;
import com.smile.taobaodemo.ui.fragment.NavHomeFragment;
import com.smile.taobaodemo.ui.fragment.NavMyFragment;
import com.smile.taobaodemo.ui.fragment.NavWeitaoFragment;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(NavHomeFragment.newInstance());
        fragments.add(NavWeitaoFragment.newInstance());
        fragments.add(NavFenleiFragment.newInstance());
        fragments.add(NavGowuFragment.newInstance());
        fragments.add(NavMyFragment.newInstance());

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
