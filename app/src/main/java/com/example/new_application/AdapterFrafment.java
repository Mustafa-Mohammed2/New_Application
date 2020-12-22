package com.example.new_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class AdapterFrafment extends FragmentStatePagerAdapter {

    ArrayList<RelativeFragment> relative;

    public AdapterFrafment(@NonNull FragmentManager fm, ArrayList<RelativeFragment> relative) {
        super(fm);
        this.relative=relative;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return relative.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return relative.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return relative.get(position).getTitle();
    }
}
