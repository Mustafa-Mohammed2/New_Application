package com.example.new_application;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class RelativeFragment {

    String title;
    Fragment fragment;

    public RelativeFragment(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
